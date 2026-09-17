import java.util.Scanner;

public class CLIController {
    private final InventoryManager inventoryManager = new InventoryManager();
    private final PriorityQueueEngine queueEngine = new PriorityQueueEngine();
    private final Scanner scanner = new Scanner(System.in);


    //here i m making multiple options for cli
    public void start() {
        while (true) {
            System.out.println("\n=$$DISASTER RELIEF SUPPLY LEDGER$$=");
            System.out.println("1. Register Supply Stock");
            System.out.println("2. Display Current Stock Inventory");
            System.out.println("3. Submit Relief Center Supply Request");
            System.out.println("4. Process Highest Priority Dispatch Request");
            System.out.println("5. Exit");
            System.out.print("Select Option: ");

            //multiple cases for slection

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> handleAddSupply();
                case "2" -> handleListSupplies();
                case "3" -> handleAddRequest();
                case "4" -> handleProcessDispatch();
                case "5" -> {
                    System.out.println("Terminating dispatch engine safely...");
                    return;
                }
                default -> System.out.println("Invalid input selection. Try again.");
            }
        }
    }
    //now to add items in the supply list
    private void handleAddSupply() {
        try {
            System.out.print("Item ID: ");
            String id = scanner.nextLine();
            System.out.print("Item Name: ");
            String name = scanner.nextLine();
            System.out.print("Category (MEDICAL/FOOD/SHELTER): ");
            String category = scanner.nextLine();
            System.out.print("Quantity: ");
            int qty = Integer.parseInt(scanner.nextLine());
            System.out.print("Urgency Weight (1-5): ");
            int urgency = Integer.parseInt(scanner.nextLine());

            inventoryManager.addOrUpdateSupply(new SupplyItem(id, name, category, qty, urgency));
            System.out.println("Supply record successfully registered/updated.");
        } catch (Exception e) {
            System.out.println("Input format error. Transaction aborted.");
        }
    }

    private void handleListSupplies() {
        System.out.println("\n--- PERSISTED INVENTORY STOCK ---");
        if (inventoryManager.getAllSupplies().isEmpty()) {
            System.out.println("No supply items available in database.");
            return;
        }
        for (SupplyItem item : inventoryManager.getAllSupplies()) {
            System.out.println(item);
        }
    }

    private void handleAddRequest() {
        try {
            System.out.print("Target Relief Center ID: ");
            String cId = scanner.nextLine();
            System.out.print("Center Location Zone: ");
            String zone = scanner.nextLine();
            System.out.print("Zone Severity Index (1-5): ");
            int severity = Integer.parseInt(scanner.nextLine());

            System.out.print("Requested Item ID: ");
            String itemId = scanner.nextLine();
            SupplyItem item = inventoryManager.getItem(itemId);

            if (item == null) {
                System.out.println("Error: Specified Item ID does not exist in inventory.");
                return;
            }

            System.out.print("Requested Quantity: ");
            int qty = Integer.parseInt(scanner.nextLine());

            ReliefCenter center = new ReliefCenter(cId, zone, severity);
            queueEngine.enqueueRequest(center, item, qty);
            System.out.println("Request queued! Active requests pending: " + queueEngine.getQueueSize());
        } catch (Exception e) {
            System.out.println("Invalid payload entered. Request canceled.");
        }
    }

    private void handleProcessDispatch() {
        if (queueEngine.isEmpty()) {
            System.out.println("Dispatch Queue is currently empty.");
            return;
        }

        PriorityQueueEngine.DispatchRequest request = queueEngine.dequeueNextRequest();
        SupplyItem item = request.getItem();
        ReliefCenter center = request.getCenter();
        int requestedQty = request.getRequestedQuantity();

        boolean success = inventoryManager.updateStock(item.getItemId(), requestedQty);
        AuditLogger.logDispatch(center.getCenterId(), item.getItemId(), requestedQty, request.getPriorityScore(), success);

        if (success) {
            System.out.printf("SUCCESS: Dispatched %d units of %s to Zone %s (Priority Score: %.1f)%n",
                    requestedQty, item.getName(), center.getLocationName(), request.getPriorityScore());
        } else {
            System.out.printf("REJECTED: Insufficient stock for Item %s. Stock available: %d | Requested: %d%n",
                    item.getName(), item.getQuantity(), requestedQty);
        }
    }
}