package ca.mcmaster.se2aa4.mazerunner.Maze;

import ca.mcmaster.se2aa4.mazerunner.enums.TileType;
import ca.mcmaster.se2aa4.mazerunner.Tiles.*;
import ca.mcmaster.se2aa4.mazerunner.Interfaces.TileFactory;

public class MazeTileFactory implements TileFactory {

    private Character openChar;
    private Character wallChar;

    public MazeTileFactory (Character openChar, Character wallChar) {
        this.openChar = openChar;
        this.wallChar = wallChar;
    }

    @Override
    public Tile getTile(TileType tileType) {
        if (tileType == TileType.OPEN) {
            return new OpenTile(openChar);
        } else if (tileType == TileType.WALL) {
            return new WallTile(wallChar);
        }
        return null;
    }

}