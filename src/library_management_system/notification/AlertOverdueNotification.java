package library_management_system.notification;

import library_management_system.models.BookReservation;

import java.util.List;

public class AlertOverdueNotification implements ReservationNotification {

    @Override
    public void notifyUser(List<BookReservation> reservationsToNotify) {
        reservationsToNotify.forEach(res -> {
            System.out.println("Alert: Book '" + res.getBookId() + "' is overdue for user " + res.getCustomerId());
        });
    }

}
