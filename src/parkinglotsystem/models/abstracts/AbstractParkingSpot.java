package parkinglotsystem.models.abstracts;

import parkinglotsystem.models.ParkingSpot;
import parkinglotsystem.models.Vehicle;
import parkinglotsystem.models.concretes.parkingspotmodels.ParkingSpotType;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractParkingSpot implements ParkingSpot {

    private final ParkingSpotType parkingSpotType;
    private final List<Vehicle> parkedVehicles = new ArrayList<>();

    protected AbstractParkingSpot(ParkingSpotType parkingSpotType) {
        this.parkingSpotType = parkingSpotType;
    }

    @Override
    public ParkingSpotType getParkingSpotType() {
        return parkingSpotType;
    }

    @Override
    public List<Vehicle> getParkedVehicles() {
        return parkedVehicles;
    }

    @Override
    public void parkVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        parkedVehicles.add(vehicle);
    }

    @Override
    public void unparkVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        parkedVehicles.remove(vehicle);
    }
}

