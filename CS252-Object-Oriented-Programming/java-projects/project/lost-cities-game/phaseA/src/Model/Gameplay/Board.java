package Model.Gameplay;

import Model.Cards.Card;
import Model.Player;
import Model.Position.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that contains all the essential attributes and methods
 * to demonstrate a gameplay.
 */
public class Board {
    private Position[][] board;
    private Player[] Players;
    private Player player_turn;
    private long timer;
    private boolean status;

    private ArrayList<Card> Cards;
    private ArrayList<Card> discardedCards;

    /**
     * Constructor: Initiliazes the players and calls the required methods
     * to start a game from scratch.
     * @param p1 Object of class Player demonstrating player 1.
     * @param p2 Object of class Player demonstrating player 2.
     */
    public Board(Player p1, Player p2) {
        this.Players = new Player[2];
        this.Cards = new ArrayList<>();
        this.discardedCards = new ArrayList<>();
        Players[0] = p1;
        Players[1] = p2;
        board = new Position[4][9];
        initBoard();
        initPlayerTurn();
        initPlayersCards();
        shuffleCards();

    }

    /**
     * Method to initialize the positions(of findings etc.).
     * when the game begins.
     */
    public void initBoard() {
        return;
    }

    /**
     * Method to choose the player who will be playing
     * first at the beggining of the game
     * Pre-Condition
     */
    public void initPlayerTurn() {
        return;
    }

    /**
     * Method to initialize the Player's cards.
     * Delivers 8 to each at the beggining.
     * Pre-Condition: Has not initialized earlier.
     * Post-Condition: Each player has 8 cards.
     */
    public void initPlayersCards() {
        return;
    }

    /**
     * Method to shuffle our card stack.
     * Pre-Condition: None
     * Post-Condition: Card stack has been shuffled.
     */
    public void shuffleCards() {
        return;
    }

    /**
     * Method to play a card.
     * Pre-Condition: The game has started.
     * Post-Condition: None.
     * @param p1 The player who's round is right now.
     */
    public void playCard(Player p1) {
        return;
    }

    /**
     * Accessor: Used to retrieve the card stack of the game.
     * Post-Condition: Must return the Card of the current game.
     * @return an ArrayList of Card objects.
     */
    public ArrayList<Card> getCards() {
        return Cards;
    }

    /**
     * Accessor: Will be used to retrieve the time of this game.
     * Post-Condition: Must return the time of the game.
     * @return a long value.
     */
    public long getTimer() {
        return timer;
    }

    /**
     * Accessor: Accesses the positions of the game, and their contains.
     * Post-Condition: Must return the positions to the player.
     * @return An ArrayList of Position objects.
     */
    public Position[][] getBoard() {
        return board;
    }

    /**
     * Accessor: Accesses whose player's turn is on this round
     * Post-Condition: Must return the Player.
     * @return an instace of class Player.
     */
    public Player getPlayer_turn() {
        return player_turn;
    }

    /**
     * Accessor: Informs us on whether the game is still ongoing or not.
     * Post-Condition: Must return the game status and the recipient is informed.
     * @return True if the game is active, false if the game has ended.
     */
    public boolean getStatus() {
        return this.status;
    }

    /**
     * Accessor: accesses the discarded cards of this game.
     * @return the Discarded Cards of the game.
     */
    public ArrayList<Card> getDiscardedCards() {
        return discardedCards;
    }

    /**
     * Transformer(Mutative): Sets the board of the game.
     * Post-Condition: The board of this class has been set.
     * @param board a 2D array of type Position.
     */
    public void setBoard(Position[][] board) {
        this.board = board;
    }

    /**
     * Transformer(Mutative): Sets the Card stack for the game.
     * Post-Condition: Cards have been set.
     * @param cards ArrayList of Card objects.
     */
    public void setCards(ArrayList<Card> cards) {
        Cards = cards;
    }

    /**
     * Transformer(Mutative): Updates the player turn for the round.
     * Post-Condition: The player turn must not be equal as before.
     * @param player_turn Instace of Player class.
     */

    public void setPlayer_turn(Player player_turn) {
        this.player_turn = player_turn;
    }

    /**
     * Transformer(Mutative): Updates the status of the game.
     * Post-Condition: The game status is changed to the opposite.
     * @param status True if the game is on going,False if the game has finished.
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Transformer(Mutative): Sets the timer of the game.
     * Post-Condition: Updates the value of the time,a higher value than earlier.
     * @param timer A long variable for the time.
     */
    public void setTimer(long timer) {
        this.timer = timer;
    }

    /**
     * Switches the turn of the player playing for the round.
     * Post-Condition: The opposite player must play now.
     */
    public void switchTurn() {
        return;
    }

    /**
     * Checks whether a position has a finding or not.
     * @param index index of the position we want to check.
     * @return True if there is one, false if not.
     */
    public boolean checkForFinding(int index) {
        return false;
    }

    /**
     * Method to check whether we have a winner or not,
     * @return True if there is a winner, false if not.
     */
    public boolean hasWinner() {
        return false;
    }
}
