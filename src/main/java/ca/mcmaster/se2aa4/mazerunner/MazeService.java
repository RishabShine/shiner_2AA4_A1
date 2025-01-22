package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;

public class MazeService {

    private final MazeReader mazeReader =  new MazeReader();
    private final MazeSolver mazeSolver = new MazeSolver();

    public String getPath(String filepath) {
        
        List<List<Character>> maze = mazeReader.loadMaze(filepath);

        int[] start = mazeSolver.findStart(maze);
        int[] finish = mazeSolver.findFinish(maze);

        int[][] checked = new int[maze.size()][maze.get(0).size()];

        String path = mazeSolver.findPath(maze, start, finish, filepath, checked, 'E');

        return path;
    }
}
