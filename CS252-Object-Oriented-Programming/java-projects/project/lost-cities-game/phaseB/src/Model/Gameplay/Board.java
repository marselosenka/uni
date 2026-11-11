package Model.Gameplay;

import Model.Cards.Card;
import Model.Cards.CardAnaktoro;
import Model.Cards.NumberCard;
import Model.Cards.SpecialCard;
import Model.Cards.SpecialCards.Ariadne;
import Model.Cards.SpecialCards.Minotaur;
import Model.Finding.Finding;
import Model.Finding.Fresco;
import Model.Finding.RareFinding;
import Model.Finding.SnakeGoddess;
import Model.Pawns.Pawn;
import Model.Player;
import Model.Position.FindingPosition;
import Model.Position.Position;
import Model.Position.SimplePosition;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Class that contains all the essential attributes and methods
 * to demonstrate a gameplay.
 */
public class Board {
    private Position[][] board;
    private Player[] Players;

    private ArrayList<Finding> findings;
    private ArrayList<RareFinding> rareFindings;
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
    public Board(Player p1, Player p2) throws FileNotFoundException {
        this.Players = new Player[2];
        this.Cards = new ArrayList<>();
        this.discardedCards = new ArrayList<>();

        this.findings = new ArrayList<>();
        this.rareFindings = new ArrayList<>();
        Players[0] = p1;
        Players[1] = p2;
        board = new Position[4][9];
        initFindings();
        initRareFindings();
        initBoard();
        initPlayerTurn();
        initCards();
        shuffleCards();
        initPlayersCards();
    }

