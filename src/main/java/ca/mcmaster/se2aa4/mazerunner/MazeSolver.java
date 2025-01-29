package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;
import java.util.Map;

public class MazeSolver extends PathFinder {

    private char[] movePriority = {'R', 'F', 'L'};



    // map of moves with directional keys
    private Map<Character, int[]> moves = Map.of(
        'N', new int[]{-1, 0},
        'E', new int[]{0, 1},
        'S', new int[]{1, 0},
        'W', new int[]{0, -1}
    );

    // corresponding directions for a right for current direction
    private Map<Character, Character> rightTurn = Map.of(
        'N', 'E',
        'E', 'S',
        'S', 'W',
        'W', 'N'
    );

    // corresponding directions for a right turn for current direction
    private Map<Character, Character> leftTurn = Map.of(
        'N', 'W',
        'E', 'N',
        'S', 'E',
        'W', 'S'
    );

    @Override
    public String findPath(List<List<Character>> maze, int[] currPos, int[] finish, String path, int[][] checked, char previousMove) {
        int row = currPos[0];
        int col = currPos[1];

        // base cases
        if (row == finish[0] && col == finish[1]) {
            maze.get(row).set(col, 'p');
            return path;  // found the finish, return the path taken
        }
        // checking if in an invalid position (wall or off the maze)
        if (row < 0 || row >= maze.size() || col < 0 || col >= maze.get(0).size() 
                || maze.get(row).get(col) == '#' || checked[row][col] == 1) {
            return null;
        }

        // mark current position as visited
        checked[row][col] = 1;
        char originalChar = maze.get(row).get(col);  // store original value
        maze.get(row).set(col, 'p');

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
        maze.get(row).set(col, originalChar);  
        return null; 
    }

    @Override
    public boolean validatePath(List<List<Character>> maze, String path) {

        // finding start and end positions
        int[] start = findStart(maze);
        if (start == null) return false;

        int[] finish = findFinish(maze);
        if (finish == null) return false;

        int row = start[0];
        int col = start[1];
    
        // initial direction is East (E)
        char currDir = 'E';
    
        // iterate through the given path
        for (char move : path.toCharArray()) {
            // updating position if moving forward
            if (move == 'F') {
                int[] moveOffset = moves.get(currDir);
                row += moveOffset[0];
                col += moveOffset[1];
    
                // check for out-of-bounds or hitting a wall
                if (row < 0 || row >= maze.size() || col < 0 || col >= maze.get(0).size() || maze.get(row).get(col) == '#') {
                    return false;  // invalid path
                }

            // adjusting orientation
            } else if (move == 'L') {
                currDir = leftTurn.get(currDir);
            } else if (move == 'R') {
                currDir = rightTurn.get(currDir);
            } else if (move != ' ') {
                return false;  // invalid character in path
            }
        }
    
        // if the final position matches the finish position, path is valid
        return row == finish[0] && col == finish[1];
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
