// Import necessary classes

import javafx.animation.PathTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Optional;

/**
 * 150119007 Ahmet Hakan Beşel  150119004 Semir Tatlı
 * Board class for showing tiles in GridPane And includes required functions for
 * game playability
 */
public class Board extends GridPane {
    // File name of level
    private int level;
    // Level object for current level which showing on the board
    public Level currentLevel;
    // For storing clicked tiles
    private Tile[] clickedTiles = new Tile[2];
    // Number of moves
    private int numberOfMoves = 0;
    // Array for storing tiles which on the board
    private final Tile[] tiles = new Tile[16];
    private final ArrayList<Tile> pipes = new ArrayList<>();
    private int a = -1;
    private int b = -1;
    // Ball
    private Circle ball;
    // Main class of the game
    public Main Main;
    // Animation duration
    private final static int DURATION = 5000;

    // Board's constructor with levelFile argument
    public Board(int level) {
        // Assigning the level file
        this.level = level;
        String levelFile = "level" + level + ".txt";
        // Creating current level
        this.currentLevel = new Level(levelFile);
        // Creating the current level board
        int n = 0;
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Tile currentTile = new Tile(this.currentLevel.getTile(n)[0], this.currentLevel.getTile(n)[1],
                        this.currentLevel.getTile(n)[2]);

                if (currentTile.getTileType() != 0) {
                    count++;
                }
                tiles[n] = currentTile;

                if ((currentTile.getTileType() == 0
                        && (currentTile.getTileDirection() == 3 || currentTile.getTileDirection() == 0))
                        || currentTile.getTileType() == 3) {
                    currentTile.setOnMousePressed(e -> mousePressed(currentTile));
                }
                this.add(currentTile, j, i);
                n++;
            }
        }
        // Invoke createBall method
        createBall();
        // Setting the size of the board
        this.setPrefSize(600, 600);
        // Adding space to the board
        this.setPadding(new Insets(0, 100, 0, 100));
        this.setAlignment(Pos.CENTER);
    }

    private void createBall() {
        // Drawing a Sphere
        this.ball = new Circle(0, 0, 30);
        ball.setTranslateY(((findStartTile().getTileId() - 1) / 4) * 150 - 15);
        ball.setTranslateX(((findStartTile().getTileId() - 1) % 4) * 150 + 45);
        ball.setFill(Color.WHITE);
        ball.setStyle("-fx-opacity: 0.75");
        // Add ball to starting tile
        getChildren().add(ball);
        // this.add(ball, 0, 0);
    }

    // Finds the start tile in tiles array
    public Tile findStartTile() {
        int n = -1;
        for (int i = 0; i < 16; i++) {
            if (tiles[i].getTileType() == 1) {
                n = i;
                break;
            }
        }
        if (n < 16 && n > -1) {
            return tiles[n];
        } else {
            System.out.println("Start tile cannot found");
            return null;
        }
    }

    private void mousePressed(Tile tile) {
        // Change opacity of tile if it is clicked
        tile.setOpacity(tile.getOpacity() == 1.0 ? 0.5 : 1.0);
        // set clicked tiles array when a tile clicked
        if (clickedTiles[0] == null) {
            clickedTiles[0] = tile;
            for (int i = 0; i < 16; i++) {
                if (clickedTiles[0].getTileId() == i + 1) {
                    a = i;
                }
            }
        } else if (clickedTiles[1] == null) {
            clickedTiles[1] = tile;
            for (int i = 0; i < 16; i++) {
                if (clickedTiles[1].getTileId() == i + 1) {
                    b = i;
                }
            }
        }
        // swap tiles if both clicked tiles are full
        if (clickedTiles[0] != null && clickedTiles[1] != null) {
            if (checkTileTypes(clickedTiles[0], clickedTiles[1])) {
                // change tiles
                swapTiles();
            } else {
                // pop-up in a wrong move
                showAlert();
            }
            // reset clicked tiles
            resetClickedTiles();
        }
    }

    private void resetClickedTiles() {
        if (clickedTiles[0] != null) {
            clickedTiles[0].setOpacity(1.0);
            clickedTiles[0] = null;
        }
        if (clickedTiles[1] != null) {
            clickedTiles[1].setOpacity(1.0);
            clickedTiles[1] = null;
        }
    }

    private void swapTiles() {
        int x1 = getColumnIndex(clickedTiles[0]);
        int y1 = getRowIndex(clickedTiles[0]);
        int x2 = getColumnIndex(clickedTiles[1]);
        int y2 = getRowIndex(clickedTiles[1]);
        if (checkTileTypes(clickedTiles[0], clickedTiles[1]) && checkTileCoordinates(x1, y1, x2, y2)) {
            setColumnIndex(clickedTiles[0], x2);
            setColumnIndex(clickedTiles[1], x1);
            setRowIndex(clickedTiles[0], y2);
            setRowIndex(clickedTiles[1], y1);
            tiles[a] = clickedTiles[1];
            tiles[b] = clickedTiles[0];
            tiles[a].setTileId(y1 * 4 + x1 + 1);
            tiles[b].setTileId(y2 * 4 + x2 + 1);
            tiles[a].setTileType(clickedTiles[1].getTileType());
            tiles[b].setTileType(clickedTiles[0].getTileType());
            tiles[a].setTileDirection(clickedTiles[1].getTileDirection());
            tiles[b].setTileDirection(clickedTiles[0].getTileDirection());
            this.numberOfMoves++;
            ((VBox) this.getParent()).getChildren().set(1, new Text("Moves: " + numberOfMoves));
            if (findWay(findStartTile())) {
                Player player = Main.getPlayer();
                player.addPlayedLevel(level);
                System.out.printf("Level %s Completed\n", level);
                moveBall();
                showInfo();
            }
        } else {
            showAlert();
        }
    }

    private boolean checkTileTypes(Tile firstTile, Tile secondTile) {
        // Both tiles can not be static
        boolean isFirstMovable = firstTile.getTileType() == 3
                || (firstTile.getTileType() == 0 && firstTile.getTileDirection() == 0);
        boolean isSecondMovable = secondTile.getTileDirection() == 3 && secondTile.getTileType() == 0;
        return (isFirstMovable && isSecondMovable);
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Illegal Move");
        alert.setHeaderText("Please try again");
        alert.setContentText("You can move only brown tiles to grey cells.");
        alert.show();
    }

    private void showInfo() {
        resetClickedTiles();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Congratulations");
        alert.setHeaderText(String.format("Level %s Completed", level));
        alert.setContentText("You will be redirected to the next level.");
        alert.initStyle(StageStyle.UNDECORATED);
        Optional<ButtonType> result = alert.showAndWait();
        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            showNextLevel();
        }
    }

    private void showNextLevel() {
        if (level + 1 <= Level.getLevels().length) {
            Main.showLevel(level + 1);
        } else if (level == Level.getLevels().length) {
            Main.levelMenu();
        }
    }

    private void moveBall() {
        // Create a new path for ball animation
        Path path = new Path();
        // Declare starting point
        MoveTo start = new MoveTo(ball.getTranslateX(), ball.getTranslateY());
        path.getElements().add(start);
        LineTo lastPath = null;
        for (Tile tile : this.pipes) {
            if (tile.getTileType() != 0 && tile.getTileType() != -1) {
                int tileDirection = tile.getTileDirection();
                // Coordinates of current tile
                int x = getColumnIndex(tile) + 1;
                int y = getRowIndex(tile) + 1;
                // Get current position of the ball
                double xBall = ball.getTranslateX();
                double yBall = ball.getTranslateY();
                switch (tileDirection) {
                    case 1: // Vertical
                        if (tile.getTileType() == 2) {
                            lastPath = new LineTo(xBall + ((x - 1) * 150), yBall + (y - 1) * 75 + 75);
                        } else {
                            path.getElements().add(new LineTo(xBall + ((x - 1) * 150), yBall + (y - 1) * 150));
                        }
                        break;
                    case 2: // Horizontal
                        if (tile.getTileType() == 2) {
                            lastPath = new LineTo(xBall + (x * 150) - 140, ((y - 1) * 150));
                        } else {
                            path.getElements().add(new LineTo(xBall + x * 150, ((y - 1) * 150)));
                        }
                        break;
                    case 4: // _|
                        path.getElements().add(new LineTo(xBall + (x - 1) * 150, ((y - 1) * 150)));
                        break;
                    case 5: // |_
                        path.getElements().add(new LineTo(xBall + (x - 1) * 150, ((y - 1) * 150)));
                        break;
                    case 6: // |¯
                        path.getElements().add(new LineTo(xBall + (x - 1) * 150, ((y - 1) * 150)));
                        break;
                    case 7: // ¯|
                        path.getElements().add(new LineTo(xBall + (x - 1) * 150, ((y - 1) * 150)));
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + tileDirection);
                }
            }
        }
        path.getElements().add(lastPath);
        PathTransition pathTransition = new PathTransition();
        // Setting the duration of the transition
        pathTransition.setDuration(Duration.millis(DURATION));
        // Setting the node for the transition
        pathTransition.setNode(ball);
        // Setting the path for the transition
        pathTransition.setPath(path);
        // Setting the cycle count for the transition
        pathTransition.setCycleCount(1);
        // Setting auto reverse value to true
        pathTransition.setAutoReverse(false);
        // Playing the animation
        pathTransition.play();
    }

    private boolean checkTileCoordinates(int x1, int y1, int x2, int y2) {
        /*
         * If coordinates of first tile x, y It can be move to these coordinates (x,
         * y-1), (x, y+1), (x-1, y), (x+1, y)
         */
        if (x2 == x1 && y2 == y1 - 1) {
            return true;
        } else if (x2 == x1 && y2 == y1 + 1) {
            return true;
        } else if (x2 == x1 - 1 && y2 == y1) {
            return true;
        } else
            return x2 == x1 + 1 && y2 == y1;
    }

    public boolean findWay(Tile tile) {
        // 0 for down, 1 for up, 2 for right, 3 for left
        int lastmove = 0;
        boolean loop = true;
        boolean solved = false;
        if (tile == null) {
            loop = false;
        }
        while (loop) {
            switch (tile.getTileDirection()) {
                // Empty - None
                case 0:
                    loop = false;
                    break;
                // Vertical
                case 1:
                    if (lastmove == 0) {
                        if ((tiles[tile.getTileId() + 3].getTileDirection() == 1)
                                || tiles[tile.getTileId() + 3].getTileDirection() == 4
                                || tiles[tile.getTileId() + 3].getTileDirection() == 5) {
                            pipes.add(tile);

                            tile = tiles[tile.getTileId() + 3];
                            lastmove = 0;
                        } else
                            loop = false;
                    }
                    if (lastmove == 1) {
                        if (tiles[tile.getTileId() - 5].getTileDirection() == 1
                                || tiles[tile.getTileId() - 5].getTileDirection() == 6
                                || tiles[tile.getTileId() - 5].getTileDirection() == 7) {
                            pipes.add(tile);

                            tile = tiles[tile.getTileId() - 5];
                            lastmove = 1;
                        } else
                            loop = false;
                    }
                    break;
                // Horizontal
                case 2:
                    if (lastmove == 2) {
                        if (tiles[tile.getTileId()].getTileDirection() == 2
                                || tiles[tile.getTileId()].getTileDirection() == 4
                                || tiles[tile.getTileId()].getTileDirection() == 7) {
                            pipes.add(tile);

                            tile = tiles[tile.getTileId()];
                            lastmove = 2;
                        } else
                            loop = false;
                    }
                    if (lastmove == 3) {
                        if (tiles[tile.getTileId() - 2].getTileDirection() == 2
                                || tiles[tile.getTileId() - 2].getTileDirection() == 4
                                || tiles[tile.getTileId() - 2].getTileDirection() == 7) {
                            pipes.add(tile);

                            tile = tiles[tile.getTileId() - 2];
                            lastmove = 3;
                        } else
                            loop = false;
                    }
                    break;
                // Empty - Free
                case 3:
                    loop = false;
                    break;
                // 00
                case 4:
                    if (lastmove == 0) {
                        if (tiles[tile.getTileId() - 2].getTileDirection() == 4
                                || tiles[tile.getTileId() - 2].getTileDirection() == 7
                                || tiles[tile.getTileId() - 2].getTileDirection() == 2) {
                            pipes.add(tile);

                            tile = tiles[tile.getTileId() - 2];
                            lastmove = 3;
                        } else
                            loop = false;
                    }
                    if (lastmove == 2) {
                        if (tiles[tile.getTileId() - 5].getTileDirection() == 6
                                || tiles[tile.getTileId() - 5].getTileDirection() == 7
                                || tiles[tile.getTileId() - 5].getTileDirection() == 1) {
                            pipes.add(tile);

                            tile = tiles[tile.getTileId() - 5];
                            lastmove = 1;
                        } else
                            loop = false;
                    }
                    break;
                // 01
                case 5:
                    if (lastmove == 0) {
                        if (tiles[tile.getTileId()].getTileDirection() == 4
                                || tiles[tile.getTileId()].getTileDirection() == 7
                                || tiles[tile.getTileId()].getTileDirection() == 2) {
                            pipes.add(tile);

                            tile = tiles[tile.getTileId()];
                            lastmove = 2;
                        } else
                            loop = false;
                    }
                    if (lastmove == 3) {
                        if (tiles[tile.getTileId() - 5].getTileDirection() == 1
                                || tiles[tile.getTileId() - 5].getTileDirection() == 6
                                || tiles[tile.getTileId() - 5].getTileDirection() == 7) {
                            pipes.add(tile);

                            tile = tiles[tile.getTileId() - 5];
                            lastmove = 1;
                        } else
                            loop = false;
                    }
                    break;
                // 11
                case 6:
                    if (lastmove == 1) {
                        if (tiles[tile.getTileId()].getTileDirection() == 2
                                || tiles[tile.getTileId()].getTileDirection() == 4
                                || tiles[tile.getTileId()].getTileDirection() == 7) {
                            pipes.add(tile);

                            tile = tiles[tile.getTileId()];
                            lastmove = 2;
                        } else
                            loop = false;
                    }
                    if (lastmove == 3) {
                        if (tiles[tile.getTileId() + 3].getTileDirection() == 1
                                || tiles[tile.getTileId() + 3].getTileDirection() == 4
                                || tiles[tile.getTileId() + 3].getTileDirection() == 5) {
                            pipes.add(tile);

                            tile = tiles[tile.getTileId() + 3];
                            lastmove = 0;
                        } else
                            loop = false;
                    }
                    break;
                // 10
                case 7:
                    if (lastmove == 2) {
                        if (tiles[tile.getTileId() + 3].getTileDirection() == 1
                                || tiles[tile.getTileId() + 3].getTileDirection() == 4
                                || tiles[tile.getTileId() + 3].getTileDirection() == 5) {
                            pipes.add(tile);

                            tile = tiles[tile.getTileId() + 3];
                            lastmove = 0;
                        } else
                            loop = false;
                    }
                    if (lastmove == 1) {
                        if (tiles[tile.getTileId() - 2].getTileDirection() == 2
                                || tiles[tile.getTileId() - 2].getTileDirection() == 5
                                || tiles[tile.getTileId() - 2].getTileDirection() == 7) {
                            pipes.add(tile);

                            tile = tiles[tile.getTileId() - 2];
                            lastmove = 3;
                        } else
                            loop = false;
                    }
                    break;
            }
            if (tile.getTileType() == 2) {
                pipes.add(tile);

                solved = true;
                loop = false;
            }
        }
        if (!solved)
            pipes.clear();
        return solved;
    }

    // Setters and getters
    public int getLevel() {
        return level;
    }

    public void setLevel(int levelFile) {
        this.level = levelFile;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Tile[] getClickedTiles() {
        return clickedTiles;
    }

    public void setClickedTiles(Tile[] clickedTiles) {
        this.clickedTiles = clickedTiles;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public void setNumberOfMoves(int numberOfMoves) {
        this.numberOfMoves = numberOfMoves;
    }
}