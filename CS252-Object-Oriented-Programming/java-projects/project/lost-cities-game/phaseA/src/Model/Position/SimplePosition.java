package Model.Position;

/**
 * A class that represents a SimplePosition
 * on the board.
 */
public class SimplePosition extends Position{
    /**
     * Constructor: generates a simple position, via super()
     * @param index index on the board.
     * @param image_path path for the image.
     * @param points points that this positions gives to the player.
     */
    SimplePosition(int index,String image_path,int points) {
        super(index,image_path,points);

    }
}
