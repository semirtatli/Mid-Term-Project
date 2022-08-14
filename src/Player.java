/**
 * Player class for storing completed levels
 */
public class Player {
    // Id of the player
    private int id;
    // Array for storing which levels played or not
    private int[] completedLevels = new int[Level.getLevels().length];

    // Player's constructor
    public Player() {
    }

    /**
     * Returns true if given level can be played by player
     * Player can play level 1 at least.
     * Also player can play last completed level and the next level.
     *
     * @param levelNumber Integer for level number
     * @return boolean
     */
    public boolean isPlayable(int levelNumber) {
        if (levelNumber == 1) {
            return true;
        } else {
            return completedLevels[levelNumber - 2] == 1;
        }
    }

    /**
     * Returns true if given level completed by player before
     * @param levelNumber Integer for level number
     * @return boolean
     */
    public boolean isPlayed(int levelNumber) {
        return completedLevels[levelNumber - 1] == 1;
    }

    /**
     * Sets given level is played in playedLevels array
     * @param levelNumber Integer for level number
     */
    public void addPlayedLevel(int levelNumber) {
        completedLevels[levelNumber - 1] = 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[] getPlayedLevels() {
        return completedLevels;
    }

    public void setPlayedLevels(int[] playedLevels) {
        this.completedLevels = playedLevels;
    }
}
