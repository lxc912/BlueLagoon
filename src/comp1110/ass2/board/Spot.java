package comp1110.ass2.board;

import comp1110.ass2.Resource;

/**
 * Created by Yufei Huang on 22/3/2023
 * Modified by Yufei Huang on 18/5/2023
 */
public class Spot {
    private final int terrain;
    private final int islandId;
    private Resource resource = null;
    private int occupier = -1;
    private boolean isVillage = false;

    public Spot(int terrain, int islandId) {
        this.terrain = terrain;
        this.islandId = islandId;
    }

    public int getTerrain() {
        return terrain;
    }

    public int getIslandId() {
        return islandId;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public void setResource(int resource) {
        this.resource = Resource.fromInt(resource);
    }

    public Resource getResource() {
        return resource;
    }

    public void occupy(int playerId, boolean isVillage) {
        this.occupier = playerId;
        this.isVillage = isVillage;
    }

    public void unOccupy() {
        occupier = -1;
    }

    public boolean isOccupied() {
        return !(occupier == -1);
    }

    public int getOccupier() {
        return occupier;
    }

    public boolean isVillage() {
        return isVillage;
    }
}
