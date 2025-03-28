package ca.mcmaster.se2aa4.mazerunner;

import java.util.Objects;

public class Position {

    int row;
    int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Position move(int[] offset) {
        return new Position(this.row + offset[0], this.col + offset[1]);
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return this.row == position.getRow() && this.col == position.getCol();
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
