package library_management_system.observer;

import library_management_system.models.BookCopy;
import library_management_system.models.BookCopyStatus;
import library_management_system.models.BookReservation;
import library_management_system.notification.ReservationNotification;
import library_management_system.records.BooksInventory;
import library_management_system.records.ReservationRecords;

import java.util.ArrayList;
import java.util.List;

public class AvailableReservationObserver implements ReservationObserver {

    private final ReservationNotification notificationService;
    private final ReservationRecords reservationRecords = ReservationRecords.getInstance();
    private final BooksInventory booksInventory = BooksInventory.getInstance();

    public AvailableReservationObserver(ReservationNotification reservationNotification) {
        this.notificationService = reservationNotification;
    }

    @Override
    public void observe() {
        List<BookReservation> queuedReservations = reservationRecords.getQueuedReservations();
        List<BookReservation> availableReservations = new ArrayList<>();
        queuedReservations.forEach(res -> {
            BookCopy bookCopy = booksInventory.getBookCopyByBookId(res.getBookId());
            if (bookCopy != null && bookCopy.getBookCopyStatus() == BookCopyStatus.AVAILABLE) {
                availableReservations.add(res);
            }
        });
        if (!availableReservations.isEmpty()) {
            notificationService.notifyUser(availableReservations);
        }
    }

}
