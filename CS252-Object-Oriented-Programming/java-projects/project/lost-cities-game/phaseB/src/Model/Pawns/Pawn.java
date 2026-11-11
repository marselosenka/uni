package Model.Pawns;

import Model.Cards.CardAnaktoro;

/**
 * A class to create a pawn for the gameplay.
 */
public abstract class Pawn {
    private String image_path;
    private String image_path_hidden;
    private int position;
    private CardAnaktoro Anaktoro;

    private boolean isHidden;

    /**
     *
     * @param image_path The path for the image of the pawn.
     * @param image_path_hidden The path for the image of the pawn when it's value is not displayed.
     */
    Pawn(String image_path,String image_path_hidden) {
        this.image_path = image_path;
        this.image_path_hidden = image_path_hidden;
        this.position = -1;
        this.Anaktoro = CardAnaktoro.NONE;
        this.isHidden = true;
    }

    /**
     * Accessor: returns the image of the path.
     * @return a String value of the path.
     */
    public String getImage_path() {
        return image_path;
    }
    /**
     * Accessor: returns the image of the path when hidden.
     * @return a String value of the path.
     */
    public String getImage_path_hidden() {
        return image_path_hidden;
    }

    /**
     * Accessor: The receipt receives the position of this pawn.
     * @return an integer for the position of the pawn. (-1 if not on the board yet).
     */
    public int getPosition() {
        return position;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public CardAnaktoro getAnaktoro() {
        return Anaktoro;
    }

    /**
     * Transformer(mutative):  Updates the image of the path.
     * @param image_path String that indicates the path.
     */
    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
    /**
     * Transformer(mutative):  Updates the image of the path when the pawn is hidden.
     * @param image_path_hidden String that indicates the path.
     */
    public void setImage_path_hidden(String image_path_hidden) {
        this.image_path_hidden = image_path_hidden;
    }

    /**
     * Transformer(mutative): Sets a position for the pawn.
     * Post-Condition: Must be higher or equal to 0
     * @param position the wanted position of the pawn.
     */
    public void setPosition(int position) {
        this.position = position;
    }

    public void setAnaktoro(CardAnaktoro anaktoro) {
        Anaktoro = anaktoro;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }
}
