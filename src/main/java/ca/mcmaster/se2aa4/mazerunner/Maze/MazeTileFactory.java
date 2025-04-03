package ca.mcmaster.se2aa4.mazerunner.Maze;

import ca.mcmaster.se2aa4.mazerunner.enums.TileType;
import ca.mcmaster.se2aa4.mazerunner.Tiles.*;
import ca.mcmaster.se2aa4.mazerunner.Interfaces.TileFactory;

public class MazeTileFactory implements TileFactory {

    // default values
    private Character openChar = ' ';
    private Character wallChar = '#';
    private Character pathChar = 'P';

    public MazeTileFactory (Character openChar, Character wallChar) {
        this.openChar = openChar;
        this.wallChar = wallChar;
    }


    public MazeTileFactory (Character openChar, Character wallChar, Character pathChar) {
        this.openChar = openChar;
        this.wallChar = wallChar;
        this.pathChar = pathChar;
    }

    @Override
    public Tile getTile(TileType tileType) {
        if (tileType == TileType.OPEN) {
            return new OpenTile(openChar);
        } else if (tileType == TileType.WALL) {
            return new WallTile(wallChar);
        } else if (tileType == TileType.PATH) {
            return new PathTile(pathChar);
        }
        throw new IllegalArgumentException("Invalid tile type: " + tileType);
    }

}