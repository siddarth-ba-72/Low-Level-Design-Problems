package elevatorsystem.strategy;

import elevatorsystem.models.Elevator;
import elevatorsystem.models.Request;

import java.util.List;

public interface DispatchStrategy {
    Elevator selectElevator(List<Elevator> elevators, Request request);
}

