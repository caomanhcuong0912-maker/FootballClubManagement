/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package footballclubmanagement;

/**
 *
 * @author AD
 */
import java.util.Scanner;
import java.util.InputMismatchException;
public class ReportManager {
    private SalaryManager salaryMgr;
    private Scanner scan = new Scanner(System.in);

    // ReportManager HAS-A SalaryManager (composition)
    public ReportManager(SalaryManager salaryMgr) {
        this.salaryMgr = salaryMgr;
    }

    // Task S13 — Salary Summary Report
    public void salarySummaryReport() {
        System.out.println("\n----------- SALARY SUMMARY REPORT -----------");
        int[] monthYear = salaryMgr.readMonthYear(); // reuse from SalaryManager
        int month = monthYear[0];
        int year  = monthYear[1];

        System.out.println("Month: " + month + "/" + year);
        System.out.println("--------------------------------------------------------------");
        System.out.printf("%-5s %-20s %-14s %-12s %-10s %-10s%n",
            "ID", "Name", "Type", "Base Salary", "Bonus", "Total");
        System.out.println("--------------------------------------------------------------");

        // TODO: loop through all players and print salary
        // needs Person 1's PlayerManager and Person 3's MatchManager
        System.out.println("[Waiting for teammates]");

        System.out.println("--------------------------------------------------------------");
        System.out.println("Press ENTER to return...");
        scan.nextLine();
    }

    // Task S14 — All-time Top Goal Scorers
    public void topGoalScorers() {
        System.out.println("\n----------- ALL-TIME TOP GOAL SCORERS -----------");
        System.out.println("----------------------------------------------------");
        System.out.printf("%-5s %-10s %-20s %-12s %-5s%n",
            "Rank", "Player ID", "Name", "Position", "Goals");
        System.out.println("----------------------------------------------------");

        // TODO: get all performance records from MatchManager
        // sum goals per player, sort descending, print ranking
        // needs Person 3's MatchManager
        System.out.println("[Waiting for Person 3]");

        System.out.println("----------------------------------------------------");
        System.out.println("Press ENTER to return...");
        scan.nextLine();
    }
    
}
