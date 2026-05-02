package car_rental_system.models;

import car_rental_system.enums.VehicleStatus;
import car_rental_system.strategy.VehicleSearchStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * VehicleCatalog holds all vehicles at a branch and delegates
 * search to a pluggable VehicleSearchStrategy.
 *
 * Interview note: Context class in the Strategy pattern.
 */
public class VehicleCatalog {

    private List<Vehicle> vehicles;
    private VehicleSearchStrategy searchStrategy;

    public VehicleCatalog() {
        this.vehicles = new ArrayList<>();
    }

    public void setSearchStrategy(VehicleSearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }

    /** Req 11: Search by type or model depending on strategy set */
    public List<Vehicle> search(String query) {
        if (searchStrategy == null) {
            throw new IllegalStateException("Search strategy not set on VehicleCatalog.");
        }
        return searchStrategy.search(vehicles, query);
    }

    /** Returns only available vehicles */
    public List<Vehicle> getAvailableVehicles() {
        return vehicles.stream()
                .filter(v -> v.getStatus() == VehicleStatus.AVAILABLE)
                .collect(Collectors.toList());
    }

    public List<Vehicle> getAllVehicles() {
        return vehicles;
    }
}
