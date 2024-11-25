package SOS449;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JUnit_TestWinConditions {

    @Test
    public void testSimpleGameWin() {
        SimpleSOSGame simpleGame = new SimpleSOSGame(3);

        simpleGame.placeLetter(0, 0, 'S');
        simpleGame.placeLetter(0, 1, 'O');
        simpleGame.placeLetter(0, 2, 'S');

        assertTrue(simpleGame.isGameOver(), "The game should be over.");
        assertTrue(simpleGame.isGameWon(), "Player 1 should have won the game.");
        assertEquals("Player 1", simpleGame.getWinner(), "Player 1 should be declared the winner.");
    }
}

