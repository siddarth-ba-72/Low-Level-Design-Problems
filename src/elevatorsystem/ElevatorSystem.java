package elevatorsystem;

import elevatorsystem.enums.Direction;
import elevatorsystem.enums.RequestType;
import elevatorsystem.models.Elevator;
import elevatorsystem.models.Floor;
import elevatorsystem.models.Request;
import elevatorsystem.strategy.DispatchStrategy;
import elevatorsystem.strategy.NearestElevatorStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ElevatorSystem {

    private List<Elevator> elevators;
    private List<Floor> floors;
    private DispatchStrategy dispatchStrategy;

    private ElevatorSystem() {
    }

    public static ElevatorSystem getInstance() {
        return ElevatorSystemHolder.INSTANCE;
    }

    // ========================
    //  Initialization
    // ========================

    public void initialize(int numFloors, int numElevators) {
        if (numFloors < 1 || numFloors > 15) {
            throw new IllegalArgumentException("Floors must be between 1 and 15.");
        }
        if (numElevators < 1 || numElevators > 3) {
            throw new IllegalArgumentException("Elevators must be between 1 and 3.");
        }

        this.dispatchStrategy = new NearestElevatorStrategy();

        // Create elevators
        this.elevators = new ArrayList<>();
        for (int i = 1; i <= numElevators; i++) {
            elevators.add(new Elevator(i, numFloors));
        }

        // Create floors and wire their outer panels to this system
        this.floors = new ArrayList<>();
        for (int i = 1; i <= numFloors; i++) {
            Floor floor = new Floor(i);
            floor.getOuterPanel().setElevatorSystem(this);
            floors.add(floor);
        }

        System.out.println("Elevator System initialized: " + numFloors + " floors, " + numElevators + " elevators.\n");
    }

    // ========================
    //  Request Handling
    // ========================

    /**
     * Handle a request from an outer panel (passenger calls an elevator from a floor).
     * Dispatches the best elevator to the floor.
     */
    public Elevator handleExternalRequest(int floor, Direction direction) {
        Request request = new Request(floor, direction, RequestType.EXTERNAL);
        Elevator bestElevator = dispatchStrategy.selectElevator(elevators, request);

        if (bestElevator != null) {
            bestElevator.addStop(floor);
            System.out.println("  → Dispatched Elevator " + bestElevator.getId() + " to floor " + floor);
        } else {
            System.out.println("  ✖ No elevator available to dispatch.");
        }

        return bestElevator;
    }

    /**
     * Handle a request from an inner panel (passenger presses a floor button inside an elevator).
     */
    public void handleInternalRequest(int elevatorId, int floor) {
        Elevator elevator = getElevatorById(elevatorId);
        if (elevator != null) {
            elevator.addStop(floor);
            System.out.println("  → Elevator " + elevatorId + " will stop at floor " + floor);
        } else {
            System.out.println("  ✖ Elevator " + elevatorId + " not found.");
        }
    }

    // ========================
    //  Simulation
    // ========================

    /**
     * Advance all elevators by one step (each moves one floor if active).
     */
    public void stepSimulation() {
        System.out.println("\n--- Step ---");
        for (Elevator elevator : elevators) {
            elevator.move();
        }
    }

    /**
     * Run the simulation until all elevators are idle with no pending stops.
     */
    public void runUntilIdle() {
        System.out.println("\n--- Running simulation until all elevators idle ---");
        int maxSteps = 100; // safety limit
        int step = 0;
        while (!allIdle() && step < maxSteps) {
            step++;
            System.out.println("\n--- Step " + step + " ---");
            for (Elevator elevator : elevators) {
                elevator.move();
            }
        }
        if (allIdle()) {
            System.out.println("\n✔ All elevators are now idle.");
        } else {
            System.out.println("\n⚠ Reached max steps (" + maxSteps + "). Some elevators may still be active.");
        }
    }

    private boolean allIdle() {
        return elevators.stream().allMatch(e -> e.isIdle() && e.getTotalPendingStops() == 0);
    }

    // ========================
    //  Status Display
    // ========================

    public void showStatus() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                     SYSTEM STATUS                            ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        for (Elevator elevator : elevators) {
            System.out.printf("║ Elevator %d | Floor: %-2d | State: %-7s | Dir: %-4s          ║%n",
                    elevator.getId(),
                    elevator.getCurrentFloor(),
                    elevator.getState(),
                    elevator.getCurrentDirection());
            System.out.printf("║   Passengers: %d/%d | Weight: %d/%d kg                        ║%n",
                    elevator.getPassengers().size(), Elevator.MAX_CAPACITY,
                    elevator.getCurrentLoad(), Elevator.MAX_WEIGHT);
            System.out.printf("║   UpStops: %-15s | DownStops: %-15s     ║%n",
                    elevator.getUpStops(), elevator.getDownStops());

            if (!elevator.getPassengers().isEmpty()) {
                System.out.printf("║   Riders: %-50s║%n",
                        elevator.getPassengers());
            }

            System.out.println("║   Inner Display: ");
            System.out.print("║     ");
            elevator.getInnerDisplay().show();
        }
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");
    }

    // ========================
    //  Getters
    // ========================

    public Elevator getElevatorById(int id) {
        return elevators.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Elevator> getElevators() {
        return Collections.unmodifiableList(elevators);
    }

    public List<Floor> getFloors() {
        return Collections.unmodifiableList(floors);
    }

    public Floor getFloor(int floorNumber) {
        if (floorNumber < 1 || floorNumber > floors.size()) return null;
        return floors.get(floorNumber - 1);
    }

    public void setDispatchStrategy(DispatchStrategy strategy) {
        this.dispatchStrategy = strategy;
    }

    // Lazy-loaded singleton holder
    private static class ElevatorSystemHolder {
        private static final ElevatorSystem INSTANCE = new ElevatorSystem();
    }
}

