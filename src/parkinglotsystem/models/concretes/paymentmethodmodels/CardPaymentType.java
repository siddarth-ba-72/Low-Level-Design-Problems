package parkinglotsystem.models.concretes.paymentmethodmodels;

import parkinglotsystem.models.PaymentMethod;

import static parkinglotsystem.models.concretes.paymentmethodmodels.PaymentType.CARD;

public class CardPaymentType implements PaymentMethod {

    private final PaymentType paymentType;
    private final String cardNumber;
    private final String cardHolderName;
    private final double amount;

    public CardPaymentType(String cardNumber, String cardHolderName, double amount) {
        this.paymentType = CARD;
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.amount = amount;
    }

    @Override
    public PaymentType getPaymentType() {
        return paymentType;
    }

    @Override
    public boolean processPaymentTransaction(double amount) {
        // Simulate card payment processing logic
        System.out.println("Processing card payment of $" + amount + " for card holder: " + cardHolderName);
        // In a real implementation, integrate with a payment gateway here
        return true; // Assume payment is successful for simulation
    }
}
