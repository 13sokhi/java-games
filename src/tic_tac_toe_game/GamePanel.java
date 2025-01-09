package tic_tac_toe_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    int WIDTH;
    int HEIGHT;
    int LINE_THICKNESS = 8;
    int NO_OF_MARKED_BLOCKS = 0; // to keep track of how many blocks in game(out of 9) have been clicked

    Boolean gameEnd = false;

    JFrame frame;
    int FINAL_PANEL_WIDTH;
    int FINAL_PANEL_HEIGHT;

    Color LINE_COLOR = new Color(175, 155, 78);
    Color BUTTON_BG_COLOR = new Color(0,0,0);
    Color P1_COLOR = new Color(255,0,0);
    Color P2_COLOR = new Color(0,0,255);

    Color [] PLAYER_COLORS = {P1_COLOR, P2_COLOR};
    Color CURRENT_COLOR;

    Font BUTTON_FONT = new Font("Sans Serif", Font.BOLD, 96);

    JButton button1 = new JButton();
    JButton button2 = new JButton();
    JButton button3 = new JButton();
    JButton button4 = new JButton();
    JButton button5 = new JButton();
    JButton button6 = new JButton();
    JButton button7 = new JButton();
    JButton button8 = new JButton();
    JButton button9 = new JButton();

    JButton [] BUTTONS = {button1,button2,button3,button4,button5,button6,button7,button8,button9};

    Color bgColor = new Color(255,0,0);

    char [] SYMBOLS = {'O','X'};
    char CURRENT_SYMBOL;

    String [] PLAYERS = {"Player 1","Player 2"};
    String CURRENT_PLAYER;
    String WINNER = null;
    String RESULT; // store the result of game (like winning player and if game ties)

    InfoPanel infoPanel;

    int [][] WINNING_PAIRS = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    char [] CURRENT_TABLE = {
            'A','A','A',
            'A','A','A',
            'A','A','A'
    };

    public GamePanel(int width, int height, JFrame frame, InfoPanel infoPanel) {
        this.infoPanel = infoPanel;
        this.frame = frame;
        this.FINAL_PANEL_WIDTH = infoPanel.getWidth();
        this.FINAL_PANEL_HEIGHT = infoPanel.getHeight();

        this.WIDTH = width;
        this.HEIGHT = height;

        frame.add(this, BorderLayout.SOUTH);
        this.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
        this.setLayout(new GridLayout(3,3, LINE_THICKNESS, LINE_THICKNESS));

        this.setBackground(LINE_COLOR);

        this.add(button1);
        this.add(button2);
        this.add(button3);
        this.add(button4);
        this.add(button5);
        this.add(button6);
        this.add(button7);
        this.add(button8);
        this.add(button9);

        button1.setBackground(BUTTON_BG_COLOR);
        button2.setBackground(BUTTON_BG_COLOR);
        button3.setBackground(BUTTON_BG_COLOR);
        button4.setBackground(BUTTON_BG_COLOR);
        button5.setBackground(BUTTON_BG_COLOR);
        button6.setBackground(BUTTON_BG_COLOR);
        button7.setBackground(BUTTON_BG_COLOR);
        button8.setBackground(BUTTON_BG_COLOR);
        button9.setBackground(BUTTON_BG_COLOR);
        button1.setFont(BUTTON_FONT);
        button2.setFont(BUTTON_FONT);
        button3.setFont(BUTTON_FONT);
        button4.setFont(BUTTON_FONT);
        button5.setFont(BUTTON_FONT);
        button6.setFont(BUTTON_FONT);
        button7.setFont(BUTTON_FONT);
        button8.setFont(BUTTON_FONT);
        button9.setFont(BUTTON_FONT);
        button1.setFocusable(false);
        button2.setFocusable(false);
        button3.setFocusable(false);
        button4.setFocusable(false);
        button5.setFocusable(false);
        button6.setFocusable(false);
        button7.setFocusable(false);
        button8.setFocusable(false);
        button9.setFocusable(false);

        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        button4.addActionListener(this);
        button5.addActionListener(this);
        button6.addActionListener(this);
        button7.addActionListener(this);
        button8.addActionListener(this);
        button9.addActionListener(this);
    }

    public void startGame() {
        decideFirstPlayer();
    }

    public void decideFirstPlayer() {
        int randomNo = new Random().nextInt(2);
        CURRENT_PLAYER = PLAYERS[randomNo];
        CURRENT_SYMBOL = SYMBOLS[randomNo];
        CURRENT_COLOR = PLAYER_COLORS[randomNo];
        infoPanel.turnLabel.setText(String.format("%s(%s) Turn",CURRENT_PLAYER,CURRENT_SYMBOL));
    }

    public boolean preventReClick(int clickedButton) { // method made to prevent re-clicking of already used button (returns true if user clicked already used button)
        if (CURRENT_TABLE[clickedButton-1] == 'O' || CURRENT_TABLE[clickedButton-1] == 'X') {
            return true;
        }
        return false;
    }

    public void updateMarking(int clickedButton) {
        CURRENT_TABLE[clickedButton-1] = CURRENT_SYMBOL;
    }

    public void checkTie() {
        if (NO_OF_MARKED_BLOCKS == 9 && WINNER == null) {
            System.out.println("Game is tied!");

            RESULT = "Game is Tied!";
            infoPanel.turnLabel.setText(RESULT);
            gameEnd = true;
            gameEnd();
        }
    }

    public void checkWin() {
        for (int [] arr: WINNING_PAIRS) {
            int flag = 0;
            for (int index: arr) {
                if (CURRENT_TABLE[index] != CURRENT_SYMBOL) {
                    flag ++;
                }
            }
            if (flag == 0) { // this code will run if any player wins
                WINNER = CURRENT_PLAYER;
                RESULT = String.format("%s(%s) Won!",WINNER,CURRENT_SYMBOL);
                infoPanel.turnLabel.setText(RESULT);
                gameEnd = true;
                disableRest(arr); // passing the matched pair thar resulted in winning
                gameEnd();

                System.out.println(RESULT);
            }
        }
    }

    public void disableRest(int [] matchedPair) {
        for (JButton eachButton: BUTTONS) {
            eachButton.setEnabled(false);
        }
        for (int i:matchedPair) {
            BUTTONS[i].setEnabled(true);
            BUTTONS[i].setBackground(Color.GREEN);
        }
    }

    public void changeCurrents() { // method made to change CURRENT_PLAYER, CURRENT_SYMBOL, CURRENT_COLOR
        if (CURRENT_PLAYER.equals(PLAYERS[0])) {
            CURRENT_PLAYER = PLAYERS[1];
            CURRENT_SYMBOL = SYMBOLS[1];
            CURRENT_COLOR = PLAYER_COLORS[1];
        } else {
            CURRENT_PLAYER = PLAYERS[0];
            CURRENT_SYMBOL = SYMBOLS[0];
            CURRENT_COLOR = PLAYER_COLORS[0];
        }
        infoPanel.turnLabel.setText(String.format("%s(%s) Turn",CURRENT_PLAYER,CURRENT_SYMBOL));
    }

    public void buttonClicked(JButton clickedButton, int clickedButtonNo) {
        if (!preventReClick(clickedButtonNo)) {
            NO_OF_MARKED_BLOCKS++;
            updateMarking(clickedButtonNo);
            clickedButton.setForeground(CURRENT_COLOR);
            clickedButton.setText(""+CURRENT_SYMBOL);
            checkTie();
            checkWin();

            if (!gameEnd) {
                changeCurrents();
            }
        }
    }

    public void gameEnd() {
        infoPanel.setVisible(false);
        FinalPanel finalPanel = new FinalPanel(frame, infoPanel.getWidth(), infoPanel.getHeight(), infoPanel.getBackground(), infoPanel.turnLabel.getForeground(),RESULT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            buttonClicked(button1, 1);
        }
        if (e.getSource() == button2) {
            buttonClicked(button2, 2);
        }
        if (e.getSource() == button3) {
            buttonClicked(button3, 3);
        }
        if (e.getSource() == button4) {
            buttonClicked(button4, 4);
        }
        if (e.getSource() == button5) {
            buttonClicked(button5, 5);
        }
        if (e.getSource() == button6) {
            buttonClicked(button6, 6);
        }
        if (e.getSource() == button7) {
            buttonClicked(button7, 7);
        }
        if (e.getSource() == button8) {
            buttonClicked(button8, 8);
        }
        if (e.getSource() == button9) {
            buttonClicked(button9, 9);
        }
    }
}
