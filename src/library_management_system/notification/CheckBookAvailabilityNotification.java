package library_management_system.notification;

import library_management_system.models.BookReservation;

import java.util.List;

public class CheckBookAvailabilityNotification implements ReservationNotification {

    @Override
    public void notifyUser(List<BookReservation> reservationsToNotify) {
        reservationsToNotify.forEach(res -> {
            System.out.println("Notification: Book with ISBN " + res.getIsbnNumber() + " is now available for reservation ID: " + res.getReservationId());
        });
    }

}
