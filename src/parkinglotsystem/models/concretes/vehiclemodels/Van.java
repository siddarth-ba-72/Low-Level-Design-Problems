package parkinglotsystem.models.concretes.vehiclemodels;

import parkinglotsystem.models.abstracts.AbstractVehicle;
import parkinglotsystem.models.concretes.humanmodels.Customer;

import static parkinglotsystem.models.concretes.vehiclemodels.VehicleType.LMV;

public class Van extends AbstractVehicle {

    public Van(String licensePlate, Customer owner) {
        super(LMV, licensePlate, owner);
    }

}
