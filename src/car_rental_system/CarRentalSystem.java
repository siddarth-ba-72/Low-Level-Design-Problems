package car_rental_system;

import car_rental_system.models.CarRentalBranch;
import car_rental_system.models.Reservation;
import car_rental_system.models.Vehicle;
import car_rental_system.models.VehicleLog;
import car_rental_system.models.people.Customer;
import car_rental_system.observer.FineObserver;
import car_rental_system.observer.NotificationObserver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * CarRentalSystem — Singleton entry point for the entire system.
 *
 * Interview note: Singleton ensures there is exactly ONE system managing
 * all branches, all reservations. This avoids duplicate state.
 *
 * Responsibilities:
 *  - Manages branches
 *  - Creates and tracks reservations
 *  - Wires up observers (notification + fine) on each reservation
 *  - Checks for overdue rentals
 */
public class CarRentalSystem {

    private static CarRentalSystem instance;

    private final List<CarRentalBranch> branches;
    // Req 4: reservation records
    private final Map<String, Reservation> reservations; // reservationId -> Reservation

    // Private constructor — Singleton
    private CarRentalSystem() {
        branches = new ArrayList<>();
        reservations = new HashMap<>();
    }

    public static CarRentalSystem getInstance() {
        if (instance == null) {
            instance = new CarRentalSystem();
        }
        return instance;
    }

    // ---- Branch Management (Req 12) ----

    public void addBranch(CarRentalBranch branch) {
        branches.add(branch);
        System.out.println("Branch added: " + branch.getBranchName());
    }

    public List<CarRentalBranch> getBranches() {
        return branches;
    }

    // ---- Reservation Management ----

    /**
     * Creates a reservation, wires up observers, and logs the event on the vehicle.
     * Req 4: records who rented + issue date
     * Req 10: attaches NotificationObserver + FineObserver for overdue handling
     */
    public Reservation createReservation(Customer customer, Vehicle vehicle,
                                         LocalDate startDate, LocalDate dueDate) {
        String reservationId = "RES-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Reservation reservation = new Reservation(reservationId, customer, vehicle, startDate, dueDate);

        // Attach observers (Observer Pattern)
        reservation.addObserver(new NotificationObserver());
        reservation.addObserver(new FineObserver());

        reservation.confirmReservation();
        customer.addReservation(reservation);
        reservations.put(reservationId, reservation);

        // Req 7: log the vehicle activity
        VehicleLog log = new VehicleLog(
                UUID.randomUUID().toString(),
                vehicle.getVehicleId(),
                "Reserved by customer: " + customer.getCustomerId(),
                LocalDateTime.now(),
                customer.getCustomerId()
        );
        vehicle.addLog(log);

        System.out.println("Reservation created: " + reservationId +
                " | Customer: " + customer.getCustomerId() +
                " | Vehicle: " + vehicle.getModel() +
                " | From: " + startDate + " To: " + dueDate);
        return reservation;
    }

    /**
     * Checks all active reservations and marks overdue ones.
     * Req 10: triggers notifications and fines via observers.
     */
    public void checkOverdueReservations() {
        LocalDate today = LocalDate.now();
        reservations.values().stream()
                .filter(r -> r.getStatus() == car_rental_system.enums.ReservationStatus.ACTIVE
                        || r.getStatus() == car_rental_system.enums.ReservationStatus.CONFIRMED)
                .filter(r -> r.getDueDate().isBefore(today))
                .forEach(Reservation::markOverdue);
    }

    public Reservation getReservation(String reservationId) {
        return reservations.get(reservationId);
    }

    public Map<String, Reservation> getAllReservations() {
        return reservations;
    }
}

