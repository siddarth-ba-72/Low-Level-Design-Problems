package car_rental_system.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Req 13: Each branch will have parking stalls for the vehicles.
 */
public class ParkingStall {

    private String stallId;
    private boolean isOccupied;
    private Vehicle parkedVehicle;

    public ParkingStall(String stallId) {
        this.stallId = stallId;
        this.isOccupied = false;
    }

    public void parkVehicle(Vehicle vehicle) {
        this.parkedVehicle = vehicle;
        this.isOccupied = true;
        vehicle.setParkingStall(this);
    }

    public void vacateStall() {
        if (parkedVehicle != null) {
            parkedVehicle.setParkingStall(null);
        }
        this.parkedVehicle = null;
        this.isOccupied = false;
    }

    public String getStallId() { return stallId; }
    public boolean isOccupied() { return isOccupied; }
    public Vehicle getParkedVehicle() { return parkedVehicle; }
}
