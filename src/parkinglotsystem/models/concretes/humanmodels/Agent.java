package parkinglotsystem.models.concretes.humanmodels;

import parkinglotsystem.details.ParkingLotSystemDetails;
import parkinglotsystem.models.*;
import parkinglotsystem.models.abstracts.AbstractHuman;
import parkinglotsystem.models.concretes.parkingspotmodels.CompactParkingSpot;
import parkinglotsystem.models.concretes.parkingspotmodels.HandicappedParkingSpot;
import parkinglotsystem.models.concretes.parkingspotmodels.LargeParkingSpot;
import parkinglotsystem.models.concretes.parkingspotmodels.MotorCycleParkingSpot;

import java.util.Map;
import java.util.stream.Collectors;

import static parkinglotsystem.models.concretes.humanmodels.HumanType.AGENT;

public final class Agent extends AbstractHuman {

    private final ParkingLotSystemDetails details;

    private Agent(String name) {
        super(AGENT, name);
        this.details = ParkingLotSystemDetails.getInstance();
    }

    public static class AgentHolder {
        private static final Agent INSTANCE = new Agent("Parking Lot Agent");
    }

    public static Agent getInstance() {
        return AgentHolder.INSTANCE;
    }

    public Map<ParkingSpot, Integer> getCurrentOccupancy() {
        return Map.copyOf(details.getVehiclesCount());
    }

    public ParkingSpotEntrance getParkingSpotEntrance(ParkingSpot parkingSpot) {
        return details.getEntrances().get(parkingSpot);
    }

    public synchronized boolean checkParkingSpotAvailability() {
        long occupiedSpots = details.getVehiclesCount().values().stream()
                .filter(count -> count > 0).
                count();
        return occupiedSpots < details.getMaxParkingSlots();
    }

    public ParkingSpot assignParkingSpot(Vehicle vehicle) {
        if (vehicle.getOwner().isHandicapped()) {
            return HandicappedParkingSpot.getInstance();
        } else {
            return switch (vehicle.getVehicleType()) {
                case LMV -> CompactParkingSpot.getInstance();
                case MOTORCYCLE -> MotorCycleParkingSpot.getInstance();
                case TRUCK -> LargeParkingSpot.getInstance();
            };
        }
    }

    public void addParkingDetails(ParkingSpot parkingSpot, Map<Vehicle, ParkingTicket> vehicleParkingTicket) {
        details.getVehiclesCount().merge(parkingSpot, 1, Integer::sum);
        details.getIssuedTickets().putAll(vehicleParkingTicket);
    }

    public Map<Vehicle, ParkingTicket> getVehicleTicket(String licensePlateNumber) {
        return details.getIssuedTickets().entrySet().stream()
                .filter(entry -> entry.getKey().getLicensePlate().equals(licensePlateNumber))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public ParkingSpotExit getParkingSpotExit(ParkingTicket parkingTicket) {
        return details.getExits().get(parkingTicket.parkingSpot());
    }

    public double calculateParkingFee(Vehicle vehicle, ParkingTicket parkingTicket) {
        Double ratePerHour = details.getHourlyParkingRates().get(vehicle.getVehicleType());
        if (ratePerHour == null) {
            throw new IllegalArgumentException("Unsupported vehicle type: " + vehicle.getVehicleType());
        }
        return parkingTicket.calculateParkingFee(ratePerHour);
    }

    public void removeParkingDetails(Vehicle vehicle) {
        ParkingTicket ticket = details.getIssuedTickets().remove(vehicle);
        if (ticket != null) {
            details.getVehiclesCount().merge(ticket.parkingSpot(), -1, Integer::sum);
        }
    }

}
