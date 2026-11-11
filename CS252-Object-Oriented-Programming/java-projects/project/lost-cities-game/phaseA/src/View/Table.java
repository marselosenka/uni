package View;

import javax.swing.*;

/**
 * Class Table displays the board of the game.
 */
public class Table extends JLayeredPane {

    private JLayeredPane table;

    /**
     * Constructor: Generates a new JLayeredPane() where
     * everything will be added onto.
     */
    Table() {
        this.table = new JLayeredPane();
    }
}
