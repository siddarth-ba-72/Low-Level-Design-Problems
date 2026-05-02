package car_rental_system.observer;

import car_rental_system.models.Reservation;

/**
 * Observer interface for the Reservation subject.
 * Interview note: This is the classic Observer pattern.
 * Subject (Reservation) notifies all registered observers when it goes OVERDUE.
 */
public interface ReservationObserver {
    void update(Reservation reservation);
}

