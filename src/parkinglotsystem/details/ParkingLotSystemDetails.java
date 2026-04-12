package parkinglotsystem.details;

import parkinglotsystem.models.*;
import parkinglotsystem.models.concretes.vehiclemodels.VehicleType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ParkingLotSystemDetails {

    public final int MAX_PARKING_SLOTS = 40000;
    private final Map<ParkingSpot, Integer> vehiclesCount;
    private final Map<Vehicle, ParkingTicket> issuedTickets;
    private Map<ParkingSpot, ParkingSpotEntrance> entrances;
    private Map<ParkingSpot, ParkingSpotExit> exits;

    private ParkingLotSystemDetails() {
        this.vehiclesCount = new ConcurrentHashMap<>();
        this.issuedTickets = new ConcurrentHashMap<>();
    }

    public static ParkingLotSystemDetails getInstance() {
        return ParkingLotSystemDetailsHolder.INSTANCE;
    }

    private final Map<VehicleType, Double> hourlyParkingRates = Map.of(
            VehicleType.LMV, 30.0,
            VehicleType.MOTORCYCLE, 10.0,
            VehicleType.TRUCK, 80.0
    );

    public int getMaxParkingSlots() {
        return MAX_PARKING_SLOTS;
    }

    public Map<ParkingSpot, Integer> getVehiclesCount() {
        return vehiclesCount;
    }

    public Map<Vehicle, ParkingTicket> getIssuedTickets() {
        return issuedTickets;
    }

    public Map<VehicleType, Double> getHourlyParkingRates() {
        return hourlyParkingRates;
    }

    public Map<ParkingSpot, ParkingSpotEntrance> getEntrances() {
        return entrances;
    }

    public void setEntrances(Map<ParkingSpot, ParkingSpotEntrance> entrances) {
        this.entrances = entrances;
    }

    public Map<ParkingSpot, ParkingSpotExit> getExits() {
        return exits;
    }

    public void setExits(Map<ParkingSpot, ParkingSpotExit> exits) {
        this.exits = exits;
    }

    private static class ParkingLotSystemDetailsHolder {
        private static final ParkingLotSystemDetails INSTANCE = new ParkingLotSystemDetails();
    }

}
