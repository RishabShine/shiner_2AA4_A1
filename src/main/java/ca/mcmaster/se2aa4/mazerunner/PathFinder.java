package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.Interfaces.SolverObserver;
import ca.mcmaster.se2aa4.mazerunner.Interfaces.SolverSubject;
import ca.mcmaster.se2aa4.mazerunner.enums.Heading;
import ca.mcmaster.se2aa4.mazerunner.enums.SolverUpdateType;
import ca.mcmaster.se2aa4.mazerunner.Maze.Maze;

import java.util.ArrayList;
import java.util.List;

public abstract class PathFinder implements SolverSubject {

    /*
     * list of observers that will make some change upon the PathFinder visiting
     * a tile
     */
    private List<SolverObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(SolverObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(SolverObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Position pos, SolverUpdateType updateType) {
        for (SolverObserver observer : observers) {
            if (updateType == SolverUpdateType.CHECK) {
                observer.updateChecked(pos);
            } else if (updateType == SolverUpdateType.ADD_PATH) {
                observer.updatePath(pos);
            }
        }
    }

    public abstract String findPath(Maze maze, Position startPos, Heading startHeading);


    /*
     * 
     * given a path and maze, will return boolean indicating if the path solves the maze
     * 
     * @param maze: 2D list representing the maze
     * @param path: provided path to be validated
     * 
     */
    //public abstract boolean validatePath(Maze maze, String path);

    public boolean validatePath(Maze maze, String path) {
        Position start = maze.getStart();
        if (start == null) return false;
        if (maze.getFinish() == null) return false;
    
        Position currPos = start;
        Heading currHeading = Heading.E;

        path = path.replace(" ", ""); // removing all spaces
    
        for (char move : path.toCharArray()) {
            if (move == 'F') {
                currPos = currPos.move(MapNavigator.getOffset(currHeading));
                if (!maze.isValidMove(currPos)) {
                    return false;
                }
            } else if (move == 'L') {
                currHeading = MapNavigator.getLeft(currHeading);
            } else if (move == 'R') {
                currHeading = MapNavigator.getRight(currHeading);
            } else { // Invalid character in path
                return false;
            }
        }
        return maze.isFinish(currPos);
    }
    

}
