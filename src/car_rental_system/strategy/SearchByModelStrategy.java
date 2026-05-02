package car_rental_system.strategy;

import car_rental_system.models.Vehicle;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Searches vehicles by their model name (case-insensitive partial match).
 */
public class SearchByModelStrategy implements VehicleSearchStrategy {

    @Override
    public List<Vehicle> search(List<Vehicle> vehicles, String query) {
        return vehicles.stream()
                .filter(v -> v.getModel().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}

