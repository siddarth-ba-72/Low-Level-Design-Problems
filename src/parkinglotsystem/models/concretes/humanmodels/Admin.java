package parkinglotsystem.models.concretes.humanmodels;

import parkinglotsystem.models.abstracts.AbstractHuman;

import static parkinglotsystem.models.concretes.humanmodels.HumanType.ADMIN;

public class Admin extends AbstractHuman {

    public Admin(String name) {
        super(ADMIN, name);
    }

}
