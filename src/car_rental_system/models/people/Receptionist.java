package car_rental_system.models.people;

import car_rental_system.models.PersonAccessingAccount;
import car_rental_system.models.Reservation;
import car_rental_system.models.Vehicle;
import car_rental_system.models.VehicleCatalog;

public class Receptionist extends PersonAccessingAccount {

    private String receptionistId;
    private String name;
    private int age;

    public Receptionist(String receptionistId, String name, int age) {
        this.receptionistId = receptionistId;
        this.name = name;
        this.age = age;
    }

    /** Receptionist can add a vehicle to the branch catalog */
    public void addVehicleToCatalog(VehicleCatalog catalog, Vehicle vehicle) {
        catalog.addVehicle(vehicle);
        System.out.println("Receptionist " + name + " added vehicle: " + vehicle.getModel());
    }

    /** Receptionist confirms/processes a reservation on behalf of the customer */
    public void processReservation(Reservation reservation) {
        reservation.confirmReservation();
        System.out.println("Receptionist " + name + " confirmed reservation: "
                + reservation.getReservationId());
    }

    @Override
    public void login(String id, String password) {
        System.out.println("Receptionist " + name + " logged in.");
    }

    @Override
    public void logout() {
        System.out.println("Receptionist " + name + " logged out.");
    }

    @Override
    public void bookVehicle() {
        System.out.println("Receptionist " + name + " is booking a vehicle on behalf of a customer.");
    }

    @Override
    public Vehicle searchVehicle(VehicleCatalog catalog) {
        return catalog.getAvailableVehicles().stream().findFirst().orElse(null);
    }

    @Override
    public PersonAccessingAccount updateProfile(String name, int age) {
        this.name = name;
        this.age = age;
        return this;
    }

    // Getters
    public String getReceptionistId() { return receptionistId; }
    public String getName() { return name; }
    public int getAge() { return age; }
}
