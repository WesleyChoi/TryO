package model;

import exceptions.PlayerAlreadyExistsException;
import exceptions.PlayerDoesNotExistException;

import java.util.ArrayList;

import static persistence.EditorJson.*;
import static persistence.EditorJson.findAndDisplayAllPickedPlayers;

public class PlayerList {

    ArrayList<Player> listPlayers;
    StringBuilder returnList = new StringBuilder();
    StringBuilder returnPickedList = new StringBuilder();
    ArrayList<Player> retrievedPickedPlayers;

    // EFFECTS: constructs new player list
    public PlayerList() {
        listPlayers = new ArrayList<>();
    }

    // MODIFIES: this, playerListDatabase
    // EFFECTS: adds new player to end of linked list listPlayers
    public void addPlayer(Player player) throws PlayerAlreadyExistsException {
        if (listPlayers.contains(player)) {
            throw new PlayerAlreadyExistsException("This player already exists in the player list.");
        } else {
            listPlayers.add(player);
            addPlayerToPlayerList(player);
        }
    }

    // MODIFIES: playerListDatabase
    // EFFECTS: removes an existing player from playerListDatabase
    public void deletePlayer(String playerToBeRemoved) {
        try {
            removePlayerFromPlayerList(playerToBeRemoved);
        } catch (PlayerDoesNotExistException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: returns size of list
    public Integer length() {
        return listPlayers.size();
    }

    // MODIFIES: this
    // EFFECTS: returns player name, number, position, description, and status
    public StringBuilder compileList(int i) {

        Player player = listPlayers.get(i);
        String playerName = " Name: " + player.getPlayerName();
        String playerNum = " Number: " + player.getPlayerNum();
        String playerPos = " Position: " + player.getPlayerPosition();
        String playerDescription = " Description: " + player.getPlayerDescription();
        String pickStatus = " Status: " + player.getPickStatus();


        returnList.append((i + 1) + playerName + playerNum + playerPos + playerDescription + pickStatus + "\n");
        return returnList;
    }

    // MODIFIES: this
    // EFFECTS: returns listPlayers, and all the players inside it
    public StringBuilder returnList() {
        returnList.delete(0, 50000);

        for (int i = 0; i < listPlayers.size(); i++) {
            compileList(i);
        }
        return returnList;
    }

    // EFFECTS: returns all players whose pickStatus is "pick"
    public void returnPickedList() {

        //returnPickedList.delete(0, 50000);

        retrievedPickedPlayers = findAndDisplayAllPickedPlayers();
        for (int i = 0; i < retrievedPickedPlayers.size(); i++) {
            System.out.println(retrievedPickedPlayers.get(i).getPlayerName());
        }
    }

    // EFFECTS: returns listPlayers
    public ArrayList<Player> getListPlayers() {
        return listPlayers;
    }

    // MODIFIES: this
    // EFFECTS: sets listPlayers as playerArrayList
    public void setListPlayers(ArrayList<Player> playerArrayList) {
        listPlayers = playerArrayList;
    }

}
