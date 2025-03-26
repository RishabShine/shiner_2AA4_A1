package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.enums.Heading;
import ca.mcmaster.se2aa4.mazerunner.enums.Maze;
import java.util.List;

public abstract class PathFinder {

    /*
     * returns an int array corresponding to the index of the start position
     * 
     * @param maze: 2D list representing the maze
     * 
     */
    public int[] findStart(List<List<Maze>> maze) {
        for (int i = 0; i < maze.size(); i++) {
            if (maze.get(i).get(0) == Maze.SPACE) {
                return new int[]{i, 0};
            }
        }
        return null;
    }

    /*
     * returns an int array corresponding to the index of the finish position
     * 
     * @param maze: 2D list representing the maze
     * 
     */
    public int[] findFinish(List<List<Maze>> maze) {

        int mazeLength = maze.get(0).size() - 1;

        for (int i = 0; i < maze.size(); i++) {
            if (maze.get(i).get(mazeLength) == Maze.SPACE) {
                return new int[]{i, mazeLength};
            }
        }
        return null;
    }

    /*
     * returns a string that describes a path from start to finish
     * 
     * @param maze: 2D list representing the maze
     * @param currPos: current position / index at that iteration
     * @param finish: finishing position / index
     * @param path: the path taken
     * @param checked: contains all visited indexes
     * @param previousMove: direction of previous move to find determine the 
     *                      direction of next move (on first iteration defaults
     *                      to 'E')
     * 
     */
    public abstract String findPath(List<List<Maze>> maze, int[] currPos, int[] finish, String path, int[][] checked, Heading previousHeading);

    /*
     * 
     * given a path and maze, will return boolean indicating if the path solves the maze
     * 
     * @param maze: 2D list representing the maze
     * @param path: provided path to be validated
     * 
     */
    public abstract boolean validatePath(List<List<Maze>> maze, String path);

}
