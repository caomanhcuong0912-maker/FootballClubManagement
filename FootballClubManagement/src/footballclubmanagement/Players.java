/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package footballclubmanagement;

/**
 *
 * @author AD
 */
public class Players {
    private String playerID;
    private String fullName;
    private int age;
    private String nationality;
    private String position;
    private int shirtNumber;
    private double baseSalary;
    private String playerType;
    private String status;

    public Players(String playerID, String fullName, int age, String nationality,
                  String position, int shirtNumber, double baseSalary,
                  String playerType, String status) {
        this.playerID = playerID;
        this.fullName = fullName;
        this.age = age;
        this.nationality = nationality;
        this.position = position;
        this.shirtNumber = shirtNumber;
        this.baseSalary = baseSalary;
        this.playerType = playerType;
        this.status = status;
    }

    public String getPlayerId()   { return playerID; }
    public String getFullName()   { return fullName; }
    public int getAge()           { return age; }
    public String getNationality(){ return nationality; }
    public String getPosition()   { return position; }
    public int getShirtNumber()   { return shirtNumber; }
    public double getBaseSalary() { return baseSalary; }
    public String getPlayerType() { return playerType; }
    public String getStatus()     { return status; }
    public double calculateBonus(int points) { return 0; }
}
