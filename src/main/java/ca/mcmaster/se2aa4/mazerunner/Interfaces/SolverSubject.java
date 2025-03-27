package ca.mcmaster.se2aa4.mazerunner.Interfaces;

public interface SolverSubject {

    public void notifyObservers(int[] pos);

    public void addObserver(SolverObserver observer);

    public void removeObserver(SolverObserver observer);
    
}
