package car_rental_system.models.vehicles;

import car_rental_system.enums.CarType;
import car_rental_system.enums.VehicleType;
import car_rental_system.models.Vehicle;

/**
 * Req 3: Cars can be economy, luxury, standard, or compact.
 */
public class Car extends Vehicle {

    private CarType carType;

    public Car(String vehicleId, String brand, String model, int year,
               double dailyRentalRate, CarType carType) {
        super(vehicleId, brand, model, year, dailyRentalRate, VehicleType.CAR);
        this.carType = carType;
    }

    public CarType getCarType() { return carType; }
}
