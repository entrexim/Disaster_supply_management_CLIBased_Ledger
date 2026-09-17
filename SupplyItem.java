public class SupplyItem {
    private String itemId;
    private String name;
    private String category; // e.g., MEDICAL, RATIONS, SHELTER
    private int quantity;
    private int urgencyWeight; // 1 (Low) to 5 (Critical)

    public SupplyItem(String itemId, String name, String category, int quantity, int urgencyWeight) {
        this.itemId = itemId;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.urgencyWeight = urgencyWeight;
    }

    public String getItemId() { return itemId; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public int getUrgencyWeight() { return urgencyWeight; }

    public void deductQuantity(int amount) {
        if (amount <= this.quantity) {
            this.quantity -= amount;
        }
    }

    public String toCsv() {
        return itemId + "," + name + "," + category + "," + quantity + "," + urgencyWeight;
    }

    public static SupplyItem fromCsv(String csvLine) {
        String[] parts = csvLine.split(",");
        return new SupplyItem(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s) - Qty: %d | Urgency Factor: %d/5", 
                itemId, name, category, quantity, urgencyWeight);
    }
}