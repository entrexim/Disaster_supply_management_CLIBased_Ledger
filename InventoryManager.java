import java.io.*;
import java.util.*;

public class InventoryManager {
    private final Map<String, SupplyItem> inventory = new HashMap<>();
    private final String storageFilePath = "inventory_data.csv";

    public InventoryManager() {
        loadFromDisk();
    }

    public void addOrUpdateSupply(SupplyItem item) {
        inventory.put(item.getItemId(), item);
        saveToDisk();
    }

    public SupplyItem getItem(String itemId) {
        return inventory.get(itemId);
    }

    public Collection<SupplyItem> getAllSupplies() {
        return inventory.values();
    }

    public boolean updateStock(String itemId, int allocatedQty) {
        SupplyItem item = inventory.get(itemId);
        if (item != null && item.getQuantity() >= allocatedQty) {
            item.deductQuantity(allocatedQty);
            saveToDisk();
            return true;
        }
        return false;
    }

    private void saveToDisk() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(storageFilePath))) {
            for (SupplyItem item : inventory.values()) {
                writer.write(item.toCsv());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Persistence Error: Failed to write inventory data.");
        }
    }

    private void loadFromDisk() {
        File file = new File(storageFilePath);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    SupplyItem item = SupplyItem.fromCsv(line);
                    inventory.put(item.getItemId(), item);
                }
            }
        } catch (Exception e) {
            System.err.println("Persistence Warning: Corrupted disk record skipped.");
        }
    }
}