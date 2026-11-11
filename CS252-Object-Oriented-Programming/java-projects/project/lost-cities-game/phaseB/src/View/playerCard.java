package View;

import javax.swing.*;

/**
 * Class used to Display the cards that
 * a specified player holds.
 * Extends class JButton since they will be clickable.
 */
public class playerCard extends JButton {

    private String image;


    /**
     * Constructor: Generates a new playerCard button.
     * @param image path where the image is located.
     */
    playerCard(String image) {

        this.image = image;
    }



}
