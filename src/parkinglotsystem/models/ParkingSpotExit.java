package parkinglotsystem.models;

import parkinglotsystem.factory.PaymentMethodFactory;
import parkinglotsystem.models.concretes.paymentmethodmodels.PaymentType;

public class ParkingSpotExit {

    private final ParkingSpot parkingSpot;

    public ParkingSpotExit(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public boolean processPayment(PaymentType paymentType, String cardNumber, String cardHolderName, double amount) {
        PaymentMethod paymentMethod = PaymentMethodFactory.createPaymentMethod(paymentType, cardNumber, cardHolderName, amount);
        return paymentMethod.processPaymentTransaction(amount);
    }

}

