package SOS449;

import javax.swing.*;
import java.util.List;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class SOSGameView extends JFrame {
    private SOSGameController controller;
    private SOSGame game;
    private JButton[][] buttons;
    private JPanel initialPanel;
    private JPanel gamePanel;
    private JRadioButton simpleGameButton;
    private JRadioButton generalGameButton;
    private JTextField boardSizeField;
    private JButton startPersonVsPersonButton;
    private JButton startPersonVsComputerButton;
    private JButton startSimpleComputerVsComputerButton;
    private JButton startGeneralComputerVsComputerButton; 
    private JButton recordGameButton; 
    private JButton replayGameButton;

    private JLabel playerTurnLabel;
    private JLabel player1ScoreLabel;
    private JLabel player2ScoreLabel;

    private JRadioButton sButton;
    private JRadioButton oButton;

    public SOSGameView(SOSGameController controller) {
        this.controller = controller;
        controller.setView(this); 
        initializeUI();
    }

    private void initializeUI() {
        setTitle("SOS Game");
        setSize(800, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        setLayout(new BorderLayout());

        initialPanel = new JPanel();
        initialPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        simpleGameButton = new JRadioButton("Simple Game");
        generalGameButton = new JRadioButton("General Game");
        ButtonGroup gameModeGroup = new ButtonGroup();
        gameModeGroup.add(simpleGameButton);
        gameModeGroup.add(generalGameButton);
        simpleGameButton.setSelected(true); 

        JLabel boardSizeLabel = new JLabel("Board Size (must be greater than 2):");
        boardSizeField = new JTextField(5);

        startPersonVsPersonButton = new JButton("Start Person VS Person Game");
        startPersonVsPersonButton.addActionListener(e -> {
            controller.startGame(false);
        });

        startPersonVsComputerButton = new JButton("Start Person VS Computer Game");
        startPersonVsComputerButton.addActionListener(e -> {
            controller.startGame(true); 
        });

        startSimpleComputerVsComputerButton = new JButton("Start Simple Computer VS Computer Game");
        startSimpleComputerVsComputerButton.addActionListener(e -> {
            controller.startComputerVsComputerGame(true); 
        });

        startGeneralComputerVsComputerButton = new JButton("Start General Computer VS Computer Game");
        startGeneralComputerVsComputerButton.addActionListener(e -> {
            controller.startComputerVsComputerGame(false); 
        });

        recordGameButton = new JButton("Record Game");
        recordGameButton.addActionListener(e -> {
            controller.startRecordingGame();
        });

        replayGameButton = new JButton("Replay Game");
        replayGameButton.addActionListener(e -> {
            List<String> recordedGames = GameRecording.getRecordedGames();
            String selectedGame = (String) JOptionPane.showInputDialog(this, "Select a game to replay:", "Replay Game",
                    JOptionPane.PLAIN_MESSAGE, null, recordedGames.toArray(), recordedGames.size() > 0 ? recordedGames.get(0) : null);
            if (selectedGame != null) {
                controller.replayGame(selectedGame);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        initialPanel.add(simpleGameButton, gbc);

        gbc.gridx = 1;
        initialPanel.add(generalGameButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        initialPanel.add(boardSizeLabel, gbc);

        gbc.gridx = 1;
        initialPanel.add(boardSizeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        initialPanel.add(startPersonVsPersonButton, gbc);

        gbc.gridy = 3;
        initialPanel.add(startPersonVsComputerButton, gbc);

        gbc.gridy = 4;
        initialPanel.add(startSimpleComputerVsComputerButton, gbc);

        gbc.gridy = 5;
        initialPanel.add(startGeneralComputerVsComputerButton, gbc);

        gbc.gridy = 6;
        initialPanel.add(recordGameButton, gbc);

        gbc.gridy = 7;
        initialPanel.add(replayGameButton, gbc);

        add(initialPanel, BorderLayout.CENTER);
        setVisible(true);
    }

 

    public void showGameScreen(int boardSize, SOSGame game) {
        this.game = game; 

        remove(initialPanel);

        gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        sButton = new JRadioButton("S");
        oButton = new JRadioButton("O");
        ButtonGroup letterGroup = new ButtonGroup();
        letterGroup.add(sButton);
        letterGroup.add(oButton);
        sButton.setSelected(true); 

        playerTurnLabel = new JLabel("Player 1's turn");
        playerTurnLabel.setFont(new Font("Arial", Font.BOLD, 16));

        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(sButton, gbc);

        gbc.gridx = 1;
        infoPanel.add(playerTurnLabel, gbc);

        gbc.gridx = 2;
        infoPanel.add(oButton, gbc);

        if (game instanceof GeneralSOSGame) {
            player1ScoreLabel = new JLabel("Player 1 Score: 0");
            player2ScoreLabel = new JLabel("Player 2 Score: 0");
            player1ScoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
            player2ScoreLabel.setFont(new Font("Arial", Font.BOLD, 16));

            gbc.gridx = 0;
            gbc.gridy = 1;
            infoPanel.add(player1ScoreLabel, gbc);

            gbc.gridx = 2;
            infoPanel.add(player2ScoreLabel, gbc);
        }

        gamePanel.add(infoPanel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(boardSize, boardSize));
        boardPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); 

        buttons = new JButton[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(60, 60)); 
                button.setFont(new Font("Arial", Font.BOLD, 24)); 
                buttons[i][j] = button;
                int row = i;
                int col = j;
                button.addActionListener(e -> {
                    char letter = getSelectedLetter();
                    controller.handleCellClick(row, col, letter);
                });
                boardPanel.add(button);
            }
        }

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(boardPanel, BorderLayout.CENTER);

        gamePanel.add(centerPanel, BorderLayout.CENTER);

        add(gamePanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    public void resetToInitialSetup() {
        if (gamePanel != null) {
            remove(gamePanel);
        }

        add(initialPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
        
        boardSizeField.setText(""); 
        simpleGameButton.setSelected(true); 
        generalGameButton.setSelected(false);
    }

    public boolean isSimpleGameSelected() {
        return simpleGameButton.isSelected();
    }

    public String getBoardSizeInput() {
        return boardSizeField.getText();
    }

    public void updateBoard(boolean isPlayerOne, int row, int col, char letter) {
        buttons[row][col].setText(String.valueOf(letter));

        for (ActionListener al : buttons[row][col].getActionListeners()) {
            buttons[row][col].removeActionListener(al);
        }

        if (isPlayerOne) {
            buttons[row][col].setForeground(Color.RED); 
            System.out.println("Set color to RED at (" + row + ", " + col + ")");
        } else {
            buttons[row][col].setForeground(Color.BLUE); 
            System.out.println("Set color to BLUE at (" + row + ", " + col + ")");
        }

        buttons[row][col].repaint();
    }

    public void updateTurnLabel(String message) {
        playerTurnLabel.setText(message);
    }

    public void updateScore(int player1Score, int player2Score) {
        if (player1ScoreLabel != null && player2ScoreLabel != null) {
            player1ScoreLabel.setText("Player 1 Score: " + player1Score);
            player2ScoreLabel.setText("Player 2 Score: " + player2Score);
            System.out.println("Updated scores on screen: Player 1 - " + player1Score + ", Player 2 - " + player2Score);
        }
    }


    public void showWinner(String message) {
        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        for (JButton[] row : buttons) {
            for (JButton button : row) {
                button.setEnabled(false);
            }
        }
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Invalid Move", JOptionPane.ERROR_MESSAGE);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public char getSelectedLetter() {
        if (sButton.isSelected()) {
            return 'S';
        } else {
            return 'O';
        }
    }
}
