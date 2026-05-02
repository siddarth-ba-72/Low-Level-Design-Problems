package car_rental_system.models;

import java.time.LocalDateTime;

/**
 * VehicleLog tracks all activities related to a vehicle.
 * Requirement 7: "To monitor all activities related to each vehicle,
 *                 the system will keep a detailed log."
 */
public class VehicleLog {

    private String logId;
    private String vehicleId;
    private String activityDescription;
    private LocalDateTime activityDate;
    private String performedBy; // customerId or receptionistId

    public VehicleLog(String logId, String vehicleId, String activityDescription,
                      LocalDateTime activityDate, String performedBy) {
        this.logId = logId;
        this.vehicleId = vehicleId;
        this.activityDescription = activityDescription;
        this.activityDate = activityDate;
        this.performedBy = performedBy;
    }

    public String getLogId() { return logId; }
    public String getVehicleId() { return vehicleId; }
    public String getActivityDescription() { return activityDescription; }
    public LocalDateTime getActivityDate() { return activityDate; }
    public String getPerformedBy() { return performedBy; }
}

