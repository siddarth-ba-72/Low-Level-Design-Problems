package library_management_system.models;

import java.util.Date;

public class BookReservation {

    private final String reservationId;
    private final String isbnNumber;
    private final int bookId;
    private final int customerId;
    private final Date issueDate;
    private Date returnDate;
    private BookReservationStatus bookReservationStatus;

    public BookReservation(
            String reservationId, String isbnNumber, int bookId, int customerId,
            BookReservationStatus bookReservationStatus, Date issueDate) {
        this.reservationId = reservationId;
        this.isbnNumber = isbnNumber;
        this.bookId = bookId;
        this.customerId = customerId;
        this.issueDate = issueDate;
        this.returnDate = new Date(issueDate.getTime() + 15L * 24 * 60 * 60 * 1000);
        this.bookReservationStatus = bookReservationStatus;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getIsbnNumber() {
        return isbnNumber;
    }

    public int getBookId() {
        return bookId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public BookReservationStatus getBookReservationStatus() {
        return bookReservationStatus;
    }

    public void setBookReservationStatus(BookReservationStatus bookReservationStatus) {
        this.bookReservationStatus = bookReservationStatus;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

}
