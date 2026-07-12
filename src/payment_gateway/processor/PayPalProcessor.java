package payment_gateway.processor;

import payment_gateway.models.PaymentRequest;
import payment_gateway.models.PaymentResponse;
import payment_gateway.models.PaymentStatus;

public class PayPalProcessor extends AbstractPaymentProcessor {
    @Override
    protected PaymentResponse doProcess(PaymentRequest request) {
        System.out.println("Redirecting to PayPal for payer " + request.getPayerId());
        // Simulate PayPal API interaction
        return new PaymentResponse(PaymentStatus.SUCCESSFUL, "Paypal payment successful.");
    }
}
