package elevatorsystem.observer;

import elevatorsystem.enums.Direction;

public interface ElevatorObserver {
    void onFloorChange(int elevatorId, int floor);
    void onDirectionChange(int elevatorId, Direction direction);
    void onCapacityChange(int elevatorId, int currentLoad, int passengerCount);
}

