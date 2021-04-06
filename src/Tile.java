// Import necessary classes

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;

/**
 * 150119007 Ahmet Hakan Beşel  150119004 Semir Tatlı
 * Tile class for creating tiles
 * It draws tile on pane
 */
public class Tile extends Pane {
    // Data field for tile number
    private int tileId;
    // Data field for tile type
    private int tileType;
    // Data field for direction of tile
    private int tileDirection;
    // Color palette for tiles
    private final static String PRIMARY_COLOR = "#a86d4d";
    private final static String SECONDARY_COLOR = "#4f4f4f";
    private final static String RED_COLOR = "#7D1016";
    private final static String BLUE_COLOR = "#08415C";

    // Invoke Tile constructor with args tileID, tileType and tileDirection
    public Tile(int tileId, int tileType, int tileDirection) {
        // Set tile id
        setTileId(tileId);
        // Setting the type of the tile
        setTileType(tileType);
        // Setting the direction of tile
        setTileDirection(tileDirection);
        // color change when clicked on the tile
        this.setPrefSize(150, 150);
        draw();
    }

    /**
     * Draws required rectangles and arches which depends on tile type and direction on pane
     */
    public void draw() {
        int tileType = getTileType();
        int tileDirection = getTileDirection();
        Arc arc = null;
        Rectangle rectangle = null;
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(1), new BorderWidths(2))));
        if (tileType == 0) { // Empty tile
            switch (tileDirection) {
                case 3:
                    setStyle("-fx-background-color:" + SECONDARY_COLOR);
                    break;
                case 0:
                    setStyle("-fx-background-color:" + PRIMARY_COLOR);
                    break;
            }
        } else if (tileType == 1 || tileType == 2) {
            setStyle("-fx-background-color: " + (tileType == 1 ? BLUE_COLOR : RED_COLOR));
            switch (tileDirection) {
                case 1:
                    arc = new Arc(75, 60, 30, 30, 0, 180);
                    rectangle = new Rectangle(45, 60, 60, 89);
                    break;
                case 2:
                    arc = new Arc(89, 75, 30, 30, 270, 180);
                    rectangle = new Rectangle(0, 45, 89, 60);
                    break;
            }
            arc.setType(ArcType.OPEN);
            arc.setStrokeWidth(0);
            arc.setStroke(null);
            arc.setStrokeWidth(60);
            rectangle.setOpacity(0.5);
            arc.setOpacity(0.5);
            this.getChildren().addAll(rectangle, arc);
        } else if (tileType == 3 || tileType == 4) {
            this.setStyle("-fx-background-color:" + (tileType == 4 ? BLUE_COLOR : PRIMARY_COLOR));
            if (tileDirection == 1 || tileDirection == 2) {
                switch (tileDirection) {
                    case 1:
                        rectangle = new Rectangle(150 / 2 - 30, 0, 60, 150);
                        break;
                    case 2:
                        rectangle = new Rectangle(150 / 2 - 30, 0, 60, 150);
                        rectangle.setRotate(90);
                        break;
                }
                rectangle.setOpacity(0.5);
                this.getChildren().add(rectangle);
            } else {
                switch (tileDirection) {
                    case 4:
                        arc = new Arc(30, 30, 45, 45, 270, 90);
                        break;
                    case 5:
                        arc = new Arc(120, 30, 45, 45, 180, 90);
                        break;
                    case 6:
                        arc = new Arc(120, 120, 45, 45, 90, 90);
                        break;
                    case 7:
                        arc = new Arc(30, 120, 45, 45, 0, 90);
                        break;
                }
                arc.setType(ArcType.OPEN);
                arc.setFill(null);
                arc.setStroke(Color.BLACK);
                arc.setStrokeWidth(60);
                arc.setOpacity(0.5);
                this.getChildren().add(arc);
            }
        }
    }

    /**
     * Returns an integer value by depends on the name of tile (which is declared level text file)
     * @param tileName is string for tile name
     * @return Returns integer values
     */
    public static int tileType(String tileName) {
        switch (tileName) {
            case "Empty":
                return 0;
            case "Starter":
                return 1;
            case "End":
                return 2;
            case "Pipe":
                return 3;
            case "PipeStatic":
                return 4;
            default:
                return -1;
        }
    }

    /**
     * Returns an integer value by depends on the direction of tile (which is declared level text file)
     * @param tileDirection is string for tile direction
     * @return Returns an integer
     */
    public static int tileDirection(String tileDirection) {
        switch (tileDirection) {
            case "none":
                return 0;
            case "Vertical":
                return 1;
            case "Horizontal":
                return 2;
            case "Free":
                return 3;
            case "00": // _|
                return 4;
            case "01": // |_
                return 5;
            case "11": // |¯
                return 6;
            case "10": // ¯|
                return 7;
            default:
                return -1;
        }
    }

    public int getTileType() {
        return tileType;
    }

    public void setTileType(int tileType) {
        this.tileType = tileType;
    }

    public int getTileDirection() {
        return tileDirection;
    }

    public void setTileDirection(int tileDirection) {
        this.tileDirection = tileDirection;
    }

    public int getTileId() {
        return tileId;
    }

    public void setTileId(int tileId) {
        this.tileId = tileId;
    }
}