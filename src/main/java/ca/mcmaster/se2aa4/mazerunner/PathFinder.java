package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.Interfaces.SolverObserver;
import ca.mcmaster.se2aa4.mazerunner.Interfaces.SolverSubject;
import ca.mcmaster.se2aa4.mazerunner.enums.Heading;
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
    public void notifyObservers(Position pos) {
        for (SolverObserver observer : observers) {
            observer.update(pos);
        }
    }

    //public void 

    /*
     * returns an int array corresponding to the index of the start position
     * 
     * @param maze: 2D list representing the maze
     * 
     */
    // public int[] findStart(List<List<Maze>> maze) {
    //     for (int i = 0; i < maze.size(); i++) {
    //         if (maze.get(i).get(0) == Maze.SPACE) {
    //             return new int[]{i, 0};
    //         }
    //     }
    //     return null;
    // }

    /*
     * returns an int array corresponding to the index of the finish position
     * 
     * @param maze: 2D list representing the maze
     * 
     */
    // public int[] findFinish(List<List<Maze>> maze) {

    //     int mazeLength = maze.get(0).size() - 1;

    //     for (int i = 0; i < maze.size(); i++) {
    //         if (maze.get(i).get(mazeLength) == Maze.SPACE) {
    //             return new int[]{i, mazeLength};
    //         }
    //     }
    //     return null;
    // }

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
    // public abstract String findPath(Maze maze, Position currPos, Position finish, String path, int[][] checked, Heading previousHeading);
    // public abstract String findPath(Maze maze, Position currPos, String path, Heading previousHeading);
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
    
        //Position finish = maze.findFinish();
        if (maze.getFinish() == null) return false;
    
        Position currPos = start;
        Heading currHeading = Heading.E;
    
        for (char move : path.toCharArray()) {
            if (move == 'F') {
                currPos = currPos.move(MapNavigator.getOffset(currHeading));
    
                if (!maze.isValidMove(currPos)) {
                    return false;
                }
                //current = next; // Move forward
            } else if (move == 'L') {
                currHeading = MapNavigator.getLeft(currHeading);
            } else if (move == 'R') {
                currHeading = MapNavigator.getRight(currHeading);
            } else if (move != ' ') { // Invalid character in path
                return false;
            }
        }
        return maze.isCheckable(currPos);
    }
    

}
