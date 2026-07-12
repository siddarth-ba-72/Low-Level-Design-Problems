package payment_gateway.processor;

import payment_gateway.models.PaymentRequest;
import payment_gateway.models.PaymentResponse;
import payment_gateway.models.PaymentStatus;

public class UPIProcessor extends AbstractPaymentProcessor {
    @Override
    protected PaymentResponse doProcess(PaymentRequest request) {
        System.out.println("Processing UPI payment of " + request.getAmount() + " " + request.getCurrency());
        return new PaymentResponse(PaymentStatus.SUCCESSFUL, "UPI payment successful.");
    }
}
