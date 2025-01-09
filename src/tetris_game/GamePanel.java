package tetris_game;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    GameManager gm;
    JLabel pointsLabel = new JLabel("Points: 0");
    JLabel infoLabel = new JLabel("Press ENTER to start");

    Color BG_COLOR = Color.BLACK;

    Thread gameLoop;
    int FPS = 2;

    public GamePanel(GameManager gameManager) {
        this.gm = gameManager;
        this.setBackground(BG_COLOR);
        gameLoop = new Thread(this);

        this.setLayout(null);
        this.add(pointsLabel);
        this.add(infoLabel);

        infoLabel.setBounds(20, 10, 200, 25);
        pointsLabel.setBounds(280, 10, 100, 25);

        infoLabel.setFont(new Font("Aerial", Font.PLAIN, 14));
        pointsLabel.setFont(new Font("Aerial", Font.PLAIN, 14));
        infoLabel.setForeground(Color.YELLOW);
        pointsLabel.setForeground(Color.GREEN);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        gm.draw(g);
    }

    @Override
    public void run() {
        while (!gm.gameOver && !gm.gameWon) {
            int updateInterval = 1000000000 / FPS;
            int nextUpdateInterval = (int) System.nanoTime() + updateInterval;
            System.out.println('h');
            while (gm.gameRunning) {
                update();
                repaint();
//                System.out.println("Game loop running...");

                int remainingTime = nextUpdateInterval - (int) System.nanoTime();
                try {
                    Thread.sleep(remainingTime / 1000000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                nextUpdateInterval += updateInterval;
            }
        }
        if (gm.gameOver) {
            infoLabel.setText("Game Over! R to Restart");
        } else if (gm.gameWon) {
            infoLabel.setText("You Won!");
        }
    }

    public void update() {
        gm.update();
        pointsLabel.setText("Points: " + gm.points);
    }
}
