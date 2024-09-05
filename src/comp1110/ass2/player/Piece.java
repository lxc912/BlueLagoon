package comp1110.ass2.player;

import comp1110.ass2.Position;

public class Piece {
    private final boolean settlerVillage;
    private final Colour colour;
    private Position position;

    public Piece(String settlerVillage, Colour colour) {
        this.settlerVillage = (settlerVillage.equals("Village"));
        this.colour = colour;
    }
    public boolean isVillage() {
        return settlerVillage;
    }
    public Colour getColour() {
        return colour;
    }
    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
    public Piece[] getNeighbors(){
        return new Piece[0];
    }
}