    /**
     * Method to initialize the positions(of findings etc.).
     * when the game begins.
     */
    public void initBoard() {
        Random random = new Random();
        int[] possibleValues = {2, 4, 6, 8, 9};
        int faistos_rare = possibleValues[random.nextInt(possibleValues.length)];
        System.out.println(faistos_rare);
        int knwssos_rare = possibleValues[random.nextInt(possibleValues.length)];
        System.out.println(knwssos_rare);
        int malia_rare = possibleValues[random.nextInt(possibleValues.length)];
        System.out.println(knwssos_rare);
        int zakros_rare = possibleValues[random.nextInt(possibleValues.length)];
        System.out.println(knwssos_rare);
        for(int i = 0; i<4 ; i++) {
            for (int j = 0; j < 9; j++) {

                if(i == 0) {
                    if( (j+1)== 2 || (j+1)== 4 || (j+1)== 6 || (j+1)== 8 || (j+1)== 9 ) {
                        if(knwssos_rare == j+1) {
                            if(j == 8) {
                                board[i][j] = new FindingPosition(j+1,"./src/project_assets/images/paths/knossosPalace.jpg",rareFindings.get(0));
                            } else {
                                board[i][j] = new FindingPosition(j+1,"./src/project_assets/images/paths/knossos2.jpg",rareFindings.get(0));
                            }
                        } else {
                            String imagePath = (j == 8) ? "./src/project_assets/images/paths/knossosPalace.jpg" : "./src/project_assets/images/paths/knossos2.jpg";
                            board[i][j] = new FindingPosition(j + 1, imagePath, findings.remove(findings.size() - 1));
                        }
                    } else {
                        board[i][j] = new SimplePosition(j + 1, "./src/project_assets/images/paths/knossos.jpg");
                    }

                } else if (i == 1) {

                    if ( (j+1)==2 || (j+1)==4 || (j+1)==6 || (j+1)==8 || (j+1)==9) {
                        if(malia_rare == j+1) {
                            if(j==8) {
                                board[i][j] = new FindingPosition(j+1,"./src/project_assets/images/paths/maliaPalace.jpg",rareFindings.get(1));
                            } else {
                                board[i][j] = new FindingPosition(j+1,"./src/project_assets/images/paths/malia2.jpg",rareFindings.get(1));
                            }
                        } else {
                            String imagePath = (j == 8) ? "./src/project_assets/images/paths/maliaPalace.jpg" : "./src/project_assets/images/paths/malia2.jpg";
                            board[i][j] = new FindingPosition(j+1,imagePath ,findings.remove(findings.size()-1));
                        }
                    } else {
                        board[i][j] = new SimplePosition(j + 1, "./src/project_assets/images/paths/malia.jpg");
                    }

                } else if (i == 2) {

                    if ( (j+1)==2 || (j+1)==4 || (j+1)==6 || (j+1)==8 || (j+1)==9 ) {
                        if(faistos_rare == j+1) {
                            if(j==8) {
                                board[i][j] = new FindingPosition(j+1,"./src/project_assets/images/paths/phaistosPalace.jpg",rareFindings.get(3));
                            } else {
                                board[i][j] = new FindingPosition(j+1,"./src/project_assets/images/paths/phaistos2.jpg",rareFindings.get(3));
                            }
                        } else {
                            String imagePath = (j == 8) ? "./src/project_assets/images/paths/phaistosPalace.jpg" : "./src/project_assets/images/paths/phaistos2.jpg";
                            board[i][j] = new FindingPosition(j+1,imagePath,findings.remove(findings.size()-1));
                        }
                    } else {
                        board[i][j] = new SimplePosition(j + 1, "./src/project_assets/images/paths/phaistos.jpg");
                    }

                } else if (i==3) {

                    if ( (j+1)==2 || (j+1)==4 || (j+1)==6 || (j+1)==8 || (j+1)==9 ) {
                        if(zakros_rare == j+1) {
                            if(j==8) {
                                board[i][j] = new FindingPosition(j+1,"./src/project_assets/images/paths/zakrosPalace.jpg",rareFindings.get(2));
                            } else {
                                board[i][j] = new FindingPosition(j+1,"./src/project_assets/images/paths/zakros2.jpg",rareFindings.get(2));
                            }
                        } else {
                            String imagePath = (j == 8) ? "./src/project_assets/images/paths/zakrosPalace.jpg" : "./src/project_assets/images/paths/zakros2.jpg";
                            board[i][j] = new FindingPosition(j+1,imagePath,findings.remove(findings.size()-1));
                        }
                    } else {
                        board[i][j] = new SimplePosition(j + 1, "./src/project_assets/images/paths/zakros.jpg");
                    }
                }

            }
        }

        for(int i = 0;i<4;i++) {
            for(int j =0;j<9;j++) {
                if(j==0) {
                    board[i][j].setPoints(-20);
                } else if (j==1) {
                    board[i][j].setPoints(-15);
                } else if (j==2) {
                    board[i][j].setPoints(-10);
                } else if (j==3) {
                    board[i][j].setPoints(5);
                } else if (j==4) {
                    board[i][j].setPoints(10);
                } else if (j==5) {
                    board[i][j].setPoints(15);
                } else if (j==6) {
                    board[i][j].setPoints(30);
                } else if (j==7) {
                    board[i][j].setPoints(35);
                } else if (j== 8) {
                    board[i][j].setPoints(50);
                }
            }
        }
        return;
    }

    /**
     * Method to choose the player who will be playing
     * first at the beggining of the game
     * Pre-Condition: Has not initialized before,
     * Post-Condition: is not null.
     */
    public void initPlayerTurn() {
        Random random = new Random();
        int turn = random.nextInt(2);
        // System.out.println("turn: "+turn);
        if(turn==0) {
            setPlayer_turn(Players[0]);
        } else if(turn==1) {
            setPlayer_turn(Players[1]);
        } else {
            System.out.println("Error during initializing player turn");
        }
    }

