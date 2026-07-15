/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package footballclubmanagement;

/**
 *
 * @author AD
 */
public class RegularPlayer extends Player {
    public RegularPlayer(String playerID, String fullName, int age,
                         String nationality, String position,
                         int shirtNumber, double baseSalary, String status) {
        super(playerID, fullName, age, nationality, position, shirtNumber, baseSalary, status);
    }

    @Override
    public double calculateBonus(int monthlyPoints) { return 0; }

    @Override
    public String getPlayerType() { return "Regular Player"; }
}
