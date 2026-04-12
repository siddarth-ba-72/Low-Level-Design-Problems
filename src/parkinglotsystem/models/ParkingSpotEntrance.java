package parkinglotsystem.models;

import java.util.Map;
import java.util.UUID;

public class ParkingSpotEntrance {

    private final ParkingSpot parkingSpot;

    public ParkingSpotEntrance(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public Map<Vehicle, ParkingTicket> issueParkingTicket(Vehicle vehicle, ParkingSpot assignedSpot) {
        System.out.println("Issuing parking ticket for vehicle with license plate: " + vehicle.getLicensePlate());
        ParkingTicket ticket = new ParkingTicket(
                UUID.randomUUID().toString(),
                vehicle,
                assignedSpot,
                System.currentTimeMillis()
        );
        return Map.of(vehicle, ticket);
    }

    public void parkVehicle(Vehicle vehicle) {
        parkingSpot.parkVehicle(vehicle);
    }

}
