package Control;

import Model.Gameplay.Board;
import Model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class that handles the Game logic (Model) And graphic design (View).
 */
public class Controller {

    private Board board;
    private List<Board> savedGames;

    /**
     * Constructor: Initializes the gameplay by creating the correct objects from Model and View
     * with the given correct attributes and starting the game.
     */
    public Controller() {
        Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");
        this.board = new Board(p1,p2);
    }

    /**
     * Method to create a new game session.
     */
    public void startNewGame() {

    }

    /**
     * Method to save the current on-going game.
     */
    public void saveGame() {

    }

    /**
     * Method to continue a saved game in the past.
     */
    public void continueGame() {

    }

    /**
     * Method to exit the current game.
     */
    public void exitGame() {

    }


}
