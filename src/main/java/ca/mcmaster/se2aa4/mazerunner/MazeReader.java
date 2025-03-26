package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.enums.Maze;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MazeReader {

    private static final Logger logger = LogManager.getLogger();

    public List<List<Maze>> loadMaze(String filepath) {
        try {
            logger.info("**** Reading the maze from file ");
            System.out.println("**** Reading the maze from file ");
            BufferedReader reader = new BufferedReader(new FileReader(filepath));

            List<String> lines = new ArrayList<>();
            int cols = 0; // max row length


            //! was different before
            String line;
            while ((line = reader.readLine()) != null) { // FIXED HERE
                lines.add(line);
                cols = Math.max(cols, line.length());
            }
            reader.close();

            int rows = lines.size();
            List<List<Maze>> maze = new ArrayList<>();

            for (int row = 0; row < rows; row++) {
                String currentLine = lines.get(row);
                List<Maze> mazeRow = new ArrayList<>();

                for (int col = 0; col < cols; col++) {
                    if (col < currentLine.length()) {
                        if (currentLine.charAt(col) == '#') {
                            mazeRow.add(Maze.WALL);
                        } else {
                            mazeRow.add(Maze.SPACE);
                        }
                    } else {
                        mazeRow.add(Maze.SPACE); // padding with spaces
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
