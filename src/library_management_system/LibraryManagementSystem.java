package library_management_system;

import library_management_system.models.*;
import library_management_system.records.BooksInventory;
import library_management_system.records.ReservationRecords;
import library_management_system.records.UserCardRecords;

import java.util.Date;
import java.util.UUID;

public class LibraryManagementSystem {

    private final BooksInventory booksInventory = BooksInventory.getInstance();
    private final ReservationRecords reservationRecords = ReservationRecords.getInstance();
    private final UserCardRecords userCardRecords = UserCardRecords.getInstance();

    public void handleBookBorrow(int customerId, int bookId) {
        LibraryCard userCard = userCardRecords.getLibraryCardsHolders().stream()
                .filter(card -> card.getCustomerId() == customerId)
                .filter(card -> card.getExpiryDate().after(new Date()))
                .findFirst()
                .orElse(null);
        if (userCard == null) {
            System.out.println("User does not have a valid library card.");
            return;
        }
        int currentReservations = reservationRecords.getBookReservationRecords().stream()
                .filter(record -> record.getCustomerId() == customerId)
                .filter(record -> record.getBookReservationStatus() == BookReservationStatus.BORROWED)
                .distinct()
                .toArray().length;
        if (currentReservations < 10) {
            Book book = booksInventory.getBookById(bookId);
            if (book != null) {
                BookCopy availableCopy = book.getCopies().stream()
                        .filter(copy -> copy.getBookCopyStatus() == BookCopyStatus.AVAILABLE)
                        .findFirst()
                        .orElse(null);
                if (availableCopy != null) {
                    availableCopy.setBookCopyStatus(BookCopyStatus.ISSUED);
                }
                BookReservation reservationRecord = new BookReservation(
                        UUID.randomUUID().toString(),
                        availableCopy != null ? availableCopy.getIsbnNumber() : null,
                        book.getBookId(),
                        customerId,
                        availableCopy != null ? BookReservationStatus.BORROWED : BookReservationStatus.QUEUED,
                        new Date()
                );
                reservationRecords.addReservationRecord(reservationRecord);
            } else {
                System.out.println("Book is not available in the inventory.");
            }
        } else {
            System.out.println("User has reached the maximum number of reservations allowed.");
        }
    }

    public void handleBookReturn(int customerId, String isbnNumber) {
        BookCopy bookCopy = booksInventory.getBooks().stream()
                .flatMap(book -> book.getCopies().stream())
                .filter(copy -> copy.getIsbnNumber().equals(isbnNumber))
                .findFirst()
                .orElse(null);
        if (bookCopy == null) {
            System.out.println("Book copy with the given ISBN number not found in the system.");
            return;
        }
        if (bookCopy.getBookCopyStatus() != BookCopyStatus.ISSUED) {
            System.out.println("This book copy is not currently issued.");
            return;
        }
        BookReservation reservationRecord = reservationRecords.getBookReservationRecords().stream()
                .filter(record -> record.getIsbnNumber().equals(isbnNumber) && record.getCustomerId() == customerId)
                .filter(record -> record.getBookReservationStatus() == BookReservationStatus.BORROWED)
                .findFirst()
                .orElse(null);
        if (reservationRecord != null) {
            reservationRecords.removeReservationRecord(reservationRecord);
            bookCopy.setBookCopyStatus(BookCopyStatus.AVAILABLE);
        } else {
            System.out.println("No active reservation found for this book copy and user.");
        }
    }

    public void handleBookRenewal(int customer, String isbnNumber) {
        BookCopy bookCopy = booksInventory.getBooks().stream()
                .flatMap(book -> book.getCopies().stream())
                .filter(copy -> copy.getIsbnNumber().equals(isbnNumber))
                .findFirst()
                .orElse(null);
        if (bookCopy == null) {
            System.out.println("Book copy with the given ISBN number not found in the system.");
            return;
        }
        BookReservation reservationRecord = reservationRecords.getBookReservationRecords().stream()
                .filter(record -> record.getBookReservationStatus() == BookReservationStatus.BORROWED &&
                        record.getIsbnNumber().equals(isbnNumber) &&
                        record.getCustomerId() == customer
                )
                .filter(record -> record.getBookReservationStatus() == BookReservationStatus.BORROWED)
                .findFirst()
                .orElse(null);
        if (reservationRecord != null) {
            reservationRecord.setReturnDate(new Date(reservationRecord.getReturnDate().getTime() + 15L * 24 * 60 * 60 * 1000));
        } else {
            System.out.println("No active reservation found for this book copy and user.");
        }
    }

    public void handleIssueNewLibraryCard(int customerId, MembershipType membershipType) {
        Date expiryDate;
        switch (membershipType) {
            case MONTHLY -> expiryDate = new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000); // 1 month
            case ANNUAL -> expiryDate = new Date(System.currentTimeMillis() + 365L * 24 * 60 * 60 * 1000); // 1 year
            case LIFETIME -> expiryDate = new Date(Long.MAX_VALUE); // Lifetime
            default -> throw new IllegalArgumentException("Invalid membership type");
        }
        LibraryCard newCard = new LibraryCard(
                UUID.randomUUID().toString(),
                customerId,
                membershipType,
                expiryDate
        );
        userCardRecords.addLibraryCardHolder(newCard);
    }

    public void handleRenewLibraryCard(int customerId, MembershipType membershipType) {
        LibraryCard existingCard = userCardRecords.getLibraryCardsHolders().stream()
                .filter(card -> card.getCustomerId() == customerId)
                .findFirst()
                .orElse(null);
        if (existingCard != null) {
            Date newExpiryDate;
            switch (membershipType) {
                case MONTHLY -> newExpiryDate = new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000); // 1 month
                case ANNUAL -> newExpiryDate = new Date(System.currentTimeMillis() + 365L * 24 * 60 * 60 * 1000); // 1 year
                case LIFETIME -> newExpiryDate = new Date(Long.MAX_VALUE); // Lifetime
                default -> throw new IllegalArgumentException("Invalid membership type");
            }
            existingCard.setMembershipType(membershipType);
            existingCard.setExpiryDate(newExpiryDate);
        } else {
            System.out.println("No existing library card found for the user.");
        }
    }

    public void handleAddNewBook(String title, String author, String subject, Date publicationDate, int edition) {
        booksInventory.addBook(title, author, subject, publicationDate, edition);
    }

    public void handleAddBookCopy(int bookId) {
        booksInventory.addBookCopy(bookId);
    }

    public void handleUpdateBook(int bookId, String title, String author, String subject, Date publicationDate, int edition) {
        booksInventory.updateBook(bookId, title, author, subject, publicationDate, edition);
    }

    public void handleRemoveBook(String bookId) {
        booksInventory.removeBook(bookId);
    }

}
