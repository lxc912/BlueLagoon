package comp1110.ass2.gui;

import comp1110.ass2.Position;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;


/**
 * Created by Yufei Huang on 17/4/2023
 * Modified by Yufei Huang on 19/5/2023
 */
public class Hexagon extends Polygon {
    public Position position;   // The position of this hexagon on map
    public Hexagon(double x, double y, double side, Color color, int i, int j) {
        double halfWidth = side * Math.sin(Math.PI/3.0);
        double halfHeight = side / 2.0;
        this.getPoints().addAll(0.0, -side, halfWidth, -halfHeight, halfWidth, halfHeight,
                                    0.0, side, -halfWidth, halfHeight, -halfWidth, -halfHeight);
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setFill(color);
        this.position = new Position(i, j);
    }
}
