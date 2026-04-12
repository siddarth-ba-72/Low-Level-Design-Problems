package parkinglotsystem.models.concretes.parkingspotmodels;

import parkinglotsystem.models.Vehicle;
import parkinglotsystem.models.abstracts.AbstractParkingSpot;

import static parkinglotsystem.models.concretes.parkingspotmodels.ParkingSpotType.HANDICAPPED;

public final class HandicappedParkingSpot extends AbstractParkingSpot {

    private HandicappedParkingSpot() {
        super(HANDICAPPED);
    }

    private static class HandicappedParkingSpotHolder {
        private static final HandicappedParkingSpot INSTANCE = new HandicappedParkingSpot();
    }

    public static HandicappedParkingSpot getInstance() {
        return HandicappedParkingSpotHolder.INSTANCE;
    }

    @Override
    public void parkVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        // Could add handicap permit validation here
        if (!vehicle.getOwner().isHandicapped()) {
            throw new IllegalArgumentException("Vehicle owner does not have a handicap permit");
        }
        super.parkVehicle(vehicle);
    }

}
