package parkinglotsystem.models.abstracts;

import parkinglotsystem.models.Human;
import parkinglotsystem.models.concretes.humanmodels.HumanType;

/**
 * Abstract class: holds SHARED STATE (name) that ALL humans have.
 *
 * WHY abstract class here?
 *   → Every human (Customer, Admin, Agent) has a name.
 *   → Interface Human defines the contract, this class provides common fields.
 */
public abstract class AbstractHuman implements Human {

    private final HumanType humanType;
    private final String name;

    protected AbstractHuman(HumanType humanType, String name) {
        this.humanType = humanType;
        this.name = name;
    }

    @Override
    public HumanType getHumanType() {
        return humanType;
    }

    @Override
    public String getName() {
        return name;
    }

}

