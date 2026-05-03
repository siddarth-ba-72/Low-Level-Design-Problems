package library_management_system.actors;

import library_management_system.models.Book;
import library_management_system.utils.BookSearchCriteria;

public interface User {
    Book searchBook(BookSearchCriteria criteria);
}
