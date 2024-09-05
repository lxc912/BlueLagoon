package comp1110.ass2.player;

import comp1110.ass2.BlueLagoon;
import comp1110.ass2.Position;
import comp1110.ass2.Resource;
import comp1110.ass2.Resources;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zeqi Gao on 18/5/2023
 * Modified by Zeqi Gao on 19/5/2023
 */
public class Player {
    private Colour colour;
    final Piece[] settlers;
    final Piece[] villages;

    public List<Position> settlerCoords;
    public List<Position> villageCoords;
    private int settlersAvailable;
    private int villagesAvailable;
    private int score;
    private int playerId;
    private Integer coconut;
    private Integer bamboo;
    private Integer water;
    private Integer preciousStone;
    private Integer statuette;

    public Player(String[] player) {
        this.playerId = Integer.parseInt(player[1]);
        this.score = Integer.parseInt(player[2]);
        this.coconut = Integer.parseInt(player[3]);
        this.bamboo = Integer.parseInt(player[4]);
        this.water = Integer.parseInt(player[5]);
        this.preciousStone = Integer.parseInt(player[6]);
        this.statuette = Integer.parseInt(player[7]);
        ArrayList<Position> temp = new ArrayList<>();
        for (int i = 9; i < BlueLagoon.searchStr(player, "T"); i++) {
            temp.add(new Position(player[i]));
        }
        settlers = new Piece[temp.size()];
        settlerCoords = new ArrayList<>(temp);
        temp = new ArrayList<>();
        for (int i = BlueLagoon.searchStr(player, "T") + 1; i < player.length; i++) {
            temp.add(new Position(player[i]));
        }
        villageCoords = new ArrayList<>(temp);
        villages = new Piece[temp.size()];
    }

    public void addResource(Resource resource) {
        if (resource == null) return;
        switch (resource) {
            case Water -> water++;
            case Coconut -> coconut++;
            case Bamboo -> bamboo++;
            case PreciousStones -> preciousStone++;
            case Statuette -> statuette++;
        }
    }

    public void addSettlerCoords(Position position) {
        settlerCoords.add(position);
        settlerCoords.sort(Position::compareTo);
    }

    public void addVillageCoords(Position position) {
        villageCoords.add(position);
        villageCoords.sort(Position::compareTo);
    }

    public Player(Colour colour, int settlerNumber) {

        this.colour = colour;
        this.settlersAvailable = settlerNumber;
        this.settlers = new Piece[settlerNumber];
        this.villagesAvailable = 5;
        this.villages = new Piece[5];
    }

    public List<Position> getSettlerCoords() {
        return settlerCoords;
    }

    public List<Position> getVillageCoords() {
        return villageCoords;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public static Player newMagenta(int settlerNumber) {
        return new Player(Colour.MAGENTA, settlerNumber);
    }
    public static Player newOrange(int settlerNumber) {
        return new Player(Colour.ORANGE, settlerNumber);
    }
    public static Player newNavy(int settlerNumber) {
        return new Player(Colour.NAVY, settlerNumber);
    }
    public static Player newYellow(int settlerNumber) {
        return new Player(Colour.YELLOW, settlerNumber);
    }

    private int countIslands() {
        return 1;
    }
    private int findLinks() {
        return 1;
    }

    public int getSettlersAvailable() {
        return settlersAvailable;
    }
    public int getVillagesAvailable() {
        return villagesAvailable;
    }
    public void calcScore() {
    }
    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("p").append(" ");
        builder.append(playerId).append(" ");
        builder.append(score).append(" ");
        builder.append(coconut).append(" ");
        builder.append(bamboo).append(" ");
        builder.append(water).append(" ");
        builder.append(preciousStone).append(" ");
        builder.append(statuette).append(" ");
        builder.append("S").append(" ");
        for (Position settlerCoord : settlerCoords) {
            builder.append(settlerCoord.toString()).append(" ");
        }
        builder.append("T");
        for (int i = 0; i < villageCoords.size(); i++) {
            builder.append(" ").append(villageCoords.get(i).toString());
        }
        return builder.toString();
    }

    public void calculateResources(final Resources resources) {
        for (Position position : resources.getResource(Resource.Coconut)) {
            if (settlerCoords.contains(position)) {
                coconut++;
            }
        }
        for (Position position : resources.getResource(Resource.Bamboo)) {
            if (settlerCoords.contains(position)) {
                bamboo++;
            }
        }
        for (Position position : resources.getResource(Resource.Water)) {
            if (settlerCoords.contains(position)) {
                water++;
            }
        }
        for (Position position : resources.getResource(Resource.PreciousStones)) {
            if (settlerCoords.contains(position)) {
                preciousStone++;
            }
        }
        for (Position position : resources.getResource(Resource.Statuette)) {
            if (settlerCoords.contains(position)) {
                statuette++;
            }
        }
    }

    public void clearResourcesScore() {
        this.coconut = 0;
        this.bamboo = 0;
        this.water = 0;
        this.preciousStone = 0;
        this.statuette = 0;
        this.settlerCoords = new ArrayList<>();
    }

    public void removeNotResources(Resources resources) {
        this.villageCoords.removeAll(resources.getPositions());
    }
}