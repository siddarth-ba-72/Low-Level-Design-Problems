package library_management_system.actors;

import library_management_system.LibraryManagementSystem;
import library_management_system.models.MembershipType;

import java.util.Date;

public class Librarian extends AbstractUser implements Admin {

    private final LibraryManagementSystem system;

    public Librarian(LibraryManagementSystem system) {
        this.system = system;
    }

    @Override
    public void addNewBook(String title, String author, String subject, Date publicationDate, int edition) {
        system.handleAddNewBook(title, author, subject, publicationDate, edition);
        System.out.println("Librarian added a new book: " + title);
    }

    @Override
    public void addBookCopy(int bookId) {
        system.handleAddBookCopy(bookId);
        System.out.println("Librarian added a copy for book with ID: " + bookId);
    }

    @Override
    public void updateBook(int bookId, String title, String author, String subject, Date publicationDate, int edition) {
        system.handleUpdateBook(bookId, title, author, subject, publicationDate, edition);
        System.out.println("Librarian updated book with ID: " + bookId);
    }

    @Override
    public void removeBook(String bookId) {
        system.handleRemoveBook(bookId);
        System.out.println("Librarian removed book with ID: " + bookId);
    }

    @Override
    public void issueNewLibraryCard(int customerId, MembershipType membershipType) {
        system.handleIssueNewLibraryCard(customerId, membershipType);
        System.out.println("Librarian issued a new library card for customer: " + membershipType);
    }

    @Override
    public void renewLibraryCard(int customerId, MembershipType membershipType) {
        system.handleRenewLibraryCard(customerId, membershipType);
        System.out.println("Librarian renewed library card for customer: " + membershipType);
    }

}
