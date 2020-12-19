package model;

import org.json.JSONObject;

public class PlayerItem {

    String playerName = new String();

    public PlayerItem(String inputName) {
        this.playerName = inputName;
    }

    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("playerName", playerName);
        return json.toString();
    }

    public PlayerItem fromJson(String item) {
        JSONObject json = new JSONObject(item);
        String playerName = json.getString("playerName");
        return new PlayerItem(playerName);
    }

    public String getName() {
        String returnName = playerName;
        return returnName;
    }

}
