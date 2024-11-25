package SOS449;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameRecording {
    private List<String> moves;
    private String fileName;
    private static int gameCounter = 1;
    private static final String DIRECTORY_PATH = ".";

    public GameRecording() {
        this.moves = new ArrayList<>();
        this.fileName = DIRECTORY_PATH + File.separator + "game" + gameCounter + ".txt";
        gameCounter++;
    }

    public void recordMove(int row, int col, char letter) {
        moves.add("Move: " + letter + " at (" + row + ", " + col + ")");
    }

    public void saveRecording() {
        File file = new File(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String move : moves) {
                writer.write(move);
                writer.newLine();
            }
            System.out.println("Game recorded in " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error saving game recording: " + e.getMessage());
        }
    }

    public static List<String> getRecordedGames() {
        List<String> recordedGames = new ArrayList<>();
        File currentDirectory = new File(DIRECTORY_PATH);
        File[] files = currentDirectory.listFiles((dir, name) -> name.startsWith("game") && name.endsWith(".txt"));
        if (files != null) {
            for (File file : files) {
                recordedGames.add(file.getName());
            }
        }
        return recordedGames;
    }

    public void resetGameRecording() {
        moves.clear();
        System.out.println("Game recording reset.");
    }
}
