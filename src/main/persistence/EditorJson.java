package persistence;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import exceptions.PlayerDoesNotExistException;
import model.Player;
import model.PlayerList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// EditorJson was created and based on the following tutorial:
// https://www.baeldung.com/jackson

// EditorJson handles database
public class EditorJson {

    // EFFECTS: retrieves content of playerListDatabase and returns it
    public static PlayerList retrieveFromJson()  {

        ObjectMapper objectMapper = new ObjectMapper();
        PlayerList retrievedPlayerList = null;
        try {
            retrievedPlayerList = objectMapper.readValue(new File("./data/playerListDatabase.json"),
                    PlayerList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retrievedPlayerList;
    }

    // MODIFIES: playerListDatabase
    // EFFECTS: updates playerLIstDatabase with passed in playerList
    public static void writeToJson(PlayerList playerList)  {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File("./data/playerListDatabase.json"), playerList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // MODIFIES: playerListDatabase
    // EFFECTS: retrieves playerList from database, adds a player, and writes playerList back to the json database
    public static void addPlayerToPlayerList(Player player)  {

        PlayerList retrievedPlayerList = null;

        retrievedPlayerList = retrieveFromJson();


        (retrievedPlayerList.getListPlayers()).add(player);

        // write updated PlayerList to JSON
        writeToJson(retrievedPlayerList);
    }

    // MODIFIES: playerListDatabase
    // EFFECTS: retrieves playerList from database, removes a player, and writes playerList back to the json database
    public static boolean removePlayerFromPlayerList(String playerToBeRemoved) throws PlayerDoesNotExistException {

        PlayerList retrievedPlayerList = null;
        retrievedPlayerList = retrieveFromJson();


        ArrayList<Player> playerList = retrievedPlayerList.getListPlayers();

        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).getPlayerName().equals(playerToBeRemoved)) {
                playerList.remove(i);
                writeToJson(retrievedPlayerList);
                return true;
            } else {
//                System.out.println("This player is not listed in the player list");
                throw new PlayerDoesNotExistException("This player is not listed in the player list.");
//                return false;
            }
        }
        return false;
    }

    // MODIFIES: Player
    // EFFECTS: retrieves existing player and sets their pick status to passed in pickStatus, then
    //          updates the playerListDatabase
    public static void togglePickStatusExistingPlayer(String playerToBeEdited, int pickStatus) {

        PlayerList playerListToEdit = null;
        playerListToEdit = retrieveFromJson();

        for (int i = 0; i < playerListToEdit.getListPlayers().size(); i++) {
            if (playerListToEdit.getListPlayers().get(i).getPlayerName().equals(playerToBeEdited)) {
                if (pickStatus == 1) {
                    playerListToEdit.getListPlayers().get(i).setPickStatus("Pick");

                } else {
                    playerListToEdit.getListPlayers().get(i).setPickStatus("Cut");
                }
                System.out.println("Player's status has been updated.");

                // EXIT THE LOOP
                break;


            } else {
                System.out.println("This player does not list in the player list");
            }
        }

        // UPDATE THE JSON
        writeToJson(playerListToEdit);
    }


    // EFFECTS: Outputs all players with "pick" pickStatus
    public static StringBuilder customPickedReturn() {

        PlayerList temp = retrieveFromJson();
        ArrayList<Player> listOfPlayer = temp.getListPlayers();

        StringBuilder returnCustomPicked = new StringBuilder();

        for (int i = 0; i < listOfPlayer.size(); i++) {
            if (listOfPlayer.get(i).getPickStatus().equals("Pick")) {
                returnCustomPicked.append(listOfPlayer.get(i).getPlayerName() + ", ");
            }
        }

        return returnCustomPicked;
    }

    // EFFECTS: finds and returns all picked players from playerList
    public static ArrayList<Player> findAndDisplayAllPickedPlayers()  {

        PlayerList originalList = null;
        originalList = retrieveFromJson();

        ArrayList<Player> playerList = originalList.getListPlayers();
        ArrayList<Player> pickedList = new ArrayList<>();

        for (int i = 0; i < playerList.size(); i++) {
            Player playerAssess = playerList.get(i);

            if (playerAssess.getPickStatus() == null) {
                System.out.println("Some of your players aren't picked or cut!");
            } else if (playerAssess.getPickStatus().equals("Pick")) {
                pickedList.add(playerList.get(i));
            }
        }
        pickedList = null;

        return pickedList;
    }

}
