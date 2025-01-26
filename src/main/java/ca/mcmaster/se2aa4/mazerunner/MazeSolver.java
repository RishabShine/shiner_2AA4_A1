package ca.mcmaster.se2aa4.mazerunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MazeSolver {

    private char[] movePriority = {'R', 'F', 'L'};



    // map of moves with directional keys
    Map<Character, int[]> moves = Map.of(
        'N', new int[]{-1, 0},
        'E', new int[]{0, 1},
        'S', new int[]{1, 0},
        'W', new int[]{0, -1}
    );

    // corresponding directions for a right for current direction
    Map<Character, Character> rightTurn = Map.of(
        'N', 'E',
        'E', 'S',
        'S', 'W',
        'W', 'N'
    );

    // corresponding directions for a right turn for current direction
    Map<Character, Character> leftTurn = Map.of(
        'N', 'W',
        'E', 'N',
        'S', 'E',
        'W', 'S'
    );

    // // order of directions for left/right calculation
    // private static final List<Character> directions = List.of('N', 'E', 'S', 'W');

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
     * @param previousMove: direction of previous move to find determine the 
     *                      direction of next move (on first iteration defaults
     *                      to 'E')
     * 
     */
    // public String findPath(List<List<Character>> maze, int[] currPos, int[] finish, String path, int[][] checked, char previousMove) {
    //     int row = currPos[0];
    //     int col = currPos[1];

    //     // base cases
    //     if (row == finish[0] && col == finish[1]) {
    //         return path + "F";  // found the finish, return the path taken
    //     }
    //     // checking if in an invalid position (wall or off the maze)
    //     if (row < 0 || row >= maze.size() || col < 0 || col >= maze.get(0).size() 
    //             || maze.get(row).get(col) == '#' || checked[row][col] == 1) {
    //         return null;
    //     }

    //     // mark current position as visited
    //     checked[row][col] = 1;

    //     // try all possible moves
    //     for (char moveDir : moves.keySet()) {
    //         int newRow = row + moves.get(moveDir)[0];
    //         int newCol = col + moves.get(moveDir)[1];

    //         // map move to path characters
    //         String moveChar = getMoveChar(previousMove, moveDir);

    //         // recursive call to explore next move + updating path and position
    //         String result = findPath(maze, new int[]{newRow, newCol}, finish, path + moveChar, checked, moveDir);
            
    //         if (result != null) {
    //             // return path if valid solution is found
    //             return result;
    //         }
    //     }

    //     // unmark position if no valid path was found
    //     checked[row][col] = 0;
    //     return null; 
    // }

    public String findPath(List<List<Character>> maze, int[] currPos, int[] finish, String path, int[][] checked, char previousMove) {
        int row = currPos[0];
        int col = currPos[1];

        // base cases
        if (row == finish[0] && col == finish[1]) {
            return path + "F";  // found the finish, return the path taken
        }
        // checking if in an invalid position (wall or off the maze)
        if (row < 0 || row >= maze.size() || col < 0 || col >= maze.get(0).size() 
                || maze.get(row).get(col) == '#' || checked[row][col] == 1) {
            return null;
        }

        // mark current position as visited
        checked[row][col] = 1;

        for (char move : movePriority) {

            char newDirection = previousMove;
            String moveString = getMoveString(move);
    
            if (move == 'R') {
                newDirection = rightTurn.get(previousMove);
            } else if (move == 'L') {
                newDirection = leftTurn.get(previousMove);
            }
    
            int[] moveOffset = moves.get(newDirection);
            int newRow = row + moveOffset[0];
            int newCol = col + moveOffset[1];
    
            // recursive call to explore next move and update path
            String result = findPath(maze, new int[]{newRow, newCol}, finish, path + moveString, checked, newDirection);
    
            if (result != null) {
                return result;  // return the path if a solution is found
            }
        }

        // unmark position if no valid path was found
        checked[row][col] = 0;
        return null; 
    }

    // maps moves to letter for the path
    public static String getMoveString(char move) {
        if (move == 'F') {
            return "F"; // forward
        } else if (move == 'R') {
            return "RF"; // right turn
        } else if (move == 'L') {
            return "LF"; // left turn
        }
        return "F";
    }
}
