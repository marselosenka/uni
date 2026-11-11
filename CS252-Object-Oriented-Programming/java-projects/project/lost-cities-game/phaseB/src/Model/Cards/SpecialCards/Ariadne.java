package Model.Cards.SpecialCards;
import Model.Cards.CardAnaktoro;
import Model.Cards.SpecialCard;

/**
 * This class create a new Ariadne card,
 * (One of the two special cards).
 */
public class Ariadne extends SpecialCard {

    /**
     * Constructor : Creates a new Ariadne card, via the parent class.
     * @param image_path path of where to find the image.
     * @param Anaktoro enum of which palace does the card belong to.
     */
    public Ariadne(String image_path, CardAnaktoro Anaktoro) {
        super(image_path,Anaktoro);
    }

}
