package Model;

import Model.Finding.Finding;
import java.util.ArrayList;
import Model.Cards.Card;
import Model.Pawns.Pawn;

/**
 * Class that represents a player of the game.
 */
public class Player {
    private int score;
    private ArrayList<Finding> Findings;
    private ArrayList<Card> Cards;

    private Pawn[] pawns;
    private String name;

    /**
     * Constructor: Generates a new Player and initializes them correctly.
     * @param name name of the Player
     */
    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.Cards = new ArrayList<>();
        this.Findings = new ArrayList<>();
        this.pawns = new Pawn[4];
    }

    /**
     * Accessor: Returns the Cards that the player owns.
     * Pre-Condition: ArrayList must have been initialized
     * Post-Condition: cards should be accessible.
     * @return an ArrayList of Cards
     */
    public ArrayList<Card> getCards() {
        return Cards;
    }

    /**
     * Accessor: Returns the Findings that a player has found.
     * Pre-Condition: ArrayList must have been initialized
     * Post-Condition: cards should be accessible.
     * @return an ArrayList of Findings.
     */
    public ArrayList<Finding> getFindings() {
        return Findings;
    }

    /**
     * Accessor: Gets the score of the player.
     * Pre-Condition: The game has already started.
     * @return a value that represents the player's score.
     */

    public int getScore() {
        return score;
    }

    /**
     * Accessor: Gets the pawns that belong to the player
     * Pre-Condition: The game has been initialized.
     * @return an array of pawns.
     */
    public Pawn[] getPawns() {
        return pawns;
    }

    /**
     * Accessor: Accesses the name of the player.
     * Pre-Condition: The game doesn't have another player of the same name.
     * @return a String that holds the player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Transformer(mutative): Updates the player's name.
     *
     * @param name the string that holds the player's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Transformer(mutative): Updates the player's cards.
     * Post-Condition: The changes should be applied on the arraylist.
     * @param cards ArrayList of desired cards.
     */
    public void setCards(ArrayList<Card> cards) {
        Cards = cards;
    }

    /**
     * Transformer(mutative): Updates the player's findings list.
     * Post-Condition: The new Findings should be on the list.
     * @param findings ArrayList of desired Findings.
     */
    public void setFindings(ArrayList<Finding> findings) {
        Findings = findings;
    }

    /**
     * Transformer(Mutative): Updates the player's pawns.
     * Post-Condition: The new pawns should be added.
     * @param pawns pawns that should be added to the player.
     */
    public void setPawns(Pawn[] pawns) {
        this.pawns = pawns;
    }

    /**
     * Transformer(Mutative): Updates the score of the player.
     * Post-Condition: The new score should be applied.
     * @param score desired score for the player.
     */
    public void setScore(int score) {
        this.score = score;
    }
}
