package parkinglotsystem.facade;

import parkinglotsystem.models.DisplayBoard;
import parkinglotsystem.models.*;
import parkinglotsystem.models.concretes.humanmodels.Agent;
import parkinglotsystem.models.concretes.parkingspotmodels.*;
import parkinglotsystem.models.concretes.paymentmethodmodels.PaymentType;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ParkingLotSystemFacade {

    public List<ParkingSpot> initializeParkingSpots() {
        return List.of(
                CompactParkingSpot.getInstance(),
                HandicappedParkingSpot.getInstance(),
                LargeParkingSpot.getInstance(),
                MotorCycleParkingSpot.getInstance()
        );
    }

    public Map<ParkingSpot, ParkingSpotEntrance> initializeEntrances(List<ParkingSpot> spots) {
        Map<ParkingSpot, ParkingSpotEntrance> entrances = new LinkedHashMap<>();
        for (ParkingSpot spot : spots) {
            entrances.put(spot, new ParkingSpotEntrance(spot));
        }
        return entrances;
    }

    public Map<ParkingSpot, ParkingSpotExit> initializeExits(List<ParkingSpot> spots) {
        Map<ParkingSpot, ParkingSpotExit> exits = new LinkedHashMap<>();
        for (ParkingSpot spot : spots) {
            exits.put(spot, new ParkingSpotExit(spot));
        }
        return exits;
    }

    public Agent createAgent() {
        return Agent.getInstance();
    }

    public void parkVehicle(Agent agent, Vehicle vehicle, DisplayBoard displayBoard) {
        if (!agent.checkParkingSpotAvailability()) {
            displayBoard.showFullParkingNotification();
            return;
        }
        ParkingSpot parkingSpot = agent.assignParkingSpot(vehicle);
        if (parkingSpot != null) {
            ParkingSpotEntrance entrance = agent.getParkingSpotEntrance(parkingSpot);
            Map<Vehicle, ParkingTicket> ticketMap = entrance.issueParkingTicket(vehicle, parkingSpot);
            entrance.parkVehicle(vehicle);
            agent.addParkingDetails(parkingSpot, ticketMap);
            System.out.println("Vehicle number " + vehicle.getLicensePlate() + " parked at " + parkingSpot.getParkingSpotType().getType() + " spot.");
        } else {
            System.out.println("No available parking spot for vehicle with license plate: " + vehicle.getLicensePlate());
        }
    }

    public void unparkVehicle(
            Agent agent,
            String licensePlateNumber,
            PaymentType paymentType,
            String cardNumber,
            String cardHolderName
    ) {
        Map<Vehicle, ParkingTicket> ticket = agent.getVehicleTicket(licensePlateNumber);
        Vehicle vehicle = ticket.keySet().stream()
                .filter(v -> v.getLicensePlate().equals(licensePlateNumber))
                .findFirst()
                .orElse(null);
        if (vehicle == null) {
            System.out.println("No issued ticket found for vehicle with license plate: " + licensePlateNumber);
            return;
        }
        ParkingSpotExit exit = agent.getParkingSpotExit(ticket.get(vehicle));
        double amountToPay = agent.calculateParkingFee(vehicle, ticket.get(vehicle));
        exit.processPayment(paymentType, cardNumber, cardHolderName, amountToPay);
        agent.removeParkingDetails(vehicle);
    }

}
