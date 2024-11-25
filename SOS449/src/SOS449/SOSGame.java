package SOS449;

public abstract class SOSGame {
    protected int boardSize;
    protected char[][] board; 
    protected boolean isPlayerOneTurn;
    protected int[][] playerBoard; 


    public SOSGame(int boardSize) {
        if (boardSize < 3) {
            throw new IllegalArgumentException("Board size must be at least 3.");
        }
        this.boardSize = boardSize;
        this.board = new char[boardSize][boardSize];
        this.playerBoard = new int[boardSize][boardSize]; 
        this.isPlayerOneTurn = true;
    }


    public boolean isPlayerOneTurn() {
        return isPlayerOneTurn;
    }

    public void switchPlayer() {
        isPlayerOneTurn = !isPlayerOneTurn;
    }

    public int getBoardSize() {
        return boardSize;
    }

   public char[][] getBoard() {
        char[][] boardCopy = new char[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            System.arraycopy(board[i], 0, boardCopy[i], 0, boardSize);
        }
        return boardCopy;
    }

   public boolean placeLetter(int row, int col, char letter) {
        if (board[row][col] == '\0') {
            board[row][col] = letter;
            return true;
        } else {
            return false; 
        }
    }

    protected boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < boardSize && col >= 0 && col < boardSize;
    }
    
    protected boolean setBoardCell(int row, int col, char letter) {
        if (board[row][col] == '\0') {
            board[row][col] = letter;
            return true; 
        } else {
            return false; 
        }
    }


    protected boolean checkSOSInDirection(int row, int col, int dRow, int dCol, int currentPlayer) {
        if (board[row][col] != 'S' || playerBoard[row][col] != currentPlayer) {
            return false; 
        }

        int middleRow = row + dRow;
        int middleCol = col + dCol;
        int endRow = row + 2 * dRow;
        int endCol = col + 2 * dCol;

        if (isWithinBounds(middleRow, middleCol) && isWithinBounds(endRow, endCol)) {
           if (board[middleRow][middleCol] == 'O' && playerBoard[middleRow][middleCol] == currentPlayer &&
                board[endRow][endCol] == 'S' && playerBoard[endRow][endCol] == currentPlayer) {
                return true;
            }
        }
        return false;
    }


    public abstract boolean checkForSOS(int row, int col, char letter);

    public abstract boolean isGameOver();
}

