package parkinglotsystem.models;

import parkinglotsystem.models.concretes.humanmodels.Customer;
import parkinglotsystem.models.concretes.vehiclemodels.VehicleType;

public interface Vehicle {

    VehicleType getVehicleType();
    String getLicensePlate();
    Customer getOwner();

}
