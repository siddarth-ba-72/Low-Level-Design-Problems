package library_management_system.records;

import library_management_system.models.LibraryCard;

import java.util.ArrayList;
import java.util.List;

public class UserCardRecords {

    private final List<LibraryCard> libraryCardsHolders;

    private UserCardRecords() {
        this.libraryCardsHolders = new ArrayList<>();
    }

    public static UserCardRecords getInstance() {
        return UserCardRecordsHolder.INSTANCE;
    }

    public List<LibraryCard> getLibraryCardsHolders() {
        return new ArrayList<>(libraryCardsHolders);
    }

    public void addLibraryCardHolder(LibraryCard card) {
        libraryCardsHolders.add(card);
    }

    private static class UserCardRecordsHolder {
        private static final UserCardRecords INSTANCE = new UserCardRecords();
    }

}
