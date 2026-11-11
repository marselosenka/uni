package Model.Cards.SpecialCards;
import Model.Cards.CardAnaktoro;
import Model.Cards.SpecialCard;

/**
 * This class create a new Minotaur card,
 * (One of the two special cards).
 */
public class Minotaur extends SpecialCard {

    /**
     * Constructor : Creates a new Minotaur card, via the parent class.
     * @param image_path path of where to find the image.
     * @param Anaktoro enum of which palace does the card belong to.
     */
    Minotaur(String image_path, CardAnaktoro Anaktoro) {
        super(image_path, Anaktoro);
    }
}
