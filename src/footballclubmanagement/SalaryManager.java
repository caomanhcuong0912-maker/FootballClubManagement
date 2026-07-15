/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package footballclubmanagement;

/**
 *
 * @author AD
 */
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

public class SalaryManager {
    private Scanner scan = new Scanner(System.in);
    private PlayerManager playerMgr;
    private MatchManager matchMgr;

    // SalaryManager needs PlayerManager and MatchManager
    public SalaryManager(PlayerManager playerMgr, MatchManager matchMgr) {
        this.playerMgr = playerMgr;
        this.matchMgr  = matchMgr;
    }

    // BR13 — validate month and year
    public boolean validateMonth(int month, int year) {
        if (month < 1 || month > 12) {
            System.out.println("Invalid month. Must be between 1 and 12.");
            return false;
        }
        if (year < 2000 || year > 2100) {
            System.out.println("Invalid year. Must be between 2000 and 2100.");
            return false;
        }
        return true;
    }

    // reads and validates month and year from user
    public int[] readMonthYear() {
        int month = 0, year = 0;
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Enter month (1-12): ");
                month = scan.nextInt();
                System.out.print("Enter year (2000-2100): ");
                year = scan.nextInt();
                scan.nextLine();
                valid = validateMonth(month, year);
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number.");
                scan.nextLine();
            }
        }
        return new int[]{month, year};
    }

    // Task S12 — Calculate single player salary
    public void calculateSalary() {
        System.out.println("\n----------- CALCULATE PLAYER SALARY -----------");

        // step 1 — get month and year
        int[] monthYear = readMonthYear();
        int month = monthYear[0];
        int year  = monthYear[1];

        // step 2 — get player ID
        System.out.print("Enter Player ID: ");
        String id = scan.nextLine().trim();

        // step 3 — find player
        Player p = playerMgr.findById(id);
        if (p == null) {
            System.out.println("Player ID '" + id + "' not found.");
            return;
        }

        // step 4 — get monthly performance points from MatchManager
        int monthlyPoints = (int) matchMgr.getMonthlyPerformancePoints(id, month, year);

        // step 5 — calculate bonus and total (polymorphism here!)
        double bonus = p.calculateBonus(monthlyPoints);
        double total = p.getBaseSalary() + bonus;

        // step 6 — display result
        System.out.println("\nPlayer: " + p.getFullName());
        System.out.println("Type: " + p.getPlayerType());
        System.out.println("Base Salary: " + p.getBaseSalary() + " VND");
        System.out.println("Monthly Performance Points: " + monthlyPoints);
        System.out.println("-------------------------------");
        System.out.printf("Salary Summary:%n");
        System.out.printf("Base Salary:        %,.0f VND%n", p.getBaseSalary());
        System.out.printf("Performance Bonus:  %,.0f VND%n", bonus);
        System.out.printf("Total Salary:       %,.0f VND%n", total);
    }

    // Task S16 — Salary summary for ALL players
    public void salarySummary() {
        System.out.println("\n----------- SALARY SUMMARY -----------");

        // get month and year
        int[] monthYear = readMonthYear();
        int month = monthYear[0];
        int year  = monthYear[1];

        List<Player> players = playerMgr.getAllPlayers();
        if (players.isEmpty()) {
            System.out.println("No players found.");
            return;
        }

        System.out.println("Month: " + month + "/" + year);
        System.out.println("--------------------------------------------------------------");
        System.out.printf("%-5s %-20s %-14s %-12s %-12s %-12s%n",
            "ID", "Name", "Type", "Base Salary", "Bonus", "Total");
        System.out.println("--------------------------------------------------------------");

        double grandTotal = 0;

        for (Player p : players) {
            int pts    = (int) matchMgr.getMonthlyPerformancePoints(
                             p.getPlayerID(), month, year);
            double bonus = p.calculateBonus(pts);       // polymorphism!
            double total = p.getBaseSalary() + bonus;
            grandTotal  += total;

            System.out.printf("%-5s %-20s %-14s %-12.0f %-12.0f %-12.0f%n",
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
        System.out.println("\nPress ENTER to return...");
        scan.nextLine();
    }
}

