package footballclubmanagement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TrainingSession {

    private final String trainingId;   // no change
    private LocalDate date;
    private String location;
    private String topic;
    private AttendanceRecord attendance; // null

    public static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TrainingSession(String trainingId, LocalDate date,
                           String location, String topic) {
        this.trainingId = trainingId;
        this.date       = date;
        this.location   = location;
        this.topic      = topic;
        this.attendance = null;
    }

    public String getTrainingId()         { return trainingId; }
    public LocalDate getDate()            { return date; }
    public String getLocation()           { return location; }
    public String getTopic()              { return topic; }
    public AttendanceRecord getAttendance() { return attendance; }

    public void setAttendance(AttendanceRecord attendance) {
        this.attendance = attendance;
    }

    /** Returns true if attendance has been recorded at least once. */
    public boolean hasAttendance() {
        return attendance != null;
    }

    public String toTableRow() {
        return String.format("%-8s %-12s %-25s %s",
                trainingId,
                date.format(DATE_FORMAT),
                location,
                topic);
    }

    // ── File I/O ──────────────────────────────────────────────────────────    
    
    public String toCsvLine() {
        return trainingId + "," +
               date.format(DATE_FORMAT) + "," +
               location + "," +
               topic;
    }

    public static TrainingSession fromCsvLine(String line) {
        String[] parts = line.split(",", 4);
        if (parts.length < 4) {
            throw new IllegalArgumentException(
                    "Invalid TrainingSession CSV line: " + line);
        }
        try {
            LocalDate date = LocalDate.parse(parts[1].trim(), DATE_FORMAT);
            return new TrainingSession(
                    parts[0].trim(),
                    date,
                    parts[2].trim(),
                    parts[3].trim()
            );
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Invalid date in TrainingSession CSV: " + parts[1], e);
        }
    }

    @Override
    public String toString() {
        return "TrainingSession{id='" + trainingId + "', date=" +
               date.format(DATE_FORMAT) + ", location='" + location +
               "', topic='" + topic + "', attendanceRecorded=" +
               hasAttendance() + "}";
    }
}
