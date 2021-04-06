// Importing necessary classes

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 150119007 Ahmet Hakan Beşel  150119004 Semir Tatlı
 * Main class of the game
 */
public class Main extends Application {
    // Constant values of game
    final static String GAME_TITLE = "Tile Game";
    final double VBOX_WIDTH = 250;
    final double WINDOW_HEIGHT = 800;
    final double WINDOW_WIDTH = 800;
    final String BACKGROUND_COLOR = "#dae1e7";
    final String BUTTON_COLOR = "#6983aa";
    // Primary stage for JavaFX
    private Stage PRIMARY_STAGE;
    // Player object
    private Player player;

    /**
     * Starts main game window
     * @param PRIMARY_STAGE is main (and only) stage object of the game
     */
    @Override
    public void start(Stage PRIMARY_STAGE) {
        this.PRIMARY_STAGE = PRIMARY_STAGE;
        // Creating a new Player
        this.player = new Player();
        // The game windows size will be constant
        PRIMARY_STAGE.setResizable(false);
        // Opening main menu with a method
        mainMenu();
    }

    /**
     * Shows main menu on current stage
     */
    private void mainMenu() {
        // Setting the title of window
        PRIMARY_STAGE.setTitle(GAME_TITLE);
        // Start button for opening the level menu
        Button btnStart = new Button("Start");
        // Setting the size of the button
        btnStart.setPrefSize(VBOX_WIDTH, 55);
        // Open level menu when clicking on the start button
        btnStart.setOnAction(e -> levelMenu());
        // Button for opening the information of how to play this game
        Button btnHowToPlay = new Button("How to Play");
        // Setting the size of the button
        btnHowToPlay.setPrefSize(VBOX_WIDTH, 55);
        // Open How To Play screen when clicking to button
        btnHowToPlay.setOnAction(e -> howToPlay());
        // Button for the text about who made this game
        Button btnCredits = new Button("Credits");
        // Setting the size of the button
        btnCredits.setPrefSize(VBOX_WIDTH, 55);
        // Open Credits screen when clicking to button
        btnCredits.setOnAction(e -> credits());
        // Button for closing the game
        Button btnExit = new Button("Exit");
        // Setting the size of the button
        btnExit.setPrefSize(VBOX_WIDTH, 55);
        // Closing the game when clicking the exit button
        btnExit.setOnAction(event -> System.exit(1));
        // Stackpane for adding all VBox's to the window
        StackPane root = new StackPane();
        // VBox on the top of the screen for game title
        VBox body = bodyVBox();
        // Putting all the buttons on the center
        body.getChildren().addAll(btnStart, btnHowToPlay, btnCredits, btnExit);
        for (Node node : body.getChildren()) {
            if (node instanceof Button) {
                node.setStyle("-fx-text-fill: white;-fx-font-weight:bold;-fx-font-size:150%;-fx-background-color:" + BUTTON_COLOR);
            }
        }
        // Adding scene to the stage
        root.getChildren().addAll(headVBox(GAME_TITLE), body);
        changeScene(root);
    }

    /**
     * Shows how-to-play guide
     */
    private void howToPlay() {
        defaultScene("How to Play", "Click to tile which you want to move then click to target.\nYou can move only brown tiles to empty (grey) spaces.\nTry to complete path starting from blue tile to red tile.");
    }

    /**
     * Shows credits
     */
    private void credits() {
        defaultScene("Credits", "Semir Tatlı 150119004\n\nAhmet Hakan Beşel 150119007");
    }

    /**
     * Creates a VBox for heading elements such as level title etc.
     * @param title String value for the label
     * @return VBox object
     */
    private VBox headVBox(String title) {
        VBox head = new VBox();
        Label label = new Label(title);
        // Setting the title style
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold");
        head.getChildren().add(label);
        // Setting VBox to the Center of the top of the screen
        head.setAlignment(Pos.TOP_CENTER);
        // Some space for VBox
        head.setPadding(new Insets(30, 100, 25, 100));
        return head;
    }

    /**
     * Creates a VBox for middle elements such as level choosing buttons etc.
     * @return VBox object
     */
    private VBox bodyVBox() {
        VBox body = new VBox();
        // Setting the VBox size
        body.setPrefWidth(VBOX_WIDTH);
        // Setting the VBox's position
        body.setAlignment(Pos.CENTER);
        // Some space for Center VBox
        body.setSpacing(25);
        return body;
    }

