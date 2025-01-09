package tic_tac_toe_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FinalPanel extends JPanel implements ActionListener {
    int WIDTH;
    int HEIGHT;

    JLabel label = new JLabel();
    JFrame frame;

    JButton playAgainButton = new JButton("Play Again");
    JButton exitButton = new JButton("Exit");

    Color BG_COLOR;
    Color FG_COLOR;
    Color BUTTON_BG_COLOR = new Color(0,0,0);
    Color BUTTON_FG_COLOR = new Color(243, 137, 44);

    Font LABEL_FONT = new Font("Monospace",Font.BOLD,22);
    Font BUTTON_FONT = new Font("Monospace",Font.BOLD,16);

    public FinalPanel(JFrame frame, int width, int height, Color bgColor, Color fgColor, String message) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.BG_COLOR = bgColor;
        this.FG_COLOR = fgColor;
        this.frame = frame;

        frame.add(this, BorderLayout.NORTH);
        this.setBackground(BG_COLOR);
        this.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));

        this.add(label);
        this.add(playAgainButton);
        this.add(exitButton);

        label.setText(message);

        playAgainButton.setBackground(BUTTON_BG_COLOR);
        exitButton.setBackground(BUTTON_BG_COLOR);
        playAgainButton.setForeground(BUTTON_FG_COLOR);
        exitButton.setForeground(BUTTON_FG_COLOR);

        label.setFont(LABEL_FONT);
        playAgainButton.setFont(BUTTON_FONT);
        exitButton.setFont(BUTTON_FONT);

        playAgainButton.setFocusable(false);
        exitButton.setFocusable(false);

        playAgainButton.addActionListener(this);
        exitButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playAgainButton) {
            frame.dispose();
            Frame newFrame = new Frame();
            newFrame.startGame();
        }
        if (e.getSource() == exitButton) {
            frame.dispose();
        }
    }
}
