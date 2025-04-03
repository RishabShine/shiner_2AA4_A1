package ca.mcmaster.se2aa4.mazerunner.Maze;

import ca.mcmaster.se2aa4.mazerunner.Tiles.PathTile;
import ca.mcmaster.se2aa4.mazerunner.Tiles.Tile;
import ca.mcmaster.se2aa4.mazerunner.Interfaces.SolverObserver;
import ca.mcmaster.se2aa4.mazerunner.enums.SolverUpdateType;
import ca.mcmaster.se2aa4.mazerunner.Position;
import ca.mcmaster.se2aa4.mazerunner.MazeReader;
import java.util.List;

public class Maze implements SolverObserver {

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
    }

    //! remove, only used for test
    public Maze(List<List<Tile>> maze, Position start, Position finish, char wallChar, char openChar, char pathChar) {
        this.maze = maze;
        this.rows = maze.size();
        this.cols = maze.get(0).size();
        this.start = start;
        this.finish = finish;
        this.wallChar = wallChar;
        this.openChar = openChar;
        this.pathChar = pathChar;
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

    //! can remove
    public Tile getTile(Position pos) {
        return maze.get(pos.getRow()).get(pos.getCol());
    }

    private void markAsPath(Position pos) {
        maze.get(pos.getRow()).set(pos.getCol(), new PathTile(pathChar));
    }

    private void markChecked(Position pos) {
        maze.get(pos.getRow()).get(pos.getCol()).markChecked();
    }

    //! can move this to private and make user given path valdiation done from in maze
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
