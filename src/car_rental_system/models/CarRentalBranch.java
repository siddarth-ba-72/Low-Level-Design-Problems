package car_rental_system.models;

import car_rental_system.models.people.Receptionist;

import java.util.List;
import java.util.Optional;

/**
 * Req 12: The system will oversee multiple branches of the car rental service.
 * Req 13: Each branch has parking stalls.
 */
public class CarRentalBranch {

    private String branchId;
    private String branchName;
    private String address;
    private VehicleCatalog vehicleCatalog;
    private List<ParkingStall> parkingStalls;
    private List<Receptionist> receptionists;

    public CarRentalBranch(String branchId, String branchName, String address,
                           List<ParkingStall> parkingStalls, List<Receptionist> receptionists) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.address = address;
        this.parkingStalls = parkingStalls;
        this.receptionists = receptionists;
        this.vehicleCatalog = new VehicleCatalog();
    }

    /** Find an unoccupied stall to park a newly added vehicle */
    public Optional<ParkingStall> getAvailableStall() {
        return parkingStalls.stream()
                .filter(stall -> !stall.isOccupied())
                .findFirst();
    }

    public void addVehicleToCatalog(Vehicle vehicle) {
        vehicleCatalog.addVehicle(vehicle);
        // Park vehicle in an available stall
        getAvailableStall().ifPresent(stall -> stall.parkVehicle(vehicle));
    }

    // Getters
    public String getBranchId() { return branchId; }
    public String getBranchName() { return branchName; }
    public String getAddress() { return address; }
    public VehicleCatalog getVehicleCatalog() { return vehicleCatalog; }
    public List<ParkingStall> getParkingStalls() { return parkingStalls; }
    public List<Receptionist> getReceptionists() { return receptionists; }
}
