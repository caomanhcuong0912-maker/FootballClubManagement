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
public class SalaryManager {
    private Scanner scan = new Scanner(System.in);
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

    public void calculateSalary() {
        System.out.println("[Coming soon - needs Person 1 and 3]");
    }

    public void salarySummary() {
        System.out.println("[Coming soon - needs Person 1 and 3]");
    }
}

