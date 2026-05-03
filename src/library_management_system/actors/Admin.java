package library_management_system.actors;

import library_management_system.models.MembershipType;

import java.util.Date;

public interface Admin extends User {

    void addNewBook(String title, String author, String subject, Date publicationDate, int edition);

    void addBookCopy(int bookId);

    void updateBook(int bookId, String title, String author, String subject, Date publicationDate, int edition);

    void removeBook(String bookId);

    void issueNewLibraryCard(int customerId, MembershipType membershipType);

    void renewLibraryCard(int customerId, MembershipType membershipType);

}
