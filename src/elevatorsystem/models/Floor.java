package elevatorsystem.models;

import elevatorsystem.panels.OuterPanel;

public class Floor {

    private final int floorNumber;
    private final OuterPanel outerPanel;
    private final Display outerDisplay;

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.outerPanel = new OuterPanel(floorNumber);
        this.outerDisplay = new Display(false);
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public OuterPanel getOuterPanel() {
        return outerPanel;
    }

    public Display getOuterDisplay() {
        return outerDisplay;
    }

    @Override
    public String toString() {
        return "Floor " + floorNumber;
    }
}

