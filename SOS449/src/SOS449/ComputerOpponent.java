package SOS449;

import java.util.Random;

public class ComputerOpponent {
    private Random random;
    private char computerLetter;
    private SOSGame game;
    private boolean alternate;

    public ComputerOpponent(SOSGame game, char initialLetter) {
        this.random = new Random();
        this.game = game;
        this.computerLetter = initialLetter;
        this.alternate = true; 
    }

    private char alternateLetter() {
        char letter = alternate ? 'S' : 'O';
        alternate = !alternate;
        return letter;
    }

    public int[] makeMove() {
        if (game.isGameOver()) {
            return new int[]{-1, -1}; 
        }

        int boardSize = game.getBoardSize();
        int[] move = new int[2];
        boolean moveMade = false;

        while (!moveMade) {
            int row = random.nextInt(boardSize);
            int col = random.nextInt(boardSize);
            if (game.getBoard()[row][col] == '\0') { 
                char bestLetter = findBestLetterForSOS(row, col);
                boolean placed = game.placeLetter(row, col, bestLetter);
                if (placed) {
                    move[0] = row;
                    move[1] = col;
                    moveMade = true;
                    computerLetter = bestLetter;
                    System.out.println("Computer placed " + bestLetter + " at (" + row + ", " + col + ")");
                }
            }
        }

        return move;
    }

    private char findBestLetterForSOS(int row, int col) {
        game.getBoard()[row][col] = 'S';
        boolean sosWithS = game.checkForSOS(row, col, 'S');
        game.getBoard()[row][col] = '\0'; 

        game.getBoard()[row][col] = 'O';
        boolean sosWithO = game.checkForSOS(row, col, 'O');
        game.getBoard()[row][col] = '\0'; 

        if (sosWithS) {
            return 'S';
        } else if (sosWithO) {
            return 'O';
        } else {
            return (random.nextBoolean()) ? 'S' : 'O';
        }
    }

    public char getComputerLetter() {
        return computerLetter;
    }
}
