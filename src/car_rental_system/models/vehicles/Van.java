package car_rental_system.models.vehicles;

import car_rental_system.enums.VanType;
import car_rental_system.enums.VehicleType;
import car_rental_system.models.Vehicle;

/**
 * Req 3: Vans can be passenger or cargo.
 */
public class Van extends Vehicle {

    private final VanType vanType;

    public Van(String vehicleId, String brand, String model, int year,
               double dailyRentalRate, VanType vanType) {
        super(vehicleId, brand, model, year, dailyRentalRate, VehicleType.VAN);
        this.vanType = vanType;
    }

    public VanType getVanType() { return vanType; }
}
