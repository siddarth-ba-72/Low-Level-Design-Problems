package library_management_system.records;

import library_management_system.models.Book;
import library_management_system.models.BookCopy;
import library_management_system.models.BookCopyStatus;

import java.util.*;

public class BooksInventory {

    List<Book> books;

    private BooksInventory() {
        this.books = new ArrayList<>();
    }

    public static BooksInventory getInstance() {
        return BooksInventoryHolder.INSTANCE;
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public Book getBookById(int bookId) {
        return books.stream()
                .filter(book -> book.getBookId() == bookId)
                .findFirst()
                .orElse(null);
    }

    public BookCopy getBookCopyByBookId(int bookId) {
        Book book = getBookById(bookId);
        if (book == null) {
            return null;
        }
        return book.getCopies().stream()
                .filter(copy -> copy.getBookCopyStatus() == BookCopyStatus.AVAILABLE)
                .findFirst()
                .orElse(null);
    }

    public void addBook(String title, String author, String subject, Date publicationDate, int edition) {
        int bookId = books.size() + 1; // Simulating bookId generation
        Book newBook = new Book(bookId, title, author, subject, publicationDate, edition, new ArrayList<>());
        books.add(newBook);
    }

    public void addBookCopy(int bookId) {
        Book book = getBookById(bookId);
        if (book != null) {
            List<BookCopy> copies = book.getCopies();
            List<BookCopy> newCopies = new ArrayList<>(copies);
            newCopies.add(new BookCopy(UUID.randomUUID().toString(), bookId));
            book.setCopies(newCopies);
        }
    }

    public void updateBook(int bookId, String title, String author, String subject, Date publicationDate, int edition) {
        books.stream()
                .filter(book -> book.getBookId() == bookId)
                .findFirst()
                .ifPresent(book -> {
                    book.setTitle(title);
                    book.setAuthor(author);
                    book.setSubject(subject);
                    book.setPublicationDate(publicationDate);
                    book.setEdition(edition);
                });
    }

    public void removeBook(String bookId) {
        books.removeIf(book -> String.valueOf(book.getBookId()).equals(bookId));
    }

    private static class BooksInventoryHolder {
        private static final BooksInventory INSTANCE = new BooksInventory();
    }

}
