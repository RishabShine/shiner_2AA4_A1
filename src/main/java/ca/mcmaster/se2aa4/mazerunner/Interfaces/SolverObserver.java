package ca.mcmaster.se2aa4.mazerunner.Interfaces;

import ca.mcmaster.se2aa4.mazerunner.Position;

/*
 * two changes can be made in the solver:
 *  checking a new spot
 *  deeming a spot as part of the path
 * 
 * two seperate update methods used to handle each case
 */
public interface SolverObserver {


    /*
     * accepts a position that is known to be checked
     */
    public void updateChecked(Position pos);

    /*
     * accepts a position that is known to be part of the path
     */
    public void updatePath(Position pos);
    
}
