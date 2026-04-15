package elevatorsystem.panels;

import elevatorsystem.models.Elevator;

public class InnerPanel implements Panel {

    private final int totalFloors;
    private Elevator elevator;

    public InnerPanel(int totalFloors) {
        this.totalFloors = totalFloors;
    }

    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
    }

    /**
     * Press a floor button inside the elevator.
     * @param floorNumber the destination floor (1-based)
     */
    @Override
    public void onButtonPress(int floorNumber) {
        if (floorNumber < 1 || floorNumber > totalFloors) {
            System.out.println("  Invalid floor number: " + floorNumber);
            return;
        }
        System.out.println("  [InnerPanel] Floor " + floorNumber + " pressed in Elevator " + elevator.getId());
        elevator.addStop(floorNumber);
    }

    public void onOpenDoorPress() {
        System.out.println("  [InnerPanel] Open door pressed in Elevator " + elevator.getId());
        elevator.openDoor();
    }

    public void onCloseDoorPress() {
        System.out.println("  [InnerPanel] Close door pressed in Elevator " + elevator.getId());
        elevator.closeDoor();
    }

    public int getTotalFloors() {
        return totalFloors;
    }
}

