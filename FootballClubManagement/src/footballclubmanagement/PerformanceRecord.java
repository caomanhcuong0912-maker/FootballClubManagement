package com.football.management;

public class PerformanceRecord {
    private String playerId;
    private int minutesPlayed;
    private int goals;
    private int assists;
    private int yellowCards;
    private int redCards;
    private double points;

    public PerformanceRecord(String playerId, int minutesPlayed, int goals, int assists, int yellowCards, int redCards) {
        this.playerId = playerId;
        this.minutesPlayed = minutesPlayed;
        this.goals = goals;
        this.assists = assists;
        this.yellowCards = yellowCards;
        this.redCards = redCards;
        this.points = calculatePoints();
    }

    public static boolean validateMinutes(int minutesPlayed) {
        return minutesPlayed >= 0 && minutesPlayed <= 120;
    }

    public static boolean validateStats(int goals, int assists, int yellowCards, int redCards, int minutesPlayed) {
        if (goals < 0 || assists < 0 || yellowCards < 0 || redCards < 0) {
            return false;
        }
        if (minutesPlayed == 0) {
            return goals == 0 && assists == 0 && yellowCards == 0 && redCards == 0;
        }
        return true;
    }

    public final double calculatePoints() {
        double calculatedPoints = (goals * 5) + (assists * 3) - (yellowCards * 1) - (redCards * 3);
        return Math.max(0, calculatedPoints);
    }

    public String getPlayerId() { 
        return playerId; 
    }
    
    public void setPlayerId(String playerId) { 
        this.playerId = playerId; 
    }

    public int getMinutesPlayed() { 
        return minutesPlayed; 
    }
    
    public void setMinutesPlayed(int minutesPlayed) { 
        this.minutesPlayed = minutesPlayed; 
        this.points = calculatePoints();
    }

    public int getGoals() { 
        return goals; 
    }
    
    public void setGoals(int goals) { 
        this.goals = goals; 
        this.points = calculatePoints(); 
    }

    public int getAssists() { 
        return assists; 
    }
    
    public void setAssists(int assists) { 
        this.assists = assists; 
        this.points = calculatePoints(); 
    }

    public int getYellowCards() { 
        return yellowCards; 
    }
    
    public void setYellowCards(int yellowCards) { 
        this.yellowCards = yellowCards; 
        this.points = calculatePoints(); 
    }

    public int getRedCards() { 
        return redCards; 
    }
    
    public void setRedCards(int redCards) { 
        this.redCards = redCards; 
        this.points = calculatePoints(); 
    }

    public double getPoints() { 
        return points; 
    }
}