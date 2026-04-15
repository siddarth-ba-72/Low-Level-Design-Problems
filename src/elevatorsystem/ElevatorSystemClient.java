package elevatorsystem;

import elevatorsystem.models.Elevator;
import elevatorsystem.models.Floor;
import elevatorsystem.models.Person;

import java.util.Scanner;

public class ElevatorSystemClient {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ElevatorSystem system = ElevatorSystem.getInstance();

        // ========================
        //  1. Initialize System
        // ========================

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║       ELEVATOR SYSTEM SIMULATOR      ║");
        System.out.println("╚══════════════════════════════════════╝\n");

        System.out.print("Enter number of floors (1-15): ");
        int numFloors = readInt(scanner);

        System.out.print("Enter number of elevators (1-3): ");
        int numElevators = readInt(scanner);

        system.initialize(numFloors, numElevators);

        // ========================
        //  2. Interactive Loop
        // ========================

        while (true) {
            printMenu();
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "1", "call" -> handleCallElevator(scanner, system);
                case "2", "board" -> handleBoardPassenger(scanner, system);
                case "3", "press" -> handlePressFloor(scanner, system);
                case "4", "exit" -> handleExitPassenger(scanner, system);
                case "5", "door" -> handleDoorOperation(scanner, system);
                case "6", "step" -> system.stepSimulation();
                case "7", "run" -> system.runUntilIdle();
                case "8", "status" -> system.showStatus();
                case "9", "quit" -> {
                    System.out.println("\nShutting down Elevator System. Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Unknown command. Please try again.");
            }
        }
    }

    // ========================
    //  Menu
    // ========================

    private static void printMenu() {
        System.out.println("\n--- Commands ---");
        System.out.println("  1. call   — Call elevator to a floor (external request)");
        System.out.println("  2. board  — Board a passenger into an elevator");
        System.out.println("  3. press  — Press floor button inside an elevator (internal request)");
        System.out.println("  4. exit   — Exit a passenger from an elevator");
        System.out.println("  5. door   — Open/Close elevator door");
        System.out.println("  6. step   — Advance simulation by one step");
        System.out.println("  7. run    — Run simulation until all elevators are idle");
        System.out.println("  8. status — Show system status");
        System.out.println("  9. quit   — Exit program");
        System.out.print("Enter command: ");
    }

    // ========================
    //  Command Handlers
    // ========================

    /**
     * Call an elevator to a specific floor (external request via OuterPanel).
     */
    private static void handleCallElevator(Scanner scanner, ElevatorSystem system) {
        System.out.print("  Enter floor number: ");
        int floor = readInt(scanner);

        Floor floorObj = system.getFloor(floor);
        if (floorObj == null) {
            System.out.println("  ✖ Invalid floor number.");
            return;
        }

        System.out.print("  Enter direction (up/down): ");
        String dir = scanner.nextLine().trim().toLowerCase();

        if (dir.equals("up")) {
            floorObj.getOuterPanel().pressUp();
        } else if (dir.equals("down")) {
            floorObj.getOuterPanel().pressDown();
        } else {
            System.out.println("  ✖ Invalid direction. Use 'up' or 'down'.");
        }
    }

    /**
     * Board a passenger into a specific elevator (elevator must have doors open).
     */
    private static void handleBoardPassenger(Scanner scanner, ElevatorSystem system) {
        System.out.print("  Enter elevator ID: ");
        int elevatorId = readInt(scanner);

        Elevator elevator = system.getElevatorById(elevatorId);
        if (elevator == null) {
            System.out.println("  ✖ Elevator not found.");
            return;
        }

        System.out.print("  Enter passenger name: ");
        String name = scanner.nextLine().trim();

        System.out.print("  Enter passenger weight (kg): ");
        int weight = readInt(scanner);

        Person person = new Person(name, weight);
        elevator.boardPassenger(person);
    }

    /**
     * Press a floor button inside an elevator (internal request via InnerPanel).
     */
    private static void handlePressFloor(Scanner scanner, ElevatorSystem system) {
        System.out.print("  Enter elevator ID: ");
        int elevatorId = readInt(scanner);

        Elevator elevator = system.getElevatorById(elevatorId);
        if (elevator == null) {
            System.out.println("  ✖ Elevator not found.");
            return;
        }

        System.out.print("  Enter destination floor: ");
        int floor = readInt(scanner);

        elevator.getInnerPanel().onButtonPress(floor);
    }

    /**
     * Exit a passenger from an elevator (elevator must have doors open).
     */
    private static void handleExitPassenger(Scanner scanner, ElevatorSystem system) {
        System.out.print("  Enter elevator ID: ");
        int elevatorId = readInt(scanner);

        Elevator elevator = system.getElevatorById(elevatorId);
        if (elevator == null) {
            System.out.println("  ✖ Elevator not found.");
            return;
        }

        if (elevator.getPassengers().isEmpty()) {
            System.out.println("  ✖ No passengers in Elevator " + elevatorId);
            return;
        }

        System.out.println("  Current passengers: " + elevator.getPassengers());
        System.out.print("  Enter passenger name to exit: ");
        String name = scanner.nextLine().trim();

        elevator.exitPassenger(name);
    }

    /**
     * Open or close the door of an elevator.
     */
    private static void handleDoorOperation(Scanner scanner, ElevatorSystem system) {
        System.out.print("  Enter elevator ID: ");
        int elevatorId = readInt(scanner);

        Elevator elevator = system.getElevatorById(elevatorId);
        if (elevator == null) {
            System.out.println("  ✖ Elevator not found.");
            return;
        }

        System.out.print("  Open or Close? (open/close): ");
        String action = scanner.nextLine().trim().toLowerCase();

        if (action.equals("open")) {
            elevator.openDoor();
        } else if (action.equals("close")) {
            elevator.closeDoor();
        } else {
            System.out.println("  ✖ Invalid action. Use 'open' or 'close'.");
        }
    }

    // ========================
    //  Utility
    // ========================

    private static int readInt(Scanner scanner) {
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("  Invalid number. Try again: ");
            }
        }
    }
}

