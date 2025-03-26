package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.enums.Heading;
import java.util.Map;

public class MapNavigator {

    // Map of moves with directional keys
    private static final Map<Heading, int[]> OFFSETS = Map.of(
        Heading.N, new int[]{-1, 0},
        Heading.E, new int[]{0, 1},
        Heading.S, new int[]{1, 0},
        Heading.W, new int[]{0, -1}
    );

    // Corresponding directions for a right turn
    private static final Map<Heading, Heading> RIGHT_TURN = Map.of(
        Heading.N, Heading.E,
        Heading.E, Heading.S,
        Heading.S, Heading.W,
        Heading.W, Heading.N
    );

    // Corresponding directions for a left turn
    private static final Map<Heading, Heading> LEFT_TURN = Map.of(
        Heading.N, Heading.W,
        Heading.W, Heading.S,
        Heading.S, Heading.E,
        Heading.E, Heading.N
    );

    public static Heading getRight(Heading direction) {
        return RIGHT_TURN.get(direction);
    }

    public static Heading getLeft(Heading direction) {
        return LEFT_TURN.get(direction);
    }

    public static int[] getOffset(Heading direction) {
        return OFFSETS.get(direction);
    }
}
