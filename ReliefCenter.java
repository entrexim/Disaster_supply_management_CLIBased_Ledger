public class ReliefCenter {
    private String centerId;
    private String locationName;
    private int severityIndex; // 1 (Minor) to 5 (Extreme Emergency)

    public ReliefCenter(String centerId, String locationName, int severityIndex) {
        this.centerId = centerId;
        this.locationName = locationName;
        this.severityIndex = severityIndex;
    }

    public String getCenterId() { return centerId; }
    public String getLocationName() { return locationName; }
    public int getSeverityIndex() { return severityIndex; }

    @Override
    public String toString() {
        return String.format("Center ID: %s | Zone: %s | Severity Index: %d/5", centerId, locationName, severityIndex);
    }
}