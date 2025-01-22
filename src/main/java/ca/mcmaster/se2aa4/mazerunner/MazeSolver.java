package ca.mcmaster.se2aa4.mazerunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MazeSolver {

    // possible moves from an index (move down once, right once ...)
    // private int[][] moves = {
    //     {1, 0}, 
    //     {0, 1}, 
    //     {0, -1}, 
    //     {-1, 0},
    // };

    // Map of moves with directional keys
    private static final Map<Character, int[]> moves = new HashMap<>() {{
        put('N', new int[]{-1, 0}); // Up
        put('E', new int[]{0, 1});  // Right
        put('S', new int[]{1, 0});  // Down
        put('W', new int[]{0, -1}); // Left
    }};

    // Define the order of directions for left/right calculation
    private static final List<Character> directions = List.of('N', 'E', 'S', 'W');

    /*
     * returns an int array corresponding to the index of the start position
     * 
     * @param maze: 2D list representing the maze
     * 
     */
    public int[] findStart(List<List<Character>> maze) {
        for (int i = 0; i < maze.size(); i++) {
            if (maze.get(i).get(0) == ' ') {
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
    public int[] findFinish(List<List<Character>> maze) {

        int mazeLength = maze.get(0).size() - 1;

        for (int i = 0; i < maze.size(); i++) {
            if (maze.get(i).get(mazeLength) == ' ') {
                return new int[]{i, mazeLength};
            }
        }
        return null;
    }

    /*
     * returns a string that describes a path from start to findih
     * 
     * @param maze: 2D list representing the maze
     * @param currPos: current position / index at that iteration
     * @param finish: finishing position / index
     * @param path: the path taken
     * @param checked: contains all visited indexes
     * 
     */
    public String findPath(List<List<Character>> maze, int[] currPos, int[] finish, String path, int[][] checked, char previousMove) {
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
        for (char moveDir : moves.keySet()) {
            int newRow = row + moves.get(moveDir)[0];
            int newCol = col + moves.get(moveDir)[1];

            // map move to path characters
            char moveChar = getMoveChar(previousMove, moveDir);

            // recursive call to explore next move + updating path and position
            String result = findPath(maze, new int[]{newRow, newCol}, finish, path + moveChar, checked, moveChar);
            
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
    public static char getMoveChar(char previousMove, char currentMove) {
        int prevIndex = directions.indexOf(previousMove);
        int currIndex = directions.indexOf(currentMove);

        if (currIndex == prevIndex) {
            return 'F'; // Forward
        } else if ((prevIndex + 1) % 4 == currIndex) {
            return 'R'; // Right turn
        } else if ((prevIndex + 3) % 4 == currIndex) {
            return 'L'; // Left turn
        }

        return 'F'; // Default forward if somehow not detected
    }
}
