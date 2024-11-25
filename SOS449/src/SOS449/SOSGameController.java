package SOS449;

import javax.swing.Timer;
import java.util.ArrayList;
import java.io.*;
import java.util.List;

public class SOSGameController {
    private SOSGame model;
    private SOSGameView view;
    private GameRecording recording;
    private Timer replayTimer;

    public SOSGameController() {
    }

    public void setView(SOSGameView view) {
        this.view = view;
    }

    private ComputerOpponent computerOpponent; 
    private ComputerOpponent computerOpponent1; 
    private ComputerOpponent computerOpponent2; 

    public void startGame(boolean isVsComputer) {
        try {
            String boardSizeStr = view.getBoardSizeInput();
            int boardSize = Integer.parseInt(boardSizeStr);

            if (boardSize < 3) {
                throw new NumberFormatException("Board size must be at least 3.");
            }

            boolean isSimpleGame = view.isSimpleGameSelected();

            if (isSimpleGame) {
                model = new SimpleSOSGame(boardSize);
            } else {
                model = new GeneralSOSGame(boardSize);
            }

            if (isVsComputer) {
                computerOpponent = new ComputerOpponent(model, 'S'); 
            }
            
            if (recording == null) {
            	recording = new GameRecording(); 
            }

            view.showGameScreen(boardSize, model);
            view.updateTurnLabel("Player 1's turn");

        } catch (NumberFormatException ex) {
            view.showErrorMessage("Invalid board size. Please enter a number greater than or equal to 3.");
        }
    }

    public void startComputerVsComputerGame(boolean isSimpleGame) {
        try {
            String boardSizeStr = view.getBoardSizeInput();
            int boardSize = Integer.parseInt(boardSizeStr);

            if (boardSize < 3) {
                throw new NumberFormatException("Board size must be at least 3.");
            }

            if (isSimpleGame) {
                model = new SimpleSOSGame(boardSize);
            } else {
                model = new GeneralSOSGame(boardSize);
            }

            computerOpponent1 = new ComputerOpponent(model, 'S');
            computerOpponent2 = new ComputerOpponent(model, 'O');

            view.showGameScreen(boardSize, model);

            runComputerVsComputerGame();

        } catch (NumberFormatException ex) {
            view.showErrorMessage("Invalid board size. Please enter a number greater than or equal to 3.");
        }
    }

    private void runComputerVsComputerGame() {
        Timer timer = new Timer(1000, e -> {
            if (!model.isGameOver()) {
                ComputerOpponent currentComputer = model.isPlayerOneTurn() ? computerOpponent1 : computerOpponent2;
                int[] move = currentComputer.makeMove();
                if (move[0] >= 0 && move[1] >= 0) {
                    view.updateBoard(model.isPlayerOneTurn(), move[0], move[1], currentComputer.getComputerLetter());
                    view.updateTurnLabel("Player " + (model.isPlayerOneTurn() ? "1" : "2") + "'s turn");
                }
            } else {
                ((Timer) e.getSource()).stop(); 
                handleGameOver();
            }
        });

        timer.setInitialDelay(0);
        timer.start();
    }
    
    public void handleCellClick(int row, int col, char letter) {
        if (model.isGameOver()) {
            view.showErrorMessage("The game is already over. No more moves allowed.");
            return;
        }

        boolean isPlayerOneTurn = model.isPlayerOneTurn(); 

        System.out.println("handleCellClick called: Player " + (isPlayerOneTurn ? "1" : "2") + " is placing " + letter);

        if (model.placeLetter(row, col, letter)) {
            System.out.println("Letter " + letter + " placed successfully at (" + row + ", " + col + ")");
            view.updateBoard(isPlayerOneTurn, row, col, letter);
            
            if (recording != null) {
            	recording.recordMove(row, col, letter);
            }

            view.updateTurnLabel("Player " + (model.isPlayerOneTurn() ? "1" : "2") + "'s turn");
            System.out.println("After placeLetter: It is now Player " + (model.isPlayerOneTurn() ? "1" : "2") + "'s turn");

            if (computerOpponent != null && !model.isPlayerOneTurn() && !model.isGameOver()) {
                int[] move = computerOpponent.makeMove();
                if (move[0] >= 0 && move[1] >= 0) {
                    int compRow = move[0];
                    int compCol = move[1];

                    char computerMoveLetter = computerOpponent.getComputerLetter();
                    
                    if (recording != null) {
                    	recording.recordMove(compRow, compCol, computerMoveLetter);
                    }
                    view.updateBoard(false, compRow, compCol, computerMoveLetter);
                    System.out.println("Computer placed " + computerMoveLetter + " at (" + compRow + ", " + compCol + ")");

                    view.updateTurnLabel("Player " + (model.isPlayerOneTurn() ? "1" : "2") + "'s turn");
                    System.out.println("After computer move: It is now Player " + (model.isPlayerOneTurn() ? "1" : "2") + "'s turn");
                }
            }

            if (model.isGameOver()) {
                handleGameOver();
                if (recording != null) {
                	recording.saveRecording(); 
                	recording = null; 
                }
            }
        } else {
            view.showErrorMessage("Invalid move. The cell is already occupied.");
        }
    }

