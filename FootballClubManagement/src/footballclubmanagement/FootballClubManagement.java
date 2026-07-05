/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package footballclubmanagement;
import java.util.Scanner;
import java.util.InputMismatchException;
public class FootballClubManagement {
    static SalaryManager salaryMgr = new SalaryManager();
    static ReportManager reportMgr = new ReportManager(salaryMgr); 
    static TrainingManager trainingMgr = new TrainingManager();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    trainingMgr.loadFromFile();
    boolean running = true;
    Scanner scan = new Scanner(System.in);
    while (running) {
        System.out.println("\n======================================");
        System.out.println("  FOOTBALL PLAYER MANAGEMENT SYSTEM  ");
        System.out.println("======================================");
        System.out.println("1. Manage Players");
        System.out.println("2. Training and Match Management");
        System.out.println("3. Contract and Salary Management");
        System.out.println("4. Reports");
        System.out.println("5. Exit");
        System.out.println("--------------------------------------");
        System.out.print("Choose an option: ");

        try {
            int choice = scan.nextInt();
            scan.nextLine();
            switch (choice) {
                case 1 -> showPlayerMenu(scan);
                case 2 -> showTrainingMenu(scan);
                case 3 -> showSalaryMenu(scan);
                case 4 -> showReportMenu(scan);
                case 5 -> { running = false; showExitMenu(scan); }
                default -> System.out.println("Invalid option.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter a number.");
            scan.nextLine();
        }
    }
}
    static void showPlayerMenu(Scanner scan) {
    boolean back = false;
    while (!back) {
        System.out.println("\n----------- MANAGE PLAYERS -----------");
        System.out.println("1. Add a new player");
        System.out.println("2. Update player information");
        System.out.println("3. Deactivate a player");
        System.out.println("4. View all players");
        System.out.println("5. Search players");
        System.out.println("6. Display player details");
        System.out.println("0. Back");
        System.out.print("Choose an option: ");
        try {
            int choice = scan.nextInt();
            scan.nextLine();
            switch (choice) {
                case 1 -> System.out.println("[Person 1 pending]");
                case 2 -> System.out.println("[Person 1 pending]");
                case 3 -> System.out.println("[Person 1 pending]");
                case 4 -> System.out.println("[Person 1 pending]");
                case 5 -> System.out.println("[Person 1 pending]");
                case 6 -> System.out.println("[Person 1 pending]");
                case 0 -> back = true;
                default -> System.out.println("Invalid option.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter a number.");
            scan.nextLine();
        }
    }
}

static void showSalaryMenu(Scanner scan) {
    boolean back = false;
    while (!back) {
        System.out.println("\n------- CONTRACT & SALARY MANAGEMENT -------");
        System.out.println("1. Calculate Player Salary");
        System.out.println("2. Salary Summary");
        System.out.println("0. Back");
        System.out.print("Choose an option: ");
        try {
            int choice = scan.nextInt();
            scan.nextLine();
            switch (choice) {
                case 1 -> salaryMgr.calculateSalary();
                case 2 -> salaryMgr.salarySummary();
                case 0 -> back = true;
                default -> System.out.println("Invalid option.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter a number.");
            scan.nextLine();
        }
    }
}

static void showReportMenu(Scanner scan) {
    boolean back = false;
    while (!back) {
        System.out.println("\n----------- REPORTS -----------");
        System.out.println("1. Salary Summary Report");
        System.out.println("2. All-time Top Goal Scorers");
        System.out.println("0. Back");
        System.out.print("Choose an option: ");
        try {
            int choice = scan.nextInt();
            scan.nextLine();
            switch (choice) {
                case 1 -> reportMgr.salarySummaryReport();
                case 2 -> reportMgr.topGoalScorers();
                case 0 -> back = true;
                default -> System.out.println("Invalid option.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter a number.");
            scan.nextLine();
        }
    }
}

static void showTrainingMenu(Scanner scan) {
    boolean back = false;
    while (!back) {
        System.out.println("\n------- TRAINING & MATCH MANAGEMENT -------");
        System.out.println("1. Create Training Session");
        System.out.println("2. Record Attendance");
        System.out.println("3. Create Match Record");
        System.out.println("4. Add / Update Performance");
        System.out.println("5. View Training History");
        System.out.println("6. View Match History");
        System.out.println("0. Back");
        System.out.print("Choose an option: ");
        try {
            int choice = scan.nextInt();
            scan.nextLine();
            switch (choice) {
                case 1 -> handleCreateTrainingSession(scan);
                case 2 -> handleRecordAttendance(scan);
                case 5 -> trainingMgr.viewTrainingHistory();
                case 3, 4, 6 -> System.out.println("[Person 3 pending]");
                case 0 -> back = true;
                default -> System.out.println("Invalid option.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter a number.");
            scan.nextLine();
        }
    }
}

static void showExitMenu(Scanner scan) {
    System.out.println("\n----------- EXIT SYSTEM -----------");
    System.out.println("1. Save and Exit");
    System.out.println("2. Exit without Saving");
    System.out.println("3. Cancel");
    System.out.print("Choose an option: ");
    try {
        int choice = scan.nextInt();
        switch (choice) {
            case 1 -> {
                trainingMgr.saveToFile();
                System.out.println("Data saved successfully.");
                System.out.println("Thank you for using the Football Player Management System.");
                System.exit(0);
            }
            case 2 -> {
                System.out.println("Thank you for using the Football Player Management System.");
                System.exit(0);
            }
            case 3 -> {} // do nothing, returns to main loop
        }
    } catch (InputMismatchException e) {
        scan.nextLine();
    }
}

static void handleCreateTrainingSession(Scanner scan) {
    System.out.print("Training ID: ");
    String id = scan.nextLine();
    System.out.print("Date (dd/MM/yyyy): ");
    String date = scan.nextLine();
    System.out.print("Location: ");
    String location = scan.nextLine();
    System.out.print("Training Topic: ");
    String topic = scan.nextLine();
    trainingMgr.createTrainingSession(id, date, location, topic);
}

static void handleRecordAttendance(Scanner scan) {
    System.out.print("Training ID: ");
    String id = scan.nextLine();
    System.out.print("Absent Player IDs (comma-separated, leave blank if all present): ");
    String absentIds = scan.nextLine();
    trainingMgr.recordAttendance(id, absentIds);
}
    
}


    

    
