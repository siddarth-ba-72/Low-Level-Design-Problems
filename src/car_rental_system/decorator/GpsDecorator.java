package car_rental_system.decorator;

public class GpsDecorator extends EquipmentDecorator {

    private static final double GPS_DAILY_COST = 10.0;

    public GpsDecorator(RentalPriceComponent wrappedRental) {
        super(wrappedRental);
    }

    @Override
    public double getPrice() {
        return wrappedRental.getPrice() + GPS_DAILY_COST;
    }

    @Override
    public String getDescription() {
        return wrappedRental.getDescription() + " + GPS Navigation ($" + GPS_DAILY_COST + ")";
    }
}

