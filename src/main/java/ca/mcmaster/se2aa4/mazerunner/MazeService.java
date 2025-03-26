package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.enums.Maze;
import ca.mcmaster.se2aa4.mazerunner.enums.Heading;

import java.util.List;

public class MazeService {

    private final MazeReader mazeReader =  new MazeReader();
    private final PathFinder pathFinder;
        
    private final char wallSymbol;
    private final char openSymbol;
    private final char pathSymbol;
    //private final MazeSolver mazeSolver = new MazeSolver();

    public MazeService(PathFinder pathFinder, char wallSymbol, char openSymbol, char pathSymbol) {
        this.pathFinder = pathFinder;
        this.wallSymbol = wallSymbol;
        this.openSymbol = openSymbol;
        this.pathSymbol = pathSymbol;
    }

    public String getPath(String filepath) {
        
        List<List<Maze>> maze = mazeReader.loadMaze(filepath);

        int[] start = pathFinder.findStart(maze);
        int[] finish = pathFinder.findFinish(maze);

        int[][] checked = new int[maze.size()][maze.get(0).size()];

        String path = pathFinder.findPath(maze, start, finish, "", checked, Heading.E);

        System.out.println("*** Path through maze (path indicated by " + pathSymbol + ") ***");
        for (List<Maze> row : maze) {
            for (Maze cell : row) {
                // Print custom symbols based on the maze cell type
                if (cell == Maze.WALL) {
                    System.out.print(wallSymbol);
                } else if (cell == Maze.PATH) {
                    System.out.print(pathSymbol);
                } else {
                    System.out.print(openSymbol);
                }
            }
            System.out.println();
        }

        return path;
    }

    public String isValidPath(String filepath, String path) {

        List<List<Maze>> maze = mazeReader.loadMaze(filepath);

        if (pathFinder.validatePath(maze, path)) {
            return "Provided path is valid";
        } else {
            return "Provided path is NOT valid";
        }

    }
}
