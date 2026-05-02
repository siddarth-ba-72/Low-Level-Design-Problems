package car_rental_system.decorator;

/**
 * Component interface for the Decorator pattern.
 * Interview note: The Decorator pattern lets us add equipment (GPS, child seat, ski rack)
 * to a rental and stack their costs on top of the base rental price dynamically,
 * without modifying the Reservation class itself.
 *
 * Req 8: "Users can add extra equipment to their reservations"
 */
public interface RentalPriceComponent {
    double getPrice();
    String getDescription();
}

