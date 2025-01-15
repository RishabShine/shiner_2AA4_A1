package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MazeReader {

    private static final Logger logger = LogManager.getLogger();
    
    public char[][] readMaze(String[] args) {
        System.out.println("** Starting Maze Runner");

        try {
            System.out.println("**** Reading the maze from file " + args[0]);
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));

            // reading lines from file into array list
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();

            // initializing 2D array to model maze
            int rows = lines.size();
            int cols = lines.get(0).length();
            char[][] maze = new char[rows][cols];

            // populating 2D array
            for (int row = 0; row < rows; row++) {
                String currentLine = lines.get(row);
                for (int col = 0; col < currentLine.length(); col++) {
                    maze[row][col] = currentLine.charAt(col);
                }
            }

            return maze;

        } catch (Exception e) {
            System.err.println("/!\\ An error has occurred /!\\");
            logger.error("Error reading maze file", e);
            return null;
        }
    }

}
