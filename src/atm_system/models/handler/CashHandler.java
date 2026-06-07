package atm_system.models.handler;

import atm_system.models.states.Denomination;

import java.util.Map;

public interface CashHandler {
    void setNextHandler(CashHandler nextHandler);
    void dispense(int amount, Map<Denomination, Integer> result);
}
