package ca.mcmaster.se2aa4.mazerunner.Maze;

import ca.mcmaster.se2aa4.mazerunner.Tiles.PathTile;
import ca.mcmaster.se2aa4.mazerunner.Tiles.Tile;
import ca.mcmaster.se2aa4.mazerunner.PathFinder;
import ca.mcmaster.se2aa4.mazerunner.Interfaces.SolverObserver;
import ca.mcmaster.se2aa4.mazerunner.Position;
import java.util.List;

public class Maze implements SolverObserver{

    //private PathFinder pathFinder;

    private List<List<Tile>> maze;
    //private Tile[][] grid;
    private int rows, cols;
    private Position start;
    private Position finish;

    public Maze(List<List<Tile>> maze, Position start, Position finish) {
        this.maze = maze;
        this.rows = maze.size();
        this.cols = maze.get(0).size();
        this.start = start;
        this.finish = finish;
    }

    @Override
    public void update(Position pos) {
        if (isCheckable(pos)) {
            markChecked(pos);
            System.out.println(pos);
            //grid[row][col].setChecked(true);
        }
    }

    public boolean isFinish(Position pos) {
        if (pos.equals(finish)) {
            System.out.println("Maze solved!");
            return true;
        }
        return false;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Position getStart() {
        return start;
    }

    public Position getFinish() {
        return finish;
    }

    public Tile getTile(Position pos) {
        return maze.get(pos.getRow()).get(pos.getCol());//grid[row][col];
    }

    public void markAsPath(Position pos, Character pathSymbol) {
        maze.get(pos.getRow()).set(pos.getCol(), new PathTile(pathSymbol));// = new PathTile(pathSymbol);
    }

    public void markChecked(Position pos) {
        maze.get(pos.getRow()).get(pos.getCol()).markChecked();
    }

    public boolean isCheckable(Position pos) {
        return maze.get(pos.getRow()).get(pos.getCol()).isCheckable();
    }

    public boolean isValidMove(Position pos) {
        // System.out.println(pos.getRow() >= 0 && pos.getRow() < rows && 
        // pos.getCol() >= 0 && pos.getCol() < cols && 
        // maze.get(pos.getRow()).get(pos.getCol()).isCheckable() && 
        // !maze.get(pos.getRow()).get(pos.getCol()).isChecked());

        return pos.getRow() >= 0 && pos.getRow() < rows && 
               pos.getCol() >= 0 && pos.getCol() < cols && 
               maze.get(pos.getRow()).get(pos.getCol()).isCheckable() && 
               !maze.get(pos.getRow()).get(pos.getCol()).isChecked();
    }

    public void printMaze() {
        for (List<Tile> row : maze) {
            for (Tile tile : row) {
                System.out.print(tile.getSymbol());
            }
            System.out.println();
        }
    }
    
}
