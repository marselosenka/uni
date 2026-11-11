package Model.Pawns;

/**
 * Creates a new Archeologist object for the game.
 */
public class Archeologist extends Pawn{
    /**
     * Constructor: creates a new object of subclass Archeologist, of Pawn.
     * @param image_path Image of the pawn when displayed.
     * @param image_path_hidden Image of the pawn when not displayed.
     */
    public Archeologist(String image_path, String image_path_hidden) {
        super(image_path,image_path_hidden);
    }
}
