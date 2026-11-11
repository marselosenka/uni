package Model.Position;

/**
 * Abstract class that represents a Position.
 */
public abstract class Position {
    private int index;
    private String image_path;

    private int points;

    /**
     * Constructor: Generates a new Position object.
     * @param index index of the board.
     * @param image_path path to find the desired image we want to display.
     * @param points points this position offers.
     */
    Position(int index,String image_path,int points) {
        this.index = index;
        this.image_path = image_path;
        this.points = points;
    }

    /**
     * Accessor: Accesses the index of the position
     * Pre-Condition: the board is initialized.
     * @return an integer value that represents the index (ranges 0-35).
     */
    public int getIndex() {
        return index;
    }

    /**
     * Accessor: Accesses the points this position holds.
     * Pre-Condition: The board has been initialized
     * @return an integer that represents the points.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Accessor: Gets the image of the position.
     * Post-Condition: The path is valid.
     * Pre-Condition: image_path is initialized and not NULL.
     * @return a String of the image path.
     */
    public String getImage_path() {
        return image_path;
    }

    /**
     * Transformer(mutative): Updates the index of the position.
     * Post-Condition: The new index is applied.
     * Pre-Condition: None.
     * @param index of the board.
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Transformer(mutative): Alters the points that this position offers.
     * Post-Condition: The new value is applied
     * Pre-Condition: None.
     * @param points The desired points this position offers.
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Transformer(mutative): Changes the desired image of this position
     * Post-Condition: the new image path is applied.
     * Pre-Condition: None.
     * @param image_path String that holds the path.
     */
    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
