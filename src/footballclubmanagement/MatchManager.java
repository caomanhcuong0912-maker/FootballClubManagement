package footballclubmanagement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MatchManager {

    private List<MatchRecord> matchList;
    private Scanner scanner;
    private DateTimeFormatter dateFormatter;

    public MatchManager() {
        this.matchList = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }
    
    public List<MatchRecord> getMatchList() {
    return matchList;
    }

    public void createMatchRecord() {
        System.out.println("\n--- TẠO MỚI TRẬN ĐẤU ---");

        System.out.print("Nhập Match ID: ");
        String matchId = scanner.nextLine().trim();
        if (matchId.isEmpty() || getMatchById(matchId) != null) {
            System.out.println("Lỗi: Match ID rỗng hoặc đã tồn tại!");
            return;
        }

        System.out.print("Nhập ngày trận đấu (dd/mm/yyyy): ");
        String dateStr = scanner.nextLine().trim();
        LocalDate matchDate;
        try {
            matchDate = LocalDate.parse(dateStr, dateFormatter);
        } catch (DateTimeParseException e) {
            System.out.println("Lỗi: Định dạng ngày dd/mm/yyyy không chính xác!");
            return;
        }

        System.out.print("Nhập tên đội đối thủ: ");
        String opponent = scanner.nextLine().trim();
        if (opponent.isEmpty()) {
            System.out.println("Lỗi: Tên đối thủ không được trống!");
            return;
        }

        System.out.print("Nhập loại trận đấu (Friendly/League/Cup): ");
        String type = scanner.nextLine().trim();
        if (!type.equalsIgnoreCase("Friendly") && !type.equalsIgnoreCase("League") && !type.equalsIgnoreCase("Cup")) {
            System.out.println("Lỗi: Loại trận không hợp lệ!");
            return;
        }

        MatchRecord newMatch = new MatchRecord(matchId, matchDate, opponent, type);
        matchList.add(newMatch);
        System.out.println(">> Thêm trận đấu mới thành công!");
    }

    public void addOrUpdatePerformance() {
        System.out.println("\n--- THÊM / CẬP NHẬT HIỆU SUẤT ---");
        System.out.print("Nhập Match ID: ");
        String matchId = scanner.nextLine().trim();
        MatchRecord match = getMatchById(matchId);

        if (match == null) {
            System.out.println("Lỗi: Trận đấu không tồn tại!");
            return;
        }

        System.out.print("Nhập Player ID: ");
        String playerId = scanner.nextLine().trim();
        if (playerId.isEmpty()) {
            System.out.println("Lỗi: Player ID trống!");
            return;
        }

        PerformanceRecord oldRecord = match.getPerformanceByPlayerId(playerId);
        if (oldRecord != null) {
            System.out.println("Cảnh báo: Bản ghi hiệu suất cầu thủ đã tồn tại.");
            System.out.println("Thông tin cũ - Phút: " + oldRecord.getMinutesPlayed() + " | Điểm: " + oldRecord.getPoints());
            System.out.print("Xác nhận ghi đè? (Y/N): ");
            String confirm = scanner.nextLine().trim();
            if (!confirm.equalsIgnoreCase("Y")) {
                System.out.println("Hủy thao tác.");
                return;
            }
        }

        try {
            System.out.print("Nhập số phút thi đấu (0-120): ");
            int mins = Integer.parseInt(scanner.nextLine().trim());
            if (!PerformanceRecord.validateMinutes(mins)) {
                System.out.println("Lỗi: Số phút phải từ 0 đến 120!");
                return;
            }

            System.out.print("Nhập số bàn thắng: ");
            int goals = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Nhập số kiến tạo: ");
            int assists = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Nhập thẻ vàng: ");
            int yellow = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Nhập thẻ đỏ: ");
            int red = Integer.parseInt(scanner.nextLine().trim());

            if (!PerformanceRecord.validateStats(goals, assists, yellow, red, mins)) {
                System.out.println("Lỗi: Các chỉ số không hợp lệ hoặc không đồng bộ với số phút = 0!");
                return;
            }

            PerformanceRecord newRecord = new PerformanceRecord(playerId, mins, goals, assists, yellow, red);
            match.addOrUpdatePerformanceRecord(newRecord);
            System.out.println(">> Lưu hiệu suất thành công! Điểm: " + newRecord.getPoints());

        } catch (NumberFormatException e) {
            System.out.println("Lỗi: Dữ liệu nhập vào phải là số nguyên!");
        }
    }

    public void viewMatchHistory() {
        System.out.println("\n--- LỊCH SỬ TRẬN ĐẤU ---");
        if (matchList.isEmpty()) {
            System.out.println("Không có trận đấu nào.");
            return;
        }

        System.out.printf("%-12s %-15s %-22s %-14s %-18s\n", "Match ID", "Ngày đấu", "Đối thủ", "Loại giải", "Số cầu thủ");
        System.out.println("---------------------------------------------------------------------------------------------");
        for (MatchRecord m : matchList) {
            System.out.printf("%-12s %-15s %-22s %-14s %-18d\n",
                    m.getMatchId(),
                    m.getMatchDate().format(dateFormatter),
                    m.getOpponentTeam(),
                    m.getMatchType(),
                    m.getPerformanceRecords().size());
        }
    }

    public MatchRecord getMatchById(String matchId) {
        for (MatchRecord m : matchList) {
            if (m.getMatchId().equalsIgnoreCase(matchId)) {
                return m;
            }
        }
        return null;
    }

    public double getMonthlyPerformancePoints(String playerId, int month, int year) {
        double totalPoints = 0;
        for (MatchRecord match : matchList) {
            if (match.getMatchDate().getMonthValue() == month && match.getMatchDate().getYear() == year) {
                PerformanceRecord pr = match.getPerformanceByPlayerId(playerId);
                if (pr != null) {
                    totalPoints += pr.getPoints();
                }
            }
        }
        return totalPoints;
    }
}
