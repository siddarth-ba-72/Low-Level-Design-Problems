package library_management_system;

import library_management_system.actors.Customer;
import library_management_system.actors.Librarian;
import library_management_system.models.Book;
import library_management_system.models.BookCopy;
import library_management_system.models.MembershipType;
import library_management_system.notification.AlertOverdueNotification;
import library_management_system.notification.CheckBookAvailabilityNotification;
import library_management_system.observer.AvailableReservationObserver;
import library_management_system.observer.OverdueReservationObserver;
import library_management_system.records.BooksInventory;
import library_management_system.utils.BookSearchCriteria;

import java.util.Date;
import java.util.List;

public class LibraryManagementSystemClient {

    public static void main(String[] args) {

        // ─────────────────────────────────────────────
        // 1. Bootstrap the system
        // ─────────────────────────────────────────────
        LibraryManagementSystem system = new LibraryManagementSystem();

        // Wire up observers with their notification services
        AvailableReservationObserver availabilityObserver =
                new AvailableReservationObserver(new CheckBookAvailabilityNotification());
        OverdueReservationObserver overdueObserver =
                new OverdueReservationObserver(new AlertOverdueNotification());

        // ─────────────────────────────────────────────
        // 2. Create actors
        // ─────────────────────────────────────────────
        Librarian librarian = new Librarian(system);

        Customer alice = new Customer(101, system);
        Customer bob = new Customer(102, system);

        // ─────────────────────────────────────────────
        // 3. Librarian sets up the library
        // ─────────────────────────────────────────────
        System.out.println("\n===== LIBRARIAN: Setting up inventory =====");

        librarian.addNewBook("Clean Code", "Robert C. Martin", "Software Engineering",
                new Date(2008 - 1900, 0, 1), 1);   // bookId = 1

        librarian.addNewBook("Design Patterns", "Gang of Four", "Software Engineering",
                new Date(1994 - 1900, 9, 21), 1);  // bookId = 2

        librarian.addNewBook("The Pragmatic Programmer", "Andrew Hunt", "Software Engineering",
                new Date(1999 - 1900, 9, 20), 2);  // bookId = 3

        // Add 2 copies of "Clean Code" so we can demo queuing
        librarian.addBookCopy(1);  // copy 1 of Clean Code
        librarian.addBookCopy(1);  // copy 2 of Clean Code
        librarian.addBookCopy(2);  // copy 1 of Design Patterns
        librarian.addBookCopy(3);  // copy 1 of Pragmatic Programmer

        // ─────────────────────────────────────────────
        // 4. Librarian issues library cards
        // ─────────────────────────────────────────────
        System.out.println("\n===== LIBRARIAN: Issuing library cards =====");
        librarian.issueNewLibraryCard(alice.getCustomerId(), MembershipType.ANNUAL);
        librarian.issueNewLibraryCard(bob.getCustomerId(), MembershipType.MONTHLY);

        // ─────────────────────────────────────────────
        // 5. Customers search for books
        // ─────────────────────────────────────────────
        System.out.println("\n===== SEARCH =====");

        BookSearchCriteria searchByTitle = BookSearchCriteria.builder()
                .title("Clean Code")
                .build();
        Book found = alice.searchBook(searchByTitle);
        System.out.println("Alice searched by title → Found: " + (found != null ? found.getTitle() : "Not found"));

        BookSearchCriteria searchByAuthor = BookSearchCriteria.builder()
                .author("Gang of Four")
                .build();
        Book found2 = bob.searchBook(searchByAuthor);
        System.out.println("Bob searched by author → Found: " + (found2 != null ? found2.getTitle() : "Not found"));

        // ─────────────────────────────────────────────
        // 6. Customers borrow books
        // ─────────────────────────────────────────────
        System.out.println("\n===== BORROW =====");

        alice.borrowBook(1);  // Alice borrows Clean Code (copy 1 → ISSUED)
        bob.borrowBook(1);    // Bob borrows Clean Code (copy 2 → ISSUED)

        alice.borrowBook(2);  // Alice borrows Design Patterns
        bob.borrowBook(3);    // Bob borrows Pragmatic Programmer

        // ─────────────────────────────────────────────
        // 7. Show current inventory status
        // ─────────────────────────────────────────────
        System.out.println("\n===== INVENTORY STATUS =====");
        printInventory();

        // ─────────────────────────────────────────────
        // 8. Demo: QUEUED reservation (all copies issued)
        // ─────────────────────────────────────────────
        System.out.println("\n===== QUEUE DEMO =====");
        // A third customer tries to borrow Clean Code — both copies are ISSUED
        Customer charlie = new Customer(103, system);
        librarian.issueNewLibraryCard(charlie.getCustomerId(), MembershipType.ANNUAL);
        charlie.borrowBook(1);  // → should be QUEUED since both copies are out
        System.out.println("Charlie tried to borrow Clean Code — should be QUEUED");

        // ─────────────────────────────────────────────
        // 9. Return a book — triggers availability observer
        // ─────────────────────────────────────────────
        System.out.println("\n===== RETURN =====");

        // Fetch Alice's Clean Code copy ISBN to return it
        BooksInventory inventory = BooksInventory.getInstance();
        String aliceCleanCodeIsbn = inventory.getBooks().stream()
                .filter(b -> b.getBookId() == 1)
                .flatMap(b -> b.getCopies().stream())
                .filter(c -> c.getBookCopyStatus().name().equals("ISSUED"))
                .map(BookCopy::getIsbnNumber)
                .findFirst()
                .orElse(null);

        if (aliceCleanCodeIsbn != null) {
            alice.returnBook(aliceCleanCodeIsbn);
            System.out.println("Alice returned Clean Code (ISBN: " + aliceCleanCodeIsbn + ")");

            // Trigger availability observer — Charlie's queue entry should now be notified
            System.out.println("\n--- Availability Observer triggered ---");
            availabilityObserver.observe();
        }

        // ─────────────────────────────────────────────
        // 10. Renew a book
        // ─────────────────────────────────────────────
        System.out.println("\n===== RENEWAL =====");
        // Bob still has Design Patterns — find its ISBN and renew
        String bobDesignPatternsIsbn = inventory.getBooks().stream()
                .filter(b -> b.getBookId() == 2)
                .flatMap(b -> b.getCopies().stream())
                .filter(c -> c.getBookCopyStatus().name().equals("ISSUED"))
                .map(BookCopy::getIsbnNumber)
                .findFirst()
                .orElse(null);

        if (bobDesignPatternsIsbn != null) {
            bob.renewBook(bobDesignPatternsIsbn);
            System.out.println("Bob renewed Design Patterns (ISBN: " + bobDesignPatternsIsbn + ")");
        }

        // ─────────────────────────────────────────────
        // 11. Trigger overdue observer (simulated)
        // ─────────────────────────────────────────────
        System.out.println("\n===== OVERDUE OBSERVER (simulated - no records overdue yet) =====");
        overdueObserver.observe();
        System.out.println("Overdue check complete.");

        // ─────────────────────────────────────────────
        // 12. Librarian management operations
        // ─────────────────────────────────────────────
        System.out.println("\n===== LIBRARIAN: Book Management =====");

        librarian.updateBook(3, "The Pragmatic Programmer", "Andrew Hunt & David Thomas",
                "Software Engineering", new Date(1999 - 1900, 9, 20), 20);

        librarian.addBookCopy(2);  // Add another copy of Design Patterns

        librarian.renewLibraryCard(alice.getCustomerId(), MembershipType.LIFETIME);

        System.out.println("\n===== FINAL INVENTORY STATUS =====");
        printInventory();
    }

    private static void printInventory() {
        BooksInventory inventory = BooksInventory.getInstance();
        List<Book> books = inventory.getBooks();
        books.forEach(book -> {
            System.out.println("  [Book " + book.getBookId() + "] " + book.getTitle()
                    + " by " + book.getAuthor()
                    + " | Copies: " + book.getCopies().size());
            book.getCopies().forEach(copy ->
                    System.out.println("    ISBN: " + copy.getIsbnNumber()
                            + " → " + copy.getBookCopyStatus()));
        });
    }

}

