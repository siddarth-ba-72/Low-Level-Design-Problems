package splitwise.observer;

import splitwise.models.Expense;
import splitwise.models.Split;

public class EmailNotificationObserver implements ExpenseObserver {
    @Override
    public void onExpenseAdded(Expense expense) {
        // Notify each participant individually with their own share
        for (Split split : expense.getSplits()) {
            String detail = split.getUserId().equals(expense.getPaidByUserId())
                    ? String.format("you paid $%.2f", expense.getAmount())
                    : String.format("your share is $%.2f", split.getAmount());
            System.out.printf("[Email to %s] New expense '%s': %s%n",
                    split.getUserId(), expense.getDescription(), detail);
        }
    }

    @Override
    public void onSettlement(String fromUserId, String toUserId, double amount) {
        // Notify both parties to the settlement
        System.out.printf("[Email to %s] You paid %s $%.2f%n", fromUserId, toUserId, amount);
        System.out.printf("[Email to %s] %s paid you $%.2f%n", toUserId, fromUserId, amount);
    }
}
