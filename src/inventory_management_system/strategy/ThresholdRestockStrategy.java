package inventory_management_system.strategy;

import inventory_management_system.exception.InventoryException;

public class ThresholdRestockStrategy implements RestockStrategy {
    private final int reorderLevel;
    private final int restockQuantity;

    public ThresholdRestockStrategy(int reorderLevel, int restockQuantity) {
        if (reorderLevel < 0) {
            throw new InventoryException("Reorder level cannot be negative");
        }
        if (restockQuantity <= 0) {
            throw new InventoryException("Restock quantity must be positive");
        }
        this.reorderLevel = reorderLevel;
        this.restockQuantity = restockQuantity;
    }

    @Override
    public boolean shouldRestock(String productId, int currentQuantity) {
        return currentQuantity < reorderLevel;
    }

    @Override
    public int getRestockQuantity(String productId) {
        return restockQuantity;
    }
}
