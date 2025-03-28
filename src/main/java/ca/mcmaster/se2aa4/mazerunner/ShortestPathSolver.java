package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.enums.Heading;
import ca.mcmaster.se2aa4.mazerunner.enums.Direction;
import ca.mcmaster.se2aa4.mazerunner.Maze.Maze;
import java.util.*;

public class ShortestPathSolver extends PathFinder {
    
    //private MapNavigator navigator = new MapNavigator();
    
    public String findPath(Maze maze, Position startPos, Heading startHeading) {
        // int rows = maze.size();
        // int cols = maze.get(0).size();
        Queue<Position> queue = new LinkedList<>();
        Queue<String> paths = new LinkedList<>();
        Queue<Heading> lastHeadings = new LinkedList<>();
        Map<Position, Position> parentMap = new HashMap<>(); // stores the path from each position
    
        queue.add(startPos);
        paths.add("");
        lastHeadings.add(startHeading);
    
        //checked[currPos[0]][currPos[1]] = 1;
        notifyObservers(startPos);

        // marking starting position as part of path as it always will be
        maze.markAsPath(startPos, 'P');
        parentMap.put(startPos, null); // mark the start position
    
        while (!queue.isEmpty()) {
            Position pos = queue.poll();
            String currPath = paths.poll();
            Heading currHeading = lastHeadings.poll();
    
            if (maze.isFinish(pos)) {
                System.out.println("AT FINISH");
                System.out.println(currPath);
                //System.out.println(parentMap);
                updateMazeWithPath(maze, parentMap, maze.getStart(), maze.getFinish());
                //System.out.println(currPath);
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
                //int[] moveOffset = MapNavigator.getOffset(newHeading);
                Position newPos = pos.move(MapNavigator.getOffset(newHeading));
            
                // check if the move is valid (within bounds and not in a wall)
                if (maze.isValidMove(newPos) && !parentMap.containsKey(newPos)) {
                    queue.add(newPos);
                    paths.add(currPath + getMoveString(turnDirection));
                    lastHeadings.add(newHeading);
                    //checked[newRow][newCol] = 1; // mark as visited
                    notifyObservers(newPos);
                    parentMap.put(newPos, pos); // store the parent

                    //maze.markAsPath(newPos, 'P');
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
    private void updateMazeWithPath(Maze maze, Map<Position, Position> parentMap, Position start, Position finish) {
        //String currentKey = positionKey(finish);
        Position curr = finish;
        Set<Position> visited = new HashSet<>(); 

        System.out.println("IN UPDATE MAZE WITH PATH");

        while (!curr.equals(start)) {
        //while (curr != null) {

            if (visited.contains(curr)) {
                System.out.println("Detected cycle in parent map! Position: " + curr);
                return;
            }
            visited.add(curr);

            maze.markAsPath(curr, 'P');
            System.out.println("IN WHILE LOOP");
            // String[] parts = currentKey.split(",");
            // int row = Integer.parseInt(parts[0]);
            // int col = Integer.parseInt(parts[1]);
            // maze.get(row).set(col, Maze.PATH);

            // currentKey = parentMap.get(currentKey); // Move to the previous position

            curr = parentMap.get(curr); // Move to the previous position
            System.out.println("GOT NEXT POS");
            System.out.println(curr);
            if (curr == null) {
                throw new IllegalStateException("Path reconstruction failed. Parent map is incomplete.");
            }
            System.out.println("PASSED NULL CHECK");
        }
        System.out.println("COMPLETE");
    }
    
    //! move to parent class if logic remains same
    // @Override
    // public boolean validatePath(Maze maze, String path) {
    //     int[] start = findStart(maze);
    //     if (start == null) return false;

    //     int[] finish = findFinish(maze);
    //     if (finish == null) return false;

    //     int row = start[0];
    //     int col = start[1];
    //     Heading currHeading = Heading.E;
    
    //     for (char move : path.toCharArray()) {
    //         if (move == 'F') {
    //             int[] moveOffset = MapNavigator.getOffset(currHeading);
    //             row += moveOffset[0];
    //             col += moveOffset[1];
    
    //             if (row < 0 || row >= maze.size() || col < 0 || col >= maze.get(0).size() || maze.get(row).get(col) == Maze.WALL) {
    //                 return false;
    //             }
    //         } else if (move == 'L') {
    //             currHeading = MapNavigator.getLeft(currHeading);
    //         } else if (move == 'R') {
    //             currHeading = MapNavigator.getRight(currHeading);
    //         } else if (move != ' ') {
    //             return false;
    //         }
    //     }
    //     return row == finish[0] && col == finish[1];
    // }
}