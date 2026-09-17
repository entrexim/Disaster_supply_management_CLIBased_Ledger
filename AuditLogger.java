import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLogger {
    private static final String AUDIT_LOG_FILE = "dispatch_audit.log";

    public static void logDispatch(String centerId, String itemId, int quantity, double priorityScore, boolean success) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String status = success ? "DISPATCHED" : "REJECTED_STOCK_DEFICIT";
        String logEntry = String.format("[%s] STATUS: %s | Center: %s | Item: %s | Qty: %d | Score: %.1f", 
                timestamp, status, centerId, itemId, quantity, priorityScore);

        try (PrintWriter out = new PrintWriter(new FileWriter(AUDIT_LOG_FILE, true))) {
            out.println(logEntry);
        } catch (IOException e) {
            System.err.println("Audit System Error: Failed to append log file.");
        }
    }
}