package parkinglotsystem.models.concretes.vehiclemodels;

import parkinglotsystem.models.abstracts.AbstractVehicle;
import parkinglotsystem.models.concretes.humanmodels.Customer;

import static parkinglotsystem.models.concretes.vehiclemodels.VehicleType.TRUCK;

public class Truck extends AbstractVehicle {

    private final double loadCapacityTons;

    public Truck(String licensePlate, Customer owner, double loadCapacityTons) {
        super(TRUCK, licensePlate, owner);
        this.loadCapacityTons = loadCapacityTons;
    }

    public double getLoadCapacityTons() {
        return loadCapacityTons;
    }

}
