package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Arrays;

class MazeTest {
    private PathFormatter pathFormatter;
    private MazeSolver mazeSolver;
    private MazeReader mazeReader;
    private MazeService mazeService;
    
    @BeforeEach
    void setUp() {
        pathFormatter = new PathFormatter();
        mazeSolver = new MazeSolver();
        mazeReader = new MazeReader();
        mazeService = new MazeService(mazeSolver);
    }
    
    // 1. Test PathFormatter - Valid Path
    @Test
    void testFactorizedPathValid() {
        assertEquals("3F 1R 1F", pathFormatter.getFactorizedPath("FFFRF"));
    }
    
    // 2. Test PathFormatter - Invalid Path
    @Test
    void testFactorizedPathInvalid() {
        assertNull(pathFormatter.getFactorizedPath("FFXRR"));
    }
    
    // 3. Test MazeSolver - Find Start Position
    @Test
    void testFindStart() {
        List<List<Character>> maze = Arrays.asList(
            Arrays.asList('#', '#', '#'),
            Arrays.asList(' ', ' ', '#'),
            Arrays.asList('#', '#', '#')
        );
        assertArrayEquals(new int[]{1, 0}, mazeSolver.findStart(maze));
    }
    
    // 4. Test MazeSolver - Find Finish Position
    @Test
    void testFindFinish() {
        List<List<Character>> maze = Arrays.asList(
            Arrays.asList('#', '#', ' '),
            Arrays.asList('#', '#', '#'),
            Arrays.asList(' ', '#', '#')
        );
        assertArrayEquals(new int[]{0, 2}, mazeSolver.findFinish(maze));
    }
    
    // 5. Test MazeSolver - Validate Correct Path
    @Test
    void testValidatePathValid() {
        List<List<Character>> maze = Arrays.asList(
            Arrays.asList('#', '#', ' '),
            Arrays.asList('#', ' ', ' '),
            Arrays.asList(' ', ' ', '#')
        );
        assertTrue(mazeSolver.validatePath(maze, "FLFRFLF"));
    }
    
    // 6. Test MazeSolver - Validate Incorrect Path
    @Test
    void testValidatePathInvalid() {
        List<List<Character>> maze = Arrays.asList(
            Arrays.asList('#', '#', ' '),
            Arrays.asList('#', '#', '#'),
            Arrays.asList(' ', '#', '#')
        );
        assertFalse(mazeSolver.validatePath(maze, "FF"));
    }
    
    // 7. Test MazeReader - Load Valid Maze
    @Test
    void testLoadMazeValid() {
        List<List<Character>> maze = mazeReader.loadMaze("examples/small.maz.txt");
        assertNotNull(maze);
        assertFalse(maze.isEmpty());
    }
    
    // 8. Test MazeReader - Load Invalid Maze File
    @Test
    void testLoadMazeInvalid() {
        List<List<Character>> maze = mazeReader.loadMaze("examples/nonexistent.txt");
        assertNull(maze);
    }
    
    // 9. Test MazeService - Valid Path
    @Test
    void testGetPath() {
        String path = mazeService.getPath("examples/small.maz.txt");
        assertNotNull(path);
        assertFalse(path.isEmpty());
    }
    
    // 10. Test MazeService - Validate Path
    @Test
    void testIsValidPath() {
        String result = mazeService.isValidPath("examples/small.maz.txt", "FLFRFFLFFFFFFRFFFFRFFLFFRFFLF");
        assertEquals("Provided path is valid", result);
    }
}
