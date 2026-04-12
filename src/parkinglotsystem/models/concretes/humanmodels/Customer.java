package parkinglotsystem.models.concretes.humanmodels;

import parkinglotsystem.models.abstracts.AbstractHuman;

import static parkinglotsystem.models.concretes.humanmodels.HumanType.CUSTOMER;

public class Customer extends AbstractHuman {

    private final boolean isHandicapped;

    public Customer(String name, boolean isHandicapped) {
        super(CUSTOMER, name);
        this.isHandicapped = isHandicapped;
    }

    public boolean isHandicapped() {
        return isHandicapped;
    }

}
