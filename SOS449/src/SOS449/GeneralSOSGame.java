package SOS449;

public class GeneralSOSGame extends SOSGame {

    private int player1Score = 0;
    private int player2Score = 0;

    public GeneralSOSGame(int boardSize) {
        super(boardSize);
    }

    @Override
    public boolean placeLetter(int row, int col, char letter) {
        boolean placed = setBoardCell(row, col, letter); 

        if (placed) {
            playerBoard[row][col] = isPlayerOneTurn ? 1 : 2; 
            int sosCount = checkAllDirectionsForSOS(row, col);
            if (sosCount > 0) {
                incrementScore(isPlayerOneTurn, sosCount);
            } else {
                switchPlayer(); 
            }
        } else {
            System.out.println("Attempted to place letter at (" + row + ", " + col + "), but it is already occupied.");
        }

        return placed;
    }


    @Override
    public boolean checkForSOS(int row, int col, char letter) {
        return checkAllDirectionsForSOS(row, col) > 0;
    }

    @Override
    public boolean isGameOver() {
        return isBoardFull();
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    private void incrementScore(boolean isPlayerOne, int points) {
        if (isPlayerOne) {
            player1Score += points;
        } else {
            player2Score += points;
        }
        System.out.println("Scores updated: Player 1 - " + player1Score + ", Player 2 - " + player2Score);
    }

    private boolean isBoardFull() {
        for (char[] rowArray : board) {
            for (char cell : rowArray) {
                if (cell == '\0') {
                    return false;
                }
            }
        }
        return true;
    }

    private int checkAllDirectionsForSOS(int row, int col) {
        System.out.println("Checking all directions for SOS starting from (" + row + ", " + col + ")");
        int sosCount = 0;
        int currentPlayer = playerBoard[row][col]; 

        int[][] directions = {
            {-1,  0}, // Up
            {-1,  1}, // Up Right
            { 0,  1}, // Right
            { 1,  1}, // Down Right
            { 1,  0}, // Down
            { 1, -1}, // Down Left
            { 0, -1}, // Left
            {-1, -1}  // Up Left
        };

        for (int[] dir : directions) {
            boolean isSOS = checkSOSInDirection(row, col, dir[0], dir[1], currentPlayer);
            if (isSOS) {
                System.out.println("SOS detected in direction (" + dir[0] + ", " + dir[1] + ")");
                sosCount++;
            }
        }

        System.out.println("Total SOS count found: " + sosCount);
        return sosCount;
    }

}
