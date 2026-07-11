package inventory_management_system.observer;

import inventory_management_system.models.StockMovement;

public interface InventoryObserver {
    void onStockUpdated(String warehouseId, String productId, int newQuantity);

    void onStockMovement(StockMovement movement);
}
