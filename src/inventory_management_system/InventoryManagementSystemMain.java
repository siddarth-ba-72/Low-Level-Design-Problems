package inventory_management_system;

import inventory_management_system.exception.InventoryException;
import inventory_management_system.models.Category;
import inventory_management_system.models.Product;
import inventory_management_system.models.StockMovement;
import inventory_management_system.models.Warehouse;
import inventory_management_system.observer.LowStockAlertObserver;
import inventory_management_system.strategy.ThresholdRestockStrategy;

import java.util.*;
import java.util.concurrent.*;
import java.time.*;
import java.time.format.*;

public class InventoryManagementSystemMain {
    public static void main(String[] args) {
        InventoryManagementSystem system = InventoryManagementSystem.getInstance();
        system.addObserver(new LowStockAlertObserver(10));

        // Create warehouses
        Warehouse warehouse1 = new Warehouse("W1", "Main Warehouse");
        Warehouse warehouse2 = new Warehouse("W2", "East Warehouse");
        system.addWarehouse(warehouse1);
        system.addWarehouse(warehouse2);

        // Each warehouse can run its own restock policy
        warehouse1.setRestockStrategy(new ThresholdRestockStrategy(10, 50));
        warehouse2.setRestockStrategy(new ThresholdRestockStrategy(10, 50));

        // Create products
        Product laptop = new Product("P1", "Laptop", Category.ELECTRONICS, 999.99);
        Product tshirt = new Product("P2", "T-Shirt", Category.CLOTHING, 29.99);
        Product rice = new Product("P3", "Rice Bag", Category.FOOD, 12.50);

        // Register products in warehouses
        warehouse1.addProduct(laptop);
        warehouse1.addProduct(tshirt);
        warehouse1.addProduct(rice);
        warehouse2.addProduct(laptop);
        warehouse2.addProduct(rice);

        // Scenario 1: Add stock
        System.out.println("========== SCENARIO 1: ADD STOCK ==========");
        system.addStock("W1", "P1", 50);
        system.addStock("W1", "P2", 200);
        system.addStock("W1", "P3", 100);
        system.addStock("W2", "P1", 30);
        system.addStock("W2", "P3", 75);

        System.out.println("\nW1 Laptop stock: " + warehouse1.getStock("P1"));
        System.out.println("W2 Laptop stock: " + warehouse2.getStock("P1"));

        // Scenario 2: Remove stock (triggers low stock alert)
        System.out.println("\n========== SCENARIO 2: REMOVE STOCK ==========");
        system.removeStock("W1", "P1", 45);
        System.out.println("W1 Laptop stock after removal: " + warehouse1.getStock("P1"));

        // Scenario 3: Transfer between warehouses
        System.out.println("\n========== SCENARIO 3: TRANSFER STOCK ==========");
        system.transferStock("W2", "W1", "P1", 20);
        System.out.println("W1 Laptop stock after transfer: " + warehouse1.getStock("P1"));
        System.out.println("W2 Laptop stock after transfer: " + warehouse2.getStock("P1"));

        // Scenario 4: Error handling
        System.out.println("\n========== SCENARIO 4: ERROR HANDLING ==========");
        try {
            system.removeStock("W2", "P1", 100);
        } catch (InventoryException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Print movement history
        System.out.println("\n========== MOVEMENT HISTORY (W1) ==========");
        for (StockMovement m : warehouse1.getMovements()) {
            System.out.println(m);
        }
    }
}