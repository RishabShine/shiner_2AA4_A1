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

class MazeTest {
    private Maze maze;
    private Position start;
    private Position finish;

    @BeforeEach
    void setUp() {
        // Create a 3x3 sample maze
        List<List<Tile>> mazeGrid = new ArrayList<>();
        
        for (int i = 0; i < 3; i++) {
            List<Tile> row = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                row.add(new OpenTile(' ')); // Open path
            }
            mazeGrid.add(row);
        }

        // Add a wall at (1,1)
        mazeGrid.get(1).set(1, new WallTile('P'));

        start = new Position(0, 0);
        finish = new Position(2, 2);
        maze = new Maze(mazeGrid, start, finish, '#', '.', 'P');
    }

    @Test
    void testIsFinishAtFinishPosition() {
        assertTrue(maze.isFinish(finish), "Expected isFinish() to return true at the finish position.");
    }

    @Test
    void testIsFinishAtNonFinishPosition() {
        Position nonFinish = new Position(1, 1);
        assertFalse(maze.isFinish(nonFinish), "Expected isFinish() to return false at a non-finish position.");
    }

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
