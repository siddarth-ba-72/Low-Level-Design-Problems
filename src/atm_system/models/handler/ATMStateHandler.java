package atm_system.models.handler;

import atm_system.ATM;
import atm_system.models.data.Card;

public interface ATMStateHandler {
    void insertCard(ATM atm, Card card);
    void authenticate(ATM atm, String pin);
    void withdraw(ATM atm, double amount);
    void deposit(ATM atm, double amount);
    double checkBalance(ATM atm);
    void ejectCard(ATM atm);
}
