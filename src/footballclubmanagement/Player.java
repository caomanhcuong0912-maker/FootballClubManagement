/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package footballclubmanagement;

/**
 *
 * @author AD
 */
public abstract class Player {
    private String playerID;
    private String fullName;
    private int age;
    private String nationality;
    private String position;
    private int shirtNumber;
    private double baseSalary;
    private String status;
    private String playerType;

    public Player(String playerID, String fullName, int age,
                  String nationality, String position, int shirtNumber,
                  double baseSalary, String status) {
        this.playerID = playerID;
        this.fullName = fullName;
        this.age = age;
        this.nationality = nationality;
        this.position = position;
        this.shirtNumber = shirtNumber;
        this.baseSalary = baseSalary;
        this.status = status;
    }

    public abstract double calculateBonus(int monthlyPoints);
    public abstract String getPlayerType();

    public String getPlayerID()    { return playerID; }
    public String getFullName()    { return fullName; }
    public int getAge()            { return age; }
    public String getNationality() { return nationality; }
    public String getPosition()    { return position; }
    public int getShirtNumber()    { return shirtNumber; }
    public double getBaseSalary()  { return baseSalary; }
    public String getStatus()      { return status; }
    public void setStatus(String status) { this.status = status; }
    public void setPosition(String position) { this.position = position; }
    public void setShirtNumber(int n) { this.shirtNumber = n; }
    public void setBaseSalary(double s) { this.baseSalary = s; }
}

