package payment_gateway;

import payment_gateway.models.PaymentMethod;
import payment_gateway.models.PaymentRequest;
import payment_gateway.observer.CustomerNotifier;
import payment_gateway.observer.MerchantNotifier;
import payment_gateway.service.PaymentGatewayService;

import java.util.*;
import java.time.*;

public class PaymentGatewayMain {
    public static void main(String[] args) {
        // 1. Setup the gateway
        PaymentGatewayService paymentGateway = new PaymentGatewayService();

        // 2. Register observers to be notified of transaction events
        paymentGateway.addObserver(new MerchantNotifier());
        paymentGateway.addObserver(new CustomerNotifier());

        System.out.println("----------- SCENARIO 1: Successful Credit Card Payment -----------");
        // a. Merchant's backend creates a payment request
        PaymentRequest ccRequest = new PaymentRequest(
                "idem-cc-001", "U-123", 150.75, "INR",
                PaymentMethod.CREDIT_CARD, Map.of("cardNumber", "1234..."));

        // b. Merchant's backend sends it to the gateway
        paymentGateway.processPayment(ccRequest);

        System.out.println("\n----------- SCENARIO 2: Successful PayPal Payment -----------");
        PaymentRequest paypalRequest = new PaymentRequest(
                "idem-pp-001", "U-456", 88.50, "USD",
                PaymentMethod.PAYPAL, Map.of("email", "customer@example.com"));

        paymentGateway.processPayment(paypalRequest);
    }
}