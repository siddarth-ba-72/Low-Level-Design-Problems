package payment_gateway.processor;

import payment_gateway.models.PaymentRequest;
import payment_gateway.models.PaymentResponse;
import payment_gateway.models.PaymentStatus;

public abstract class AbstractPaymentProcessor implements PaymentProcessor {
    private static final int MAX_RETRIES = 3;

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        int attempts = 0;
        PaymentResponse response;
        // Each attempt carries the same request (and idempotency key), so a retried
        // charge is de-duplicated downstream rather than charging the payer twice.
        do {
            response = doProcess(request);
            attempts++;
        } while (response.getStatus() == PaymentStatus.FAILED && attempts < MAX_RETRIES);
        return response;
    }

    protected abstract PaymentResponse doProcess(PaymentRequest request);
}