    /**
     * Method to initialize the Player's cards.
     * Delivers 8 to each at the beggining.
     * Pre-Condition: Has not initialized earlier.
     * Post-Condition: Each player has 8 cards.
     */
    public void initPlayersCards() {
        ArrayList<Card> c1 = new ArrayList<>();
        ArrayList<Card> c2 = new ArrayList<>();
        System.out.println(Cards.size());
        for(int i=0;i<8;i++) {
            c1.add(Cards.remove(0));
        }
        for(int i=0;i<8;i++) {
            c2.add(Cards.remove(0));
        }
        Players[0].setCards(c1);
        Players[1].setCards(c2);
        System.out.println(Cards.size());
        return;
    }

    /**
     * Void method that initializes the Cards
     */
    public void initCards() {
        // KNOSSOS
        for(int j=0;j<2;j++) {
            for(int i=0;i<10;i++) {
                Cards.add(new NumberCard("./src/project_assets/images/cards/knossos"+(i+1)+".jpg", CardAnaktoro.KNOSSOS,(i+1)));
            }
        }
        for(int i=0;i<3;i++) {
            Cards.add(new Ariadne("./src/project_assets/images/cards/knossosAri.jpg", CardAnaktoro.KNOSSOS));
        }
        for(int i=0;i<2;i++) {
            Cards.add(new Minotaur("./src/project_assets/images/cards/KnossosMin.jpg", CardAnaktoro.KNOSSOS));
        }
        // MALIA
        for(int j=0;j<2;j++) {
            for(int i=0;i<10;i++) {
                Cards.add(new NumberCard("./src/project_assets/images/cards/malia"+(i+1)+".jpg", CardAnaktoro.MALIA,(i+1)));
            }
        }
        for(int i=0;i<3;i++) {
            Cards.add(new Ariadne("./src/project_assets/images/cards/maliaAri.jpg", CardAnaktoro.MALIA));
        }
        for(int i=0;i<2;i++) {
            Cards.add(new Minotaur("./src/project_assets/images/cards/maliaMin.jpg", CardAnaktoro.MALIA));
        }
        // PHAISTOS
        for(int j=0;j<2;j++) {
            for(int i=0;i<10;i++) {
                Cards.add(new NumberCard("./src/project_assets/images/cards/phaistos"+(i+1)+".jpg", CardAnaktoro.PHAISTOS,(i+1)));
            }
        }
        for(int i=0;i<3;i++) {
            Cards.add(new Ariadne("./src/project_assets/images/cards/phaistosAri.jpg", CardAnaktoro.PHAISTOS));
        }
        for(int i=0;i<2;i++) {
            Cards.add(new Minotaur("./src/project_assets/images/cards/phaistosMin.jpg", CardAnaktoro.PHAISTOS));
        }
        //ZAKROS
        for(int j=0;j<2;j++) {
            for(int i=0;i<10;i++) {
                Cards.add(new NumberCard("./src/project_assets/images/cards/zakros"+(i+1)+".jpg", CardAnaktoro.ZAKROS,(i+1)));
            }
        }
        for(int i=0;i<3;i++) {
            Cards.add(new Ariadne("./src/project_assets/images/cards/zakrosAri.jpg", CardAnaktoro.ZAKROS));
        }
        for(int i=0;i<2;i++) {
            Cards.add(new Minotaur("./src/project_assets/images/cards/zakrosMin.jpg", CardAnaktoro.ZAKROS));
        }

    }

