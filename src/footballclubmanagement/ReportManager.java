/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package footballclubmanagement;

/**
 *
 * @author AD
 */
import java.util.*;
public class ReportManager {
    private SalaryManager salaryMgr;
    private MatchManager matchMgr;   
    private PlayerManager playerMgr;
    private Scanner scan = new Scanner(System.in);

    // ReportManager HAS-A SalaryManager (composition)
    public ReportManager(SalaryManager salaryMgr, 
                         MatchManager matchMgr, 
                         PlayerManager playerMgr) {
        this.salaryMgr = salaryMgr;
        this.matchMgr  = matchMgr;
        this.playerMgr = playerMgr;
    }

    // Task S13 — Salary Summary Report
    public void salarySummaryReport() {
    System.out.println("\n----------- SALARY SUMMARY REPORT -----------");
    int[] monthYear = salaryMgr.readMonthYear();
    int month = monthYear[0];
    int year  = monthYear[1];

    List<Player> players = playerMgr.getAllPlayers();
    if (players.isEmpty()) {
        System.out.println("No players found.");
        System.out.println("Press ENTER to return...");
        scan.nextLine();
        return;
    }

    System.out.println("Month: " + month + "/" + year);
    System.out.println("--------------------------------------------------------------");
    System.out.printf("%-5s %-20s %-14s %-12s %-10s %-10s%n",
        "ID", "Name", "Type", "Base Salary", "Bonus", "Total");
    System.out.println("--------------------------------------------------------------");

    double grandTotal = 0;
    for (Player p : players) {
        int pts = (int) matchMgr.getMonthlyPerformancePoints(
                      p.getPlayerID(), month, year);
        double bonus = p.calculateBonus(pts);
        double total = p.getBaseSalary() + bonus;
        grandTotal += total;

        System.out.printf("%-5s %-20s %-14s %-12.0f %-10.0f %-10.0f%n",
            p.getPlayerID(),
            p.getFullName(),
            p.getPlayerType(),
            p.getBaseSalary(),
            bonus,
            total
        );
    }

    System.out.println("--------------------------------------------------------------");
    System.out.printf("Total Monthly Salary Cost: %,.0f VND%n", grandTotal);
    System.out.println("Press ENTER to return...");
    scan.nextLine();
}

    // Task S14 — All-time Top Goal Scorers
    public void topGoalScorers() {
    System.out.println("\n----------- ALL-TIME TOP GOAL SCORERS -----------");

    // step 1 — get all match records from MatchManager
    List<MatchRecord> matches = matchMgr.getMatchList();

    // step 2 — sum goals per player using a HashMap
    Map<String, Integer> goalMap = new HashMap<>();
    for (MatchRecord match : matches) {
        for (PerformanceRecord pr : match.getPerformanceRecords()) {
            String pid = pr.getPlayerId();
            goalMap.put(pid, goalMap.getOrDefault(pid, 0) + pr.getGoals());
        }
    }

    if (goalMap.isEmpty()) {
        System.out.println("No performance records found.");
        System.out.println("Press ENTER to return...");
        scan.nextLine();
        return;
    }

    // step 3 — sort by goals descending
    List<Map.Entry<String, Integer>> sorted = new ArrayList<>(goalMap.entrySet());
    sorted.sort((a, b) -> b.getValue() - a.getValue());

    // step 4 — print ranking
    System.out.println("----------------------------------------------------");
    System.out.printf("%-5s %-10s %-20s %-12s %-5s%n",
        "Rank", "Player ID", "Name", "Position", "Goals");
    System.out.println("----------------------------------------------------");

    int rank = 1;
    for (Map.Entry<String, Integer> entry : sorted) {
        Player p = playerMgr.findById(entry.getKey());
        String name     = p != null ? p.getFullName()  : "Unknown";
        String position = p != null ? p.getPosition()  : "Unknown";
        System.out.printf("%-5d %-10s %-20s %-12s %-5d%n",
            rank++,
            entry.getKey(),
            name,
            position,
            entry.getValue()
        );
    }

    System.out.println("----------------------------------------------------");
    System.out.println("Press ENTER to return...");
    scan.nextLine();
}
    
}
