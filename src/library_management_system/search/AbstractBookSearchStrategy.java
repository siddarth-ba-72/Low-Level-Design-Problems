package library_management_system.search;

import library_management_system.models.Book;
import library_management_system.records.BooksInventory;

import java.util.List;

public abstract class AbstractBookSearchStrategy implements BookSearchStrategy {

    private final BooksInventory booksInventory = BooksInventory.getInstance();

    protected List<Book> getBooks() {
        return booksInventory.getBooks();
    }

}
