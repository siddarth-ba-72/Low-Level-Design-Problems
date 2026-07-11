package inventory_management_system.observer;

import inventory_management_system.models.StockMovement;

public class LowStockAlertObserver implements InventoryObserver {
    private final int threshold;

    public LowStockAlertObserver(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public void onStockUpdated(String warehouseId, String productId, int newQuantity) {
        if (newQuantity < threshold) {
            System.out.println("[LOW STOCK ALERT] " + productId + " in warehouse " +
                    warehouseId + ": only " + newQuantity + " units remaining");
        }
    }

    @Override
    public void onStockMovement(StockMovement movement) {
        System.out.println("[MOVEMENT] " + movement.getType() + ": " +
                movement.getQuantity() + "x " + movement.getProduct().getName() +
                " at " + (movement.getDestinationWarehouse() != null ?
                movement.getDestinationWarehouse() : movement.getSourceWarehouse()));
    }
}
