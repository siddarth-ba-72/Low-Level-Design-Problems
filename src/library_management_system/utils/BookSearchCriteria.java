package library_management_system.utils;

import java.util.Date;

public class BookSearchCriteria {

    private final String title;
    private final String author;
    private final String subject;
    private final Date publicationDate;

    // Private constructor — only Builder can instantiate
    private BookSearchCriteria(Builder builder) {
        this.title = builder.title;
        this.author = builder.author;
        this.subject = builder.subject;
        this.publicationDate = builder.publicationDate;
    }

    // Static entry point — no `new Builder()` needed
    public static Builder builder() {
        return new Builder();
    }

    public String getTitle()             { return title; }
    public String getAuthor()            { return author; }
    public String getSubject()           { return subject; }
    public Date getPublicationDate()     { return publicationDate; }

    // Static nested Builder
    public static class Builder {

        private String title;
        private String author;
        private String subject;
        private Date publicationDate;

        private Builder() {}

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder publicationDate(Date publicationDate) {
            this.publicationDate = publicationDate;
            return this;
        }

        public BookSearchCriteria build() {
            return new BookSearchCriteria(this);
        }
    }
}