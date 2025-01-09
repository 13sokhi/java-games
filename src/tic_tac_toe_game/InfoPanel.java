package tic_tac_toe_game;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    int WIDTH;
    int HEIGHT;

    public JLabel turnLabel = new JLabel(); // to display the turn of current player

    public Color bgColor = new Color(175, 155, 78);
    public Color fgColor = new Color(58, 44, 44);

    Font labelFont = new Font("Monospace", Font.PLAIN, 22);


    public InfoPanel(int width, int height, JFrame frame) {
        this.WIDTH = width;
        this.HEIGHT = height;

        frame.add(this, BorderLayout.NORTH);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        this.setBackground(bgColor);
        this.setForeground(fgColor);

        this.add(turnLabel);
        turnLabel.setText("Hello World!");
        turnLabel.setFont(labelFont);
    }
}
