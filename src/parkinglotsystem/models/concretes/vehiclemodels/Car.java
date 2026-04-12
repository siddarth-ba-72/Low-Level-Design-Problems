package parkinglotsystem.models.concretes.vehiclemodels;

import parkinglotsystem.models.abstracts.AbstractVehicle;
import parkinglotsystem.models.concretes.humanmodels.Customer;

import static parkinglotsystem.models.concretes.vehiclemodels.VehicleType.LMV;

public class Car extends AbstractVehicle {

    private final boolean isElectric;

    public Car(String licensePlate, Customer owner, boolean isElectric) {
        super(LMV, licensePlate, owner);
        this.isElectric = isElectric;
    }

    public boolean isElectric() {
        return isElectric;
    }

}
