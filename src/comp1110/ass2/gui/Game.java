package comp1110.ass2.gui;

import comp1110.ass2.BlueLagoon;
import comp1110.ass2.Position;
import comp1110.ass2.Resource;
import comp1110.ass2.board.Board;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Paul Scott on 13/3/2023
 * Modified by Yufei Huang on 19/5/2023
 */
// FIXME Task 14
// FIXME Task 15
public class Game extends Application {
    private final Group root = new Group();
    private TextField stateTextField;
    private Group controls = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;

    void displayState(String stateString, boolean isAiGame) throws FileNotFoundException {
        // AI Move (Skip display)
        if (isAiGame && BlueLagoon.getCurrentPlayerId(stateString) != 0) {
            if (BlueLagoon.getPhase(stateString) == 'E' || !BlueLagoon.isPhaseOver(stateString)) {
                String newStateString = BlueLagoon.applyMove(stateString, BlueLagoon.generateAIMove(stateString));
                root.getChildren().clear();
                displayState(newStateString, true);
                return;
            }
        }

        int height = Integer.parseInt(stateString.split(" ", 3)[1]);     // Size of the map (max width is equal to height)
        double side = Math.round((WINDOW_HEIGHT - 150) / ((height - 1) * 1.5 + 2));   // Length of hexagon side
        double halfWidth = side * Math.sin(Math.PI / 3.0);
        int center = height / 2;    // Center hexagon
        double centerX = WINDOW_WIDTH / 2.0;
        double centerY = WINDOW_HEIGHT / 2.0;

        // Create new board for display
        Board board = new Board(stateString);
        System.out.println(stateString);

        // Generate all moves
        List<String> moves = new ArrayList<>(BlueLagoon.generateAllValidMoves(stateString));

        // Show current phase on top middle
        Label phase;
        // Show leaderboard if game is over
        if (board.getPhase() == 'S' && BlueLagoon.isPhaseOver(stateString)) {
            phase = new Label("End Game");
            end(BlueLagoon.getScores(stateString));
        }
        else {
            phase = new Label(switch (board.getPhase()) {
                case 'E' -> "Explore";
                case 'S' -> "Settlement";
                default -> throw new IllegalStateException("Unexpected value: " + board.getPhase());
            });
        }
        phase.setFont(new Font(30));
        phase.setTextFill(Color.LIGHTYELLOW);
        phase.setLayoutX(centerX - 50);
        phase.setLayoutY(10);
        root.getChildren().add(phase);

        // Show the map on scene
        controls.getChildren().clear();
        root.getScene().setFill(Color.NAVY);

        // Draw hexagons and edges
        List<Hexagon> hexes = new ArrayList<>();
        List<Hexagon> edges = new ArrayList<>();
        for (int i = 0; i < height; i++)
            for (int j = 0; j < height; j++) {
                if (board.spotMatrix[i][j] != null && board.spotMatrix[i][j].getTerrain() >= 0) {
                    // Position of current hexagon on the scene
                    double layoutX, layoutY;
                    if (i % 2 == 0)
                        layoutX = centerX + ((j - center) * 2 + 1) * halfWidth;
                    else
                        layoutX = centerX + ((j - center) * 2) * halfWidth;
                    layoutY = centerY + ((i - center) * 1.5) * side;

                    // Draw white hexagon edge (expand)
                    Hexagon edge = new Hexagon(layoutX, layoutY, side * 1.05, Color.WHITE, i, j); // Add white edge to hexagon
                    edges.add(edge);
                    root.getChildren().add(edge);

                    // Draw hexagon based on terrain (shrink)
                    Color color = board.spotMatrix[i][j].getTerrain() > 0 ? Color.GREEN : Color.DEEPSKYBLUE; // Determine color by terrain
                    Hexagon hexagon = new Hexagon(layoutX, layoutY, side * 0.95, color, i, j);
                    hexes.add(hexagon);
                    root.getChildren().add(hexagon);

                    // Draw stone circle on top
                    if (board.spotMatrix[i][j].getTerrain() == 2)
                        drawStoneCircle(layoutX, layoutY, halfWidth, root);
                }
            }

        double resizeRatio = side / 100.0;      // Resize pieces and resources to match the size of hexagon

        // Get unclaimed resources
        List<List<String>> resources = BlueLagoon.getUnclaimedResources(stateString);
        System.out.println(resources);

        // Show unclaimed resources
        for (int resource = 0; resource < 5; resource++) {
            if (resources.get(resource).size() > 0) {
                // Get corresponding image
                String resourcePath = Viewer.getResourcePath(resource);
                Image resourceImg = new Image(new FileInputStream(resourcePath));

                // Show resource on given coordinate
                for (String coordinate : resources.get(resource)) {
                    int x = new Position(coordinate).getX();
                    int y = new Position(coordinate).getY();
                    ImageView imageView = new ImageView(resourceImg);
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(resourceImg.getHeight() * resizeRatio);

                    // Position of current hexagon on scene
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

        // Get placed pieces
        List<List<String>> placed = BlueLagoon.getPlaced(stateString);
        for (int i = 0; i < placed.size(); i++) {
            int playerId = i / 2;   // Two lines for one player (settler and village)
            for (String coordinate : placed.get(i)) {
                int x = new Position(coordinate).getX();
                int y = new Position(coordinate).getY();

                int pieceType = 0;  // Settler boat
                if (i % 2 == 1)
                    pieceType = 2;  // Village
                else if (board.spotMatrix[x][y].getTerrain() > 0)
                    pieceType = 1;  // Settler land

                // Get corresponding image
                String piecePath = Viewer.getPiecePath(playerId, pieceType);
                Image pieceImg = new Image(new FileInputStream(piecePath));
                ImageView imageView = new ImageView(pieceImg);
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(pieceImg.getHeight() * resizeRatio);

                // Position of current hexagon on scene
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

        // Show available pieces and claimed resources for each player
        for (int playerId = 0; playerId < board.getPlayerNum(); playerId++){
            // Settler (land) and village pieces
            for (int pieceType = 1; pieceType <= 2; pieceType++) {
                // Get corresponding image
                String piecePath = Viewer.getPiecePath(playerId, pieceType);
                Image pieceImg = new Image(new FileInputStream(piecePath));
                ImageView imageView = new ImageView(pieceImg);
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(pieceImg.getHeight() * resizeRatio);
                imageView.setOpacity(0.5);      // Distinct from draggable pieces
                imageView.setLayoutX(WINDOW_WIDTH * 0.8 * (playerId % 2) + 20 + 100 * (pieceType - 1));
                imageView.setLayoutY(WINDOW_HEIGHT * 0.5 * (playerId / 2) + side - pieceImg.getHeight() * resizeRatio / 2);

                // Don't show if not available
                if ((pieceType == 1 && board.players[playerId].getSettlersAvailable() > (playerId == board.getCurrentPlayer() ? 1 : 0))
                        || (pieceType == 2 && board.players[playerId].getVillagesAvailable() > (playerId == board.getCurrentPlayer() ? 1 : 0))) {
                    root.getChildren().add(imageView);
                }

                // Text label showing pieces available (bottom right corner)
                Label available;
                if (pieceType == 1)
                    available = new Label(Integer.toString(board.players[playerId].getSettlersAvailable()));
                else
                    available = new Label(Integer.toString(board.players[playerId].getVillagesAvailable()));
                available.setLayoutX(imageView.getLayoutX() + pieceImg.getWidth() * resizeRatio);
                available.setLayoutY(imageView.getLayoutY() + pieceImg.getHeight() * resizeRatio * 0.5);
                available.setFont(new Font(20));
                available.setTextFill(Color.WHITESMOKE);
                root.getChildren().add(available);
            }

            // Claimed resources
            for (int resource = 0; resource < 5; resource++) {
                for (int i = 0; i < board.players[playerId].getClaimedResources(resource) ; i++){
                    // Get corresponding image
                    String resourcePath = Viewer.getResourcePath(resource);
                    Image resourceImg = new Image(new FileInputStream(resourcePath));
                    ImageView imageView = new ImageView(resourceImg);
                    imageView.setPreserveRatio(true);
                    double fixedHeight = resourceImg.getHeight() * 0.25;    // Not change with map size
                    imageView.setFitHeight(fixedHeight);
                    imageView.setLayoutX(WINDOW_WIDTH * 0.8 * (playerId % 2) + 25 * (i + 1) - resourceImg.getWidth() * 0.125);
                    imageView.setLayoutY(WINDOW_HEIGHT * 0.5 * (playerId / 2) + resource * 35 + side * 2 + 20 - fixedHeight * 0.5);
                    root.getChildren().add(imageView);
                }
            }

            // Scores
            Label score = new Label("Score: " + board.players[playerId].getScore());
            score.setLayoutX(WINDOW_WIDTH * 0.8 * (playerId % 2) + 25 );
            score.setLayoutY(WINDOW_HEIGHT * 0.5 * (playerId / 2) + 180 + side * 2 + 20);
            score.setFont(new Font(24));
            score.setTextFill(Color.WHITESMOKE);
            root.getChildren().add(score);
        }

        // Show Draggable
        int playerId = board.getCurrentPlayer();

        // Find all hexagons representing valid moves for settlers and villages
        List<Position> settlerMoves = new ArrayList<>();
        List<Position> villageMoves = new ArrayList<>();
        List<Hexagon> settlerHexes = new ArrayList<>();
        List<Hexagon> villageHexes = new ArrayList<>();
        for (String move : moves) {
            if (move.charAt(0) == 'S') {
                settlerMoves.add(new Position(move.substring(2)));
            }
            else {
                villageMoves.add(new Position(move.substring(2)));
            }
        }
        // Add to list
        for (Hexagon hexagon : hexes) {
            if (settlerMoves.contains(hexagon.position))
                settlerHexes.add(hexagon);
            if (villageMoves.contains(hexagon.position))
                villageHexes.add(hexagon);
        }

        // Make draggable settler
        Image settlerImg = new Image(new FileInputStream((Viewer.getPiecePath(playerId, 1))));
        DraggablePiece settlerDraggable = new DraggablePiece(settlerImg,
                WINDOW_WIDTH * 0.8 * (playerId % 2) + 20,
                WINDOW_HEIGHT * 0.5 * (playerId / 2) + side - settlerImg.getHeight() * resizeRatio / 2,
                side, settlerHexes);
        if (board.players[playerId].getSettlersAvailable() > 0) {
            root.getChildren().add(settlerDraggable);
        }

        // Make draggable village
        Image villageImg = new Image(new FileInputStream((Viewer.getPiecePath(playerId, 2))));
        DraggablePiece villageDraggable = new DraggablePiece(villageImg,
                WINDOW_WIDTH * 0.8 * (playerId % 2) + 120,
                WINDOW_HEIGHT * 0.5 * (playerId / 2) + side - villageImg.getHeight() * resizeRatio / 2,
                side, villageHexes);
        if (board.getPhase() == 'E' && board.players[playerId].getVillagesAvailable() > 0) {
            root.getChildren().add(villageDraggable);
        }

        // Buttons for in game controls at bottom
        // Confirm move input
        Button confirm = new Button("Confirm");
        confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    String newStateString;
                    if (settlerDraggable.getNearest() != null) {
                        newStateString = BlueLagoon.applyMove(stateString, "S " + settlerDraggable.getNearest().position.toString());
                    }
                    else if (villageDraggable.getNearest() != null) {
                        newStateString = BlueLagoon.applyMove(stateString, "T " + villageDraggable.getNearest().position.toString());
                    }
                    else {
                        newStateString = stateString;
                    }
                    root.getChildren().clear();
                    displayState(newStateString, isAiGame);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        confirm.setPrefWidth(100);
        confirm.setLayoutX((WINDOW_WIDTH - confirm.getPrefWidth()) / 2.0 - 120);
        confirm.setLayoutY(WINDOW_HEIGHT - 50);

        // AI generated move
        Button aiMove = new Button("HELP");
        aiMove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    String newStateString = BlueLagoon.applyMove(stateString, BlueLagoon.generateAIMove(stateString));
                    root.getChildren().clear();
                    displayState(newStateString, isAiGame);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        aiMove.setPrefWidth(100);
        aiMove.setLayoutX((WINDOW_WIDTH - aiMove.getPrefWidth()) / 2.0);
        aiMove.setLayoutY(WINDOW_HEIGHT - 50);

        // Random move
        Button randomMove = new Button("Random");
        randomMove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    String newStateString = BlueLagoon.applyMove(stateString, BlueLagoon.generateRandomMove(stateString));
                    root.getChildren().clear();
                    displayState(newStateString, isAiGame);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        randomMove.setPrefWidth(100);
        randomMove.setLayoutX((WINDOW_WIDTH - randomMove.getPrefWidth()) / 2.0 + 120);
        randomMove.setLayoutY(WINDOW_HEIGHT - 50);

        root.getChildren().addAll(aiMove, confirm, randomMove);

        // Back to main menu button
        Button mainMenu = new Button("Main Menu");
        mainMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                root.getChildren().clear();
                root.getScene().setFill(Color.WHITE);
                root.getChildren().add(controls);
                makeMainControls();
            }
        });
        mainMenu.setPrefWidth(100);
        mainMenu.setLayoutX(WINDOW_WIDTH - 105);
        mainMenu.setLayoutY(WINDOW_HEIGHT - 30);
        root.getChildren().add(mainMenu);
    }

    public static void drawStoneCircle(double x, double y, double halfWidth, Group root) {
        double radR = halfWidth * 0.8;  // Radius of the circle
        double radS = halfWidth * 0.1;  // Radius of stones
        // Draw 8 stones on the edge of the circle
        for (int i = 0; i < 8; i++) {
            Circle circle = new Circle(x+(radR * Math.sin(Math.PI/4.0 * i)), y+(radR* Math.cos(Math.PI/4.0 * i)), radS);
            circle.setFill(Color.LIGHTGRAY);
            root.getChildren().add(circle);
        }
    }


    private void makeMainControls() {
        // Main menu
        // Background
        controls.getScene().setFill(Color.ALICEBLUE);

        // Title and credits
        Label titleLine1 = new Label("BLUE");
        Label titleLine2 = new Label("LAGOON");
        titleLine1.setAlignment(Pos.CENTER);
        titleLine2.setAlignment(Pos.CENTER);
        titleLine1.setFont(new Font(64));
        titleLine2.setFont(new Font(48));
        titleLine1.setTextFill(Color.NAVY);
        titleLine2.setTextFill(Color.DARKGOLDENROD);

        Label name1 = new Label("Yufei Huang");
        Label studentId1 = new Label("u7618869");
        Label name2 = new Label("Zeqi Gao");
        Label studentId2 = new Label("u7591163");

        VBox title = new VBox();
        title.getChildren().addAll(titleLine1, titleLine2, name1, studentId1, name2, studentId2);
        title.setMaxWidth(400);
        title.setLayoutX(200);
        title.setLayoutY(WINDOW_HEIGHT / 4.0);
        controls.getChildren().add(title);

        // List of different games and players
        // Start new games
        List<Button> buttons = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 1; j <= 4; j++) {
                Button button = new Button(switch (i) {
                    default -> "DEFAULT_GAME";
                    case 1 -> "WHEELS_GAME";
                    case 2 -> "FACE_GAME";
                    case 3 -> "SIDES_GAME";
                    case 4 -> "SPACE_INVADERS_GAME";
                } + " " + (j == 1 ? "Versus AI" : j + " Players"));     // Game for 1 to 4 players (1 player means AI game)
                boolean isAiGame = (j == 1);
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        try {
                            displayState(BlueLagoon.distributeResources(BlueLagoon.getGame(button.getText())), isAiGame);
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                buttons.add(button);
            }
        }

        // Start from customize state
        stateTextField = new TextField();
        stateTextField.setPrefWidth(500);
        Button refresh = new Button("Refresh");
        refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    displayState(stateTextField.getText(), false);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(stateTextField, refresh);
        hb.setSpacing(10);

        VBox vb = new VBox();
        vb.getChildren().addAll(buttons);
        vb.getChildren().add(hb);
        vb.setSpacing(5);
        vb.setLayoutX(WINDOW_WIDTH / 2.0);
        vb.setLayoutY(50);
        controls.getChildren().add(vb);
    }

    public void end(int[] scores) {
        // Pop up leaderboard
        Group leaderBoard = new Group();
        List<Label> scoreStrings = new ArrayList<>();

        // Highlight the winner
        int best = 0;
        List<Integer> winners = new ArrayList<>();
        for (int playerId = 0; playerId < scores.length; playerId++) {
            if (scores[playerId] > best) {
                best = scores[playerId];
                winners.clear();
                winners.add(playerId);
            }
            else if (scores[playerId] == best)
                winners.add(playerId);
            Label scoreString = new Label("Player " + (playerId + 1) + ": " + scores[playerId]);
            scoreString.setFont(new Font(30));
            scoreString.setAlignment(Pos.CENTER);
            scoreStrings.add(scoreString);
        }
        for (int winner : winners)
            scoreStrings.get(winner).setTextFill(Color.DEEPSKYBLUE);

        VBox vb = new VBox();
        vb.setPrefWidth(200);
        vb.setPrefHeight(150);
        vb.setLayoutX((400 - vb.getPrefWidth()) / 2);
        vb.setLayoutY((300 - vb.getPrefHeight()) / 2);
        vb.getChildren().addAll(scoreStrings);
        leaderBoard.getChildren().add(vb);

        Scene scene = new Scene(leaderBoard, 400, 300);

        Stage stage = new Stage();
        stage.setTitle("Leader Board");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Blue Lagoon Game");
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);

        root.getChildren().add(controls);
        makeMainControls();

        stage.setScene(scene);
        stage.show();
    }
}
