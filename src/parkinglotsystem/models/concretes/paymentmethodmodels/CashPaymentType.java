package parkinglotsystem.models.concretes.paymentmethodmodels;

import parkinglotsystem.models.PaymentMethod;

import static parkinglotsystem.models.concretes.paymentmethodmodels.PaymentType.CASH;

public class CashPaymentType implements PaymentMethod {

    private final PaymentType paymentType;
    private final double cashAmount;

    public CashPaymentType(double cashAmount) {
        this.paymentType = CASH;
        this.cashAmount = cashAmount;
    }

    @Override
    public PaymentType getPaymentType() {
        return paymentType;
    }

    public double getCashAmount() {
        return cashAmount;
    }

    @Override
    public boolean processPaymentTransaction(double amount) {
        System.out.println("Processing cash payment of Rs." + amount);
        return true;
    }
}
