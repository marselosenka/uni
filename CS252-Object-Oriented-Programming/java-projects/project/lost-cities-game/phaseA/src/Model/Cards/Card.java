package Model.Cards;

/**
 * Contains the base of every Card of
 * Lost Cities: Minoan Palace.
 *
 */
public abstract class Card {
    private String image_path;
    private CardAnaktoro Anaktoro;

    /**
     * Constructor of class Card, creates a Card object
     * @param image_path Path for the source image that will be displayed.
     * @param anaktoro enum of the Anaktoro it belongs to.
     */
    Card(String image_path,CardAnaktoro anaktoro) {
        this.image_path = image_path;
        this.Anaktoro = anaktoro;
    }

    /**
     * Accessor: Returns a String where the path of the Card's image is located
     * Post-Condition: The image's path must be returned.
     * @return String of the path.
     */
    public String getImage_path() {
        return image_path;
    }

    /**
     * Accessor: Returns in which Anaktoro this card belongs to.
     * Post-Condition:  Must be one of the 4 values of CardAnaktoro and must not be null
     * @return a CardAnaktoro typed value.
     */
    public CardAnaktoro getAnaktoro() {
        return Anaktoro;
    }

    /**
     *  Transformer(Mutative): Sets the card's image.
     * Post-Condition: The card's image has been updated.
     * @param image_path a path to where the image is located.
     */
    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    /**
     * Transformer(Mutative): Sets the Anaktoro of a card.
     * Post-Condition: The Anaktoro is set succesfully.
     * @param anaktoro one of the 4 availble palaces.
     */
    public void setAnaktoro(CardAnaktoro anaktoro) {
        Anaktoro = anaktoro;
    }
}
