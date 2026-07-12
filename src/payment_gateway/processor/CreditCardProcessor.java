package payment_gateway.processor;

import payment_gateway.models.PaymentRequest;
import payment_gateway.models.PaymentResponse;
import payment_gateway.models.PaymentStatus;

public class CreditCardProcessor extends AbstractPaymentProcessor {
    @Override
    protected PaymentResponse doProcess(PaymentRequest request) {
        System.out.println("Processing credit card payment of amount " + request.getAmount() + " " + request.getCurrency());
        // Simulate interaction with Visa/Mastercard network
        return new PaymentResponse(PaymentStatus.SUCCESSFUL, "Credit Card payment successful.");
    }
}
