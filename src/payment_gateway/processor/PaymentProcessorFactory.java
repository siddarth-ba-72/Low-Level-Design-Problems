package payment_gateway.processor;

import payment_gateway.models.PaymentMethod;

public class PaymentProcessorFactory {
    public static PaymentProcessor getProcessor(PaymentMethod method) {
        return switch (method) {
            case CREDIT_CARD -> new CreditCardProcessor();
            case UPI -> new UPIProcessor();
            case PAYPAL -> new PayPalProcessor();
        };
    }
}
