package library_management_system.actors;

import library_management_system.models.Book;
import library_management_system.search.BookSearchStrategy;
import library_management_system.search.BookSearchStrategyFactory;
import library_management_system.utils.BookSearchCriteria;

public abstract class AbstractUser implements User {

    @Override
    public Book searchBook(BookSearchCriteria criteria) {
        BookSearchStrategy searchStrategy = BookSearchStrategyFactory.getSearchStrategy(criteria);
        return searchStrategy.getBook(criteria);
    }

}
