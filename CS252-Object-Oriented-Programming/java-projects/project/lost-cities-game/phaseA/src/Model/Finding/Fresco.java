package Model.Finding;

/**
 * This class creates a new Fresco Finding
 */
public class Fresco extends Finding{
    private int points;

    /**
     * Constructor: Creates a new instance of Fresco Findings,executes a super() call to initialize
     * the required attributes of a Finding object.
     * @param image_path Path for the image of the Fresco.
     * @param name Name of the Fresco finding.
     * @param points number of points that finding offers.
     * @param description   description of the finding.
     * @param message   message of the finding.
     */
    Fresco(String image_path,String name,String message,String description,int points) {
        super(image_path,name,message,description);
        this.points = points;
    }

    /**
     * Accessor: Gets the points of that Fresco
     * @return an integer value that represents the points.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Transformer(mutative): Changes the points of the Fresco
     * Post-Condition: A new value is applied.
     * @param points value of desired points.
     */
    public void setPoints(int points) {
        this.points = points;
    }
}
