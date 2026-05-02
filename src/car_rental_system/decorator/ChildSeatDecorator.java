package car_rental_system.decorator;

public class ChildSeatDecorator extends EquipmentDecorator {

    private static final double CHILD_SEAT_COST = 15.0;

    public ChildSeatDecorator(RentalPriceComponent wrappedRental) {
        super(wrappedRental);
    }

    @Override
    public double getPrice() {
        return wrappedRental.getPrice() + CHILD_SEAT_COST;
    }

    @Override
    public String getDescription() {
        return wrappedRental.getDescription() + " + Child Seat ($" + CHILD_SEAT_COST + ")";
    }
}

