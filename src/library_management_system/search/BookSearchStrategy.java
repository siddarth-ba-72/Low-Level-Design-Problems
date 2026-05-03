package library_management_system.search;

import library_management_system.models.Book;
import library_management_system.utils.BookSearchCriteria;

public interface BookSearchStrategy {
    Book getBook(BookSearchCriteria criteria);
}
