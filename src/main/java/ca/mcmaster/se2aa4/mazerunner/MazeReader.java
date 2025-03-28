package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.Maze.Maze;
import ca.mcmaster.se2aa4.mazerunner.Tiles.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MazeReader {

    private static final Logger logger = LogManager.getLogger();

    // default chars for tiles
    char wallChar = '#';
    char openChar = ' ';
    char pathChar = 'P';

    // optional constructor if user wants to use different chars
    public MazeReader(char wallChar, char openChar, char pathChar) {
        this.wallChar = wallChar;
        this.openChar = openChar;
        this.pathChar = pathChar;
    }

    //! can possible use factory pattern here
    public Maze loadMaze(String filepath) {
        try {
            logger.info("**** Reading the maze from file ");
            System.out.println("**** Reading the maze from file ");
            BufferedReader reader = new BufferedReader(new FileReader(filepath));

            List<String> lines = new ArrayList<>();
            int cols = 0; // max row length

            String line;
            while ((line = reader.readLine()) != null) { // FIXED HERE
                lines.add(line);
                cols = Math.max(cols, line.length());
            }
            reader.close();

            int rows = lines.size();
            List<List<Tile>> maze = new ArrayList<>();
            // List<List<Maze>> maze = new ArrayList<>();

            for (int row = 0; row < rows; row++) {
                String currentLine = lines.get(row);
                List<Tile> mazeRow = new ArrayList<>();

                for (int col = 0; col < cols; col++) {
                    if (col < currentLine.length()) {
                        if (currentLine.charAt(col) == '#') {
                            mazeRow.add(new WallTile('#'));
                        } else {
                            mazeRow.add(new OpenTile(' '));
                        }
                    } else {
                        mazeRow.add(new OpenTile(' ')); // padding with spaces
                    }
                }
                maze.add(mazeRow);
            }
            return convertToMaze(maze);

        } catch (Exception e) {
            logger.error("Error reading maze file", e);
            System.err.println("/!\\ An error has occurred /!\\");
            return null;
        }
    }

    private Maze convertToMaze(List<List<Tile>> maze) {
        Position start = findStart(maze);
        Position finish = findFinish(maze);

        if (start == null || finish == null) {
            throw new IllegalArgumentException("Invalid maze");
        }
        return new Maze(maze, start, finish, wallChar, openChar, pathChar);
    }

    private Position findStart(List<List<Tile>> maze) {
        for (int i = 0; i < maze.size(); i++) {
            if (maze.get(i).get(0).isCheckable()) {
                return new Position(i, 0);
                //return new int[]{i, 0};
            }
        }
        return null;
    }

    private Position findFinish(List<List<Tile>> maze) {

        int mazeLength = maze.get(0).size() - 1;

        for (int i = 0; i < maze.size(); i++) {
            if (maze.get(i).get(mazeLength).isCheckable()) {
                return new Position(i, mazeLength);
                //return new int[]{i, mazeLength};
            }
        }
        return null;
    }
}
