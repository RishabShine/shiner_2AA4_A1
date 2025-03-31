package ca.mcmaster.se2aa4.mazerunner.Interfaces;

import ca.mcmaster.se2aa4.mazerunner.enums.TileType;
import ca.mcmaster.se2aa4.mazerunner.Tiles.Tile;

public interface TileFactory {

    public Tile getTile(TileType tileType);

}