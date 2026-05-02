package car_rental_system.observer;

import car_rental_system.models.Reservation;

/**
 * Listens for OVERDUE reservations and sends a notification to the customer.
 * Req 10: "the system will notify the customer"
 */
public class NotificationObserver implements ReservationObserver {

    @Override
    public void update(Reservation reservation) {
        String customerId = reservation.getCustomer().getCustomerId();
        String vehicleModel = reservation.getVehicle().getModel();
        System.out.println("[NOTIFICATION] Customer " + customerId +
                ": Your rental for vehicle '" + vehicleModel +
                "' is overdue! Due date was: " + reservation.getDueDate());
        // In a real system: send email/SMS via a Notification service
    }
}

