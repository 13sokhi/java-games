package tetris_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Frame extends JFrame implements KeyListener {
    GamePanel gamePanel;

    private int frameWidth = 400;
    private int frameHeight = 550;

    public Frame(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Tetris Game");

        this.setLayout(new BorderLayout());
        this.setSize(frameWidth, frameHeight);
        this.setLocationRelativeTo(null);

        this.add(gamePanel);
        this.addKeyListener(this);

//        gamePanel.gm.startGame();
//        gamePanel.gameLoop.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_DOWN && gamePanel.gm.gameRunning) {
            gamePanel.gm.currentShape.moveDown(gamePanel.gm.unitSize);
            gamePanel.gm.checkBottomCollision();
            repaint();
        }
        if (e.getKeyCode()==KeyEvent.VK_LEFT && gamePanel.gm.gameRunning) {
            gamePanel.gm.currentShape.moveLeft(gamePanel.gm.unitSize);
            gamePanel.gm.checkWallCollision();
            repaint();
        }
        if (e.getKeyCode()==KeyEvent.VK_RIGHT && gamePanel.gm.gameRunning) {
            gamePanel.gm.currentShape.moveRight(gamePanel.gm.unitSize);
            gamePanel.gm.checkWallCollision();
            repaint();
        }
        if (e.getKeyCode()==KeyEvent.VK_UP && gamePanel.gm.gameRunning) {
            gamePanel.gm.currentShape.rotate(GameManager.unitSize);
            gamePanel.gm.checkRotationCollision();
            repaint(); // for some reason, gamePanel.repaint() does not work
        }
        if (e.getKeyCode()==KeyEvent.VK_ENTER && !gamePanel.gm.gameStated) {
            System.out.println("Enter pressed");
            gamePanel.infoLabel.setText("Press P to Pause");
            gamePanel.gm.startGame();
            gamePanel.gameLoop.start();
        }
        if (e.getKeyCode()==KeyEvent.VK_P) {
            System.out.println("Paused");
            if (gamePanel.gm.gamePaused) {
                gamePanel.gm.unPauseGame();
                gamePanel.infoLabel.setText("Press P to Pause");
            } else {
                gamePanel.infoLabel.setText("Press P to Resume");
                gamePanel.gm.pauseGame();
            }
        }
        if (e.getKeyCode()==KeyEvent.VK_R) {
            System.out.println("Restarted");
            this.dispose();
            new Frame(new GamePanel(new GameManager()));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        new Frame(new GamePanel(new GameManager()));
    }
}
