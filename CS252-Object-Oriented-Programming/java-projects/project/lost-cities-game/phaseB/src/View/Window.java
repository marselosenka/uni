package View;

import javax.swing.*;
import java.awt.*;

/**
 * Class Table displays the board of the game.
 */
public class Window extends JFrame {



    /**
     * Constructor: Generates a new JFrame() where
     * everything will be added onto.
     */
    public Window() {

        setTitle("Αναζητώντας τα χαμένα ανάκτορα");
        setSize(1300,900);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }




}
