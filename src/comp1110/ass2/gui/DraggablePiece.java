package comp1110.ass2.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by Yufei Huang on 17/5/2023
 * Modified by Yufei Huang on 19/5/2023
 */
public class DraggablePiece extends ImageView {
    private final List<Hexagon> hexes;
    private Hexagon nearest = null;
    double radius;
    double side;
    double halfWidth;
    final double startX;
    final double startY;
    private double mouseX;
    private double mouseY;
    private Hexagon highlight = null;

    private Hexagon findNearest(double centerX, double centerY) {
        Hexagon nearest = null;
        double best = side;     // Center of the piece must be within the hexagon
        for (Hexagon hexagon : hexes) {
            double distance = Math.sqrt(Math.pow((centerX + side) - (hexagon.getLayoutX() + halfWidth), 2)
                                    + Math.pow((centerY + side) - (hexagon.getLayoutY() + side), 2));
            if (distance < best) {
                best = distance;
                nearest = hexagon;
            }
        }
        return nearest;
    }

    private void highlightNearestPiece(double centerX, double centerY) {
        // Reset previously highlighted
        if (highlight != null)
            highlight.setFill(highlight.getFill() == Color.GREENYELLOW ? Color.GREEN : Color.DEEPSKYBLUE);

        // Highlight the nearest if exist
        highlight = findNearest(centerX, centerY);
        if (highlight != null)
            highlight.setFill(highlight.getFill() == Color.GREEN ? Color.GREENYELLOW : Color.LIGHTSKYBLUE);
    }

    public DraggablePiece(Image pieceImg, double x, double y, double side, List<Hexagon> hexes) throws FileNotFoundException {
        super(pieceImg);
        this.side = side;
        double resizeRatio = side / 100;
        this.radius = pieceImg.getHeight() * resizeRatio / 2.0;
        this.halfWidth = side * Math.sin(Math.PI/3.0);
        this.hexes = hexes;

        this.setPreserveRatio(true);
        this.setFitHeight(pieceImg.getHeight() * resizeRatio);
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.startX = getLayoutX();
        this.startY = getLayoutY();

        // Remember the initial mouse position when clicked
        this.setOnMousePressed(event -> {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
            toFront();
        });

        // Move the piece by same distance as the mouse
        this.setOnMouseDragged(event -> {
            setLayoutX(getLayoutX() + event.getSceneX() - mouseX);
            setLayoutY(getLayoutY() + event.getSceneY() - mouseY);

            // Refresh mouse position
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();

            // Continuously Highlight the nearest
            highlightNearestPiece(getLayoutX() + radius, getLayoutY() + radius);
        });

        // Snap to the nearest hexagon or initial position if
        this.setOnMouseReleased(event -> {
            // Find the nearest hexagon, center of the piece must be within the hexagon
            this.nearest = findNearest(getLayoutX() + radius, getLayoutY() + radius);
            if (nearest != null){
                setLayoutX(nearest.getLayoutX() - radius);
                setLayoutY(nearest.getLayoutY() - radius);
            }

            // Reset to initial position
            else {
                setLayoutX(startX);
                setLayoutY(startY);
            }
        });
    }

    public Hexagon getNearest() {
        return nearest;
    }
}
