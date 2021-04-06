import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 150119007 Ahmet Hakan Beşel  150119004 Semir Tatlı
 * Level class for create a levels from given text files
 */
public class Level {
    // List of all level files
    private final static String[] levels = new String[]{"level1.txt", "level2.txt", "level3.txt", "level4.txt", "level5.txt", "level6.txt"};
    // Data field for store tiles of current level
    private int[][] tiles = new int[16][3];
    // Data field for store rows which comes from level text files
    private ArrayList<String> rows = new ArrayList<>();

    public Level(String levelFile) {
        // Read level file and store rows in ArrayList
        readLevelFile(levelFile);
        // Generate tiles from rows
        readRowsList();
    }

    /**
     * Generates tiles from previously read rows
     */
    private void readRowsList() {
        int i = 0;
        for (String row : this.rows) {
            String[] rowData = row.split(",");
            this.tiles[i][0] = Integer.parseInt(rowData[0]); // Tile number
            this.tiles[i][1] = Tile.tileType(rowData[1]); // Tile type
            this.tiles[i][2] = Tile.tileDirection(rowData[2]); // Tile direction
            i++;
        }
    }

    /**
     * Reads level file row by row and stores row in the data field
     * @param filePath File path of level file
     */
    private void readLevelFile(String filePath) {
        java.io.File file = new java.io.File(filePath);
        // Create a Scanner for the file
        try {
            Scanner input = new Scanner(file);
            // Read data from a file
            while (input.hasNextLine()) {
                String row = input.nextLine();
                if (!row.isEmpty()) {
                    this.rows.add(row);
                }
            }
            // Close the file
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String[] getLevels() {
        return levels;
    }

    public int[][] getTiles() {
        return tiles;
    }

    public int[] getTile(int i) {
        return tiles[i];
    }

    public void setTiles(int[][] tiles) {
        this.tiles = tiles;
    }

    public ArrayList<String> getRows() {
        return rows;
    }

    public void setRows(ArrayList<String> rows) {
        this.rows = rows;
    }

    public void addRow(String row) {
        ArrayList<String> rows = getRows();
        rows.add(row);
        setRows(rows);
    }
}