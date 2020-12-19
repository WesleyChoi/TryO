package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


// Tests for Player class
class PlayerTest {

    private Player testPlayer;

    @BeforeEach
    void runBefore() {
        testPlayer = new Player("Jill");
    }

    @Test
    void testInitPlayer() {
        assertEquals("Jill", testPlayer.getPlayerName());
        assertNull(testPlayer.getPickStatus());
//        assertEquals(4, testPlayer.getId());
    }

    @Test
    void testNewPlayerConstructor() {
        testPlayer = new Player();
        testPlayer.setPlayerName("George");
        assertEquals("George", testPlayer.getPlayerName());
    }

    @Test
    void testNextPlayerId() {
        Player testPlayerTwo = new Player("Jack");
        Player testPlayerThree = new Player("Josephine");

//        assertEquals(7, testPlayerTwo.getId());
//        assertEquals(testPlayerThree.getId(), 8);
    }

    @Test
    void testSetPlayerPosition() {
        testPlayer.setPlayerPosition("Quarterback");
        assertEquals("Quarterback", testPlayer.getPlayerPosition());
    }

    @Test
    void testSetPlayerNumber() {
        testPlayer.setPlayerNum(32);
        assertEquals(testPlayer.getPlayerNum(), 32);
    }

    @Test
    void testSetPlayerDescription() {
        testPlayer.setPlayerDescription("Small but quick");
        assertEquals("Small but quick", testPlayer.getPlayerDescription());
    }

    @Test
    void testPlayerChangeStatusWantToPick() {
        assertNull(testPlayer.getPickStatus());
        testPlayer.setPickStatus("Pick");
        assertEquals("Pick", testPlayer.getPickStatus());
    }

    @Test
    void testPlayerChangeStatusWantToCut() {
        assertNull(testPlayer.getPickStatus());
        testPlayer.setPickStatus("Cut");
        assertEquals("Cut", testPlayer.getPickStatus());
    }


}