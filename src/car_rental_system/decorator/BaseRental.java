package car_rental_system.decorator;

/**
 * The "Concrete Component" — base rental price for a vehicle over a number of days.
 */
public class BaseRental implements RentalPriceComponent {

    private final double dailyRate;
    private final int rentalDays;

    public BaseRental(double dailyRate, int rentalDays) {
        this.dailyRate = dailyRate;
        this.rentalDays = rentalDays;
    }

    @Override
    public double getPrice() {
        return dailyRate * rentalDays;
    }

    @Override
    public String getDescription() {
        return "Base rental (" + rentalDays + " days @ $" + dailyRate + "/day)";
    }
}

