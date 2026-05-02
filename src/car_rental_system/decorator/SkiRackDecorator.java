package car_rental_system.decorator;

public class SkiRackDecorator extends EquipmentDecorator {

    private static final double SKI_RACK_COST = 20.0;

    public SkiRackDecorator(RentalPriceComponent wrappedRental) {
        super(wrappedRental);
    }

    @Override
    public double getPrice() {
        return wrappedRental.getPrice() + SKI_RACK_COST;
    }

    @Override
    public String getDescription() {
        return wrappedRental.getDescription() + " + Ski Rack ($" + SKI_RACK_COST + ")";
    }
}

