import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class AttendanceRecord {

    private final String trainingId;

    private final Map<String, Boolean> attendanceSnapshot;

    public AttendanceRecord(String trainingId, Set<String> initialPlayerIds) {
        this.trainingId         = trainingId;
        this.attendanceSnapshot = new LinkedHashMap<>();
        for (String pid : initialPlayerIds) {
            attendanceSnapshot.put(pid, true); // default: present
        }
    }

    private AttendanceRecord(String trainingId, Map<String, Boolean> snapshot) {
        this.trainingId         = trainingId;
        this.attendanceSnapshot = new LinkedHashMap<>(snapshot);
    }

    public String getTrainingId() { return trainingId; }

    public Map<String, Boolean> getSnapshot() {
        return java.util.Collections.unmodifiableMap(attendanceSnapshot);
    }

    public void markAbsent(String playerId) {
        if (!attendanceSnapshot.containsKey(playerId)) {
            throw new IllegalArgumentException(
                    "Player ID '" + playerId + "' is not in this attendance snapshot.");
        }
        attendanceSnapshot.put(playerId, false);
    }

    public void resetAllToPresent() {
        for (String pid : attendanceSnapshot.keySet()) {
            attendanceSnapshot.put(pid, true);
        }
    }

    public boolean isPresent(String playerId) {
        if (!attendanceSnapshot.containsKey(playerId)) {
            throw new IllegalArgumentException(
                    "Player ID '" + playerId + "' is not in this attendance snapshot.");
        }
        return attendanceSnapshot.get(playerId);
    }

    public int countPresent() {
        int count = 0;
        for (boolean present : attendanceSnapshot.values()) {
            if (present) count++;
        }
        return count;
    }

    public int countAbsent() {
        return attendanceSnapshot.size() - countPresent();
    }

    public int totalPlayers() {
        return attendanceSnapshot.size();
    }

    public boolean containsPlayer(String playerId) {
        return attendanceSnapshot.containsKey(playerId);
    }

    // ── File I/O ──────────────────────────────────────────────────────────

    public String toCsvLines() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Boolean> entry : attendanceSnapshot.entrySet()) {
            sb.append(trainingId).append(",")
              .append(entry.getKey()).append(",")
              .append(entry.getValue() ? "P" : "A")
              .append("\n");
        }
        return sb.toString();
    }

    public static AttendanceRecord fromCsvLines(String trainingId,
                                                java.util.List<String> lines) {
        Map<String, Boolean> snapshot = new LinkedHashMap<>();
        for (String line : lines) {
            String[] parts = line.split(",", 3);
            if (parts.length < 3) {
                throw new IllegalArgumentException(
                        "Invalid AttendanceRecord CSV line: " + line);
            }
            String pid    = parts[1].trim();
            boolean present = parts[2].trim().equalsIgnoreCase("P");
            snapshot.put(pid, present);
        }
        return new AttendanceRecord(trainingId, snapshot);
    }

    @Override
    public String toString() {
        return "AttendanceRecord{trainingId='" + trainingId +
               "', present=" + countPresent() +
               ", absent=" + countAbsent() + "}";
    }
}
