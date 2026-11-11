package View;

import javax.swing.*;

public class Menu extends JMenuBar {
    JMenu newGame;
    JMenu saveGame;
    JMenu ContinueSavedGame;
    JMenu exitGame;

    JMenuItem newGameItem;
    JMenuItem saveGameItem;
    JMenuItem continueSavedGameItem;
    JMenuItem exitGameItem;
    public Menu() {
        new JMenuBar();

        newGameItem = new JMenuItem("Start New Game");
        saveGameItem = new JMenuItem("Save Game");
        continueSavedGameItem = new JMenuItem("Continue Saved Game");
        exitGameItem = new JMenuItem("Exit Game");

        add(newGameItem);
        add(saveGameItem);
        add(continueSavedGameItem);
        add(exitGameItem);
    }

    public JMenuItem getContinueSavedGameItem() {
        return continueSavedGameItem;
    }


    public JMenuItem getSaveGameItem() {
        return saveGameItem;
    }

    public JMenuItem getExitGameItem() {
        return exitGameItem;
    }

    public JMenuItem getNewGameItem() {
        return newGameItem;
    }
}
