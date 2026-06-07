package atm_system.models.handler;

import atm_system.ATM;
import atm_system.models.data.Card;
import atm_system.models.exception.ATMException;
import atm_system.models.states.ATMState;

public class IdleState implements ATMStateHandler {

    @Override
    public void insertCard(ATM atm, Card card) {
        atm.setCurrentCard(card);
        atm.setState(ATMState.CARD_INSERTED);
        System.out.println("Card " + card.getCardNumber() + " inserted successfully");
    }

    @Override
    public void authenticate(ATM atm, String pin) {
        throw new ATMException("Please insert a card first");
    }

    @Override
    public void withdraw(ATM atm, double amount) {
        throw new ATMException("Please insert a card first");
    }

    @Override
    public void deposit(ATM atm, double amount) {
        throw new ATMException("Please insert a card first");
    }

    @Override
    public double checkBalance(ATM atm) {
        throw new ATMException("Please insert a card first");
    }

    @Override
    public void ejectCard(ATM atm) {
        throw new ATMException("No card to eject");
    }
}
