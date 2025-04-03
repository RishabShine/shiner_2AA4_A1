package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.Interfaces.TileFactory;
import ca.mcmaster.se2aa4.mazerunner.Maze.MazeTileFactory;
import ca.mcmaster.se2aa4.mazerunner.Tiles.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ca.mcmaster.se2aa4.mazerunner.enums.TileType;

public class MazeReader {

    private static final Logger logger = LogManager.getLogger();
    private TileFactory tileFactory;

    // default chars for tiles
    char wallChar = '#';
    char openChar = ' ';
    char pathChar = 'P';

    // optional constructor if user wants to use different chars
    public MazeReader(char wallChar, char openChar, char pathChar) {
        this.wallChar = wallChar;
        this.openChar = openChar;
        this.pathChar = pathChar;
        this.tileFactory = new MazeTileFactory(openChar, wallChar);
    }

    public List<List<Tile>> loadMaze(String filepath) {
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

            for (int row = 0; row < rows; row++) {
                String currentLine = lines.get(row);
                List<Tile> mazeRow = new ArrayList<>();

                for (int col = 0; col < cols; col++) {
                    if (col < currentLine.length()) {
                        if (currentLine.charAt(col) == '#') {
                            mazeRow.add(tileFactory.getTile(TileType.WALL));
                        } else {
                            mazeRow.add(tileFactory.getTile(TileType.OPEN));
                        }
                    } else {
                        mazeRow.add(tileFactory.getTile(TileType.OPEN)); // padding with spaces
                    }
                }
                maze.add(mazeRow);
            }
            return maze;
        } catch (Exception e) {
            logger.error("Error reading maze file", e);
            System.err.println("/!\\ An error has occurred /!\\");
            return null;
        }
    }
}
