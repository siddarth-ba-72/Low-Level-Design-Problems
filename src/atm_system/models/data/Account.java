package atm_system.models.data;

import atm_system.models.exception.ATMException;

public class Account {
    private final String accountNumber;
    private double balance;

    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public synchronized void credit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public synchronized void debit(double amount) {
        if (amount > balance) {
            throw new ATMException("Insufficient funds. Account balance: $" + balance + ", requested: $" + amount);
        }
        balance -= amount;
    }
}
