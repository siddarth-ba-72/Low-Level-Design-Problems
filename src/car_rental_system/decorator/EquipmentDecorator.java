package car_rental_system.decorator;

/**
 * Abstract Decorator — wraps a RentalPriceComponent.
 * All equipment decorators extend this.
 */
public abstract class EquipmentDecorator implements RentalPriceComponent {

    protected final RentalPriceComponent wrappedRental;

    public EquipmentDecorator(RentalPriceComponent wrappedRental) {
        this.wrappedRental = wrappedRental;
    }

    @Override
    public double getPrice() {
        return wrappedRental.getPrice();
    }

    @Override
    public String getDescription() {
        return wrappedRental.getDescription();
    }
}

