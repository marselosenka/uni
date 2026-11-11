package Model.Finding;

public class RareFinding extends Finding{
    private int points;

    /**
     * Constructor: creates a new RareFinding object, executes a supercall to initialize the required
     * attributes in order an object to be a Finding.
     * @param image_path Path for the image of the Fresco.
     * @param name Name of the Fresco finding.
     * @param message   message of the finding.
     * @param description   description of the finding.
     * @param points number of points that finding offers.
     */
    RareFinding(String image_path,String name,String message,String description,int points) {
        super(image_path,name,message,description);
        this.points = points;

    }

    /**
     * Accessor: Gets the points of that RareFinding.
     * @return an integer value that represents the points.
     */
    public int getPoints() {
        return points;
    }
    /**
     * Transformer(mutative): Changes the points of the RareFinding/
     * Post-Condition: A new value is applied.
     * @param points value of desired points.
     */
    public void setPoints(int points) {
        this.points = points;
    }
}
