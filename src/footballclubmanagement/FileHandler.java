/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package footballclubmanagement;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author AD
 */
public class FileHandler {
    private static final String PLAYERS_FILE    = "players.csv";
    private static final String MATCHES_FILE    = "matches.csv";
    private static final String TRAINING_FILE   = "training_sessions.csv";
    private static final String ATTENDANCE_FILE = "attendance_records.csv";

    // SAVE players
    public void savePlayers(List<Player> players) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(PLAYERS_FILE));
            for (Player p : players) {
                bw.write(
                    p.getPlayerID() + "," +
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

    // LOAD players
    public List<Player> loadPlayers() {
        List<Player> players = new ArrayList<>();
        try {
            File f = new File(PLAYERS_FILE);
            if (!f.exists()) {
                System.out.println("No save file found. Starting fresh.");
                return players;
            }

            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // skip blank lines
                String[] part = line.split(",");
                String type = part[7];
                Player p;
                if (type.equals("Star Player")) {
                    p = new StarPlayer(
                        part[0],                       // playerID
                        part[1],                       // fullName
                        Integer.parseInt(part[2]),     // age
                        part[3],                       // nationality
                        part[4],                       // position
                        Integer.parseInt(part[5]),     // shirtNumber
                        Double.parseDouble(part[6]),   // baseSalary
                        part[8]                        // status
                    );
                } else {
                    p = new RegularPlayer(
                        part[0],
                        part[1],
                        Integer.parseInt(part[2]),
                        part[3],
                        part[4],
                        Integer.parseInt(part[5]),
                        Double.parseDouble(part[6]),
                        part[8]
                    );
                }
                players.add(p);
            }
            br.close();
            System.out.println("Players loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error loading players: " + e.getMessage());
        }
        return players;
    }

    // check if save exists
    public boolean saveExists() {
        return new File(PLAYERS_FILE).exists();
    }
}
