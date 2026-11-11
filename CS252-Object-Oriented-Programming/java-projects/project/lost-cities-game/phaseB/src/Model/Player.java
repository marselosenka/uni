package Model;

import Model.Cards.CardAnaktoro;
import Model.Cards.NumberCard;
import Model.Finding.Finding;
import java.util.ArrayList;
import Model.Cards.Card;
import Model.Finding.Fresco;
import Model.Finding.RareFinding;
import Model.Pawns.Pawn;

/**
 * Class that represents a player of the game.
 */
public class Player {
    private int score;
    private ArrayList<Fresco> Findings;
    private ArrayList<Card> Cards;
    private NumberCard[] lastCards;

    private ArrayList<RareFinding> rareFindings;
    private Pawn[] pawns;
    private String name;
    private int snakeGods;

    /**
     * Constructor: Generates a new Player and initializes them correctly.
     * @param name name of the Player
     */
    public Player(String name) {
        this.snakeGods = 0;
        this.name = name;
        this.score = 0;
        this.Cards = new ArrayList<>();
        this.Findings = new ArrayList<>();
        this.pawns = new Pawn[4];
        this.lastCards = new NumberCard[4];
        this.rareFindings = new ArrayList<>();
    }

    /**
     * Accessor: Returns the Cards that the player owns.
     * Pre-Condition: ArrayList must have been initialized
     * Post-Condition: cards should be accessible.
     * @return an ArrayList of Cards
     */
    public ArrayList<Card> getCards()
    {
        return Cards;
    }

    /**
     * Accessor: Returns the Findings that a player has found.
     * Pre-Condition: ArrayList must have been initialized
     * Post-Condition: cards should be accessible.
     * @return an ArrayList of Findings.
     */
    public ArrayList<Fresco> getFindings() {
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
     * Accessor: Accessess the LastCards of a Player.
     * Pre-Condition: Game has started.
     * @return An array of Cards, 4 of size.
     */
    public Card[] getLastCards() {
        return lastCards;
    }

    /**
     * Accessor: Accessses the number of Snake Goddesses the player owns.
     * Pre-Condition: Game has started.
     * @return the amount of snake goddesses.
     */
    public int getSnakeGods() {
        return snakeGods;
    }

    /**
     * Accessor: Accessses the rare findings player owns.
     * Pre-Condition: Game has started.
     * @return ArrayList of rare findings.
     */
    public ArrayList<RareFinding> getRareFindings() {
        return rareFindings;
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
    public void setFindings(ArrayList<Fresco> findings) {
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

    public void setLastCards(NumberCard[] lastCards) {
        this.lastCards = lastCards;
    }

    public void setSnakeGods(int snakeGods) {
        this.snakeGods = snakeGods;
    }

    public void setRareFindings(ArrayList<RareFinding> rareFindings) {
        this.rareFindings = rareFindings;
    }

    /**
     * Updates the Last Cards on a path.
     * Pre-Condition: None
     * Post-Condition: Should update the last card of that palace.
     * @param c new NumberCard we are adding as last.
     * @param cA Palace the card belogs to
     */
    public void editLast(NumberCard c,CardAnaktoro cA) {
        if (cA == CardAnaktoro.KNOSSOS) {
            lastCards[0] = c;
        } else if (cA == CardAnaktoro.MALIA) {
            lastCards[1] = c;
        } else if (cA == CardAnaktoro.PHAISTOS) {
            lastCards[2] = c;
        } else if (cA == CardAnaktoro.ZAKROS) {
            lastCards[3] = c;
        } else {
            System.out.println("Error on editLast of Player! Invalid card!");
        }
    }

    /**
     *  Method to edit the position of a pawn.
     *
     * @param pawn Pawn to edit, 0 for Theseus, 1 for Archeologist.
     * @param cA Anaktoro to update position.
     * @param pos new position.
     */
    public void editPawn(int pawn,CardAnaktoro cA,int pos) {
        boolean updated = false;
        if(pawn == 0) { // 0 for theseus.
            if(pawns[3].getAnaktoro() == CardAnaktoro.NONE) {
                pawns[3].setAnaktoro(cA);
            }
            pawns[3].setPosition(pos);
            updated = true;
        } else {
            for(int i=0;i<3;i++) {
                if(pawns[i].getAnaktoro() == cA) {
                    pawns[i].setPosition(pos);
                    updated = true;
                    break;
                }
            }
            if (!updated) {
                for(int i=0;i<3;i++) {
                    if(pawns[i].getAnaktoro()== CardAnaktoro.NONE) {
                        pawns[i].setAnaktoro(cA);
                        pawns[i].setPosition(pos);
                        updated = true;
                        break;
                    }
                }
            }
        }
        if(!updated) {
            System.out.println("Honestly dont know whats wrong");
        }
    }

    /**
     * Method to receive the Last card of the user at a specific palace.
     * Pre-Condition: The player has already played.
     * Post-Condition: Received the number of the card successfully.
     * @param cA Palace Indexing.
     * @return An integer of the value on that palace.
     */
    public int LastAtAnaktoro(CardAnaktoro cA) {
        if (cA == CardAnaktoro.KNOSSOS) {
            return lastCards[0].getValue();
        } else if (cA == CardAnaktoro.MALIA) {
            return lastCards[1].getValue();
        } else if (cA == CardAnaktoro.PHAISTOS) {
            return lastCards[2].getValue();
        } else if (cA == CardAnaktoro.ZAKROS) {
            return lastCards[3].getValue();
        } else {
            System.out.println("Error on editLast of Player! Invalid card!");
        }
        return -1;
    }
}

