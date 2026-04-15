package elevatorsystem.models;

import elevatorsystem.enums.DoorState;

public class Door {

    private DoorState state;

    public Door() {
        this.state = DoorState.CLOSED;
    }

    public void open() {
        this.state = DoorState.OPEN;
    }

    public void close() {
        this.state = DoorState.CLOSED;
    }

    public boolean isOpen() {
        return this.state == DoorState.OPEN;
    }

    public DoorState getState() {
        return state;
    }
}