    /**
     * Creates a VBox for bottom elements such as back button
     * @return VBox object
     */
    private VBox footerVBox() {
        VBox footer = new VBox();
        // Back button for turning back to the main menu
        Button btnBack = new Button("Back");
        // Setting the size of the button
        btnBack.setPrefSize(VBOX_WIDTH, 35);
        btnBack.setStyle("-fx-text-fill:white; -fx-background-color:" + BUTTON_COLOR);
        // Turning back to the main menu when clicking to the back button
        btnBack.setOnAction(e -> mainMenu());
        footer.getChildren().add(btnBack);
        // Setting its size
        footer.setPrefWidth(VBOX_WIDTH);
        // Setting its position
        footer.setAlignment(Pos.BOTTOM_CENTER);
        // Giving space for VBox
        footer.setPadding(new Insets(0, 0, 10, 0));
        footer.setSpacing(25);
        return footer;
    }

    /**
     * Shows a text with given title in current window
     * @param sceneTitle String for title
     * @param bodyText   String for main text
     */
    private void defaultScene(String sceneTitle, String bodyText) {
        // Setting the title of the window
        PRIMARY_STAGE.setTitle(GAME_TITLE + " | " + sceneTitle);
        // Vbox of middle content
        VBox body = bodyVBox();
        // Adding the text and setting its style
        Text text = new Text(bodyText);
        text.setStyle("-fx-font-size: 30px; -fx-text-alignment: center; -fx-wrap-text: true");
        // Add the text to VBox
        body.getChildren().add(text);
        // Stackpane for adding VBox's to the screen
        StackPane root = new StackPane();
        // Adding elements to stack pane
        root.getChildren().addAll(headVBox(sceneTitle), body, footerVBox());
        changeScene(root);
    }

    /**
     * Changes scene in current stage
     * @param rootPane is pane object which includes all titles, buttons etc.
     */
    private void changeScene(StackPane rootPane) {
        // Creating a scene object
        rootPane.setStyle("-fx-background-color:" + BACKGROUND_COLOR);
        Scene scene = new Scene(rootPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        PRIMARY_STAGE.setScene(scene);
        // Displaying the contents of the stage
        PRIMARY_STAGE.show();
    }

    /**
     * Shows level choosing menu
     */
    public void levelMenu() {
        // Setting the title of the window
        PRIMARY_STAGE.setTitle(GAME_TITLE);
        VBox body = bodyVBox();
        // For loop for buttons of the levels in the main menu
        GridPane levelGrid = new GridPane();
        levelGrid.setAlignment(Pos.CENTER);
        levelGrid.setHgap(100);
        levelGrid.setVgap(50);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                int level = (2 * i) + j + 1;
                if (level <= Level.getLevels().length) {
                    Button btnLevel = new Button(level + "");
                    btnLevel.setPrefSize(100, 100);
                    btnLevel.setStyle("-fx-background-color: #27496d; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 300%;");
                    if (player.isPlayable(level)) {
                        // Opening the level
                        btnLevel.setOnAction(e -> showLevel(level));
                    } else {
                        btnLevel.setText("\uD83D\uDD12" + btnLevel.getText());
                        btnLevel.setDisable(true); // If level is not unlocked disable button
                    }
                    levelGrid.add(btnLevel, j, i);
                }
            }
        }
        body.getChildren().add(levelGrid);
        StackPane root = new StackPane();
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(headVBox("Choose a Level"));
        borderPane.setCenter(body);
        borderPane.setBottom(footerVBox());
        root.getChildren().addAll(borderPane);
        changeScene(root);
    }

    /**
     * Shows game board of given level on stage
     * @param level is integer value for level number
     */
    public void showLevel(int level) {
        // Set title of the window
        PRIMARY_STAGE.setTitle(GAME_TITLE + " | Level " + level);
        // Opening the board of the current level
        Board currentBoard = new Board(level);
        currentBoard.Main = this;
        // Turning back to level menu
        Button btnBack = new Button();
        btnBack.setText("Back");
        btnBack.setPrefSize(VBOX_WIDTH, 35);
        btnBack.setOnAction(e -> levelMenu());
        btnBack.setStyle("-fx-text-fill:white; -fx-background-color:" + BUTTON_COLOR);
        // Show number of moves
        Text numberOfMoves = new Text("Moves: " + currentBoard.getNumberOfMoves());
        // Stackpane for adding all the things to the screen
        StackPane root = new StackPane();
        // VBox for the text that will be on the center of the screen
        VBox body = bodyVBox();
        // Adding title board and back button to the VBox's
        body.getChildren().addAll(currentBoard, numberOfMoves);
        // Adding VBox's to the Border Pane
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(headVBox("Level " + level));
        borderPane.setCenter(body);
        VBox footer = footerVBox();
        footer.getChildren().set(0, btnBack);
        borderPane.setBottom(footer);
        root.getChildren().addAll(borderPane);
        changeScene(root);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public static void main(String[] args) {
        launch(args);
    }
}