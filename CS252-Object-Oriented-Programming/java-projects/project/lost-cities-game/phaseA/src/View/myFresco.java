package View;

import javax.swing.*;

/**
 * Class used to display the Fresco findings of the player.
 * Extends the JButton class since it will be a button.
 */
public class myFresco extends JButton {

    private JButton button;
    private String image_path;
    /**
     * Constructor: Generates a new MyFresco object,
     * @param image_path Path for the image.
     */
    myFresco(String image_path) {
        this.button = new JButton();
        this.image_path = image_path;
    }

    public String getImage_path() {
        return image_path;
    }

    public JButton getButton() {
        return button;
    }
}
