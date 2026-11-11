package Model.Cards;

/**
 * A normal card, which has only a number displayed on it.
 *
 */
public class NumberCard extends Card{
    private int value;

    /**
     * Constructor : Creates a new Ariadne card, via the parent class.
     * @param image_path path of where to find the image.
     * @param anaktoro enum of which palace does the card belong to.
     * @param value the value of this card. (ranged 1-10)
     */
    public NumberCard(String image_path,CardAnaktoro anaktoro,int value) {
        super(image_path,anaktoro);
        this.value = value;
    }

    /**
     * Accessor: Returns the value of this card, the number displayed on the board.
     * Post-Condition: Must return the value and should be equal on the one displayed on the image.
     * @return an Integer value.
     *
     */
    public int getValue() {
        return value;
    }

    /**
     *  Transformer(Mutative): Updates the value of the NumberCard.
     * Post-Condition: The Card's value should be updated.
     * @param value new integer value for the classes' value.
     */
    public void setValue(int value) {
        this.value = value;
    }
}
