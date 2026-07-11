package inventory_management_system.models;

import inventory_management_system.exception.InventoryException;
import inventory_management_system.strategy.RestockStrategy;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Warehouse {
    private final String id;
    private final String name;
    private final ConcurrentHashMap<String, Integer> inventory;
    private final ConcurrentHashMap<String, Product> products;
    private final CopyOnWriteArrayList<StockMovement> movements;
    private volatile RestockStrategy restockStrategy;

    public Warehouse(String id, String name) {
        this.id = id;
        this.name = name;
        this.inventory = new ConcurrentHashMap<>();
        this.products = new ConcurrentHashMap<>();
        this.movements = new CopyOnWriteArrayList<>();
    }

    public void setRestockStrategy(RestockStrategy strategy) {
        this.restockStrategy = strategy;
    }

    public RestockStrategy getRestockStrategy() {
        return restockStrategy;
    }

    public synchronized void addProduct(Product product) {
        if (products.containsKey(product.getId())) {
            throw new InventoryException(
                    "Product " + product.getName() + " already exists in " + name);
        }
        products.put(product.getId(), product);
        inventory.put(product.getId(), 0);
    }

    public synchronized StockMovement addStock(String productId, int quantity) {
        validateProductExists(productId);
        if (quantity <= 0) {
            throw new InventoryException("Quantity must be positive");
        }

        inventory.merge(productId, quantity, Integer::sum);

        StockMovement movement = new StockMovement(
                products.get(productId), MovementType.ADDITION,
                quantity, null, name);
        movements.add(movement);
        return movement;
    }

    public synchronized StockMovement removeStock(String productId, int quantity) {
        validateProductExists(productId);
        if (quantity <= 0) {
            throw new InventoryException("Quantity must be positive");
        }

        int currentStock = inventory.getOrDefault(productId, 0);
        if (currentStock < quantity) {
            throw new InventoryException(
                    "Insufficient stock for " + products.get(productId).getName() +
                            " in " + name + ". Available: " + currentStock +
                            ", Requested: " + quantity);
        }

        inventory.put(productId, currentStock - quantity);

        StockMovement movement = new StockMovement(
                products.get(productId), MovementType.REMOVAL,
                quantity, name, null);
        movements.add(movement);
        return movement;
    }

    // Transfer helpers: adjust quantity under the warehouse lock without
    // recording a per-warehouse movement. The system records one TRANSFER
    // movement for the whole operation.
    public synchronized void decreaseForTransfer(String productId, int quantity) {
        validateProductExists(productId);
        if (quantity <= 0) {
            throw new InventoryException("Quantity must be positive");
        }
        int currentStock = inventory.getOrDefault(productId, 0);
        if (currentStock < quantity) {
            throw new InventoryException(
                    "Insufficient stock for " + products.get(productId).getName() +
                            " in " + name + ". Available: " + currentStock +
                            ", Requested: " + quantity);
        }
        inventory.put(productId, currentStock - quantity);
    }

    public synchronized void increaseForTransfer(String productId, int quantity) {
        validateProductExists(productId);
        inventory.merge(productId, quantity, Integer::sum);
    }

    public synchronized void recordMovement(StockMovement movement) {
        movements.add(movement);
    }

    public int getStock(String productId) {
        return inventory.getOrDefault(productId, 0);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Product getProduct(String productId) {
        return products.get(productId);
    }

    public List<StockMovement> getMovements() {
        return Collections.unmodifiableList(movements);
    }

    private void validateProductExists(String productId) {
        if (!products.containsKey(productId)) {
            throw new InventoryException(
                    "Product " + productId + " not found in warehouse " + name);
        }
    }
}
