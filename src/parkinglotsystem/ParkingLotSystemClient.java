package parkinglotsystem;

import parkinglotsystem.models.Vehicle;
import parkinglotsystem.models.concretes.humanmodels.Customer;
import parkinglotsystem.models.concretes.paymentmethodmodels.PaymentType;
import parkinglotsystem.models.concretes.vehiclemodels.Car;
import parkinglotsystem.models.concretes.vehiclemodels.MotorCycle;
import parkinglotsystem.models.concretes.vehiclemodels.Truck;

import java.util.Scanner;

public class ParkingLotSystemClient {
    public static void main(String[] args) {

        // 1. Boot up the system
        ParkingLotSystem parkingLotSystem = ParkingLotSystem.getInstance();
        parkingLotSystem.initializeParkingSpots();
        parkingLotSystem.initializeAgent();

        System.out.println(parkingLotSystem.getSystemAgent().getName() + " is ready to assist customers.");

        // 2. Interactive loop
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter action (park / unpark / exit): ");
            String action = scanner.nextLine().trim().toLowerCase();

            if (action.equals("exit")) {
                System.out.println("Shutting down parking lot system. Goodbye!");
                break;
            }

            if (action.equals("park")) {
                handlePark(scanner, parkingLotSystem);
            } else if (action.equals("unpark")) {
                handleUnpark(scanner, parkingLotSystem);
            } else {
                System.out.println("Unknown action. Please enter 'park', 'unpark' or 'exit'.");
            }
        }

        scanner.close();
    }

    private static void handlePark(Scanner scanner, ParkingLotSystem parkingLotSystem) {
        // a. Disability
        System.out.println("Do you have a disability? (yes / no): ");
        boolean isHandicapped = scanner.nextLine().trim().equalsIgnoreCase("yes");

        // b. Vehicle type
        System.out.println("Enter vehicle type (car / motorcycle / truck): ");
        String vehicleType = scanner.nextLine().trim().toLowerCase();

        // c. License plate
        System.out.println("Enter vehicle license plate: ");
        String licensePlate = scanner.nextLine().trim();

        // d. Create customer and vehicle
        Customer customer = new Customer("Customer", isHandicapped);
        Vehicle vehicle = createVehicle(vehicleType, licensePlate, customer);

        if (vehicle == null) {
            System.out.println("Invalid vehicle type. Try again.");
            return;
        }

        // e. Display current availability, then park
        System.out.println("\n--- Before parking ---");
        parkingLotSystem.getCurrentOccupancy();

        parkingLotSystem.parkVehicle(vehicle);

        System.out.println("\n--- After parking ---");
        parkingLotSystem.getCurrentOccupancy();

        // f. Show parking tickets
        parkingLotSystem.showVehicleParkingTickets();
    }

    private static void handleUnpark(Scanner scanner, ParkingLotSystem parkingLotSystem) {
        // a. Display all issued tickets
        System.out.println("\n--- Issued Tickets ---");
        parkingLotSystem.showVehicleParkingTickets();

        // b. Ask for vehicle license number
        System.out.println("Enter vehicle license plate to unpark: ");
        String licensePlate = scanner.nextLine().trim();

        // c. Ask for payment type
        System.out.println("Select payment type (cash / card): ");
        String paymentTypeInput = scanner.nextLine().trim().toLowerCase();

        PaymentType paymentType;
        String cardNumber = null;
        String cardHolderName = null;

        if (paymentTypeInput.equals("card")) {
            paymentType = PaymentType.CARD;

            // d. Ask for card details
            System.out.println("Enter card number: ");
            cardNumber = scanner.nextLine().trim();

            System.out.println("Enter card holder name: ");
            cardHolderName = scanner.nextLine().trim();
        } else {
            // e. Cash — no card details needed
            paymentType = PaymentType.CASH;
        }

        // f. Unpark vehicle (payment is processed inside the facade)
        parkingLotSystem.unparkVehicle(licensePlate, paymentType, cardNumber, cardHolderName);

        System.out.println("\n--- After unparking ---");
        parkingLotSystem.getCurrentOccupancy();
    }

    private static Vehicle createVehicle(String type, String licensePlate, Customer owner) {
        return switch (type) {
            case "car" -> new Car(licensePlate, owner, false);
            case "motorcycle" -> new MotorCycle(licensePlate, owner);
            case "truck" -> new Truck(licensePlate, owner, 0);
            default -> null;
        };
    }
}
