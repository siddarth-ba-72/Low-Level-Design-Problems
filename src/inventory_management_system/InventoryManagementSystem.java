package inventory_management_system;

import inventory_management_system.exception.InventoryException;
import inventory_management_system.models.MovementType;
import inventory_management_system.models.Product;
import inventory_management_system.models.StockMovement;
import inventory_management_system.models.Warehouse;
import inventory_management_system.observer.InventoryObserver;
import inventory_management_system.strategy.RestockStrategy;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class InventoryManagementSystem {
    // Volatile ensures visibility across threads during double-checked locking
    private static volatile InventoryManagementSystem instance;
    private static final Object lock = new Object();

    private final ConcurrentHashMap<String, Warehouse> warehouses;
    private final CopyOnWriteArrayList<InventoryObserver> observers;

    private InventoryManagementSystem() {
        this.warehouses = new ConcurrentHashMap<>();
        this.observers = new CopyOnWriteArrayList<>();
    }

    public static InventoryManagementSystem getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new InventoryManagementSystem();
                }
            }
        }
        return instance;
    }

    public void addWarehouse(Warehouse warehouse) {
        warehouses.put(warehouse.getId(), warehouse);
    }

    public void addStock(String warehouseId, String productId, int quantity) {
        Warehouse warehouse = getWarehouseOrThrow(warehouseId);
        StockMovement movement = warehouse.addStock(productId, quantity);

        int newQuantity = warehouse.getStock(productId);
        notifyStockUpdated(warehouseId, productId, newQuantity);
        notifyStockMovement(movement);
        checkRestock(warehouse, productId, newQuantity);
    }

    public void removeStock(String warehouseId, String productId, int quantity) {
        Warehouse warehouse = getWarehouseOrThrow(warehouseId);
        StockMovement movement = warehouse.removeStock(productId, quantity);

        int newQuantity = warehouse.getStock(productId);
        notifyStockUpdated(warehouseId, productId, newQuantity);
        notifyStockMovement(movement);
        checkRestock(warehouse, productId, newQuantity);
    }

    public synchronized void transferStock(String fromWarehouseId, String toWarehouseId,
                                           String productId, int quantity) {
        Warehouse from = getWarehouseOrThrow(fromWarehouseId);
        Warehouse to = getWarehouseOrThrow(toWarehouseId);

        // Remove from the source first. If the source lacks stock this throws
        // before the destination is touched.
        from.decreaseForTransfer(productId, quantity);
        try {
            to.increaseForTransfer(productId, quantity);
        } catch (RuntimeException e) {
            // Destination add failed (e.g., product not registered there).
            // Return the units to the source so no stock is lost.
            from.increaseForTransfer(productId, quantity);
            throw e;
        }

        // Record one TRANSFER movement in both warehouses' histories.
        Product product = from.getProduct(productId);
        StockMovement transfer = new StockMovement(
                product, MovementType.TRANSFER,
                quantity, from.getName(), to.getName());
        from.recordMovement(transfer);
        to.recordMovement(transfer);

        notifyStockUpdated(fromWarehouseId, productId, from.getStock(productId));
        notifyStockUpdated(toWarehouseId, productId, to.getStock(productId));
        notifyStockMovement(transfer);
        checkRestock(from, productId, from.getStock(productId));
    }

    public void addObserver(InventoryObserver observer) {
        observers.add(observer);
    }

    public Warehouse getWarehouse(String warehouseId) {
        return warehouses.get(warehouseId);
    }

    private Warehouse getWarehouseOrThrow(String warehouseId) {
        Warehouse warehouse = warehouses.get(warehouseId);
        if (warehouse == null) {
            throw new InventoryException("Warehouse not found: " + warehouseId);
        }
        return warehouse;
    }

    private void notifyStockUpdated(String warehouseId, String productId, int newQuantity) {
        for (InventoryObserver observer : observers) {
            observer.onStockUpdated(warehouseId, productId, newQuantity);
        }
    }

    private void notifyStockMovement(StockMovement movement) {
        for (InventoryObserver observer : observers) {
            observer.onStockMovement(movement);
        }
    }

    private void checkRestock(Warehouse warehouse, String productId, int currentQuantity) {
        RestockStrategy strategy = warehouse.getRestockStrategy();
        if (strategy != null && strategy.shouldRestock(productId, currentQuantity)) {
            int restockQty = strategy.getRestockQuantity(productId);
            System.out.println("[RESTOCK SUGGESTION] " + productId + " in " +
                    warehouse.getId() + " needs restocking. Order quantity: " + restockQty);
        }
    }

    public static void resetInstance() {
        synchronized (lock) {
            instance = null;
        }
    }
}
