package snake_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Game extends JFrame implements ActionListener {
    PanelClass panel = new PanelClass();

    public Game() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setTitle("Snake Game");

        this.setLayout(new BorderLayout());
        this.add(panel , BorderLayout.SOUTH);
        this.add(panel.infoPanel , BorderLayout.NORTH);
        this.add(panel.finalPanel);
        panel.exitButton.addActionListener(this);
        panel.playButton.addActionListener(this);
        panel.settingsButton.addActionListener(this);

        this.pack();
        this.setLocationRelativeTo(null);
    }

    public void settingsFrame() {
        JFrame frame = new JFrame("Configure settings");

        String [] speed = {"Slow","Normal","Fast","High"};
        String [] size = {"Small","Normal","Larger"};

        JLabel snakeHeadColor = new JLabel("Snake head color : ");
        JLabel snakeBodyColor = new JLabel("Snake body color : ");
        JLabel snakeSpeed = new JLabel("Snake speed : ");
        JLabel snakeSize = new JLabel("Snake size : ");

        JButton colorButton1 = new JButton("Select");
        JButton colorButton2 = new JButton("Select");
        JComboBox speedBox = new JComboBox(speed);
        JComboBox sizeBox = new JComboBox(size);
        Font optionFont = new Font(null,Font.PLAIN,16);

        frame.setLayout(new FlowLayout());
        frame.setVisible(true);
        frame.setPreferredSize(new Dimension(550, 150));

        frame.add(snakeHeadColor);
        frame.add(colorButton1);
        frame.add(snakeBodyColor);
        frame.add(colorButton2);
        frame.add(snakeSpeed);
        frame.add(speedBox);
        frame.add(snakeSize);
        frame.add(sizeBox);
        snakeHeadColor.setFont(optionFont);
        snakeBodyColor.setFont(optionFont);
        snakeSpeed.setFont(optionFont);
        snakeSize.setFont(optionFont);

        speedBox.setSelectedItem("Normal");
        sizeBox.setSelectedItem("Normal");

        frame.pack();
        frame.setLocationRelativeTo(null);

        // Aynonymous classes for 2 listeners:
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == colorButton1) {
                    Color c = JColorChooser.showDialog(null, "Select snake head color", null);
                    panel.snakeHeadColor = c;
                }
                if (e.getSource() == colorButton2) {
                    Color c = JColorChooser.showDialog(null, "Select snake body color", null);
                    panel.snakeBodyColor = c;
                }
            }
        };
        ItemListener itemListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String selectedSpeed = (String)speedBox.getSelectedItem();
                // System.out.println(selectedSpeed);
                String selectedSize = (String)sizeBox.getSelectedItem();
                // System.out.println(selectedSize);

                switch (selectedSpeed) {
                    case "Slow" :
                        panel.delayForTimer = 270;
                        break;
                    case "Normal" :
                        panel.delayForTimer = 125;
                        break;
                    case "Fast" :
                        panel.delayForTimer = 80;
                        break;
                    case "High" :
                        panel.delayForTimer = 50;
                        break;
                }
                switch (selectedSize) {
                    case "Small" :
                        panel.UNIT_SIZE = 20;
                        break;
                    case "Normal" :
                        panel.UNIT_SIZE = 30;
                        break;
                    case "Larger" :
                        panel.UNIT_SIZE = 40;
                        break;
                }
                panel.makeApple();
                repaint();
            }
        };
        speedBox.addItemListener(itemListener);
        sizeBox.addItemListener(itemListener);
        colorButton1.addActionListener(actionListener);
        colorButton2.addActionListener(actionListener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panel.exitButton) {this.dispose();}
        if (e.getSource() == panel.playButton) {this.dispose(); new Game();}
        if (e.getSource() == panel.settingsButton) {settingsFrame(); panel.pauseGame();}
    }
}