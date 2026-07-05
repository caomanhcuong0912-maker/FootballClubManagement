package com.football.management;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MatchRecord {
    private String matchId;
    private LocalDate matchDate;
    private String opponentTeam;
    private String matchType;
    private List<PerformanceRecord> performanceRecords;

    public MatchRecord(String matchId, LocalDate matchDate, String opponentTeam, String matchType) {
        this.matchId = matchId;
        this.matchDate = matchDate;
        this.opponentTeam = opponentTeam;
        this.matchType = matchType;
        this.performanceRecords = new ArrayList<>();
    }

    public void addOrUpdatePerformanceRecord(PerformanceRecord record) {
        PerformanceRecord existing = getPerformanceByPlayerId(record.getPlayerId());
        if (existing != null) {
            performanceRecords.remove(existing);
        }
        performanceRecords.add(record);
    }

    public PerformanceRecord getPerformanceByPlayerId(String playerId) {
        for (PerformanceRecord pr : performanceRecords) {
            if (pr.getPlayerId().equalsIgnoreCase(playerId)) {
                return pr;
            }
        }
        return null;
    }

    public String getMatchId() { 
        return matchId; 
    }
    
    public LocalDate getMatchDate() { 
        return matchDate; 
    }
    
    public String getOpponentTeam() { 
        return opponentTeam; 
    }
    
    public String getMatchType() { 
        return matchType; 
    }
    
    public List<PerformanceRecord> getPerformanceRecords() { 
        return performanceRecords; 
    }
}