package ca.mcmaster.se2aa4.mazerunner.Interfaces;

import ca.mcmaster.se2aa4.mazerunner.Position;
import ca.mcmaster.se2aa4.mazerunner.enums.SolverUpdateType;

public interface SolverObserver {

    public void update(Position pos, SolverUpdateType updateType);
    
}
