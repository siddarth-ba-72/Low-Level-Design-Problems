package library_management_system.search;

import library_management_system.models.Book;
import library_management_system.utils.BookSearchCriteria;

public class BookPublicationDateSearchStrategy extends AbstractBookSearchStrategy {

    @Override
    public Book getBook(BookSearchCriteria criteria) {
        return getBooks().stream()
                .filter(book -> book.getPublicationDate().equals(criteria.getPublicationDate()))
                .findFirst()
                .orElse(null);
    }
}
