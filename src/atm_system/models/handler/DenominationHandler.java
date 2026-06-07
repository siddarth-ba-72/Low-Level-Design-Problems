package atm_system.models.handler;

import atm_system.models.states.Denomination;

import java.util.Map;

public class DenominationHandler implements CashHandler {
    private final Denomination denomination;
    private int count;
    private CashHandler nextHandler;

    public DenominationHandler(Denomination denomination, int count) {
        this.denomination = denomination;
        this.count = count;
    }

    @Override
    public void setNextHandler(CashHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void dispense(int amount, Map<Denomination, Integer> result) {
        if (amount > 0) {
            int billValue = denomination.getValue();
            int billsNeeded = amount / billValue;
            int billsToDispense = Math.min(billsNeeded, count);
            if (billsToDispense > 0) {
                result.put(denomination, billsToDispense);
                count -= billsToDispense;
            }
            int remainingAmount = amount - (billsToDispense * billValue);
            if (remainingAmount > 0 && nextHandler != null) {
                nextHandler.dispense(remainingAmount, result);
            }
        }
    }

    public int getCount() {
        return count;
    }

    public Denomination getDenomination() {
        return denomination;
    }

    public void addBills(int count) {
        this.count += count;
    }

    public void removeBills(int count) {
        this.count -= count;
    }
}
