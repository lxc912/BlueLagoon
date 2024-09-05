package comp1110.ass2.gui;

import comp1110.ass2.BlueLagoon;
import comp1110.ass2.Position;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Modified by Yufei Huang on 17/4/2023
 */
public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;

    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField stateTextField;

    public static String getPiecePath(int playerId, int pieceType) {
        String piecePath = System.getProperty("user.dir") + "/src/comp1110/ass2/gui/assets/pieces/";
        piecePath += switch (playerId) {
            case 0 -> "violet";
            case 1 -> "orange";
            case 2 -> "navy";
            default -> "yellow";
        };
        piecePath += switch (pieceType){
            case 0 -> "Sea";
            case 1 -> "Land";
            default -> "Village";
        };
        piecePath += ".png";
        return piecePath;
    }

    public static String getResourcePath(int resource) {
        String resourcePath = System.getProperty("user.dir") + "/src/comp1110/ass2/gui/assets/resources/";
        resourcePath += (switch (resource) {
            case 0 -> "coconut";
            case 1 -> "bamboo";
            case 2 -> "water";
            case 3 -> "preciousStone";
            default -> "statuette";
        } + ".png");
        return resourcePath;
    }
    /**
     * Given a state string, draw a representation of the state
     * on the screen.
     * <p>
     * This may prove useful for debugging complex states.
     *
     * @param stateString a string representing a game state
     */
    void displayState(String stateString) throws FileNotFoundException {
        int height = Integer.parseInt(stateString.split(" ",3)[1]);     // Size of the map (max width is equal to height)
        double side = Math.round((VIEWER_HEIGHT - 150) / ((height-1) * 1.5 + 2));   // Length of hexagon side
        double halfWidth = side * Math.sin(Math.PI/3.0);

        int center = height / 2;    // Center hexagon
        double centerX = VIEWER_WIDTH / 2.0;
        double centerY = VIEWER_HEIGHT / 2.0;

        /* Grid of Terrains
        // -1: Not available (out of bound)
        // 0: Sea
        // 1: Land
        // 2: StoneCircle
         */
        int[][] terrains = BlueLagoon.getTerrainsMatrix(stateString);

        // Show the map on Viewer
        root.getChildren().clear(); // Clear all children
        root.getChildren().add(controls);   // Restore controls after clear
        makeControls();
        root.getScene().setFill(Color.NAVY);

        List<Hexagon> hexes = new ArrayList<>();
        List<Hexagon> edges = new ArrayList<>();
        for (int i = 0; i < height; i++)
            for (int j = 0; j < height; j++) {
                if (terrains[i][j] >= 0){
                    double layoutX, layoutY;
                    if (i % 2 == 0)
                        layoutX = centerX + ((j - center) * 2 + 1) * halfWidth;
                    else
                        layoutX = centerX + ((j - center) * 2) * halfWidth;
                    layoutY = centerY + ((i - center) * 1.5) * side;

                    Hexagon edge = new Hexagon(layoutX, layoutY, side*1.05, Color.WHITE, i, j); // Add white edge to hexagon
                    edges.add(edge);
                    root.getChildren().add(edge);

                    Color color = terrains[i][j] > 0 ? Color.GREEN : Color.DEEPSKYBLUE; // Determine color by terrain
                    Hexagon hexagon = new Hexagon(layoutX, layoutY, side*0.95, color, i, j);
                    hexes.add(hexagon);
                    root.getChildren().add(hexagon);

                    if (terrains[i][j] == 2) drawStoneCircle(layoutX, layoutY, halfWidth, root); // Add stone circle on land
                }
            }

        // Show sample resources and pieces on Viewer
        double resizeRatio = side / 100.0;
        String currentPath = System.getProperty("user.dir") + "/src/comp1110/ass2";
        for (int i = 0; i < 5; i++) {
            String resourcePath = currentPath + "/gui/assets/resources/";
            resourcePath += (switch (i) {
                case 0 -> "coconut";
                case 1 -> "bamboo";
                case 2 -> "water";
                case 3 -> "preciousStone";
                default -> "statuette";
            } + ".png");
            Image resourceImg = new Image(new FileInputStream(resourcePath));
            ImageView imageView = new ImageView(resourceImg);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(resourceImg.getHeight() * resizeRatio);
            imageView.setX(side * 1.5 - resourceImg.getWidth() * resizeRatio / 2);
            imageView.setY((i+1) * (side * 1.5) - resourceImg.getHeight() * resizeRatio / 2);
            root.getChildren().add(imageView);
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                String piecePath = getPiecePath(i, j);

                Image pieceImg = new Image(new FileInputStream(piecePath));
                ImageView imageView = new ImageView(pieceImg);
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(pieceImg.getHeight() * resizeRatio);
                imageView.setY((i+1) * (side * 1.5) - pieceImg.getHeight() * resizeRatio / 2);
                imageView.setX(VIEWER_WIDTH - (j+1.5) * (side * 1.5) + pieceImg.getWidth() * resizeRatio / 2);
                root.getChildren().add(imageView);
            }
        }

        // Get unclaimed resources
        List<List<String>> resources = BlueLagoon.getUnclaimedResources(stateString);
        System.out.println(resources);
        // Show unclaimed resources
        for (int i = 0; i < 5; i++) {
            if (resources.get(i).size() > 0) {
                String resourcePath = currentPath + "/gui/assets/resources/";
                resourcePath += (switch (i) {
                    case 0 -> "coconut";
                    case 1 -> "bamboo";
                    case 2 -> "water";
                    case 3 -> "preciousStone";
                    default -> "statuette";
                } + ".png");
                Image resourceImg = new Image(new FileInputStream(resourcePath));

                for (String coordinate : resources.get(i)) {
                    int x = new Position(coordinate).getX();
                    int y = new Position(coordinate).getY();
                    ImageView imageView = new ImageView(resourceImg);
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(resourceImg.getHeight() * resizeRatio);

                    double layoutX;
                    if (x % 2 == 0)
                        layoutX = centerX + ((y - center) * 2 + 1) * halfWidth;
                    else
                        layoutX = centerX + ((y - center) * 2) * halfWidth;
                    double layoutY = centerY + ((x - center) * 1.5) * side;
                    imageView.setX(layoutX - (resourceImg.getWidth() * resizeRatio) / 2);
                    imageView.setY(layoutY - (resourceImg.getHeight() * resizeRatio) / 2);
                    root.getChildren().add(imageView);
                }
            }
        }

        // Get player statements
        List<List<String>> placed = BlueLagoon.getPlaced(stateString);
        int playerNum = BlueLagoon.getPlayerNumber(stateString);
        for (int i = 0; i < placed.size(); i++) {
            int playerId = i / 2;
            for (String coordinate : placed.get(i)) {
                int x = new Position(coordinate).getX();
                int y = new Position(coordinate).getY();

                int pieceType = 0;
                if (i % 2 == 1)
                    pieceType = 2;
                else if (terrains[x][y] > 0)
                    pieceType = 1;

                String piecePath = Viewer.getPiecePath(playerId, pieceType);

                Image pieceImg = new Image(new FileInputStream(piecePath));
                ImageView imageView = new ImageView(pieceImg);
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(pieceImg.getHeight() * resizeRatio);

                double layoutX;
                if (x % 2 == 0)
                    layoutX = centerX + ((y - center) * 2 + 1) * halfWidth;
                else
                    layoutX = centerX + ((y - center) * 2) * halfWidth;
                double layoutY = centerY + ((x - center) * 1.5) * side;
                imageView.setX(layoutX - (pieceImg.getWidth() * resizeRatio) / 2);
                imageView.setY(layoutY - (pieceImg.getHeight() * resizeRatio) / 2);
                root.getChildren().add(imageView);
            }
        }


        // FIXME Task 5
    }

    /**
     * Draw a stone circle (a series of stones) at given position
     *
     * @param x given x position in the viewer
     * @param y given y position in the viewer
     * @param halfWidth half width of the hexagons in the map
     */
    public static void drawStoneCircle(double x, double y, double halfWidth, Group root) {
        Random random = new Random();
        double radR = halfWidth * 0.8;  // Radius of the circle
        double radS = halfWidth * 0.1;  // Radius of stones
        // Draw 8 stones on the edge of the circle
        for (int i = 0; i < 8; i++) {
            Circle circle = new Circle(x+(radR * (1 + 0.15 * random.nextFloat(-1.0f, 1.0f)) * Math.sin(Math.PI/4.0 * i)),
                                        y+(radR * (1 + 0.15 * random.nextFloat(-1.0f, 1.0f)) * Math.cos(Math.PI/4.0 * i)),
                                        radS * (1 + 0.1 * random.nextFloat(-1.0f, 1.0f)));
            circle.setFill(Color.LIGHTGRAY);
            root.getChildren().add(circle);
        }
    }

    public static double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label playerLabel = new Label("Game State:");
        stateTextField = new TextField();
        stateTextField.setPrefWidth(200);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    displayState(stateTextField.getText());
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(playerLabel, stateTextField, button);
        hb.setSpacing(10);
        hb.setLayoutX(50);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Blue Lagoon Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(controls);

        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
