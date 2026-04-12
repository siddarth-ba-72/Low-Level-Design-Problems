package parkinglotsystem;

import parkinglotsystem.details.ParkingLotSystemDetails;
import parkinglotsystem.facade.ParkingLotSystemFacade;
import parkinglotsystem.models.DisplayBoard;
import parkinglotsystem.models.ParkingSpot;
import parkinglotsystem.models.Vehicle;
import parkinglotsystem.models.concretes.humanmodels.Agent;
import parkinglotsystem.models.concretes.paymentmethodmodels.PaymentType;

import java.util.List;
import java.util.Map;

public final class ParkingLotSystem {

    private final ParkingLotSystemDetails details;
    private final ParkingLotSystemFacade facade;
    private Agent systemAgent;
    private final DisplayBoard displayBoard;

    private ParkingLotSystem() {
        this.details = ParkingLotSystemDetails.getInstance();
        this.facade = new ParkingLotSystemFacade();
        this.displayBoard = DisplayBoard.getInstance();
    }

    public static ParkingLotSystem getInstance() {
        return ParkingLotSystemHolder.INSTANCE;
    }

    // Initialization

    public void initializeParkingSpots() {
        List<ParkingSpot> parkingSpots = facade.initializeParkingSpots();
        parkingSpots.forEach(spot -> details.getVehiclesCount().put(spot, 0));
        this.details.setEntrances(facade.initializeEntrances(parkingSpots));
        this.details.setExits(facade.initializeExits(parkingSpots));
    }

    public void initializeAgent() {
        this.systemAgent = facade.createAgent();
    }

    // Parking and Unparking Methods

    public void parkVehicle(Vehicle vehicle) {
        facade.parkVehicle(systemAgent, vehicle, displayBoard);
    }

    public void unparkVehicle(String licensePlateNumber, PaymentType paymentType, String cardNumber, String cardHolderName) {
        facade.unparkVehicle(systemAgent, licensePlateNumber, paymentType, cardNumber, cardHolderName);
    }

    // Getters

    public Agent getSystemAgent() {
        return systemAgent;
    }

    public void getCurrentOccupancy() {
        displayBoard.showAvailableSpots(Map.copyOf(details.getVehiclesCount()));
    }

    public void showVehicleParkingTickets() {
        displayBoard.showVehicleParkingTickets(Map.copyOf(details.getIssuedTickets()));
    }

    // Inner static class for lazy-loaded singleton instance

    private static class ParkingLotSystemHolder {
        private static final ParkingLotSystem INSTANCE = new ParkingLotSystem();
    }

}
