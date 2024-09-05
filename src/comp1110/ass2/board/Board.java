package comp1110.ass2.board;

import comp1110.ass2.BlueLagoon;
import comp1110.ass2.Direction;
import comp1110.ass2.Position;
import comp1110.ass2.Resource;
import comp1110.ass2.player.Piece;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yufei Huang on 22/3/2023
 * Modified by Yufei Huang on 18/5/2023
 */
public class Board {
    private final int boardHeight;
    private final int playerNum;
    private int currentPlayerId;
    private char phase;
    private final List<Integer> islandsStats;
    public final Spot[][] spotMatrix;
    private int[] resourcesLeft = new int[]{6,6,6,6,8};
    public Players[] players;

    public Board(String stateString) {
        this.boardHeight = BlueLagoon.getBoardHeight(stateString);
        this.playerNum = BlueLagoon.getPlayerNumber(stateString);
        this.currentPlayerId = BlueLagoon.getCurrentPlayerId(stateString);
        this.phase = BlueLagoon.getPhase(stateString);
        this.islandsStats = BlueLagoon.getIslandsStats(stateString);

        // Stats of every position
        this.spotMatrix = new Spot[boardHeight][boardHeight];

        // Terrain of position and island it belongs to
        int[][] terrainsMatrix = BlueLagoon.getTerrainsMatrix(stateString);
        int[][] islandsMatrix = BlueLagoon.getIslandsMatrix(stateString);
        for (int i = 0; i < boardHeight; i++)
            for (int j = 0; j < boardHeight; j++) {
                if (terrainsMatrix[i][j] != -1)
                    spotMatrix[i][j] = new Spot(terrainsMatrix[i][j], islandsMatrix[i][j]);
            }

        // Unclaimed resources
        List<List<String>> unclaimedResources = BlueLagoon.getUnclaimedResources(stateString);
        for (int i = 0; i < 5; i++) {
            resourcesLeft[i] = (i == 4 ? 8 : 6) - unclaimedResources.get(i).size();
            for (String coordinate : unclaimedResources.get(i)) {
                Position pos = new Position(coordinate);
                spotMatrix[pos.getX()][pos.getY()].setResource(i);
            }
        }

        // Players stats
        this.players = new Players[playerNum];
        List<List<String>> placed = BlueLagoon.getPlaced(stateString);
        int[] scores = BlueLagoon.getScores(stateString);
        for (int playerId = 0; playerId < playerNum; playerId++) {
            players[playerId] = new Players(playerId, playerNum);
            players[playerId].setScore(scores[playerId]);
            players[playerId].placeSettler(placed.get(playerId * 2).size());
            players[playerId].placeVillage(placed.get(playerId * 2 + 1).size());
            for (String coordinate : placed.get(playerId * 2)) {
                Position pos = new Position(coordinate);
                spotMatrix[pos.getX()][pos.getY()].occupy(playerId, false);
            }
            for (String coordinate : placed.get(playerId * 2 + 1)) {
                Position pos = new Position(coordinate);
                spotMatrix[pos.getX()][pos.getY()].occupy(playerId, true);
            }
        }
        int[][] claimedResources = BlueLagoon.getClaimedResources(stateString);
        for (int playerId = 0; playerId < playerNum; playerId++) {
            players[playerId].setClaimedResources(claimedResources[playerId]);
        }
    }

    public Spot getSpot(Position position) {
        return spotMatrix[position.getX()][position.getY()];
    }

    public boolean isSpot(Position position) {
        return spotMatrix[position.getX()][position.getY()] != null;
    }

    public boolean isSpot(int x, int y) {
        return spotMatrix[x][y] != null;
    }

    public int getHeight() {
        return boardHeight;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public int getCurrentPlayer() {
        return currentPlayerId;
    }

    public char getPhase() {
        return phase;
    }

    public boolean tryPlacePiece(Piece piece, Position position) {
        return false;
    }

    public int getResources(Resource r) {
        return resourcesLeft[Resource.toInt(r)];
    }

    private void addSpots() {

    }
    public Spot getNeighbor(Position position, Direction direction) {
        return spotMatrix[0][0];
    }

    /**
     * Create state string representing board status
     *
     * @return stateString
     */
    public String toStateString() {
        // Game Arrangement
        String stateString = "a " + boardHeight + " " + playerNum + ";";

        // Current State
        stateString += " c " + currentPlayerId + " " + phase + ";";

        // Islands
        for (int islandId = 0; islandId < islandsStats.size(); islandId++) {
            stateString += " i " + islandsStats.get(islandId);
            for (int i = 0; i < boardHeight; i++)
                for (int j = 0; j < boardHeight; j++) {
                    if (spotMatrix[i][j] != null && spotMatrix[i][j].getIslandId() == islandId)
                        stateString += " " + new Position(i,j);
                }
            stateString += ";";
        }

        // Stone Circles
        stateString += " s";
        List<Position> stoneCircles = new ArrayList<>();
        for (int i = 0; i < boardHeight; i++)
            for (int j = 0; j < boardHeight; j++) {
                if (spotMatrix[i][j] != null && spotMatrix[i][j].getTerrain() == 2) {
                    stoneCircles.add(new Position(i, j));
                    stateString += " " + new Position(i, j);
                }
            }
        stateString += ";";

        // Unclaimed Resources
        stateString += " r";
        for (int i = 0; i < 5; i++) {
            stateString += " " + Resource.toChar(Resource.fromInt(i));
            for (Position stoneCircle : stoneCircles) {
                if (spotMatrix[stoneCircle.getX()][stoneCircle.getY()] != null && spotMatrix[stoneCircle.getX()][stoneCircle.getY()].getResource() == Resource.fromInt(i))
                    stateString += " " + stoneCircle;
            }
        }
        stateString += ";";

        // Players
        for (int playerId = 0; playerId < playerNum; playerId++) {
            stateString += " p " + playerId + " " + players[playerId].getScore();
            for (int i = 0; i < 5; i++) {
                stateString += " " + players[playerId].getClaimedResources(i);
            }

            String settler = " S";
            String village = " T";
            for (int i = 0; i < boardHeight; i++)
                for (int j = 0; j < boardHeight; j++) {
                    if (spotMatrix[i][j] != null && spotMatrix[i][j].getOccupier() == playerId) {
                        if (spotMatrix[i][j].isVillage())
                            village += " " + new Position(i, j);
                        else
                            settler += " " + new Position(i, j);
                    }
                }
            stateString += settler + village + ";";
        }

        return  stateString;
    }
}
