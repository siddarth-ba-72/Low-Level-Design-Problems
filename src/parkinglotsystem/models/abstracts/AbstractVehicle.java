package parkinglotsystem.models.abstracts;

import parkinglotsystem.models.Vehicle;
import parkinglotsystem.models.concretes.humanmodels.Customer;
import parkinglotsystem.models.concretes.vehiclemodels.VehicleType;

public abstract class AbstractVehicle implements Vehicle {

    private final VehicleType vehicleType;
    private final String licensePlate;
    private final Customer owner;

    protected AbstractVehicle(VehicleType vehicleType, String licensePlate, Customer owner) {
        this.vehicleType = vehicleType;
        this.licensePlate = licensePlate;
        this.owner = owner;
    }

    @Override
    public String getLicensePlate() {
        return licensePlate;
    }

    @Override
    public Customer getOwner() {
        return owner;
    }

    @Override
    public VehicleType getVehicleType() {
        return vehicleType;
    }

}

