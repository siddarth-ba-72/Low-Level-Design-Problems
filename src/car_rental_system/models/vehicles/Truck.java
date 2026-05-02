package car_rental_system.models.vehicles;

import car_rental_system.enums.TruckType;
import car_rental_system.enums.VehicleType;
import car_rental_system.models.Vehicle;

/**
 * Req 3: Trucks can be light, medium, or heavy-duty.
 */
public class Truck extends Vehicle {

    private TruckType truckType;

    public Truck(String vehicleId, String brand, String model, int year,
                 double dailyRentalRate, TruckType truckType) {
        super(vehicleId, brand, model, year, dailyRentalRate, VehicleType.TRUCK);
        this.truckType = truckType;
    }

    public TruckType getTruckType() { return truckType; }
}
