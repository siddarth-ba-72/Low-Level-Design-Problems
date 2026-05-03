package library_management_system.search;

import library_management_system.utils.BookSearchCriteria;

public class BookSearchStrategyFactory {
    public static BookSearchStrategy getSearchStrategy(BookSearchCriteria criteria) {
        if (criteria.getTitle() != null) {
            return new BookTitleSearchStrategy();
        } else if (criteria.getAuthor() != null) {
            return new BookAuthorSearchStrategy();
        } else if (criteria.getSubject() != null) {
            return new BookSubjectSearchStrategy();
        } else if (criteria.getPublicationDate() != null) {
            return new BookPublicationDateSearchStrategy();
        } else {
            throw new IllegalArgumentException("Invalid search criteria");
        }
    }
}
