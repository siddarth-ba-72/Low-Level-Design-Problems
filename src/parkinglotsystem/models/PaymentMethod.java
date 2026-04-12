package parkinglotsystem.models;

import parkinglotsystem.models.concretes.paymentmethodmodels.PaymentType;

public interface PaymentMethod {

    PaymentType getPaymentType();

    boolean processPaymentTransaction(double amount);

}
