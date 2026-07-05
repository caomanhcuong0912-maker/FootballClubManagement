/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package footballclubmanagement;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author AD
 */
public class FileHandler {
    private static final String PLAYERS_FILE = "players.csv";
    private static final String MATCHES_FILE = "matches.csv";
    private static final String TRAINING_FILE = "training_sessions.csv";
    private static final String ATTENDANCE_FILE = "attendance_records.csv";
    public void savePlayers(List<Players> players) {
    try {
        File f = new File(PLAYERS_FILE);
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);

        for (Players p : players) {
            bw.write(
                p.getPlayerId() + "," +
                p.getFullName() + "," +
                p.getAge() + "," +
                p.getNationality() + "," +
                p.getPosition() + "," +
                p.getShirtNumber() + "," +
                p.getBaseSalary() + "," +
                p.getPlayerType() + "," +
                p.getStatus()
            );
            bw.newLine();
        }

        bw.close();
        System.out.println("Players saved successfully!");
    } catch (IOException e) {
        System.out.println("Error saving players: " + e.getMessage());
    }
}
    public List<Players> loadPlayers() {
    List<Players> players = new ArrayList<>();
    try {
        File f = new File(PLAYERS_FILE);
        if (!f.exists()) {
            System.out.println("No save file found. Starting fresh.");
            return players; // return empty list
        }

        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        while ((line = br.readLine()) != null) {
            String[] part = line.split(",");
            Players p = new Players(
                part[0],                        // playerID
                part[1],                        // fullName
                Integer.parseInt(part[2]),      // age
                part[3],                        // nationality
                part[4],                        // position
                Integer.parseInt(part[5]),      // shirtNumber
                Double.parseDouble(part[6]),    // baseSalary
                part[7],                        // playerType
                part[8]                         // status
            );
            players.add(p);
        }
        br.close();
        System.out.println("Players loaded successfully!");
    } catch (IOException e) {
        System.out.println("Error loading players: " + e.getMessage());
    }
    return players;
}
}
