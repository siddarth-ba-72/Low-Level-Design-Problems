package library_management_system.notification;

import library_management_system.models.BookReservation;

import java.util.List;

public interface ReservationNotification {
    void notifyUser(List<BookReservation> reservationsToNotify);
}
