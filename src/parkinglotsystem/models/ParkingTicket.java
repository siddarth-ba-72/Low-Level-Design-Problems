package parkinglotsystem.models;

public record ParkingTicket(
        String ticketId,
        Vehicle vehicle,
        ParkingSpot parkingSpot,
        long entryTime
) {

    public double calculateParkingFee(Double ratePerHour) {
        long currentTime = System.currentTimeMillis();
        long durationInHours = (currentTime - entryTime) / (1000 * 60 * 60);
        return durationInHours * ratePerHour;
    }

}
