package inventory_management_system.strategy;

public interface RestockStrategy {
    boolean shouldRestock(String productId, int currentQuantity);

    int getRestockQuantity(String productId);
}
