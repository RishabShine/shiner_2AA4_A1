package ca.mcmaster.se2aa4.mazerunner.Tiles;

public abstract class Tile {
    protected char symbol;
    protected boolean checkable;
    protected boolean checked;

    public Tile(Character symbol, boolean checkable) {
        this.symbol = symbol;
        this.checkable = checkable;
        this.checked = false;
    }

    public char getSymbol() {
        return symbol;
    }

    public boolean isCheckable() {
        return checkable;
    }

    public boolean isChecked() {
        return checked;
    }

    public void markChecked() {
        if (checkable) {
            this.checked = true;
        }
    }
}
