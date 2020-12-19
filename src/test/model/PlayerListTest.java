package model;

import exceptions.PlayerAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;



// tests for PlayerList class
public class PlayerListTest {

    private Player testPlayerOne;
    private Player testPlayerTwo;

    PlayerList testListPlayer;

    @BeforeEach
    void runBefore() {
        testPlayerOne = new Player("Jill");
        testPlayerTwo = new Player("Jack");

        testListPlayer = new PlayerList();

    }

    @Test
    void testAddPlayerToListPlayer() {
        try {
            testListPlayer.addPlayer(testPlayerOne);
        } catch (PlayerAlreadyExistsException e) {
            e.printStackTrace();
        }
        assertEquals(1, testListPlayer.length());

        try {
            testListPlayer.addPlayer(testPlayerTwo);
        } catch (PlayerAlreadyExistsException e) {
            e.printStackTrace();
        }
        assertEquals(2, testListPlayer.length());
    }

    @Test
    void testCompileListEmpty() {
        assertEquals(0, testListPlayer.length());
        StringBuilder testReturn = testListPlayer.returnList();
        assertEquals(0, testReturn.length());
    }

    @Test
    void testCompileListNotEmptyNoStats() {

        try {
            testListPlayer.addPlayer(testPlayerOne);
        } catch (PlayerAlreadyExistsException e) {
            e.printStackTrace();
        }

        assertEquals(1, testListPlayer.length());
        StringBuilder testReturn = testListPlayer.returnList();
        assertEquals(69, testReturn.length());
    }

    @Test
    void testDeletePlayerFail() {
        try {
            testListPlayer.addPlayer(testPlayerOne);
        } catch (PlayerAlreadyExistsException e) {
            e.printStackTrace();
        }
        assertEquals(1, testListPlayer.length());

        testListPlayer.deletePlayer("Peter");
        assertEquals(1, testListPlayer.length());
    }

    @Test
    void testSetPickStatusExceptionNotThrown() {
        testPlayerOne.setPickStatus("Pick");
        try {
            testListPlayer.addPlayer(testPlayerOne);
        } catch (PlayerAlreadyExistsException e) {
            e.printStackTrace();
        }
    }



    @Test
    void testSetListPlayers() {
        Player newTestPlayer = new Player("Georgia");
        ArrayList<Player> PlayerListTwo = new ArrayList<Player>();

        PlayerListTwo.add(newTestPlayer);

        testListPlayer.setListPlayers(PlayerListTwo);

        assertEquals(1, testListPlayer.length());
    }

}
