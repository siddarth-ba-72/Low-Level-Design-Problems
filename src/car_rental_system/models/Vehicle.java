package car_rental_system.models;

import car_rental_system.enums.VehicleStatus;
import car_rental_system.enums.VehicleType;

import java.util.ArrayList;
import java.util.List;

/**
 * AbstractVehicle serves as the base for all vehicle types in the rental system.
 * Interview note: Use an abstract class (not interface) here because vehicles
 * share STATE (fields) — interfaces only define contracts, not state.
 */
public abstract class Vehicle {

    private String vehicleId;
    private String brand;
    private String model;
    private int year;
    private double dailyRentalRate;
    private VehicleStatus status;
    private VehicleType vehicleType;
    private ParkingStall parkingStall;
    private List<VehicleLog> vehicleLogs;

    public Vehicle(String vehicleId, String brand, String model, int year,
                   double dailyRentalRate, VehicleType vehicleType) {
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.dailyRentalRate = dailyRentalRate;
        this.vehicleType = vehicleType;
        this.status = VehicleStatus.AVAILABLE;
        this.vehicleLogs = new ArrayList<>();
    }

    public void addLog(VehicleLog log) {
        this.vehicleLogs.add(log);
    }

    // Getters & Setters
    public String getVehicleId() { return vehicleId; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public double getDailyRentalRate() { return dailyRentalRate; }
    public VehicleStatus getStatus() { return status; }
    public void setStatus(VehicleStatus status) { this.status = status; }
    public VehicleType getVehicleType() { return vehicleType; }
    public ParkingStall getParkingStall() { return parkingStall; }
    public void setParkingStall(ParkingStall parkingStall) { this.parkingStall = parkingStall; }
    public List<VehicleLog> getVehicleLogs() { return vehicleLogs; }
}
