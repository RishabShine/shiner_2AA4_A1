package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;

public interface PathFinder {

    /*
     * returns an int array corresponding to the index of the start position
     * 
     * @param maze: 2D list representing the maze
     * 
     */
    public int[] findStart(List<List<Character>> maze);

    /*
     * returns an int array corresponding to the index of the finish position
     * 
     * @param maze: 2D list representing the maze
     * 
     */
    public int[] findFinish(List<List<Character>> maze);

    /*
     * returns a string that describes a path from start to findih
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
    public String findPath(List<List<Character>> maze, int[] currPos, int[] finish, String path, int[][] checked, char previousMove);
    
}
