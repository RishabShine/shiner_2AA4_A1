package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.enums.Heading;
import ca.mcmaster.se2aa4.mazerunner.enums.Direction;
import ca.mcmaster.se2aa4.mazerunner.enums.SolverUpdateType;
import ca.mcmaster.se2aa4.mazerunner.Maze.Maze;
import java.util.*;

public class ShortestPathSolver extends PathFinder {
        
    public String findPath(Maze maze, Position startPos, Heading startHeading) {
 
        Queue<Position> queue = new LinkedList<>();
        Queue<String> paths = new LinkedList<>();
        Queue<Heading> lastHeadings = new LinkedList<>();
        Map<Position, Position> parentMap = new HashMap<>(); // stores the path from each position
    
        queue.add(startPos);
        paths.add("");
        lastHeadings.add(startHeading);
    
        notifyObservers(startPos, SolverUpdateType.CHECK);

        // marking starting position as part of path as it always will be
        notifyObservers(startPos, SolverUpdateType.ADD_PATH);
        parentMap.put(startPos, null); // mark the start position
        while (!queue.isEmpty()) {
            Position pos = queue.poll();
            String currPath = paths.poll();
            Heading currHeading = lastHeadings.poll();
    
            if (maze.isFinish(pos)) {
                //System.out.println("AT FINISH");
                //System.out.println(currPath);
                updateMazeWithPath(maze, parentMap, maze.getStart(), maze.getFinish());
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
                Position newPos = pos.move(MapNavigator.getOffset(newHeading));
            
                // check if the move is valid (within bounds and not in a wall)
                if (maze.isValidMove(newPos)) {
                    queue.add(newPos);
                    paths.add(currPath + getMoveString(turnDirection));
                    lastHeadings.add(newHeading);
                    notifyObservers(newPos, SolverUpdateType.CHECK);
                    parentMap.put(newPos, pos); // store the parent
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
     * modifies maze to set all slots part of shortest path as Maze.PATH
     * for easier path visualization and testing
     */
    private void updateMazeWithPath(Maze maze, Map<Position, Position> parentMap, Position start, Position finish) {
        Position curr = finish;

        while (!curr.equals(start)) {
   
            notifyObservers(curr, SolverUpdateType.ADD_PATH);
   
            curr = parentMap.get(curr); // Move to the previous position
 
            if (curr == null) {
                throw new IllegalStateException("Path reconstruction failed. Parent map is incomplete.");
            }
        }
    }
}