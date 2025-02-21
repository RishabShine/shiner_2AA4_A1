package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MazeReader {

    private static final Logger logger = LogManager.getLogger();
    
    public List<List<Character>> loadMaze(String filepath) {
        try {
            logger.info("**** Reading the maze from file ");
            System.out.println("**** Reading the maze from file ");
            BufferedReader reader = new BufferedReader(new FileReader(filepath));

            // reading lines from file into array list
            List<String> lines = new ArrayList<>();
            String line;
            // stores max row length / num columns as some rows are completed by blanks rather than spaces
            int cols = 0;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
                // finding max row length
                cols = Math.max(cols, line.length());
            }
            reader.close();

            // initializing 2D list to model maze
            int rows = lines.size();
            List<List<Character>> maze = new ArrayList<>();

            // populating 2D list from file
            for (int row = 0; row < rows; row++) {
                String currentLine = lines.get(row);
                List<Character> mazeRow = new ArrayList<>();

                for (int col = 0; col < cols; col++) {
                    if (col < currentLine.length()) {
                        mazeRow.add(currentLine.charAt(col));
                    } else {
                        // padding with spaces
                        mazeRow.add(' ');
                    }
                }
                maze.add(mazeRow);
            }
            return maze;

        } catch (Exception e) {
            logger.info("/!\\ An error has occurred /!\\");
            System.out.println("/!\\ An error has occurred /!\\");
            logger.error("Error reading maze file", e);
            System.out.println("Error reading maze file" +  e);
            return null;
        }
    }

}
