package lookup;

public class Product {
    private int identifier;
    private String title;
    private String classification;
    private double cost;
    private int inventoryCount;

    public Product(int identifier, String title, String classification, double cost, int inventoryCount) {
        this.identifier = identifier;
        this.title = title;
        this.classification = classification;
        this.cost = cost;
        this.inventoryCount = inventoryCount;
    }

    public int getIdentifier() { return identifier; }
    public void setIdentifier(int identifier) { this.identifier = identifier; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getClassification() { return classification; }
    public void setClassification(String classification) { this.classification = classification; }

    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }

    public int getInventoryCount() { return inventoryCount; }
    public void setInventoryCount(int inventoryCount) { this.inventoryCount = inventoryCount; }
}