package car_rental_system.strategy;

import car_rental_system.models.Vehicle;

import java.util.List;

/**
 * Strategy interface for searching vehicles.
 * Req 11: "Users can search for vehicles by type or model"
 *
 * Interview note: Strategy pattern lets us swap search algorithms at runtime
 * (e.g., SearchByType vs SearchByModel) without changing the caller (VehicleCatalog).
 */
public interface VehicleSearchStrategy {
    List<Vehicle> search(List<Vehicle> vehicles, String query);
}

