package ca.mcmaster.se2aa4.mazerunner.Maze;

import ca.mcmaster.se2aa4.mazerunner.Tiles.Tile;
import ca.mcmaster.se2aa4.mazerunner.Interfaces.SolverObserver;
import ca.mcmaster.se2aa4.mazerunner.Interfaces.TileFactory;
import ca.mcmaster.se2aa4.mazerunner.enums.SolverUpdateType;
import ca.mcmaster.se2aa4.mazerunner.enums.TileType;
import ca.mcmaster.se2aa4.mazerunner.Position;
import ca.mcmaster.se2aa4.mazerunner.MazeReader;
import java.util.List;

public class Maze implements SolverObserver {

    private final TileFactory tileFactory;
    private List<List<Tile>> maze;
    private int rows, cols;
    private Position start;
    private Position finish;

    char wallChar = '#';
    char openChar = ' ';
    char pathChar = 'P';

    public Maze(char wallChar, char openChar, char pathChar, String filepath) {
        this.wallChar = wallChar;
        this.openChar = openChar;
        this.pathChar = pathChar;
        MazeReader mazeReader = new MazeReader(this.wallChar, this.openChar, this.pathChar);
        this.maze = mazeReader.loadMaze(filepath);
        this.rows = maze.size();
        this.cols = maze.get(0).size();
        this.start = findStart();
        this.finish = findFinish();
        this.tileFactory = new MazeTileFactory(openChar, wallChar, pathChar);
    }

    // alternate consutructor that directly accepts maze instead of filepath, only used for test cases
    public Maze(List<List<Tile>> maze, char wallChar, char openChar, char pathChar) {
        this.maze = maze;
        this.rows = maze.size();
        this.cols = maze.get(0).size();
        this.start = findStart();
        this.finish = findFinish();
        this.wallChar = wallChar;
        this.openChar = openChar;
        this.pathChar = pathChar;
        this.tileFactory = new MazeTileFactory(openChar, wallChar, pathChar);
    }

    @Override
    public void update(Position pos, SolverUpdateType updateType) {
        if (updateType == SolverUpdateType.CHECK) {
            if (isCheckable(pos)) {
                markChecked(pos);
            }
        } else if (updateType == SolverUpdateType.ADD_PATH) {
            if (isCheckable(pos)) {
                markAsPath(pos);
            }
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

    private void markAsPath(Position pos) {
        maze.get(pos.getRow()).set(pos.getCol(), tileFactory.getTile(TileType.PATH));
    }

    private void markChecked(Position pos) {
        maze.get(pos.getRow()).get(pos.getCol()).markChecked();
    }

    public boolean isCheckable(Position pos) {
        return maze.get(pos.getRow()).get(pos.getCol()).isCheckable();
    }

    public boolean isValidMove(Position pos) {
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

    private Position findStart() {
        if (this.maze == null) {
            return null;
        }
        for (int i = 0; i < this.maze.size(); i++) {
            if (this.maze.get(i).get(0).isCheckable()) {
                return new Position(i, 0);
                //return new int[]{i, 0};
            }
        }
        return null;
    }

    private Position findFinish() {
        if (this.maze == null) {
            return null;
        }
        int mazeLength = this.maze.get(0).size() - 1;
        for (int i = 0; i < this.maze.size(); i++) {
            if (this.maze.get(i).get(mazeLength).isCheckable()) {
                return new Position(i, mazeLength);
            }
        }
        return null;
    }
    
}
