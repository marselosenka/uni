package Model.Finding;

/**
 *  Contains the essential methods and attributes
 *  a finding requires.
 *
 */
public abstract class Finding {

    private String image_path;
    private String name;

    private String message;

    private String description;

    /**
     * Constructor:
     * @param image_path String that holds the path of the finding
     * @param name Name of the finding.
     * @param description   description of the finding.
     * @param message   message of the finding.
     */
    Finding(String image_path,String name,String message,String description) {
        this.image_path = image_path;
        this.name = name;
        this.message = message;
        this.description = description;
    }

    /**
     * Accessor: Retrieves the path of the Finding's image.
     * Post-Condition: The image's path must be returned.
     * @return a String that holds the path.
     */
    public String getImage_path() {
        return image_path;
    }

    /**
     * Accessor: retrives the name of the finding
     * Post-Condition: The finding's name must be returned.
     * @return  a String that holds the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Transformer(Mutative): Updates the path of the image.
     * Post-Condition: The path must be updated to the given value.
     * @param image_path a String that new image path.
     */
    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
    /**
     * Transformer(Mutative): Updates the name of the image.
     * Post-Condition: The given new name must be set.
     * @param name a String the name of the finding.
     */
    public void setName(String name) {
        this.name = name;
    }
}
