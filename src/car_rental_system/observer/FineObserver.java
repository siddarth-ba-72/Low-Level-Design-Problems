package car_rental_system.observer;

import car_rental_system.models.Reservation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Listens for OVERDUE reservations and imposes a fine on the customer.
 * Req 10: "the system will...impose a fine"
 */
public class FineObserver implements ReservationObserver {

    private static final double FINE_PER_DAY = 50.0;

    @Override
    public void update(Reservation reservation) {
        long daysOverdue = ChronoUnit.DAYS.between(reservation.getDueDate(), LocalDate.now());
        if (daysOverdue > 0) {
            double fineAmount = daysOverdue * FINE_PER_DAY;
            System.out.println("[FINE] Customer " + reservation.getCustomer().getCustomerId() +
                    " has been fined $" + fineAmount +
                    " for being " + daysOverdue + " day(s) overdue.");
            // In a real system: persist Fine entity to DB, deduct from customer account
        }
    }
}

