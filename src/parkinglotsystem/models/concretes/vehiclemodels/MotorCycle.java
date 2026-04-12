package parkinglotsystem.models.concretes.vehiclemodels;

import parkinglotsystem.models.abstracts.AbstractVehicle;
import parkinglotsystem.models.concretes.humanmodels.Customer;

import static parkinglotsystem.models.concretes.vehiclemodels.VehicleType.MOTORCYCLE;

public class MotorCycle extends AbstractVehicle {

    public MotorCycle(String licensePlate, Customer owner) {
        super(MOTORCYCLE, licensePlate, owner);
    }

}
