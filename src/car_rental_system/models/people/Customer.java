package car_rental_system.models.people;

import car_rental_system.models.PersonAccessingAccount;
import car_rental_system.models.Reservation;
import car_rental_system.models.Vehicle;
import car_rental_system.models.VehicleCatalog;

import java.util.ArrayList;
import java.util.List;

public class Customer extends PersonAccessingAccount {

    private String customerId;
    private String name;
    private int age;
    // Req 5: track how many vehicles a customer has rented
    private List<Reservation> rentalHistory;

    public Customer(String customerId, String name, int age) {
        this.customerId = customerId;
        this.name = name;
        this.age = age;
        this.rentalHistory = new ArrayList<>();
    }

    /** Req 5: rental count tracking */
    public int getRentalCount() {
        return rentalHistory.size();
    }

    public void addReservation(Reservation reservation) {
        rentalHistory.add(reservation);
    }

    /** Req 6: customer cancels their own reservation */
    public void cancelReservation(Reservation reservation) {
        reservation.cancelReservation();
    }

    @Override
    public void login(String id, String password) {
        System.out.println("Customer " + name + " logged in.");
    }

    @Override
    public void logout() {
        System.out.println("Customer " + name + " logged out.");
    }

    @Override
    public void bookVehicle() {
        // Booking is orchestrated by CarRentalSystem — this is a hook
        System.out.println("Customer " + name + " is initiating a booking.");
    }

    @Override
    public Vehicle searchVehicle(VehicleCatalog catalog) {
        // Delegates to VehicleCatalog with currently set strategy
        List<Vehicle> results = catalog.getAvailableVehicles();
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public PersonAccessingAccount updateProfile(String name, int age) {
        this.name = name;
        this.age = age;
        return this;
    }

    // Getters & Setters
    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public List<Reservation> getRentalHistory() { return rentalHistory; }
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
}
