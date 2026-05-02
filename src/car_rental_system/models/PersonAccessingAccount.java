package car_rental_system.models;

/**
 * Abstract base for any person who can log into the system and interact with rentals.
 * Implements the Person interface for basic identity.
 *
 * Interview note: This is an abstract class (not interface) because it captures
 * shared behaviour (login/logout/search) — forcing concrete subclasses to implement
 * role-specific details.
 */
public abstract class PersonAccessingAccount implements Person {

    public abstract void login(String id, String password);

    public abstract void logout();

    public abstract void bookVehicle();

    /** Search for an available vehicle in the given catalog */
    public abstract Vehicle searchVehicle(VehicleCatalog catalog);

    /** Update personal profile details */
    public abstract PersonAccessingAccount updateProfile(String name, int age);
}
