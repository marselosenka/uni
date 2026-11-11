package View;

import Model.Pawns.Pawn;
import Model.Player;
import Model.Position.Position;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Table extends JLayeredPane {
    private String image;
    private JButton cards;

    private JLabel cardsInfo;
    private int availableCards;
    private int checkPoints;
    private Player player_turn;
    private JLabel paths[][];

    private Map<Pawn, JLabel> pawnLabels;

    public Table(Position[][] board) {
        this.image = "./src/project_assets/images/background.jpg";
        JLabel background = new JLabel(new ImageIcon(image));
        add(background,JLayeredPane.DEFAULT_LAYER);
        background.setBounds(0,0,1300,520);


        this.cards = new JButton();
        cards.setBounds(60,290,75,125);
        ImageIcon back = new ImageIcon("./src/project_assets/images/cards/backCard.jpg");
        Image img2 = back.getImage();
        Image backScaled = img2.getScaledInstance(75,125,Image.SCALE_SMOOTH);
        back = new ImageIcon(backScaled);
        cards.setIcon(back);
        add(cards,PALETTE_LAYER);

        this.cardsInfo = new JLabel("<html>Available Cards: "+0+"<br>"
        +"Check Points: "+0+"<br>"
        +"Turn: "+"Player X");
        cardsInfo.setBounds(40,420,125,60);
        cardsInfo.setBackground(Color.WHITE);
        cardsInfo.setOpaque(true);  // background visibility
        cardsInfo.setBorder(new LineBorder(Color.black));
        add(cardsInfo,PALETTE_LAYER);

        paths = new JLabel[4][9];
        int x = 0;
        int y = 0;
        for(int i = 0;i<4;i++) {
            for(int j=0;j<9;j++) {
                if (i == 0) {
                    JLabel headerLabel = new JLabel(board[i][j].getPoints()+" Points", SwingConstants.CENTER);
                    headerLabel.setBounds(450 + x, 20, 65, 20);
                    add(headerLabel, JLayeredPane.PALETTE_LAYER);
                }
                ImageIcon img = new ImageIcon(board[i][j].getImage_path());
                Image imgOriginal = img.getImage();
                Image imgScaled = imgOriginal.getScaledInstance(65, 65, Image.SCALE_SMOOTH);
                img = new ImageIcon(imgScaled);
                paths[i][j] = new JLabel(i+" "+j);
                paths[i][j].setIcon(img);
                paths[i][j].setBounds(450+x,60+y,65,65);
                add(paths[i][j],JLayeredPane.PALETTE_LAYER);
                x+=80;
            }
            y+=100;
            x =0;

        }
        this.pawnLabels = new HashMap<>();
    }

    /**
     * Accessor: Accessess the available cards.
     * Pre-Condition: None.
     * @return
     */
    public int getAvailableCards() {
        return availableCards;
    }

    public void setAvailableCards(int availableCards) {
        this.availableCards = availableCards;
    }
    /**
     * Accessor: Accessess the Check points number.
     * Pre-Condition: None.
     * @return
     */
    public int getCheckPoints() {
        return checkPoints;
    }

    public void setCheckPoints(int checkPoints) {
        this.checkPoints = checkPoints;
    }

    public Player getPlayer_turn() {
        return player_turn;
    }

    public void setPlayer_turn(Player player_turn) {
        this.player_turn = player_turn;
    }


    public JButton getCards() {
        return cards;
    }

    public void updateCardsInfo() {
        cardsInfo.setText("<html>Available Cards: "+getAvailableCards()+"<br>"
                +"Check Points: "+getCheckPoints()+"<br>"
                +"Turn: "+getPlayer_turn().getName());
    }

    /**
     * Method to update the pawns on the table.
     * Pre-Condition: None.
     * Post-Condition: Update the Pawn position successfully.
     * @param imgPath not used.
     * @param i Row of the table
     * @param j Column of the table
     * @param pawn  Pawn we are updating
     * @param c Color of border
     * @param player Player the pawn belongs to
     */
    public void pawnPosition(String imgPath, int i, int j, Pawn pawn,Color c,Player player) {
        int x;
        if(player.getName().equals("Player 1")) {
            x = 0;
        } else {
            x = 40;
        }
        if(pawn.isHidden()) {
            imgPath = pawn.getImage_path_hidden();
        } else {
            imgPath = pawn.getImage_path();
        }
        if(pawnLabels.containsKey(pawn)) {
            JLabel existingPawnLabel = pawnLabels.get(pawn);
            ImageIcon pawnImg = new ImageIcon(imgPath);
            Image pawnImgTemp = pawnImg.getImage();
            Image pawnFinal = pawnImgTemp.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            pawnImg = new ImageIcon(pawnFinal);
            existingPawnLabel.setIcon(pawnImg);
            existingPawnLabel.setBounds(paths[i][j].getX() + x, paths[i][j].getY(), 30, 30); // Update position
            remove(existingPawnLabel);
            add(existingPawnLabel, JLayeredPane.DRAG_LAYER);

        } else {
            ImageIcon pawnImg = new ImageIcon(imgPath);
            Image pawnImgTemp = pawnImg.getImage();
            Image pawnFinal = pawnImgTemp.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            pawnImg = new ImageIcon(pawnFinal);
            JLabel p = new JLabel(pawnImg);
            p.setBounds(paths[i][j].getX()+x, paths[i][j].getY(), 30, 30);
            p.setBorder(new LineBorder(c));
            pawnLabels.put(pawn,p);
            add(p, JLayeredPane.DRAG_LAYER);
        }
        repaint();
    }

}
