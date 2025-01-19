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
    
    public char[][] readMaze(String filepath) {
        System.out.println("** Starting Maze Runner");

        try {
            System.out.println("**** Reading the maze from file " + filepath);
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

            // initializing 2D array to model maze
            int rows = lines.size();
            char[][] maze = new char[rows][cols];

            // populating 2D array from file
            for (int row = 0; row < rows; row++) {
                String currentLine = lines.get(row);
                for (int col = 0; col < cols; col++) {
                    if (col < currentLine.length()) {
                        //System.out.println(currentLine.charAt(col));
                        maze[row][col] = currentLine.charAt(col);
                    } else {
                        // padding with spaces
                        maze[row][col] = ' ';
                    }
                }
            }
            //? testing to print maze
            System.out.println("**** Maze Layout ****");
            for (int row = 0; row < maze.length; row++) {
                for (int col = 0; col < maze[row].length; col++) {
                    System.out.print(maze[row][col]);
                }
                System.out.println(); // Move to the next row
            }
            return maze;

        } catch (Exception e) {
            System.err.println("/!\\ An error has occurred /!\\");
            logger.error("Error reading maze file", e);
            return null;
        }
    }

}
