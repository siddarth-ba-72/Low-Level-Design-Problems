package parkinglotsystem.models;

import parkinglotsystem.models.concretes.parkingspotmodels.ParkingSpotType;

import java.util.List;

public interface ParkingSpot {

    ParkingSpotType getParkingSpotType();
    List<Vehicle> getParkedVehicles();

    void parkVehicle(Vehicle vehicle);
    void unparkVehicle(Vehicle vehicle);

}
