package ca.mcmaster.se2aa4.mazerunner.Maze;

import ca.mcmaster.se2aa4.mazerunner.Tiles.PathTile;
import ca.mcmaster.se2aa4.mazerunner.Tiles.Tile;
import ca.mcmaster.se2aa4.mazerunner.PathFinder;
import ca.mcmaster.se2aa4.mazerunner.Interfaces.SolverObserver;

public class Maze implements SolverObserver{

    private PathFinder pathFinder;

    private Tile[][] grid;
    private int rows, cols;
    private int[] start;
    private int[] finish;

    public Maze(Tile[][] grid, int[] start, int[] finish) {
        this.grid = grid;
        this.rows = grid.length;
        this.cols = grid[0].length;
        this.start = start;
        this.finish = finish;
    }

    @Override
    public void update(int[] pos) {
        int row = pos[0], col = pos[1];
        if (isCheckable(row, col)) {
            markChecked(row, col);
            //grid[row][col].setChecked(true);
        }
        //? can move this to a helper function
        if (row == finish[0] && col == finish[1]) {
            System.out.println("Maze solved!");
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int[] getStart() {
        return start;
    }

    public int[] getFinish() {
        return finish;
    }

    public Tile getTile(int row, int col) {
        return grid[row][col];
    }

    public void markAsPath(int row, int col, Character pathSymbol) {
        grid[row][col] = new PathTile(pathSymbol);
    }

    public void markChecked(int row, int col) {
        grid[row][col].markChecked();
    }

    public boolean isCheckable(int row, int col) {
        return grid[row][col].isCheckable();
    }

    public boolean isValidMove(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols && grid[row][col].isCheckable() && !grid[row][col].isChecked();
    }
    
}
