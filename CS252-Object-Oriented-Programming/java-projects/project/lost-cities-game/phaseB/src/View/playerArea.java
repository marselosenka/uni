package View;

import Model.Cards.Card;
import Model.Cards.CardAnaktoro;
import Model.Finding.Fresco;
import Model.Gameplay.Board;
import Model.Pawns.Pawn;
import Model.Player;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class playerArea extends JLayeredPane {

    private JLabel myScore;
    private int score;
    private JLabel myAgalmatakia;
    private int agalmatakia;
    private JButton myToixografies;

    private JButton[] cards;
    private JButton[] lastCards;
    private ArrayList<Fresco> fresco;
    private JLabel faistos;
    private JLabel ring;
    private JLabel ruto;
    private JLabel kosmima;
    private String name;
    private JLabel playerInfo;

    public playerArea(ArrayList<Card> c1,Color c,String name) {
        cards = new JButton[8];

        this.agalmatakia = 0;
        this.score = 0;
        this.name = name;
        this.playerInfo = new JLabel();
        this.playerInfo.setBounds(50,120,400,30);
        this.playerInfo.setText(name+"- Διαθέσιμα πιόνια: ΟΛΑ");
        add(playerInfo,JLayeredPane.DEFAULT_LAYER);

        int x = 0;
        for(int i=0;i<8; i++) {
            JButton b = new JButton("button"+i);
            b.setBounds(50+x,20,60,100);
            x+=70;
            cards[i] = b;
            add(b,JLayeredPane.DEFAULT_LAYER);
        }
        for(int i=0;i<8;i++) {
            ImageIcon img = new ImageIcon(c1.get(i).getImage_path());
            Image imgOriginal = img.getImage();
            Image imgScaled = imgOriginal.getScaledInstance(60, 100, Image.SCALE_SMOOTH);
            img = new ImageIcon(imgScaled);
            cards[i].setIcon(img);
        }

        lastCards = new JButton[4];
        lastCards[0] = new JButton("Κνωσσός");
        lastCards[0].setBorder(new LineBorder(Color.RED));
        lastCards[1] = new JButton("Μάλια");
        lastCards[1].setBorder(new LineBorder(Color.yellow));
        lastCards[2] = new JButton("Φαιστός");
        lastCards[2].setBorder(new LineBorder(Color.gray));
        lastCards[3] = new JButton("Ζάκρος");
        lastCards[3].setBorder(new LineBorder(Color.blue));
        x+=30;
        for(int i=0;i<4;i++) {
            lastCards[i].setBounds(50+x,20,60,100);
            x+=70;
            add(lastCards[i],JLayeredPane.DEFAULT_LAYER);
        }
        this.fresco = new ArrayList<Fresco>();
        this.faistos = new JLabel();
        this.ring = new JLabel();
        this.ruto = new JLabel();
        this.kosmima = new JLabel();
        initRareFindings();
        add(faistos,JLayeredPane.DEFAULT_LAYER);
        add(ring,JLayeredPane.DEFAULT_LAYER);
        add(ruto,JLayeredPane.DEFAULT_LAYER);
        add(kosmima,JLayeredPane.DEFAULT_LAYER);

        myScore = new JLabel("Το σκόρ μου: "+getScore()+" πόντοι");
        myScore.setBounds(50+x,20,200,60);
        add(myScore);

        myToixografies = new JButton("Οι τοιχογραφίες μου");
        myToixografies.setBounds(50+x,70,150,30);
        initToixografies();
        add(myToixografies);

        myAgalmatakia = new JLabel("Αγαλματάκια: "+ getAgalmatakia());
        myAgalmatakia.setBounds(50+x,110,200,60);
        add(myAgalmatakia);


        setBorder(new LineBorder(c));
        setVisible(true);

    }
    public void initToixografies() {

        myToixografies.addActionListener(e -> {
            JFrame win = new JFrame("MY FRESCO");
            win.setSize(1000, 700);
            win.setLayout(new BorderLayout());
            win.setLocationRelativeTo(null);

            JPanel panel = new JPanel(new GridLayout(((int) fresco.size()), 4));
            for (int i = 0; i < fresco.size(); i++) {
                JButton button1 = new JButton(fresco.get(i).getImage_path());
                JButton button2 = new JButton();
                button1.setPreferredSize(new Dimension(200, 200));
                button2.setPreferredSize(new Dimension(200, 200));
                button2.setIcon(new ImageIcon(fresco.get(i).getImage_path()));
                panel.add(button1);
                panel.add(button2);
            }
            JScrollPane s = new JScrollPane(panel);
            s.setPreferredSize(new Dimension(200, 200));
            win.add(s);
            win.setVisible(true);
        });
    }
    public void initRareFindings() {
        ImageIcon diskos = new ImageIcon("./src/project_assets/images/findings/grey/diskos.png");
        Image diskosOriginal = diskos.getImage();
        Image diskosScaled = diskosOriginal.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        diskos = new ImageIcon(diskosScaled);
        this.faistos.setBounds(lastCards[0].getX()+15,120,30,30);
        this.faistos.setIcon(diskos);

        ImageIcon ringI = new ImageIcon("./src/project_assets/images/findings/grey/ring.png");
        Image ringOriginal = ringI.getImage();
        Image ringScaled = ringOriginal.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ringI = new ImageIcon(ringScaled);
        this.ring.setBounds(lastCards[1].getX()+15,120,30,30);
        this.ring.setIcon(ringI);

        ImageIcon rutoI = new ImageIcon("./src/project_assets/images/findings/grey/ruto.png");
        Image rutoOriginal = rutoI.getImage();
        Image rutoScaled = rutoOriginal.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        rutoI = new ImageIcon(rutoScaled);
        this.ruto.setBounds(lastCards[2].getX()+15,120,30,30);
        this.ruto.setIcon(rutoI);

        ImageIcon kosmimaI = new ImageIcon("./src/project_assets/images/findings/grey/kosmima.png");
        Image kosmimaOriginal = kosmimaI.getImage();
        Image kosmimaScaled = kosmimaOriginal.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        kosmimaI = new ImageIcon(kosmimaScaled);
        this.kosmima.setBounds(lastCards[3].getX()+15,120,30,30);
        this.kosmima.setIcon(kosmimaI);
    }

    public void disableCard(int i) {
        cards[i].setIcon(null);
        cards[i].setEnabled(false);
    }
    public void disable() {
        for(JButton card : cards) {
            card.setEnabled(false);
        }
    }
    public void enable() {
        for(JButton card : cards) {
            card.setEnabled(true);
        }
    }
    public void editLastCard(Card[] playerLast) {
        for(int i=0;i<4;i++) {
            if(playerLast[i] != null) {
                ImageIcon img = new ImageIcon(playerLast[i].getImage_path());
                Image imgOriginal = img.getImage();
                Image imgScaled = imgOriginal.getScaledInstance(60, 100, Image.SCALE_SMOOTH);
                img = new ImageIcon(imgScaled);
                lastCards[i].setIcon(img);
            }
        }
    }

    public void setFresco(ArrayList<Fresco> fresco) {
        this.fresco = fresco;
    }

    public int getScore() {
        return score;
    }

    public int getAgalmatakia() {
        return agalmatakia;
    }

    @Override
    public String getName() {
        return name;
    }

    public JLabel getPlayerInfo() {
        return playerInfo;
    }

    public void setAgalmatakia(int agalmatakia) {
        this.agalmatakia = agalmatakia;
    }

    public void setMyScore(JLabel myScore) {
        this.myScore = myScore;
    }

    public void setPlayerInfo(JLabel playerInfo) {
        this.playerInfo = playerInfo;
    }


    public JButton[] getCards() {
        return cards;
    }

    public void updateCards(ArrayList<Card> c1) {
        for(int i=0;i<8;i++) {
            if(c1.get(i) != null) {
                ImageIcon img = new ImageIcon(c1.get(i).getImage_path());
                Image imgOriginal = img.getImage();
                Image imgScaled = imgOriginal.getScaledInstance(60, 100, Image.SCALE_SMOOTH);
                img = new ImageIcon(imgScaled);
                cards[i].setIcon(img);
            } else {
                System.out.println("did you get here?");
                cards[i].setIcon(null);
            }
        }
    }
    public void updateDisplay(Player p) {
        myScore.setText("Το σκόρ μου: "+p.getScore());
        myAgalmatakia.setText("Αγαλματάκια: "+p.getSnakeGods());

        if(!p.getFindings().isEmpty()) {
            for(int i = 0; i< p.getRareFindings().size(); i++) {

                if(Objects.equals(p.getRareFindings().get(i).getName(), "Δίσκος φαιστού")) {
                    ImageIcon diskos = new ImageIcon(p.getRareFindings().get(i).getImage_path());
                    Image diskosOriginal = diskos.getImage();
                    Image diskosScaled = diskosOriginal.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                    diskos = new ImageIcon(diskosScaled);
                    this.faistos.setIcon(diskos);
                } else if(Objects.equals(p.getRareFindings().get(i).getName(), "Κόσμημα Μαλίων")) {
                    ImageIcon kosmimaI = new ImageIcon(p.getRareFindings().get(i).getImage_path());
                    Image kosmimaOriginal = kosmimaI.getImage();
                    Image kosmimaScaled = kosmimaOriginal.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                    kosmimaI = new ImageIcon(kosmimaScaled);
                    this.kosmima.setIcon(kosmimaI);
                } else if(Objects.equals(p.getRareFindings().get(i).getName(), "Ρυτό Ζάκρου")) {
                    ImageIcon rutoI = new ImageIcon(p.getRareFindings().get(i).getImage_path());
                    Image rutoOriginal = rutoI.getImage();
                    Image rutoScaled = rutoOriginal.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                    rutoI = new ImageIcon(rutoScaled);
                    this.ruto.setIcon(rutoI);
                } else if(Objects.equals(p.getRareFindings().get(i).getName(), "Δαχτυλίδι του Μίνωα")) {
                    ImageIcon ringI = new ImageIcon(p.getRareFindings().get(i).getImage_path());
                    Image ringOriginal = ringI.getImage();
                    Image ringScaled = ringOriginal.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                    ringI = new ImageIcon(ringScaled);
                    this.ring.setIcon(ringI);
                }
            }
        }
        return;
    }
    public void updatePlayerInfo(String s) {

        playerInfo.setText(getName() + "-" +" Διαθέσιμα Πιόνια: "+s);
    }
}
