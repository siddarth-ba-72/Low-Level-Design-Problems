package elevatorsystem.models;

import elevatorsystem.enums.Direction;
import elevatorsystem.enums.RequestType;

public class Request {

    private final int floor;
    private final Direction direction;
    private final RequestType type;

    public Request(int floor, Direction direction, RequestType type) {
        this.floor = floor;
        this.direction = direction;
        this.type = type;
    }

    public int getFloor() {
        return floor;
    }

    public Direction getDirection() {
        return direction;
    }

    public RequestType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Request{floor=" + floor + ", direction=" + direction + ", type=" + type + "}";
    }
}

