/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package footballclubmanagement;
import java.util.*;
/**
 *
 * @author AD
 */
public class PlayerManager {
    private List<Player> players = new ArrayList<>();

    public void addPlayer(Player p) { players.add(p); }

    public List<Player> getAllPlayers() { return players; }

    public Player findById(String id) {
        for (Player p : players) {
            if (p.getPlayerID().equalsIgnoreCase(id)) return p;
        }
        return null;
    }

    public Set<String> getActivePlayerIds() {
        Set<String> ids = new LinkedHashSet<>();
        for (Player p : players) {
            if (p.getStatus().equalsIgnoreCase("Active")) {
                ids.add(p.getPlayerID());
            }
        }
        return ids;
    }
}
