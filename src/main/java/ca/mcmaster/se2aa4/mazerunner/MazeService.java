package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.Maze.Maze;
import ca.mcmaster.se2aa4.mazerunner.enums.Heading;

public class MazeService {

    private final PathFinder pathFinder;
    private Maze maze;

    private final char pathChar;

    public MazeService(PathFinder pathFinder, char wallChar, char openChar, char pathChar, String filepath) {
        this.maze = new Maze(wallChar, openChar, pathChar, filepath);
        this.pathFinder = pathFinder;
        // creating observer - subject relation
        this.pathFinder.addObserver(this.maze);
        this.pathChar = pathChar;
    }

    public String getPath() {

        if (this.maze == null) {
            throw new IllegalArgumentException("** No maze currently loaded, please specify a maze file");
        }

        String path = pathFinder.findPath(maze, maze.getStart(), Heading.E);

        if (path != null) {
            System.out.println("*** Path through maze (path indicated by " + pathChar + ") ***");
            maze.printMaze();
        }
        
        // System.out.println("*** Path through maze (path indicated by " + pathChar + ") ***");
        // maze.printMaze();

        return path;
    }

    public String isValidPath(String path) {
        if (this.maze == null) {
            throw new IllegalArgumentException("** No maze currently loaded, please specify a maze file");
        }
        if (pathFinder.validatePath(this.maze, path)) {
            return "Provided path is valid";
        } else {
            return "Provided path is NOT valid";
        }
    }
}