    /**
     * Void method which initializes the Findings.
     * @throws FileNotFoundException
     */
    public void initFindings() throws FileNotFoundException {
        BufferedReader in = new BufferedReader(new FileReader("./src/project_assets/csvFiles/csv_greek.csv"));
        try {
            String line;
            while ((line = in.readLine()) != null) {
                String[] values = line.split(";");
                if(values.length==3) {
                    if(values[0].contains("snakes")) {
                        for(int i=0;i<10;i++) {
                            findings.add(new SnakeGoddess("./src/project_assets/images/"+values[0],"Άγαλμα θέας Αθηνάς",values[1],values[2]));
                        }
                    } else if(values[0].contains("fresco1_20")) {
                        findings.add(new Fresco("./src/project_assets/images/"+values[0],"Οι γαλάζιες κυρίες",values[1],values[2],20));
                    } else if(values[0].contains("fresco2_20")) {
                        findings.add(new Fresco("./src/project_assets/images/"+values[0],"Τα ταυροκαθάψια",values[1],values[2],20));
                    } else if(values[0].contains("fresco3_15")) {
                        findings.add(new Fresco("./src/project_assets/images/"+values[0],"Τα δελφίνια",values[1],values[2],15));
                    } else if(values[0].contains("fresco4_20")) {
                        findings.add(new Fresco("./src/project_assets/images/"+values[0],"Ο πρίγκιπας με τα κρίνα",values[1],values[2],20));
                    } else if(values[0].contains("fresco5_15")) {
                        findings.add(new Fresco("./src/project_assets/images/"+values[0],"Πομπή νέων",values[1],values[2],15));
                    } else if(values[0].contains("fresco6_15")) {
                        findings.add(new Fresco("./src/project_assets/images/"+values[0],"Η παριζιάνα",values[1],values[2],15));
                    } else {
                        System.out.println("wrong input!");
                    }
                }
            }
            Collections.shuffle(findings);
        } catch (IOException ioe) {
            System.err.println("Couldn't open");
        }
    }

    public void initRareFindings() throws FileNotFoundException {
        BufferedReader in = new BufferedReader(new FileReader("./src/project_assets/csvFiles/csv_rare.csv"));
        try {
            String line;
            while ((line = in.readLine()) != null) {
                String[] values = line.split(";");
                if(values.length==3) {
                    if (values[0].contains("diskos")) {
                        rareFindings.add(new RareFinding("./src/project_assets/images/" + values[0], "Δίσκος φαιστού", values[1], values[2], 35));

                    } else if (values[0].contains("kosmima")) {
                        rareFindings.add(new RareFinding("./src/project_assets/images/" + values[0], "Κόσμημα Μαλίων", values[1], values[2], 25));
                    } else if(values[0].contains("ruto.jpg")) {
                        rareFindings.add(new RareFinding("./src/project_assets/images/"+values[0],"Ρυτό Ζάκρου",values[1],values[2],25));
                    } else if(values[0].contains("ring")) {
                        rareFindings.add(new RareFinding("./src/project_assets/images/"+values[0],"Δαχτυλίδι του Μίνωα",values[1],values[2],25));
                    }
                }
            }
        } catch (IOException ioe) {
            System.err.println("Couldn't open");
        }

    }

    /**
     * Method to calculate how many checkpoints are reached.
     * @return a value of the checkpoints.
     */
    public int calculateCheckPoints() {
        int sum = 0;
        for(int i = 0; i<4;i++) {
            if(getPlayers()[0].getPawns()[i].getPosition() >= 6) {

                sum++;
            }
            if(getPlayers()[1].getPawns()[i].getPosition() >= 6) {
                sum++;
            }
        }
        return sum;
    }

