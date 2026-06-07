package atm_system.models.data;

import atm_system.models.states.TransactionType;

public class Transaction {
    private final String id;
    private final TransactionType type;
    private final double amount;
    private final String accountNumber;
    private final String timestamp;

    public Transaction(String id, TransactionType type, double amount, String accountNumber, String timestamp) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", amount=" + amount +
                ", accountNumber='" + accountNumber + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
