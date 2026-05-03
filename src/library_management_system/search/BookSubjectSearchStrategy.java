package library_management_system.search;

import library_management_system.models.Book;
import library_management_system.utils.BookSearchCriteria;

public class BookSubjectSearchStrategy extends AbstractBookSearchStrategy {

    @Override
    public Book getBook(BookSearchCriteria criteria) {
        return getBooks().stream()
                .filter(book -> book.getSubject().equalsIgnoreCase(criteria.getSubject()))
                .findFirst()
                .orElse(null);
    }
}
