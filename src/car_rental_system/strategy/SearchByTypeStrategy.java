package car_rental_system.strategy;

import car_rental_system.models.Vehicle;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Searches vehicles by their VehicleType (CAR, VAN, TRUCK, MOTORCYCLE).
 */
public class SearchByTypeStrategy implements VehicleSearchStrategy {

    @Override
    public List<Vehicle> search(List<Vehicle> vehicles, String query) {
        return vehicles.stream()
                .filter(v -> v.getVehicleType().name().equalsIgnoreCase(query))
                .collect(Collectors.toList());
    }
}

