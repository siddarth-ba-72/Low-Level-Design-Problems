package payment_gateway.observer;

import payment_gateway.models.Transaction;

public interface PaymentObserver {
    void onTransactionUpdate(Transaction transaction);
}
