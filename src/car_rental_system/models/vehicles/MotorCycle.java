package car_rental_system.models.vehicles;

import car_rental_system.enums.MotorCycleType;
import car_rental_system.enums.VehicleType;
import car_rental_system.models.Vehicle;

/**
 * Req 3: Motorcycles can be sport, cruiser, or touring.
 */
public class MotorCycle extends Vehicle {

    private MotorCycleType motorCycleType;

    public MotorCycle(String vehicleId, String brand, String model, int year,
                      double dailyRentalRate, MotorCycleType motorCycleType) {
        super(vehicleId, brand, model, year, dailyRentalRate, VehicleType.MOTORCYCLE);
        this.motorCycleType = motorCycleType;
    }

    public MotorCycleType getMotorCycleType() { return motorCycleType; }
}
