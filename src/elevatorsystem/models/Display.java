package elevatorsystem.models;

import elevatorsystem.enums.Direction;
import elevatorsystem.observer.ElevatorObserver;

public class Display implements ElevatorObserver {

    private int currentFloor;
    private Direction direction;
    private int currentWeight;
    private int passengerCount;
    private final boolean showCapacity; // inner displays show capacity, outer displays don't

    public Display(boolean showCapacity) {
        this.showCapacity = showCapacity;
        this.currentFloor = 1;
        this.direction = Direction.NONE;
        this.currentWeight = 0;
        this.passengerCount = 0;
    }

    // --- Observer callbacks ---

    @Override
    public void onFloorChange(int elevatorId, int floor) {
        this.currentFloor = floor;
    }

    @Override
    public void onDirectionChange(int elevatorId, Direction direction) {
        this.direction = direction;
    }

    @Override
    public void onCapacityChange(int elevatorId, int currentLoad, int passengerCount) {
        this.currentWeight = currentLoad;
        this.passengerCount = passengerCount;
    }

    // --- Display rendering ---

    public void show() {
        String display = String.format("Floor: %d | Direction: %s", currentFloor, direction);
        if (showCapacity) {
            display += String.format(" | Passengers: %d | Weight: %d kg", passengerCount, currentWeight);
        }
        System.out.println(display);
    }

    // --- Getters ---

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }
}

