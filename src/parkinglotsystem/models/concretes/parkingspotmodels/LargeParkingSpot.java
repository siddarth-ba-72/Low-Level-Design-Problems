package parkinglotsystem.models.concretes.parkingspotmodels;

import parkinglotsystem.models.abstracts.AbstractParkingSpot;

import static parkinglotsystem.models.concretes.parkingspotmodels.ParkingSpotType.LARGE;

public final class LargeParkingSpot extends AbstractParkingSpot {

    private LargeParkingSpot() {
        super(LARGE);
    }

    private static class LargeParkingSpotHolder {
        private static final LargeParkingSpot INSTANCE = new LargeParkingSpot();
    }

    public static LargeParkingSpot getInstance() {
        return LargeParkingSpotHolder.INSTANCE;
    }

}
