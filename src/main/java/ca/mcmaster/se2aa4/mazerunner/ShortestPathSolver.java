package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.enums.Heading;
import ca.mcmaster.se2aa4.mazerunner.enums.Direction;
import ca.mcmaster.se2aa4.mazerunner.enums.Maze;
import java.util.*;

public class ShortestPathSolver extends PathFinder {
    
    //private MapNavigator navigator = new MapNavigator();
    
    public String findPath(List<List<Maze>> maze, int[] currPos, int[] finish, String path, int[][] checked, Heading previousHeading) {
        int rows = maze.size();
        int cols = maze.get(0).size();
        Queue<int[]> queue = new LinkedList<>();
        Queue<String> paths = new LinkedList<>();
        Queue<Heading> lastHeadings = new LinkedList<>();
        Map<String, String> parentMap = new HashMap<>(); // stores the path from each position
    
        queue.add(currPos);
        paths.add(path);
        lastHeadings.add(previousHeading);
    
        checked[currPos[0]][currPos[1]] = 1;
        // marking starting position as part of path as it always will be
        maze.get(currPos[0]).set(currPos[1], Maze.PATH);
        parentMap.put(positionKey(currPos), ""); // mark the start position
    
        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            String currPath = paths.poll();
            Heading currHeading = lastHeadings.poll();
    
            int row = pos[0], col = pos[1];
    
            if (row == finish[0] && col == finish[1]) {
                updateMazeWithPath(maze, parentMap, currPos, finish);
                return currPath;
            }
    
            for (Direction turnDirection : Direction.values()) {
                Heading newHeading = currHeading; // default is current heading for forward
                
                if (turnDirection == Direction.R) {
                    newHeading = MapNavigator.getRight(currHeading);  // turn right
                } else if (turnDirection == Direction.L) {
                    newHeading = MapNavigator.getLeft(currHeading);   // turn left
                } // heading does not change (going forward)
            
                // get offset (position change) based on new heading
                int[] moveOffset = MapNavigator.getOffset(newHeading);
                int newRow = row + moveOffset[0];
                int newCol = col + moveOffset[1];
            
                // check if the move is valid (within bounds and not in a wall)
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols &&
                    maze.get(newRow).get(newCol) != Maze.WALL && checked[newRow][newCol] == 0) {
            
                    queue.add(new int[]{newRow, newCol});
                    paths.add(currPath + getMoveString(turnDirection));
                    lastHeadings.add(newHeading);
                    checked[newRow][newCol] = 1; // mark as visited
                    parentMap.put(positionKey(new int[]{newRow, newCol}), positionKey(pos)); // store the parent
                }
            }
        }
        return null; 
    }
    
    private String getMoveString(Direction move) {
        switch (move) {
            case F: return "F";
            case R: return "RF";
            case L: return "LF";
            default: return "F";
        }
    }

    /**
     * converts a position into a string key "row, column"
     */
    private String positionKey(int[] pos) {
        return pos[0] + "," + pos[1];
    }

    /**
     * modifies maze to set all slots part of shortest path as Maze.PATH
     * for easier path visualization and testing
     */
    private void updateMazeWithPath(List<List<Maze>> maze, Map<String, String> parentMap, int[] start, int[] finish) {
        String currentKey = positionKey(finish);

        while (!currentKey.equals(positionKey(start))) {
            String[] parts = currentKey.split(",");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            maze.get(row).set(col, Maze.PATH);

            currentKey = parentMap.get(currentKey); // Move to the previous position
        }
    }
    
    //! move to parent class if logic remains same
    @Override
    public boolean validatePath(List<List<Maze>> maze, String path) {
        int[] start = findStart(maze);
        if (start == null) return false;

        int[] finish = findFinish(maze);
        if (finish == null) return false;

        int row = start[0];
        int col = start[1];
        Heading currHeading = Heading.E;
    
        for (char move : path.toCharArray()) {
            if (move == 'F') {
                int[] moveOffset = MapNavigator.getOffset(currHeading);
                row += moveOffset[0];
                col += moveOffset[1];
    
                if (row < 0 || row >= maze.size() || col < 0 || col >= maze.get(0).size() || maze.get(row).get(col) == Maze.WALL) {
                    return false;
                }
            } else if (move == 'L') {
                currHeading = MapNavigator.getLeft(currHeading);
            } else if (move == 'R') {
                currHeading = MapNavigator.getRight(currHeading);
            } else if (move != ' ') {
                return false;
            }
        }
        return row == finish[0] && col == finish[1];
    }
}
