package Model.Pawns;

/**
 * Generates a new Theseus Pawn for the game.
 */
public class Theseus extends Pawn{
    /**
     *  Generates a pawn Theseus.
     * @param image_path Image of the pawn when displayed.
     * @param image_path_hidden Image of the pawn when not displayed.
     */
    private int destroyed;
    private boolean frozen;
    public Theseus(String image_path, String image_path_hidden) {
        super(image_path,image_path_hidden);
        this.destroyed = 0;
        this.frozen = false;
    }

    public void setDestroyed(int destroyed) {
        this.destroyed = destroyed;
    }

    public int getDestroyed() {
        return destroyed;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public void setCanMove(boolean frozen) {
        this.frozen = frozen;
    }
}
