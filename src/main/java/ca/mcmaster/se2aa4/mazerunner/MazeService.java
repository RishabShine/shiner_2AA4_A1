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

        for (List<Character> row : maze) {
            for (char cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }

        return path;
    }

    public MazeService(PathFinder pathFinder) {
        this.pathFinder = pathFinder;
    }
}
