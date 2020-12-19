package persistence;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.PlayerAlreadyExistsException;
import exceptions.PlayerDoesNotExistException;
import model.Player;
import model.PlayerItem;
import model.PlayerList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// TEST CASES FOR EDITORJSON: AFTER RUNNING, MAKE SURE TO RESET playerListDatabase.json


public class TestEditorJson extends EditorJson {

    private PlayerList testPlayerList;
    private Player testPlayer;

    @BeforeEach
    void runBefore() {
        ObjectMapper objectMapper = new ObjectMapper();

        testPlayerList = new PlayerList();
        testPlayer = new Player("Jack");
        testPlayer.setPickStatus("Pick");
        try {
            testPlayerList.addPlayer(testPlayer);
        } catch (PlayerAlreadyExistsException e) {
            e.printStackTrace();
        }


        try {
            objectMapper.writeValue(new File("./data/testPlayerListDatabase.json"), testPlayerList);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testViewPickedPlayers() {
        ArrayList<Player> pickedPlayerList = null;
        ArrayList<Player> testRetrievedPickedPlayers = new ArrayList<>();
        testRetrievedPickedPlayers = findAndDisplayAllPickedPlayers();
        assertEquals(pickedPlayerList, testRetrievedPickedPlayers);
    }

    // this test does not work because it connect to playerListDatabase instead
    // of testPlayerListDatabase
    @Test
    void testViewCustomPickedPlayers() {
        StringBuilder testReturnedCustomPicked = new StringBuilder();
        testReturnedCustomPicked.append("Jack, Jack, Jack, Jack, Jack, ");
        String testReturnedCustomPickedToString = testReturnedCustomPicked.toString();

        StringBuilder returnedCustomPicked = new StringBuilder();
        returnedCustomPicked.append(customPickedReturn());
        String returnedCustomPickedToString = returnedCustomPicked.toString();

        assertEquals(testReturnedCustomPickedToString, returnedCustomPickedToString);
    }

    @Test
    void testAddPlayerToPlayerListAndWriteToJson() {
        assertEquals(1, testPlayerList.length());
    }

    @Test
    void testRemovePlayerFromPlayerListSuccess() {
        assertEquals(1, testPlayerList.length());
        testPlayerList.deletePlayer("Jack");

        assertEquals(1, testPlayerList.length());
    }

    @Test
    void testRemovePlayerFromPlayerListFail() {
        assertEquals(1, testPlayerList.length());

        testPlayerList.deletePlayer("Andrew");

        assertEquals(1, testPlayerList.length());
        try {
            assertFalse(removePlayerFromPlayerList("Andrew"));
        } catch (PlayerDoesNotExistException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testTogglePlayerFromPickToCut() {
        Player testPlayerToggle = new Player("Brian");
        try {
            testPlayerList.addPlayer(testPlayerToggle);
        } catch (PlayerAlreadyExistsException e) {
            e.printStackTrace();
        }
        togglePickStatusExistingPlayer("Brian", 0);
        ArrayList<Player> retrieveLastPlayer = new ArrayList<>();
        retrieveLastPlayer = retrieveFromJson().getListPlayers();
        Player lastPlayer = retrieveLastPlayer.get(10);
        assertEquals("Cut", lastPlayer.getPickStatus());
    }

    @Test
    void testTogglePlayerFromCutToPick() {
        togglePickStatusExistingPlayer("Jack", 1);
        assertEquals("Pick", testPlayer.getPickStatus());
    }

    @Test
    void testTogglePlayerNotFound() {
        testPlayer.getPlayerName();
        togglePickStatusExistingPlayer("Jonathan", 0);

    }

    @Test
    void TestIoExceptionWriteValue() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writeValue(new File(""), testPlayerList);
            fail("IOException expected!");
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void TestToJson() {
        PlayerItem testToJson = new PlayerItem("Max");
        testToJson.toJson();
        assertEquals("{\"playerName\":\"Max\"}", testToJson.toJson());
    }

    @Test
    void TestFromJson() {
        String testName = "{\"playerName\":\"Max\"}";
        PlayerItem testFromJson = new PlayerItem("");
        testFromJson.fromJson(testName);
        assertEquals("Max", testFromJson.fromJson(testName).getName());

    }


}
