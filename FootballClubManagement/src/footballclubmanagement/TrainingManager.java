package footballclubmanagement;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class TrainingManager {

    private static final String SESSIONS_FILE   = "training_sessions.csv";
    private static final String ATTENDANCE_FILE  = "attendance_records.csv";

    private final List<TrainingSession> trainingSessions;

    private Object playerManager; // TODO: Object -> PlayerManager

    public TrainingManager() {
        this.trainingSessions = new ArrayList<>();
        this.playerManager    = null;
    }

    public TrainingManager(Object playerManager) { // TODO: Object -> PlayerManager
        this.trainingSessions = new ArrayList<>();
        this.playerManager    = playerManager;
    }

    public void setPlayerManager(Object playerManager) { // TODO: Object -> PlayerManager
        this.playerManager = playerManager;
    }

    public void createTrainingSession(String trainingId, String dateStr,
                                      String location, String topic) {
        if (trainingId == null || trainingId.trim().isEmpty()) {
            System.out.println("Error: Training ID cannot be blank.");
            return;
        }
        trainingId = trainingId.trim();
        if (getSessionById(trainingId) != null) {
            System.out.println("Error: Training ID '" + trainingId +
                    "' already exists. IDs must be unique (BR1).");
            return;
        }
        LocalDate date = parseDate(dateStr);
        if (date == null) return; // error already printed by parseDate()
        if (location == null || location.trim().isEmpty()) {
            System.out.println("Error: Location cannot be blank.");
            return;
        }
        if (topic == null || topic.trim().isEmpty()) {
            System.out.println("Error: Training topic cannot be blank.");
            return;
        }
        TrainingSession session = new TrainingSession(
                trainingId, date, location.trim(), topic.trim());
        trainingSessions.add(session);
        System.out.println("Training session created successfully.");
    }

    public void recordAttendance(String trainingId, String absentInput) {
        TrainingSession session = getSessionById(trainingId);
        if (session == null) {
            System.out.println("Error: Training session '" + trainingId +
                    "' not found.");
            return;
        }
        List<String> absentIds = parseAbsentIds(absentInput);
        if (absentIds == null) return; // duplicate detection

        AttendanceRecord record;

        if (!session.hasAttendance()) {
            Set<String> activePlayerIds = getActivePlayerIds();
            if (activePlayerIds == null) {
                System.out.println("[INTEGRATION ERROR] PlayerManager not set. " +
                        "Call setPlayerManager() before recording attendance.");
                return;
            }
            for (String absentId : absentIds) {
                if (!activePlayerIds.contains(absentId)) {
                    System.out.println("Error: Player ID '" + absentId +
                            "' is not an active player (BR15).");
                    return;
                }
            }
            record = new AttendanceRecord(trainingId, activePlayerIds);

        } else {
            record = session.getAttendance();
            record.resetAllToPresent();
            for (String absentId : absentIds) {
                if (!record.containsPlayer(absentId)) {
                    System.out.println("Error: Player ID '" + absentId +
                            "' was not in the original attendance snapshot (BR18). " +
                            "Cannot add new players on overwrite.");
                    return;
                }
            }
        }
        for (String absentId : absentIds) {
            record.markAbsent(absentId);
        }
        session.setAttendance(record);
        System.out.println("Training attendance recorded successfully.");
        System.out.println("Summary:");
        System.out.println("  Present: " + record.countPresent());
        System.out.println("  Absent:  " + record.countAbsent());
    }

    public void viewTrainingHistory() {
        if (trainingSessions.isEmpty()) {
            System.out.println("No training sessions recorded yet.");
            return;
        }

        System.out.println("\n----------- TRAINING HISTORY -----------");
        System.out.printf("%-8s %-12s %-25s %-25s %s%n",
                "ID", "Date", "Location", "Topic", "Attendance");
        System.out.println("-".repeat(85));

        for (TrainingSession s : trainingSessions) {
            System.out.printf("%-8s %-12s %-25s %-25s %s%n",
                    s.getTrainingId(),
                    s.getDate().format(TrainingSession.DATE_FORMAT),
                    s.getLocation(),
                    s.getTopic(),
                    s.hasAttendance() ? "Recorded" : "Not recorded");
        }

        System.out.println("-".repeat(85));
    }
    
    public TrainingSession getSessionById(String trainingId) {
        if (trainingId == null) return null;
        for (TrainingSession s : trainingSessions) {
            if (s.getTrainingId().equals(trainingId.trim())) {
                return s;
            }
        }
        return null;
    }

    public void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(SESSIONS_FILE))) {
            for (TrainingSession s : trainingSessions) {
                bw.write(s.toCsvLine());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving training sessions: " + e.getMessage());
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ATTENDANCE_FILE))) {
            for (TrainingSession s : trainingSessions) {
                if (s.hasAttendance()) {
                    bw.write(s.getAttendance().toCsvLines());
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving attendance records: " + e.getMessage());
            return;
        }

        System.out.println("Training data saved successfully.");
    }

    public void loadFromFile() {
        File sessionFile = new File(SESSIONS_FILE);
        if (sessionFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(sessionFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    try {
                        TrainingSession s = TrainingSession.fromCsvLine(line);
                        trainingSessions.add(s);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Warning: Skipping malformed session line: " + line);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error loading training sessions: " + e.getMessage());
            }
        }

        File attendanceFile = new File(ATTENDANCE_FILE);
        if (attendanceFile.exists()) {
            Map<String, List<String>> grouped = new LinkedHashMap<>();
            try (BufferedReader br = new BufferedReader(new FileReader(attendanceFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    String[] parts = line.split(",", 3);
                    if (parts.length < 3) {
                        System.out.println("Warning: Skipping malformed attendance line: " + line);
                        continue;
                    }
                    String tid = parts[0].trim();
                    grouped.computeIfAbsent(tid, k -> new ArrayList<>()).add(line);
                }
            } catch (IOException e) {
                System.out.println("Error loading attendance records: " + e.getMessage());
            }
            for (Map.Entry<String, List<String>> entry : grouped.entrySet()) {
                String tid = entry.getKey();
                TrainingSession session = getSessionById(tid);
                if (session == null) {
                    System.out.println("Warning: Attendance found for unknown session '" +
                            tid + "'. Skipping.");
                    continue;
                }
                try {
                    AttendanceRecord record =
                            AttendanceRecord.fromCsvLines(tid, entry.getValue());
                    session.setAttendance(record);
                } catch (IllegalArgumentException e) {
                    System.out.println("Warning: Could not load attendance for session '" +
                            tid + "': " + e.getMessage());
                }
            }
        }
    }

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            System.out.println("Error: Date cannot be blank.");
            return null;
        }
        try {
            return LocalDate.parse(dateStr.trim(), TrainingSession.DATE_FORMAT);
        } catch (DateTimeParseException e) {
            System.out.println("Error: Invalid date '" + dateStr +
                    "'. Use format dd/MM/yyyy and ensure it is a real calendar date (BR12).");
            return null;
        }
    }
    
    private List<String> parseAbsentIds(String absentInput) {
        List<String> result = new ArrayList<>();
        if (absentInput == null || absentInput.trim().isEmpty()) {
            return result; // blank = all present
        }
        Set<String> seen = new LinkedHashSet<>();
        for (String raw : absentInput.split(",")) {
            String id = raw.trim();
            if (id.isEmpty()) continue;
            if (!seen.add(id)) {
                System.out.println("Error: Duplicate absent Player ID '" + id + "' (BR15).");
                return null;
            }
            result.add(id);
        }
        return result;
    }

    private Set<String> getActivePlayerIds() {
        if (playerManager == null) {
        System.out.println("[PLACEHOLDER 1] getActivePlayerIds() not implemented. " +
                "Integrating teammate must complete this method.");
        return null;
    }
}
