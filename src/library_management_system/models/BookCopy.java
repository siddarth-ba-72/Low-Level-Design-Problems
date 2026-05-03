package library_management_system.models;

public class BookCopy {

    private String isbnNumber;
    private int bookId;
    private BookCopyStatus bookCopyStatus;

    public BookCopy(String isbnNumber, int bookId) {
        this.isbnNumber = isbnNumber;
        this.bookId = bookId;
        this.bookCopyStatus = BookCopyStatus.AVAILABLE;
    }

    public String getIsbnNumber() {
        return isbnNumber;
    }

    public void setIsbnNumber(String isbnNumber) {
        this.isbnNumber = isbnNumber;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public BookCopyStatus getBookCopyStatus() {
        return bookCopyStatus;
    }

    public void setBookCopyStatus(BookCopyStatus bookCopyStatus) {
        this.bookCopyStatus = bookCopyStatus;
    }

}
