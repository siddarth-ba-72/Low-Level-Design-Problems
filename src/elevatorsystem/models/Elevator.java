package elevatorsystem.models;

import elevatorsystem.enums.Direction;
import elevatorsystem.enums.ElevatorState;
import elevatorsystem.observer.ElevatorObserver;
import elevatorsystem.panels.InnerPanel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

public class Elevator {

    private final int id;
    private int currentFloor;
    private ElevatorState state;
    private Direction currentDirection;
    private final Door door;
    private final InnerPanel innerPanel;
    private final Display innerDisplay;
    private final TreeSet<Integer> upStops;
    private final TreeSet<Integer> downStops;
    private final List<Person> passengers;
    private final List<ElevatorObserver> observers;

    public static final int MAX_CAPACITY = 8;
    public static final int MAX_WEIGHT = 680;

    public Elevator(int id, int totalFloors) {
        this.id = id;
        this.currentFloor = 1;
        this.state = ElevatorState.IDLE;
        this.currentDirection = Direction.NONE;
        this.door = new Door();
        this.innerPanel = new InnerPanel(totalFloors);
        this.innerPanel.setElevator(this);
        this.innerDisplay = new Display(true);
        this.upStops = new TreeSet<>();
        this.downStops = new TreeSet<>();
        this.passengers = new ArrayList<>();
        this.observers = new ArrayList<>();

        // Inner display observes this elevator
        addObserver(innerDisplay);
    }

    // ========================
    //  Stop Management
    // ========================

    /**
     * Adds a floor to the appropriate stop queue.
     * If the elevator is at the requested floor and idle, it simply opens the door.
     */
    public void addStop(int floor) {
        if (floor == currentFloor) {
            if (state == ElevatorState.IDLE && !door.isOpen()) {
                door.open();
                System.out.println("  Elevator " + id + " doors opened at floor " + currentFloor);
            }
            return;
        }

        // Place floor in the correct queue
        if (floor > currentFloor) {
            upStops.add(floor);
        } else {
            downStops.add(floor);
        }

        notifyObservers();
    }

    // ========================
    //  Movement (LOOK Algorithm)
    // ========================

    /**
     * Advances the elevator by one step.
     * - If IDLE with no stops: no-op.
     * - If IDLE with stops: close door, determine direction, start moving.
     * - If MOVING: move one floor in the current direction. Stop if at a queued floor.
     */
    public void move() {
        // Nothing to do
        if (state == ElevatorState.IDLE && !hasStops()) return;

        // Was stopped at a floor (or freshly received a request while idle) — prepare to move
        if (state == ElevatorState.IDLE && hasStops()) {
            if (door.isOpen()) {
                door.close();
                System.out.println("  Elevator " + id + " doors closed.");
            }
            state = ElevatorState.MOVING;
            if (currentDirection == Direction.NONE) {
                determineDirection();
            }
        }

        // Process one step of movement
        processMovement();
    }

    private void processMovement() {
        if (currentDirection == Direction.UP) {
            if (!upStops.isEmpty()) {
                moveOneFloorUp();
            } else if (!downStops.isEmpty()) {
                // Reverse direction and start moving down
                currentDirection = Direction.DOWN;
                moveOneFloorDown();
            } else {
                goIdle();
            }
        } else if (currentDirection == Direction.DOWN) {
            if (!downStops.isEmpty()) {
                moveOneFloorDown();
            } else if (!upStops.isEmpty()) {
                // Reverse direction and start moving up
                currentDirection = Direction.UP;
                moveOneFloorUp();
            } else {
                goIdle();
            }
        }
    }

    private void moveOneFloorUp() {
        currentFloor++;
        System.out.println("  Elevator " + id + " ↑ floor " + currentFloor);
        if (upStops.contains(currentFloor)) {
            upStops.remove(currentFloor);
            arriveAtFloor();
        } else {
            notifyObservers();
        }
    }

    private void moveOneFloorDown() {
        currentFloor--;
        System.out.println("  Elevator " + id + " ↓ floor " + currentFloor);
        if (downStops.contains(currentFloor)) {
            downStops.remove(currentFloor);
            arriveAtFloor();
        } else {
            notifyObservers();
        }
    }

    private void arriveAtFloor() {
        state = ElevatorState.IDLE;
        door.open();
        System.out.println("  >>> Elevator " + id + " arrived at floor " + currentFloor + " [Doors Open] <<<");

        // If no remaining stops, reset direction
        if (!hasStops()) {
            currentDirection = Direction.NONE;
        }

        notifyObservers();
    }

