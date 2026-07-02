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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        boolean back = false;
        Scanner scan = new Scanner(System.in);
        int choice;
        while (!back){
            System.out.println("\n----------- MANAGE PLAYERS -----------");
            System.out.println("1. Add a new player");
            System.out.println("2. Update player information");
            System.out.println("3. Deactivate a player");
            System.out.println("4. View all players");
            System.out.println("5. Search players");
            System.out.println("6. Display player details");
            System.out.println("0. Back to main menu");
            System.out.print("Choose an option: ");
            try{
                choice = scan.nextInt();
            }catch (InputMismatchException e){
                System.out.println("Invalid input. Please try again");
                scan.nextLine();
                continue;
            }
            switch (choice){
                case 1 -> System.out.println("You are Gay");
                case 2 -> System.out.println("You are Gay");
                case 3 -> System.out.println("You are Gay");
                case 4 -> System.out.println("You are Gay");
                case 5 -> System.out.println("You are Gay");
                case 6 -> System.out.println("You are Gay");
                case 0 -> back = true;
                default -> System.out.println("Invalid option. Please choose 0-6.");
            }
        }
        
    }
    
    
}
    
