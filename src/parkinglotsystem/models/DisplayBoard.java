package parkinglotsystem.models;

import java.util.Map;

public final class DisplayBoard {

    private DisplayBoard() {
        // Private constructor to prevent instantiation
    }

    public static DisplayBoard getInstance() {
        return DisplayBoardHolder.INSTANCE;
    }

    public void showAvailableSpots(Map<ParkingSpot, Integer> vehiclesCount) {
        System.out.println("\n========== AVAILABLE SPOTS ==========");
        vehiclesCount.forEach((spot, count) ->
                System.out.println("  " + spot.getParkingSpotType().getType()
                        + " → " + count + " vehicle(s) parked"));
        System.out.println("=====================================\n");
    }

    public void showVehicleParkingTickets(Map<Vehicle, ParkingTicket> vehicleParkingTicketMap) {
        System.out.println("\n========== PARKING TICKETS ==========");
        vehicleParkingTicketMap.forEach((vehicle, ticket) ->
                System.out.println("  Vehicle " + vehicle.getLicensePlate()
                        + " → Ticket ID: " + ticket.ticketId()));
        System.out.println("=====================================\n");
    }

    public void showFullParkingNotification() {
        System.out.println("Parking Lot is Full. No available parking spots.");
    }

    private static class DisplayBoardHolder {
        private static final DisplayBoard INSTANCE = new DisplayBoard();
    }
}
