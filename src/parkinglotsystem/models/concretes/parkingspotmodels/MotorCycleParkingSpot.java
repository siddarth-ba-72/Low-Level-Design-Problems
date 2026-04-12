package parkinglotsystem.models.concretes.parkingspotmodels;

import parkinglotsystem.models.abstracts.AbstractParkingSpot;

import static parkinglotsystem.models.concretes.parkingspotmodels.ParkingSpotType.MOTORCYCLE;

public final class MotorCycleParkingSpot extends AbstractParkingSpot {

    private MotorCycleParkingSpot() {
        super(MOTORCYCLE);
    }

    private static class MotorCycleParkingSpotHolder {
        private static final MotorCycleParkingSpot INSTANCE = new MotorCycleParkingSpot();
    }

    public static MotorCycleParkingSpot getInstance() {
        return MotorCycleParkingSpotHolder.INSTANCE;
    }

}
