package splitwise.models.split_types;

import splitwise.models.Split;

public class ExactSplit extends Split {
    public ExactSplit(String userId, double amount) {
        super(userId);
        setAmount(amount);
    }
}
