package ca.mcmaster.se2aa4.mazerunner;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/*
 * extensive testing required for Position as this new addition from A1 overrides the .equals() method
 * the hashing also needs to be tested due to this, especially due to the use of hashmaps in the program
 */
class PositionTest {

    @Test
    void testEquals_SameValues() {
        Position p1 = new Position(2, 3);
        Position p2 = new Position(2, 3);
        
        assertEquals(p1, p2, "Positions with the same row and column should be equal");
    }

    @Test
    void testEquals_DifferentRows() {
        Position p1 = new Position(2, 3);
        Position p2 = new Position(3, 3);
        
        assertNotEquals(p1, p2, "Positions with different rows should not be equal");
    }

    @Test
    void testEquals_DifferentCols() {
        Position p1 = new Position(2, 3);
        Position p2 = new Position(2, 4);
        
        assertNotEquals(p1, p2, "Positions with different columns should not be equal");
    }

    @Test
    void testEquals_Null() {
        Position p1 = new Position(2, 3);
        
        assertNotEquals(p1, null, "A position should not be equal to null");
    }

    @Test
    void testEquals_DifferentClass() {
        Position p1 = new Position(2, 3);
        String notAPosition = "Not a Position";
        
        assertNotEquals(p1, notAPosition, "A position should not be equal to an object of a different class");
    }

    @Test
    void testHashCode_Consistency() {
        Position p1 = new Position(2, 3);
        Position p2 = new Position(2, 3);
        
        assertEquals(p1.hashCode(), p2.hashCode(), "Equal objects must have the same hashCode");
    }

    @Test
    void testHashCode_Inequality() {
        Position p1 = new Position(2, 3);
        Position p2 = new Position(3, 2);
        
        assertNotEquals(p1.hashCode(), p2.hashCode(), "Different objects should have different hashCodes");
    }
}
