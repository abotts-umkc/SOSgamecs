package SOS449;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JUnit_TestGeneralWin {

    @Test
    public void testGeneralGameWin() {
        GeneralSOSGame generalGame = new GeneralSOSGame(3);

        generalGame.placeLetter(0, 0, 'S');
        generalGame.placeLetter(0, 1, 'O');
        generalGame.placeLetter(0, 2, 'S');

        System.out.println("Player 1 Score: " + generalGame.getPlayer1Score() + ", Player 2 Score: " + generalGame.getPlayer2Score());

        generalGame.placeLetter(1, 0, 'O');

        generalGame.placeLetter(1, 1, 'S');
        generalGame.placeLetter(1, 2, 'O');
        generalGame.placeLetter(2, 0, 'S');

        System.out.println("Player 1 Score: " + generalGame.getPlayer1Score() + ", Player 2 Score: " + generalGame.getPlayer2Score());

        assertFalse(generalGame.isGameOver(), "The game should not be over yet.");
        assertTrue(generalGame.getPlayer1Score() > generalGame.getPlayer2Score(), "Player 1 should have a higher score than Player 2.");
    }
}

