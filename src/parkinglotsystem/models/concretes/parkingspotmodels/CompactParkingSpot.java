package parkinglotsystem.models.concretes.parkingspotmodels;

import parkinglotsystem.models.abstracts.AbstractParkingSpot;

import static parkinglotsystem.models.concretes.parkingspotmodels.ParkingSpotType.COMPACT;

public final class CompactParkingSpot extends AbstractParkingSpot {

    private CompactParkingSpot() {
        super(COMPACT);
    }

    private static class CompactParkingSpotHolder {
        private static final CompactParkingSpot INSTANCE = new CompactParkingSpot();
    }

    public static CompactParkingSpot getInstance() {
        return CompactParkingSpotHolder.INSTANCE;
    }
}
