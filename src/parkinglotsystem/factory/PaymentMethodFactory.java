package parkinglotsystem.factory;

import parkinglotsystem.models.PaymentMethod;
import parkinglotsystem.models.concretes.paymentmethodmodels.CardPaymentType;
import parkinglotsystem.models.concretes.paymentmethodmodels.CashPaymentType;
import parkinglotsystem.models.concretes.paymentmethodmodels.PaymentType;

public class PaymentMethodFactory {

    public static PaymentMethod createPaymentMethod(
            PaymentType type,
            String cardNumber,
            String cardHolderName,
            double amount
    ) {
        return switch (type) {
            case CARD -> new CardPaymentType(cardNumber, cardHolderName, amount);
            case CASH -> new CashPaymentType(amount);
        };
    }

}