    /**
     * Method to shuffle our card stack.
     * Pre-Condition: None
     * Post-Condition: Card stack has been shuffled.
     */
    public void shuffleCards() {
        Collections.shuffle(Cards);

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

    public Player[] getPlayers() {
        return Players;
    }

    public void setPlayers(Player[] players) {
        Players = players;
    }

    /**
     * Switches the turn of the player playing for the round.
     * Post-Condition: The opposite player must play now.
     */
    public void switchTurn() {
        if(getPlayer_turn() == Players[0]) {
            setPlayer_turn(Players[1]);
        } else {
            setPlayer_turn(Players[0]);
        }
        return;
    }

    // CHECK IF A PAWN IS PLACED ON ONE ANAKTORO
    public int hasPawnOnAnaktoro(Player p,int index) {
        Card c = p.getCards().get(index);
        if(c.getAnaktoro() == CardAnaktoro.KNOSSOS) {
            if(p.getLastCards()[0] == null) {
                System.out.println("Has not placed a Pawn yet on KNOSSOS!");
                return 0;
            } else {
                System.out.println("Has placed a Pawn on KNOSSOS!");
                return 1;
            }
        } else if (c.getAnaktoro() == CardAnaktoro.MALIA) {
            if(p.getLastCards()[1] == null) {
                System.out.println("Has not placed a Pawn yet on MALIA!");
                return 0;
            }  else {
                System.out.println("Has placed a Pawn on MALIA!");
                return 1;
            }
        } else if (c.getAnaktoro() == CardAnaktoro.PHAISTOS) {
            if(p.getLastCards()[2] == null) {
                System.out.println("Has not placed a Pawn yet on PHAISTOS!");
                return 0;
            } else {
                System.out.println("Has placed a Pawn on PHAISTOS!");
                return 1;
            }
        } else if (c.getAnaktoro() == CardAnaktoro.ZAKROS) {
            if(p.getLastCards()[3] == null) {
                System.out.println("Has not placed a Pawn yet on ZAKROS!");
                return 0;
            } else {
                System.out.println("Has placed a Pawn on ZAKROS!");
                return 1;
            }
        } else {
            System.out.println("Issue on afterSelectCard()!");
            return -1;
        }
    }

    /**
     * Method to find a Player's pawn on a specific Palace
     * Pre-Condition: The player has already played in this palace.
     * Post-Condition: None.
     * @param p Player whose pawn we are searching for.
     * @param index Indexing to distinguish the card.
     * @return a Pawn object.
     */
    public Pawn findPawnAtAnaktoro(Player p,int index) {        // FIND THE PLAYER"S PAWN IN AN ANAKTORO
        Pawn[] pawnsTemp = p.getPawns();
        Card c = p.getCards().get(index);
        for(int i=0; i<pawnsTemp.length;i++) {
            if(pawnsTemp[i].getAnaktoro() == c.getAnaktoro()) {
                return pawnsTemp[i];
            }
        }
        return null;
    }


    /**
     * Method to check whether we have a winner or not,
     *
     */
    public Player decideWinner() {
        if(getPlayers()[0].getScore() > getPlayers()[1].getScore()) {
            return getPlayers()[0];
        } else if(getPlayers()[0].getScore() < getPlayers()[1].getScore()) {
            return getPlayers()[1];
        }

        if(getPlayers()[0].getRareFindings().size() > getPlayers()[1].getRareFindings().size()) {
            return getPlayers()[0];
        } else if(getPlayers()[0].getRareFindings().size() < getPlayers()[1].getRareFindings().size()) {
            return getPlayers()[1];
        }

        if(getPlayers()[0].getFindings().size() > getPlayers()[1].getFindings().size()) {
            return getPlayers()[0];
        } else if(getPlayers()[0].getFindings().size() < getPlayers()[1].getFindings().size()) {
            return getPlayers()[1];
        }

        if(getPlayers()[0].getSnakeGods() > getPlayers()[1].getSnakeGods()) {
            return getPlayers()[0];
        } else if (getPlayers()[0].getSnakeGods() < getPlayers()[1].getSnakeGods()) {
            return getPlayers()[1];
        }
        return null;
    }

    /**
     * Method to retrieve the palace to an index.
     * @param cA Palace variable
     * @return an integer of which palace it is.
     */
    public int getAnaktoroIndexing(CardAnaktoro cA) {
        if(cA == CardAnaktoro.KNOSSOS) {
            return 0;
        } else if (cA == CardAnaktoro.MALIA) {
            return 1;
        } else if (cA == CardAnaktoro.PHAISTOS) {
            return 2;
        } else if (cA == CardAnaktoro.ZAKROS) {
            return 3;
        }
        System.out.println("ERROR AT get ANAKTOROINDEXING OF BOARD.JAVA");
        return -1;
    }

    public Card drawCard() {
        Card ret = getCards().get(getCards().size()-1);
        getCards().remove(getCards().size()-1);
        return ret;
    }

}
