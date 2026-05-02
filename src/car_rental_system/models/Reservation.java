package car_rental_system.models;

import car_rental_system.enums.ReservationStatus;
import car_rental_system.models.people.Customer;
import car_rental_system.observer.ReservationObserver;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Reservation is the central entity of this system.
 * It links a Customer to a Vehicle for a time period.
 *
 * Design note: Reservation acts as the Subject in the Observer pattern.
 * When its status changes to OVERDUE, it notifies all observers
 * (NotificationObserver, FineObserver).
 *
 * Req 4: records who rented + date issued
 * Req 6: customer can cancel → cancelReservation()
 * Req 8: holds list of equipment decorators (priced at checkout)
 */
public class Reservation {

    private final String reservationId;
    private final Customer customer;
    private final Vehicle vehicle;
    private final LocalDate startDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;
    private ReservationStatus status;
    private double totalCost;

    // Observer pattern — observers react when reservation becomes OVERDUE
    private final List<ReservationObserver> observers;

    public Reservation(String reservationId, Customer customer, Vehicle vehicle,
                       LocalDate startDate, LocalDate dueDate) {
        this.reservationId = reservationId;
        this.customer = customer;
        this.vehicle = vehicle;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.status = ReservationStatus.PENDING;
        this.observers = new ArrayList<>();
    }

    // ---- Observer Pattern Methods ----

    public void addObserver(ReservationObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ReservationObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (ReservationObserver observer : observers) {
            observer.update(this);
        }
    }

    // ---- Reservation Actions ----

    public void confirmReservation() {
        this.status = ReservationStatus.CONFIRMED;
        vehicle.setStatus(car_rental_system.enums.VehicleStatus.RESERVED);
    }

    public void startRental() {
        this.status = ReservationStatus.ACTIVE;
        vehicle.setStatus(car_rental_system.enums.VehicleStatus.RENTED);
    }

    public void cancelReservation() {
        // Req 6: Customer should be able to cancel their reservations
        this.status = ReservationStatus.CANCELLED;
        vehicle.setStatus(car_rental_system.enums.VehicleStatus.AVAILABLE);
    }

    public void completeRental(LocalDate returnDate) {
        this.returnDate = returnDate;
        this.status = ReservationStatus.COMPLETED;
        vehicle.setStatus(car_rental_system.enums.VehicleStatus.AVAILABLE);
    }

    public void markOverdue() {
        // Req 10: If vehicle isn't returned by due date, notify customer and impose fine
        this.status = ReservationStatus.OVERDUE;
        notifyObservers(); // triggers NotificationObserver + FineObserver
    }

    // Getters
    public String getReservationId() { return reservationId; }
    public Customer getCustomer() { return customer; }
    public Vehicle getVehicle() { return vehicle; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public ReservationStatus getStatus() { return status; }
    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }
}

