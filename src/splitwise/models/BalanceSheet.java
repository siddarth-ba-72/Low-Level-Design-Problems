package splitwise.models;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BalanceSheet {
    // balances[A][B] = how much A owes B (positive = owes, negative = is owed)
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, Double>> balances;

    public BalanceSheet() {
        this.balances = new ConcurrentHashMap<>();
    }

    // Increases the debt from fromUserId to toUserId by amount
    // Also mirrors the update: toUserId's record shows they are owed
    public synchronized void updateBalance(String fromUserId, String toUserId, double amount) {
        // Forward direction: fromUser owes toUser more
        balances.computeIfAbsent(fromUserId, k -> new ConcurrentHashMap<>())
                .merge(toUserId, amount, Double::sum);

        // Mirror direction: toUser is owed more by fromUser (negative amount)
        balances.computeIfAbsent(toUserId, k -> new ConcurrentHashMap<>())
                .merge(fromUserId, -amount, Double::sum);
    }

    // Reduces the debt from fromUserId to toUserId by amount
    // If the settlement exceeds the debt, it creates a reverse debt
    public synchronized void settleUp(String fromUserId, String toUserId, double amount) {
        // Settlement is the reverse of a balance update
        updateBalance(fromUserId, toUserId, -amount);
    }

    // Returns how much userId1 owes userId2
    // Positive = userId1 owes userId2, Negative = userId2 owes userId1
    public double getBalance(String userId1, String userId2) {
        ConcurrentHashMap<String, Double> userBalances = balances.get(userId1);
        if (userBalances == null) return 0.0;
        return userBalances.getOrDefault(userId2, 0.0);
    }

    // Returns a snapshot of all balances for a user (defensive copy)
    public Map<String, Double> getBalancesForUser(String userId) {
        ConcurrentHashMap<String, Double> userBalances = balances.get(userId);
        if (userBalances == null) return new HashMap<>();
        return new HashMap<>(userBalances);
    }
}
