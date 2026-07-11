package inventory_management_system.models;

public class StockMovement {
    private final Product product;
    private final MovementType type;
    private final int quantity;
    private final String sourceWarehouse;
    private final String destinationWarehouse;
    private final java.time.LocalDateTime timestamp;

    public StockMovement(Product product, MovementType type, int quantity,
                         String sourceWarehouse, String destinationWarehouse) {
        this.product = product;
        this.type = type;
        this.quantity = quantity;
        this.sourceWarehouse = sourceWarehouse;
        this.destinationWarehouse = destinationWarehouse;
        this.timestamp = java.time.LocalDateTime.now();
    }

    public Product getProduct() {
        return product;
    }

    public MovementType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSourceWarehouse() {
        return sourceWarehouse;
    }

    public String getDestinationWarehouse() {
        return destinationWarehouse;
    }

    public java.time.LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(timestamp.format(
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("] ");
        sb.append(type).append(": ").append(quantity).append("x ").append(product.getName());

        if (type == MovementType.TRANSFER) {
            sb.append(" from ").append(sourceWarehouse)
                    .append(" to ").append(destinationWarehouse);
        } else if (type == MovementType.ADDITION) {
            sb.append(" -> ").append(destinationWarehouse);
        } else {
            sb.append(" <- ").append(sourceWarehouse);
        }

        return sb.toString();
    }
}

