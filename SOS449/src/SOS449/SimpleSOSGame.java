package SOS449;

public class SimpleSOSGame extends SOSGame {

    private boolean gameWon = false;
    private boolean isPlayerOneWinner = false;

    public SimpleSOSGame(int boardSize) {
        super(boardSize);
    }

    @Override
    public boolean placeLetter(int row, int col, char letter) {
        boolean placed = setBoardCell(row, col, letter); 

        if (placed) {
            System.out.println("Placed letter " + letter + " at (" + row + ", " + col + ")");
            
            playerBoard[row][col] = isPlayerOneTurn ? 1 : 2;  

            if (checkForSOS(row, col, letter)) {
                gameWon = true;
                isPlayerOneWinner = isPlayerOneTurn;
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
        int currentPlayer = playerBoard[row][col];
        return checkAllDirectionsForSOS(row, col, currentPlayer);
    }

    @Override
    public boolean isGameOver() {
        return gameWon || isBoardFull();
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public String getWinner() {
        if (gameWon) {
            return isPlayerOneWinner ? "Player 1" : "Player 2";
        }
        return null; 
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

    private boolean checkAllDirectionsForSOS(int row, int col, int currentPlayer) {
        int[][] directions = {
            {-1,  0}, {-1,  1}, { 0,  1}, { 1,  1}, 
            { 1,  0}, { 1, -1}, { 0, -1}, {-1, -1}
        };

        for (int[] dir : directions) {
            if (checkSOSInDirection(row, col, dir[0], dir[1], currentPlayer)) {
                return true;
            }
        }
        return false;
    }

    protected boolean checkSOSInDirection(int row, int col, int dRow, int dCol, int currentPlayer) {
        System.out.println("Checking direction (" + dRow + ", " + dCol + ") from (" + row + ", " + col + ") for player " + currentPlayer);
        
        if (board[row][col] != 'S' || playerBoard[row][col] != currentPlayer) {
            System.out.println("No 'S' found at (" + row + ", " + col + ") or incorrect player");
            return false; 
        }

        int middleRow = row + dRow;
        int middleCol = col + dCol;
        int endRow = row + 2 * dRow;
        int endCol = col + 2 * dCol;

        if (isWithinBounds(middleRow, middleCol) && isWithinBounds(endRow, endCol)) {
            if (board[middleRow][middleCol] == 'O' && playerBoard[middleRow][middleCol] == currentPlayer &&
                board[endRow][endCol] == 'S' && playerBoard[endRow][endCol] == currentPlayer) {
                System.out.println("SOS sequence found from (" + row + ", " + col + ") in direction (" + dRow + ", " + dCol + ")");
                return true;
            } else {
                System.out.println("No valid SOS sequence in this direction.");
            }
        } else {
            System.out.println("Out of bounds when checking direction (" + dRow + ", " + dCol + ")");
        }

        return false;
    }

}

