package comp1110.ass2;

import static org.junit.jupiter.api.Assertions.*;
import comp1110.ass2.testdata.GameDataLoader;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class MyTestTask6 {
    static int TOTAL = 32;
    static int REPEAT = 5000;
    final double ERROR = 0.1;
    final double PROBABILITY_STATUETTE = (double)REPEAT * 8 / 32;
    final double PROBABILITY_ELSE = (double)REPEAT * 6 / 32;

    private String getStoneCircleState(String game) {
        String[] states = game.split(";");
        for(String state : states) {
            if(state.charAt(1) == 's')
                return state.substring(3);
        }
        return "";
    }

    private String getResourceState(String game) {
        String[] states = game.split(";");
        for(String state : states) {
            if(state.charAt(1) == 'r')
                return state.substring(3);
        }
        return "";
    }

    @Test
    void isResourceDistributed() {
        String result = BlueLagoon.distributeResources(GameDataLoader.DEFAULT_GAME);

        assertTrue(BlueLagoon.isStateStringWellFormed(result), "Game state string is not well formed: " + result);

        // Split the states
        String sState = getStoneCircleState(result);
        assertNotEquals(sState, "", "Stone Circle state string not exist: " + result);
        String rState = getResourceState(result);
        assertNotEquals(rState, "", "Resources state string not exist: " + result);

        // Number of stone circles
        String[] stoneCircles = sState.split(" ");
        assertEquals(stoneCircles.length, TOTAL, "Stone Circle number not matched: " + result);

        // Number of resources distributed
        ArrayList<String> resources = new ArrayList<>();
        for(String str : rState.split(" ")) {
            if(str.length() > 1)
                resources.add(str);
        }
        assertEquals(resources.size(), stoneCircles.length, "Resources number not matched with stone circles: " + result);
    }

    @Test
    void isCoordinateValid() {
        String result = BlueLagoon.distributeResources(GameDataLoader.DEFAULT_GAME);
        int height = Integer.parseInt(result.split(" ")[1]);

        String rState = getResourceState(result);
        ArrayList<String> resources = new ArrayList<>();
        for(String part : rState.split(" ")) {
            if(part.length() > 1)
                resources.add(part);
        }

        // Coordinate out of bound
        for(String coordinate : resources) {
            for(String index : coordinate.split(",")) {
                int i = Integer.parseInt(index);
                assertTrue(i >= 0 && i < height, "Coordinate index of " + coordinate + " out of bound: " + result);
            }
        }

        // Resource coordinate collision
        for(int i = 0; i < resources.size(); i++) {
            for(int j = i+1; j < resources.size(); j++)
                assertNotEquals(resources.get(i), resources.get(j),
                        "Stone Circle at: " + resources.get(i) + " is re-distributed to different resources: " + result);
        }
    }

    @Test
    void isRandomlyDistributed() {
        String game = GameDataLoader.DEFAULT_GAME;
        ArrayList<String> sState = new ArrayList<>(List.of(getStoneCircleState(game).split(" ")));

        // Repeat distribute method and count how many times a resource distributed to a stone circle
        int[][] resources = new int[5][sState.size()];
        for(int i = 0; i < REPEAT; i++) {
            String result = BlueLagoon.distributeResources(GameDataLoader.DEFAULT_GAME);
            String[] rState = getResourceState(result).split(" ");

            int r = 0;
            for (String str : rState) {
                if (str.length() == 1)
                    r = switch (str) {
                        case "C" -> 0;
                        case "B" -> 1;
                        case "W" -> 2;
                        case "P" -> 3;
                        default -> 4;
                    };
                else {
                    resources[r][sState.indexOf(str)] += 1;
                }
            }
        }

        // Count frequency of resource distributed to different coordinates and calculate property
        for(int r = 0; r < resources.length; r++) {
            for(int c = 0; c < resources[r].length; c++) {
                if(r == 4) {
                    assertFalse(resources[r][c] < PROBABILITY_STATUETTE * (1-ERROR) || resources[r][c] > PROBABILITY_STATUETTE * (1+ERROR),
                            "Resource Statuette distributed at " + sState.get(c) + " for " + resources[r][c] + " times is out of expectation range: " + PROBABILITY_STATUETTE * (1-ERROR) + ", " + PROBABILITY_STATUETTE * (1+ERROR));
                }
                else {
                    String name = switch (r) {
                        case 0 -> "Coconut";
                        case 1 -> "Bamboo";
                        case 2 -> "Water";
                        default -> "Precious Stones";
                    };
                    assertFalse(resources[r][c] < PROBABILITY_ELSE * (1-ERROR) || resources[r][c] > PROBABILITY_ELSE * (1+ERROR),
                            "Resource " + name + " distributed at " + sState.get(c) + " for " + resources[r][c] + " times is out of expectation range: " + PROBABILITY_ELSE * (1-ERROR) + ", " + PROBABILITY_ELSE * (1+ERROR));
                }
            }
        }

    }
}
