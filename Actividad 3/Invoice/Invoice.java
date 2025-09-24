package factura;

public class Invoice{
    private String id;
    private String description;
    private int quantity;
    private double unitPrice;

    public Invoice(String id, String description, int quantity, double unitPrice) {
        this.id = id;
        this.description = description;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // Getters
    public String getId() { return id; }
    public String getDescription() { return description; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }

    // Setters
    public void setQuantity(int quantity) { 
        if (quantity >= 0) this.quantity = quantity; 
    }

    public void setUnitPrice(double unitPrice) { 
        if (unitPrice >= 0) this.unitPrice = unitPrice; 
    }

    // Total de la factura
    public double getTotal() {
        return quantity * unitPrice;
    }

    @Override
    public String toString() {
        return String.format("La factura con id=%d, tiene como producto:%s con una cantidad de:%d y su precio unitario es de:%.2f, para un total de:%.2f]",
                id, description, quantity, unitPrice, getTotal());
    }
}
