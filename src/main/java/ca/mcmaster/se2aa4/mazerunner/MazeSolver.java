package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;

public class MazeSolver {

    private int[][] moves = {
        {1, 0}, 
        {0, 1}, 
        {0, -1}, 
        {-1, 0},
    };

    /*
     * returns a string that describes a path from start to findih
     * 
     * @param maze: 2D array representing the maze
     * @param currPos: current position / index at that iteration
     * @param finish: finishing position / index
     * @param path: the path taken
     * @param checked: contains all visited indexes
     * 
     */
    public String findPath(List<List<Character>> maze, int[] currPos, int[] finish, String path, int[][] checked) {
        int row = currPos[0];
        int col = currPos[1];

        // base cases
        if (row == finish[0] && col == finish[1]) {
            return path;  // found the finish, return the path taken
        }
        // checking if in an invalid position (wall or off the maze)
        if (row < 0 || row >= maze.size() || col < 0 || col >= maze.get(0).size() 
                || maze.get(row).get(col) == '#' || checked[row][col] == 1) {
            return null;
        }

        // mark current position as visited
        checked[row][col] = 1;

        // try all possible moves
        for (int i = 0; i < moves.length; i++) {
            int newRow = row + moves[i][0];
            int newCol = col + moves[i][1];

            // map move to path characters
            char moveChar = getMoveChar(i);

            // recursive call to explore next move + updating path and position
            String result = findPath(maze, new int[]{newRow, newCol}, finish, path + moveChar, checked);
            
            if (result != null) {
                // return path if valid solution is found
                return result;
            }
        }

        // unmark position if no valid path was found
        checked[row][col] = 0;
        return null; 
    }

    // maps moves to letter for the path
    private char getMoveChar(int moveIndex) {
        switch (moveIndex) {
            case 0: return 'D';  // down
            case 1: return 'R';  // right
            case 2: return 'L';  // left
            case 3: return 'U';  // up
            default: return '?';
        }
    }
}
