package SOS449;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JUnit_TestComExtraTurn {
    private ComputerOpponent computerOpponent;
    private GeneralSOSGame game;

    @BeforeEach
    public void setUp() {
        game = new GeneralSOSGame(3);
        computerOpponent = new ComputerOpponent(game, 'S');

        game.placeLetter(0, 0, 'S'); 
        game.placeLetter(0, 2, 'S'); 
    }

    @Test
    public void testComputerGetsExtraTurnAfterSOS() {
        game.placeLetter(0, 1, 'O'); 
        game.placeLetter(0, 3, 'S'); 
        
        int[] move = computerOpponent.makeMove();
        int row = move[0];
        int col = move[1];

        assertTrue(row >= 0 && row < game.getBoardSize(), "Move row is out of bounds");
        assertTrue(col >= 0 && col < game.getBoardSize(), "Move col is out of bounds");
        assertNotEquals('\0', game.getBoard()[row][col], "Cell should not be empty after computer move");

        assertTrue(game.checkForSOS(row, col, game.getBoard()[row][col]), "SOS should have been completed");

        assertFalse(game.isPlayerOneTurn(), "Computer should get another turn after completing an SOS");
    }
} 
