package elevatorsystem.panels;

import elevatorsystem.ElevatorSystem;
import elevatorsystem.enums.Direction;

public class OuterPanel implements Panel {

    private final int floor;
    private ElevatorSystem elevatorSystem;

    public OuterPanel(int floor) {
        this.floor = floor;
    }

    public void setElevatorSystem(ElevatorSystem elevatorSystem) {
        this.elevatorSystem = elevatorSystem;
    }

    /**
     * Generic button press — 1 = UP, 2 = DOWN.
     */
    @Override
    public void onButtonPress(int buttonId) {
        if (buttonId == 1) {
            pressUp();
        } else if (buttonId == 2) {
            pressDown();
        } else {
            System.out.println("  Invalid button on outer panel.");
        }
    }

    public void pressUp() {
        System.out.println("  [OuterPanel] UP pressed at floor " + floor);
        elevatorSystem.handleExternalRequest(floor, Direction.UP);
    }

    public void pressDown() {
        System.out.println("  [OuterPanel] DOWN pressed at floor " + floor);
        elevatorSystem.handleExternalRequest(floor, Direction.DOWN);
    }

    public int getFloor() {
        return floor;
    }
}

