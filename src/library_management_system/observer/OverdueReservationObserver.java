package library_management_system.observer;

import library_management_system.models.BookReservation;
import library_management_system.models.BookReservationStatus;
import library_management_system.notification.ReservationNotification;
import library_management_system.records.ReservationRecords;

import java.util.List;

public class OverdueReservationObserver implements ReservationObserver {

    private final ReservationNotification notificationService;
    private final ReservationRecords reservationRecords = ReservationRecords.getInstance();

    public OverdueReservationObserver(ReservationNotification reservationNotification) {
        this.notificationService = reservationNotification;
    }

    @Override
    public void observe() {
        List<BookReservation> overdueReservations = reservationRecords.getOverdueReservations();
        overdueReservations.forEach(res -> {
            res.setBookReservationStatus(BookReservationStatus.OVERDUE);
        });
        if (!overdueReservations.isEmpty()) {
            notificationService.notifyUser(overdueReservations);
        }
    }
}
