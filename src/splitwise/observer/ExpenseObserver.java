package splitwise.observer;

import splitwise.models.Expense;

public interface ExpenseObserver {
    void onExpenseAdded(Expense expense);

    void onSettlement(String fromUserId, String toUserId, double amount);
}
