package inventory_management_system.models;

import inventory_management_system.exception.InventoryException;

public class Product {
    private final String id;
    private final String name;
    private final Category category;
    private final double price;

    public Product(String id, String name, Category category, double price) {
        if (id == null || id.isEmpty()) {
            throw new InventoryException("Product ID cannot be null or empty");
        }
        if (name == null || name.isEmpty()) {
            throw new InventoryException("Product name cannot be null or empty");
        }
        if (category == null) {
            throw new InventoryException("Product category cannot be null");
        }
        if (price < 0) {
            throw new InventoryException("Product price cannot be negative");
        }
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " (" + category + ", $" + String.format("%.2f", price) + ")";
    }
}

