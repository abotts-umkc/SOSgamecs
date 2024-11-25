package SOS449;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JUnit_TestComputerMove {
    private ComputerOpponent computerOpponent;
    private GeneralSOSGame game;

    @BeforeEach
    public void setUp() {
        game = new GeneralSOSGame(3);
        computerOpponent = new ComputerOpponent(game, 'S');
    }

    @Test
    public void testComputerMakesValidMove() {
        int[] move = computerOpponent.makeMove();

        int row = move[0];
        int col = move[1];

        assertTrue(row >= 0 && row < game.getBoardSize(), "Move row is out of bounds");
        assertTrue(col >= 0 && col < game.getBoardSize(), "Move col is out of bounds");
        assertNotEquals('\0', game.getBoard()[row][col], "Cell should not be empty after computer move");
    }

    @Test
    public void testComputerDoesNotPlaceOnOccupiedCell() {        
               
        int[] move = computerOpponent.makeMove();
       assertFalse(move[0] == 1 && move[1] == 1, "Computer should not place on an occupied cell");
    }

    @Test
    public void testComputerAlternatesLetters() {
        int[] firstMove = computerOpponent.makeMove();
        char firstLetter = game.getBoard()[firstMove[0]][firstMove[1]];

       assertTrue(firstLetter == 'S' || firstLetter == 'O', "First letter should be 'S' or 'O'");

       int[] secondMove = computerOpponent.makeMove();
        char secondLetter = game.getBoard()[secondMove[0]][secondMove[1]];

        assertNotEquals(firstLetter, secondLetter, "Computer should alternate letters");
    }
}

