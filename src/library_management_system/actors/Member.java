package library_management_system.actors;

public interface Member extends User {

    void borrowBook(int bookId);

    void returnBook(String isbnNumber);

    void renewBook(String isbnNumber);

}
