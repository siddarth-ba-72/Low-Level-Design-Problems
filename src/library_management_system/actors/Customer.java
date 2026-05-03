package library_management_system.actors;

import library_management_system.LibraryManagementSystem;

public class Customer extends AbstractUser implements Member {

    private final int customerId;
    private final LibraryManagementSystem system;

    public Customer(int customerId, LibraryManagementSystem system) {
        this.system = system;
        this.customerId = customerId;
    }

    @Override
    public void borrowBook(int bookId) {
        system.handleBookBorrow(this.customerId, bookId);
    }

    @Override
    public void returnBook(String isbnNumber) {
        system.handleBookReturn(this.customerId, isbnNumber);
    }

    @Override
    public void renewBook(String isbnNumber) {
        system.handleBookRenewal(this.customerId ,isbnNumber);
    }

    public int getCustomerId() {
        return customerId;
    }

}
