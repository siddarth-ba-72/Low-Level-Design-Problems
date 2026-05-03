package library_management_system.models;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Book {

    private int bookId;
    private String title;
    private String author;
    private String subject;
    private Date publicationDate;
    private int edition;
    private List<BookCopy> copies;

    public Book(int bookId, String title, String author, String subject, Date publicationDate, int edition, List<BookCopy> copies) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.subject = subject;
        this.publicationDate = publicationDate;
        this.edition = edition;
        this.copies = copies;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public List<BookCopy> getCopies() {
        return Collections.unmodifiableList(copies);
    }

    public void setCopies(List<BookCopy> copies) {
        this.copies = copies;
    }

}
