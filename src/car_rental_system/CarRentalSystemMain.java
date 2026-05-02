package car_rental_system;

import car_rental_system.decorator.*;
import car_rental_system.enums.CarType;
import car_rental_system.enums.VanType;
import car_rental_system.models.*;
import car_rental_system.models.people.Customer;
import car_rental_system.models.people.Receptionist;
import car_rental_system.models.vehicles.Car;
import car_rental_system.models.vehicles.Van;
import car_rental_system.strategy.SearchByModelStrategy;
import car_rental_system.strategy.SearchByTypeStrategy;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class CarRentalSystemMain {

    public static void main(String[] args) {

        // ---------------------------------------------------------------
        // 1. BOOTSTRAP — Singleton system, branch, stalls, receptionists
        // ---------------------------------------------------------------
        CarRentalSystem system = CarRentalSystem.getInstance();

        Receptionist alice = new Receptionist("R001", "Alice", 30);
        List<ParkingStall> stalls = Arrays.asList(
                new ParkingStall("STALL-1"),
                new ParkingStall("STALL-2"),
                new ParkingStall("STALL-3")
        );
        CarRentalBranch downtown = new CarRentalBranch(
                "B001", "Downtown Branch", "123 Main St",
                stalls, Arrays.asList(alice)
        );
        system.addBranch(downtown);

        // ---------------------------------------------------------------
        // 2. ADD VEHICLES TO BRANCH (Req 2, 3)
        // ---------------------------------------------------------------
        Car tesla = new Car("V001", "Tesla", "Model 3", 2023, 80.0, CarType.LUXURY);
        Car civic = new Car("V002", "Honda", "Civic", 2022, 40.0, CarType.ECONOMY);
        Van cargoVan = new Van("V003", "Ford", "Transit", 2021, 60.0, VanType.CARGO);

        downtown.addVehicleToCatalog(tesla);
        downtown.addVehicleToCatalog(civic);
        downtown.addVehicleToCatalog(cargoVan);

        // ---------------------------------------------------------------
        // 3. SEARCH — Strategy Pattern (Req 11)
        // ---------------------------------------------------------------
        VehicleCatalog catalog = downtown.getVehicleCatalog();

        System.out.println("\n--- Search by Model: 'civic' ---");
        catalog.setSearchStrategy(new SearchByModelStrategy());
        catalog.search("civic").forEach(v -> System.out.println("  Found: " + v.getBrand() + " " + v.getModel()));

        System.out.println("\n--- Search by Type: 'CAR' ---");
        catalog.setSearchStrategy(new SearchByTypeStrategy());
        catalog.search("CAR").forEach(v -> System.out.println("  Found: " + v.getBrand() + " " + v.getModel()));

        // ---------------------------------------------------------------
        // 4. CUSTOMER BOOKS A VEHICLE — creates reservation (Req 4, 5)
        // ---------------------------------------------------------------
        Customer john = new Customer("C001", "John Doe", 28);
        john.login("C001", "password");

        Reservation reservation = system.createReservation(
                john, tesla,
                LocalDate.now(),
                LocalDate.now().plusDays(5)
        );

        System.out.println("\nJohn's rental count: " + john.getRentalCount()); // Req 5

        // ---------------------------------------------------------------
        // 5. DECORATOR — Add equipment to price (Req 8)
        // ---------------------------------------------------------------
        System.out.println("\n--- Pricing with Equipment (Decorator Pattern) ---");
        RentalPriceComponent price = new BaseRental(tesla.getDailyRentalRate(), 5);
        System.out.println(price.getDescription() + " => $" + price.getPrice());

        price = new GpsDecorator(price);
        price = new ChildSeatDecorator(price);
        price = new SkiRackDecorator(price);
        System.out.println(price.getDescription() + " => $" + price.getPrice());

        reservation.setTotalCost(price.getPrice());

        // ---------------------------------------------------------------
        // 6. CANCEL RESERVATION (Req 6)
        // ---------------------------------------------------------------
        Customer bob = new Customer("C002", "Bob Smith", 35);
        Reservation bobReservation = system.createReservation(
                bob, civic,
                LocalDate.now(),
                LocalDate.now().plusDays(3)
        );
        System.out.println("\n--- Bob cancels his reservation ---");
        bob.cancelReservation(bobReservation);
        System.out.println("Reservation status: " + bobReservation.getStatus());

        // ---------------------------------------------------------------
        // 7. OVERDUE CHECK — Observer Pattern (Req 10)
        // ---------------------------------------------------------------
        System.out.println("\n--- Overdue Check ---");
        // In a real system, a scheduled job calls this nightly
        system.checkOverdueReservations();

        // ---------------------------------------------------------------
        // 8. VEHICLE LOG (Req 7)
        // ---------------------------------------------------------------
        System.out.println("\n--- Tesla Vehicle Log ---");
        tesla.getVehicleLogs().forEach(log ->
                System.out.println("  [" + log.getActivityDate() + "] " + log.getActivityDescription()));
    }
}
