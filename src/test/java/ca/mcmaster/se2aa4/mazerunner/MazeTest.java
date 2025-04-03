package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.Position;
import ca.mcmaster.se2aa4.mazerunner.Tiles.Tile;
import ca.mcmaster.se2aa4.mazerunner.Tiles.WallTile;
import ca.mcmaster.se2aa4.mazerunner.Tiles.OpenTile;
import ca.mcmaster.se2aa4.mazerunner.Maze.Maze;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

/*
 * extensive testing for the Maze class as it has been revamped to store the maze in a 2D list of Tile's
 * Maze is now responsible for validating moves and determining the state of the maze search 
 *      (searcher is simply in charge of moving around the maze)
 */
class MazeTest {
    private Maze maze;
    private Position start;
    private Position finish;

    @BeforeEach
    void setUp() {
        // Create a 3x3 sample maze
        List<List<Tile>> mazeGrid = new ArrayList<>();
        
        /*
         * creating maze with a set number of Open Tiles
         */
        for (int i = 0; i < 3; i++) {
            List<Tile> row = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                // all designated Open Tiles
                if ((i == 0 && j == 0) || (i == 2 && j == 2) || (i == 0 && j == 1)) {
                    row.add(new OpenTile(' ')); // Open Tile
                } else {
                    row.add(new WallTile('#')); // Wall Tile
                }            }
            mazeGrid.add(row);
        }

        start = new Position(0, 0);
        finish = new Position(2, 2);
        maze = new Maze(mazeGrid, '#', ' ', 'P');
    }

    /*
     * testing the Mazes findFinish() method
     */
    @Test
    void testIsFinishAtFinishPosition() {
        assertTrue(maze.isFinish(finish), "Expected isFinish() to return true at the finish position.");
    }

    @Test
    void testIsFinishAtNonFinishPosition() {
        Position nonFinish = new Position(1, 1);
        assertFalse(maze.isFinish(nonFinish), "Expected isFinish() to return false at a non-finish position.");
    }

    /* 
     * testing the properties of the Tiles in the maze
     */
    @Test
    void testIsCheckable_WallTile() {
        Position wallPos = new Position(1, 1);
        assertFalse(maze.isCheckable(wallPos), "Wall tiles should not be checkable.");
    }

    @Test
    void testIsCheckable_OpenTile() {
        Position openPos = new Position(0, 0);
        assertTrue(maze.isCheckable(openPos), "Open tiles should be checkable.");
    }

    /*
     * testing the new logic to check if a move is valid
     *  maze now handles this, checking if a spot is valid
     */
    @Test
    void testIsValidMove_ValidOpenTile() {
        Position validMove = new Position(0, 1); // OpenTile
        assertTrue(maze.isValidMove(validMove), "Should be a valid move to an open, unvisited tile.");
    }

    @Test
    void testIsValidMove_WallTile() {
        Position wallMove = new Position(1, 1); // WallTile
        assertFalse(maze.isValidMove(wallMove), "Should not be a valid move to a wall tile.");
    }

    @Test
    void testIsValidMove_OutOfBounds() {
        Position outOfBounds = new Position(3, 3); // Outside the 3x3 grid
        assertFalse(maze.isValidMove(outOfBounds), "Should not be a valid move outside the maze bounds.");
    }
}
