package model;


import org.json.JSONObject;

// Represents a player
public class Player {

    protected int id;
    private static int nextPlayerId = 1;
    private String playerName = null;
    protected int playerNum = 0;
    protected String playerPosition = null;
    private String playerDescription = null;
    protected String pickStatus = null;

    public Player() {

    }


    // REQUIRES: name is not empty string
    // EFFECTS: constructs a new player with name and empty statistics
    //          pickStatus set to null and nextPlayerId retrieves next
    //          available position integer;
    public Player(String name) {
//        id = nextPlayerId++;
        this.playerName = name;
        this.pickStatus = null;
    }

    // EFFECTS: returns player's name
    public String getPlayerName() {
        return playerName;
    }

    // MODIFIES: this
    // EFFECTS: sets player's name as playerName
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    // EFFECTS: returns player's number
    public int getPlayerNum() {
        return playerNum;
    }

    // MODIFIES: this
    // EFFECTS: sets player's number as playerNum
    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    // EFFECTS: returns player's position
    public String getPlayerPosition() {
        return playerPosition;
    }

    // MODIFIES: this
    // EFFECTS: sets player's position as playerPosition
    public void setPlayerPosition(String playerPosition) {
        this.playerPosition = playerPosition;
    }

    // EFFECTS: returns player's description
    public String getPlayerDescription() {
        return playerDescription;
    }

    // MODIFIES: this
    // EFFECTS: sets player's description as playerDescription
    public void setPlayerDescription(String playerDescription) {
        this.playerDescription = playerDescription;
    }

    // EFFECTS: returns player's pick status
    public String getPickStatus() {
        return pickStatus;
    }

    // MODIFIES: this
    // EFFECTS: sets player's status as pickStatus
    public void setPickStatus(String pickStatus) {
        this.pickStatus = pickStatus;
    }

}