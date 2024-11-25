package SOS449;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JUnit_TestStartGame {

    @Test
    public void testStartSimpleGame() {
        SimpleSOSGame simpleGame = new SimpleSOSGame(3);

        assertEquals(3, simpleGame.getBoardSize(), "The board size should be 3.");
        for (char[] row : simpleGame.getBoard()) {
            for (char cell : row) {
                assertEquals('\0', cell, "The board cell should be empty at the start.");
            }
        }

        assertTrue(simpleGame.isPlayerOneTurn(), "It should be Player 1's turn at the start.");
    }

    @Test
    public void testStartGeneralGame() {
        GeneralSOSGame generalGame = new GeneralSOSGame(5);

        assertEquals(5, generalGame.getBoardSize(), "The board size should be 5.");
        for (char[] row : generalGame.getBoard()) {
            for (char cell : row) {
                assertEquals('\0', cell, "The board cell should be empty at the start.");
            }
        }
        assertTrue(generalGame.isPlayerOneTurn(), "It should be Player 1's turn at the start.");
    }
} 
