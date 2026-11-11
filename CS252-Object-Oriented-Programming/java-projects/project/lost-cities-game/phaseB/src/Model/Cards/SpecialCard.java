package Model.Cards;

/**
 * Sub-Card of Card, This type of card is not a
 * regular one, expands to Ariadne or Minotaur.
 */
public abstract class SpecialCard extends Card {
    /**
     *
     * @param image_path path of where to find the image.
     * @param Anaktoro  enum of which palace does the card belong to.
     */
    public SpecialCard(String image_path, CardAnaktoro Anaktoro) {
        super(image_path,Anaktoro);
    }
}
