package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.Maze.Maze;
import ca.mcmaster.se2aa4.mazerunner.Tiles.Tile;
import ca.mcmaster.se2aa4.mazerunner.enums.Heading;

import java.util.List;

public class MazeService {

    private final MazeReader mazeReader;// = new MazeReader();
    private final PathFinder pathFinder;
    private Maze maze;
        
    private final char wallSymbol;
    private final char openSymbol;
    private final char pathSymbol;
    //private final MazeSolver mazeSolver = new MazeSolver();

    public MazeService(PathFinder pathFinder, char wallSymbol, char openSymbol, char pathSymbol) {
        this.mazeReader = new MazeReader(wallSymbol, openSymbol);
        this.pathFinder = pathFinder;
        this.wallSymbol = wallSymbol;
        this.openSymbol = openSymbol;
        this.pathSymbol = pathSymbol;
    }

    public void loadMaze(String filepath) {
        this.maze = mazeReader.loadMaze(filepath);
    }

    public String getPath() {

        if (this.maze == null) {
            //! make an error
            System.out.println("** No maze currently loaded, please specify a maze file");
        }
        
        //Maze maze = mazeReader.loadMaze(filepath);

        // int[] start = pathFinder.findStart(maze);
        // int[] finish = pathFinder.findFinish(maze);

        //int[][] checked = new int[maze.size()][maze.get(0).size()];

        String path = pathFinder.findPath(maze, maze.getStart(), Heading.E);

        //! check if path is null

        System.out.println("*** Path through maze (path indicated by " + pathSymbol + ") ***");
        maze.printMaze();

        return path;
    }

    public String isValidPath(String path) {

        //List<List<Maze>> maze = mazeReader.loadMaze(filepath);
        if (this.maze == null) {
            //! make an error
            System.out.println("** No maze currently loaded, please specify a maze file");
        }

        if (pathFinder.validatePath(this.maze, path)) {
            return "Provided path is valid";
        } else {
            return "Provided path is NOT valid";
        }

    }
}
