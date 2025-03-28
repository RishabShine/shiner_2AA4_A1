package ca.mcmaster.se2aa4.mazerunner.Interfaces;

import ca.mcmaster.se2aa4.mazerunner.Position;
import ca.mcmaster.se2aa4.mazerunner.enums.SolverUpdateType;

public interface SolverSubject {

    public void notifyObservers(Position pos, SolverUpdateType updateType);

    public void addObserver(SolverObserver observer);

    public void removeObserver(SolverObserver observer);
    
}
