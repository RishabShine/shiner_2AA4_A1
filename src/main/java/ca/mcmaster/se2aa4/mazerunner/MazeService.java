package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;

public class MazeService {

    private final MazeReader mazeReader =  new MazeReader();
    private final PathFinder pathFinder;
    //private final MazeSolver mazeSolver = new MazeSolver();

    public String getPath(String filepath) {
        
        List<List<Character>> maze = mazeReader.loadMaze(filepath);

        int[] start = pathFinder.findStart(maze);
        int[] finish = pathFinder.findFinish(maze);

        int[][] checked = new int[maze.size()][maze.get(0).size()];

        String path = pathFinder.findPath(maze, start, finish, "", checked, 'E');

        System.out.println("*** Path through maze (path indicated by 'p') ***");
        for (List<Character> row : maze) {
            for (char cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }

        return path;
    }

    public String isValidPath(String filepath, String path) {

        List<List<Character>> maze = mazeReader.loadMaze(filepath);

        if (pathFinder.validatePath(maze, path)) {
            return "Provided path is valid";
        } else {
            return "Provided path is NOT valid";
        }

    }

    public MazeService(PathFinder pathFinder) {
        this.pathFinder = pathFinder;
    }
}
