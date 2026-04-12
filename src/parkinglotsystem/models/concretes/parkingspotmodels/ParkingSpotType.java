package parkinglotsystem.models.concretes.parkingspotmodels;

public enum ParkingSpotType {

    COMPACT("COMPACT"),
    LARGE("LARGE"),
    HANDICAPPED("HANDICAPPED"),
    MOTORCYCLE("MOTORCYCLE");

    private final String type;

    ParkingSpotType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
