package payment_gateway.processor;

import payment_gateway.models.PaymentRequest;
import payment_gateway.models.PaymentResponse;

public interface PaymentProcessor {
    PaymentResponse processPayment(PaymentRequest request);
}
