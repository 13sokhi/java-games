package tic_tac_toe_game;

import javax.swing.*;

public class Frame extends JFrame {
    int GAME_WIDTH = 400;
    int GAME_HEIGHT = 400;
    int INFO_HEIGHT = 50;
    int TITLE_BAR_HEIGHT = 36;
    int TOTAL_HEIGHT = GAME_HEIGHT+INFO_HEIGHT+TITLE_BAR_HEIGHT;

    InfoPanel infoPanel;
    GamePanel gamePanel;

    public Frame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(GAME_WIDTH, TOTAL_HEIGHT);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setTitle("Tic-Tac-Toe");
        this.setResizable(false);

        infoPanel = new InfoPanel(GAME_WIDTH, INFO_HEIGHT, this);
        gamePanel = new GamePanel(GAME_WIDTH, GAME_HEIGHT, this, infoPanel);
    }

    public void startGame() {
        gamePanel.startGame();
    }
}