    private void goIdle() {
        state = ElevatorState.IDLE;
        currentDirection = Direction.NONE;
        System.out.println("  Elevator " + id + " is now IDLE at floor " + currentFloor);
        notifyObservers();
    }

    /**
     * Determines the initial direction based on which stops are closer.
     */
    private void determineDirection() {
        if (!upStops.isEmpty() && !downStops.isEmpty()) {
            int distUp = upStops.first() - currentFloor;
            int distDown = currentFloor - downStops.last();
            currentDirection = (distUp <= distDown) ? Direction.UP : Direction.DOWN;
        } else if (!upStops.isEmpty()) {
            currentDirection = Direction.UP;
        } else if (!downStops.isEmpty()) {
            currentDirection = Direction.DOWN;
        }
    }

    private boolean hasStops() {
        return !upStops.isEmpty() || !downStops.isEmpty();
    }

    // ========================
    //  Door Operations
    // ========================

    public void openDoor() {
        if (state == ElevatorState.MOVING) {
            System.out.println("  ✖ Cannot open door — Elevator " + id + " is moving!");
            return;
        }
        if (!door.isOpen()) {
            door.open();
            System.out.println("  Elevator " + id + " doors opened at floor " + currentFloor);
        }
    }

    public void closeDoor() {
        if (door.isOpen()) {
            door.close();
            System.out.println("  Elevator " + id + " doors closed.");
        }
    }

    // ========================
    //  Passenger Management
    // ========================

    public boolean canAcceptPassenger(Person person) {
        return passengers.size() < MAX_CAPACITY
                && (getCurrentLoad() + person.getWeight()) <= MAX_WEIGHT;
    }

    public boolean boardPassenger(Person person) {
        if (!door.isOpen()) {
            System.out.println("  ✖ Doors are closed — cannot board Elevator " + id);
            return false;
        }
        if (!canAcceptPassenger(person)) {
            System.out.println("  ✖ Elevator " + id + " is at capacity. Cannot board " + person.getName());
            return false;
        }
        passengers.add(person);
        System.out.println("  ✔ " + person.getName() + " boarded Elevator " + id
                + " (Passengers: " + passengers.size() + "/" + MAX_CAPACITY
                + ", Weight: " + getCurrentLoad() + "/" + MAX_WEIGHT + " kg)");
        notifyObservers();
        return true;
    }

    public boolean exitPassenger(String personName) {
        Person person = passengers.stream()
                .filter(p -> p.getName().equalsIgnoreCase(personName))
                .findFirst()
                .orElse(null);

        if (person == null) {
            System.out.println("  ✖ " + personName + " is not in Elevator " + id);
            return false;
        }
        if (!door.isOpen()) {
            System.out.println("  ✖ Doors are closed — cannot exit Elevator " + id);
            return false;
        }

        passengers.remove(person);
        System.out.println("  ✔ " + person.getName() + " exited Elevator " + id
                + " at floor " + currentFloor);
        notifyObservers();
        return true;
    }

    public int getCurrentLoad() {
        return passengers.stream().mapToInt(Person::getWeight).sum();
    }

    // ========================
    //  Observer Management
    // ========================

    public void addObserver(ElevatorObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ElevatorObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (ElevatorObserver observer : observers) {
            observer.onFloorChange(id, currentFloor);
            observer.onDirectionChange(id, currentDirection);
            observer.onCapacityChange(id, getCurrentLoad(), passengers.size());
        }
    }

    // ========================
    //  Getters
    // ========================

    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public ElevatorState getState() {
        return state;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public Door getDoor() {
        return door;
    }

    public InnerPanel getInnerPanel() {
        return innerPanel;
    }

    public Display getInnerDisplay() {
        return innerDisplay;
    }

    public List<Person> getPassengers() {
        return Collections.unmodifiableList(passengers);
    }

    public TreeSet<Integer> getUpStops() {
        return new TreeSet<>(upStops);
    }

    public TreeSet<Integer> getDownStops() {
        return new TreeSet<>(downStops);
    }

    public boolean isIdle() {
        return state == ElevatorState.IDLE;
    }

    public int getTotalPendingStops() {
        return upStops.size() + downStops.size();
    }

    @Override
    public String toString() {
        return "Elevator " + id;
    }
}