    private void handleGameOver() {
        String message;
        if (model instanceof GeneralSOSGame) {
            GeneralSOSGame generalGame = (GeneralSOSGame) model;
            int player1Score = generalGame.getPlayer1Score();
            int player2Score = generalGame.getPlayer2Score();
            if (player1Score > player2Score) {
                message = "Game over! Player 1 wins!";
            } else if (player2Score > player1Score) {
                message = "Game over! Player 2 wins!";
            } else {
                message = "Game over! It's a tie!";
            }
        } else if (model instanceof SimpleSOSGame) {
            SimpleSOSGame simpleGame = (SimpleSOSGame) model;
            if (simpleGame.isGameWon()) {
                message = simpleGame.getWinner() + " wins!";
            } else {
                message = "Game over! It's a draw!";
            }
        } else {
            message = "Game over! It's a draw!";
        }
        view.showWinner(message);
        view.resetToInitialSetup();
    }
    
    public void startRecordingGame() {
    	recording = new GameRecording();
    	System.out.println("Game recording started.");
    }
   
    public void resetGame() {
        if (replayTimer != null) {
            replayTimer.stop(); 
        }

        if (view != null) {
            view.resetToInitialSetup();  
        }

        model = null;  
        computerOpponent = null;
        computerOpponent1 = null;
        computerOpponent2 = null;

        System.out.println("Game reset complete.");
    }

    
    public void replayGame(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            resetGame();

            String boardSizeStr = view.getBoardSizeInput();
            int boardSize = Integer.parseInt(boardSizeStr);

            boolean isSimpleGame = view.isSimpleGameSelected();

            if (isSimpleGame) {
                model = new SimpleSOSGame(boardSize);
            } else {
                model = new GeneralSOSGame(boardSize);
            }

            view.showGameScreen(boardSize, model);

            List<String> moves = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim(); 
                if (!line.isEmpty() && line.startsWith("Move: ")) {
                    System.out.println("Read line: " + line);  
                    moves.add(line); 
                } else {
                    System.err.println("Skipping invalid or empty line: " + line); 
                }
            }

            Timer replayTimer = new Timer(1000, null);
            replayTimer.addActionListener(e -> {
                if (!moves.isEmpty()) {
                    String move = moves.remove(0);
                    System.out.println("Processing move: " + move);  
                    if (move.startsWith("Move: ")) {
                        try {
                            String[] parts = move.split(" at ");
                            if (parts.length == 2) {
                                char letter = parts[0].charAt(6); 
                                System.out.println("Parsed letter: " + letter);  
//  later to work on the replay feature maybe issue here....
                                String coordinatesPart = parts[1].replace("(", "").replace(")", "");
                                String[] coordinates = coordinatesPart.split(",\\s*");
                                if (coordinates.length == 2) {
                                    int row = Integer.parseInt(coordinates[0].trim());
                                    int col = Integer.parseInt(coordinates[1].trim());

                                    System.out.println("Parsed coordinates: row=" + row + ", col=" + col);  

                                    if (row >= 0 && row < boardSize && col >= 0 && col < boardSize) {
                                        model.placeLetter(row, col, letter);
                                        view.updateBoard(model.isPlayerOneTurn(), row, col, letter);
                                        view.updateTurnLabel("Player " + (model.isPlayerOneTurn() ? "1" : "2") + "'s turn");
                                    } else {
                                        System.err.println("Invalid coordinates found: row=" + row + ", col=" + col);
                                    }
                                } else {
                                    System.err.println("Invalid coordinates format: " + parts[1]);
                                }
                            } else {
                                System.err.println("Unexpected move format: " + move);
                            }
                        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                            System.err.println("Error parsing move: " + move + ", Error: " + ex.getMessage());
                        }
                    }
                } else {
                    replayTimer.stop();
                    handleGameOver();
                    System.out.println("Replay complete.");
                }
            });

            replayTimer.setInitialDelay(0);
            replayTimer.start();

        } catch (IOException | NumberFormatException e) {
            view.showErrorMessage("Error reading the recorded game file: " + e.getMessage());
        }
    }

}
