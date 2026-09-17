import java.util.PriorityQueue;

public class PriorityQueueEngine {

    public static class DispatchRequest implements Comparable<DispatchRequest> {
        private final ReliefCenter center;
        private final SupplyItem item;
        private final int requestedQuantity;
        private final double priorityScore;

        public DispatchRequest(ReliefCenter center, SupplyItem item, int requestedQuantity) {
            this.center = center;
            this.item = item;
            this.requestedQuantity = requestedQuantity;
            // Compound score formula: Severity (1-5) * UrgencyWeight (1-5)
            this.priorityScore = center.getSeverityIndex() * item.getUrgencyWeight();
        }

        public ReliefCenter getCenter() { return center; }
        public SupplyItem getItem() { return item; }
        public int getRequestedQuantity() { return requestedQuantity; }
        public double getPriorityScore() { return priorityScore; }

        @Override
        public int compareTo(DispatchRequest other) {
            // Higher priority score processed first
            return Double.compare(other.priorityScore, this.priorityScore);
        }
    }

    private final PriorityQueue<DispatchRequest> requestQueue = new PriorityQueue<>();

    public void enqueueRequest(ReliefCenter center, SupplyItem item, int qty) {
        requestQueue.add(new DispatchRequest(center, item, qty));
    }

    public DispatchRequest dequeueNextRequest() {
        return requestQueue.poll();
    }

    public boolean isEmpty() {
        return requestQueue.isEmpty();
    }

    public int getQueueSize() {
        return requestQueue.size();
    }
}