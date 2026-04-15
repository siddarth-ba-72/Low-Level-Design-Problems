package elevatorsystem.strategy;

import elevatorsystem.enums.Direction;
import elevatorsystem.models.Elevator;
import elevatorsystem.models.Request;

import java.util.List;

/**
 * Selects the best elevator using this priority:
 * 1. Idle elevator closest to the requested floor.
 * 2. Moving elevator heading towards the floor in the same direction as the request.
 * 3. Fallback — elevator with fewest pending stops.
 */
public class NearestElevatorStrategy implements DispatchStrategy {

    @Override
    public Elevator selectElevator(List<Elevator> elevators, Request request) {
        Elevator bestElevator = null;
        int bestScore = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {
            int distance = Math.abs(elevator.getCurrentFloor() - request.getFloor());
            int score;

            if (elevator.isIdle()) {
                // Priority 1: Idle and close
                score = distance;
            } else if (isMovingTowards(elevator, request)) {
                // Priority 2: Moving towards the floor in the same direction
                score = distance + 1; // slight penalty over idle
            } else {
                // Priority 3: Moving away or different direction
                score = distance + elevator.getTotalPendingStops() * 2 + 100;
            }

            if (score < bestScore) {
                bestScore = score;
                bestElevator = elevator;
            }
        }

        return bestElevator;
    }

    private boolean isMovingTowards(Elevator elevator, Request request) {
        Direction elevatorDir = elevator.getCurrentDirection();
        int elevatorFloor = elevator.getCurrentFloor();
        int requestFloor = request.getFloor();
        Direction requestDir = request.getDirection();

        if (elevatorDir == Direction.UP && requestFloor > elevatorFloor && requestDir == Direction.UP) {
            return true;
        }
        if (elevatorDir == Direction.DOWN && requestFloor < elevatorFloor && requestDir == Direction.DOWN) {
            return true;
        }
        return false;
    }
}

