package comp1110.ass2.board;

import comp1110.ass2.player.Colour;


/**
 * Created by Yufei Huang on 22/3/2023
 * Modified by Yufei Huang on 18/5/2023
 */
public class Players {
    private final int id;
    private final Colour colour;
    private int score;
    private int[] claimedResources = new int[5];
    private int settlersAvailable;
    private int villagesAvailable;

    public Players(int playerId, int playerNum) {
        this.id = playerId;
        this.colour = Colour.fromInt(playerId);
        this.score = 0;
        this.settlersAvailable = switch (playerNum) {
            case 2 -> 30;
            case 3 -> 25;
            default -> 20;
        };
        this.villagesAvailable = 5;
    }

    public Players(String playerState, int playerNum) {
        String[] stats = playerState.split(" ");
        this.id = Integer.parseInt(stats[1]);
        this.colour = Colour.fromInt(id);
        this.score = Integer.parseInt(stats[2]);
        this.settlersAvailable = switch (playerNum) {
            case 2 -> 30;
            case 3 -> 25;
            default -> 20;
        };
        this.villagesAvailable = 5;
    }


    public void setScore(int score) {
        this.score = score;
    }

    public void placeSettler(int i) {
        if (settlersAvailable >= i) settlersAvailable -= i;
    }
    public void placeVillage(int i) {
        if (villagesAvailable >= i) villagesAvailable -= i;
    }
    public int getSettlersAvailable() {
        return settlersAvailable;
    }
    public int getVillagesAvailable() {
        return villagesAvailable;
    }
    public int getScore() {
        return score;
    }
    public void setClaimedResources(int[] resources) {
        this.claimedResources = resources;
    }
    public int getClaimedResources(int i) {
        return claimedResources[i];
    }
}
