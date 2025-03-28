package ca.mcmaster.se2aa4.mazerunner.Interfaces;

import ca.mcmaster.se2aa4.mazerunner.Position;

public interface SolverSubject {

    public void notifyObservers(Position pos);

    public void addObserver(SolverObserver observer);

    public void removeObserver(SolverObserver observer);
    
}
