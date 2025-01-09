package pong_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;

public class Game extends JFrame implements ActionListener, ItemListener {
    PanelClass panel = new PanelClass();
    static JComboBox speedBox ;
    static JComboBox sizeBox ;
    static JComboBox matchPointsBox ;
    static JComboBox difficultyBox ;

    public Game() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        this.setLayout(new BorderLayout());
        this.add(panel.infoPanel , BorderLayout.NORTH);
        this.add(panel , BorderLayout.SOUTH);
        this.add(panel.finalPanel);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        panel.play.addActionListener(this);
        panel.exit.addActionListener(this);
        panel.settings.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panel.play) {
            this.dispose();
            new Game();
        }
        if (e.getSource() == panel.exit) {
            this.dispose();
        }
        if (e.getSource() == panel.settings) {
            panel.pauseGame();

            String [] speedArray = {"Slow","Normal","Fast","Very fast"};
            String [] sizeArray = {"Small","Normal","Large"};
            String [] matchPointsArray = {"5","6","7","8","9","10","11","12"};
            String [] difficultyArray = {"Easy","Normal","Tough","Hardest"};

            JFrame frame = new JFrame("Settings");
            JLabel difficulty = new JLabel("Difficulty :");
            JLabel matchPoints = new JLabel("Match length :");
            JLabel paddle1Color = new JLabel("Paddle 1 Color :      ");
            JLabel paddle2Color = new JLabel("Paddle 2 Color :      ");
            JLabel ballColor = new JLabel("Ball Color :     ");
            JLabel ballSpeed = new JLabel("Ball speed :       ");
            JLabel size = new JLabel("Size :         ");

            JButton paddle1Button = new JButton("Select");
            JButton paddle2Button = new JButton("Select");
            JButton ballButton = new JButton("Select");
            speedBox = new JComboBox(speedArray);
            sizeBox = new JComboBox(sizeArray);
            matchPointsBox = new JComboBox(matchPointsArray);
            difficultyBox = new JComboBox(difficultyArray);

            speedBox.setSelectedItem("Normal");
            sizeBox.setSelectedItem("Normal");
            difficultyBox.setSelectedItem("Normal");

            frame.setVisible(true);

            frame.setLayout(new FlowLayout());
            frame.add(matchPoints);
            frame.add(matchPointsBox);
            frame.add(paddle1Color);
            frame.add(paddle1Button);
            frame.add(paddle2Color);
            frame.add(paddle2Button);
            frame.add(ballColor);
            frame.add(ballButton);
            frame.add(ballSpeed);
            frame.add(speedBox);
            frame.add(size);
            frame.add(sizeBox);
            frame.add(difficulty);
            frame.add(difficultyBox);

            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    if (panel.gameStarted) {
                        panel.resumeGame();
                    }
                }
            });

            ActionListener buttonListener = (ActionEvent evt) -> {
                if (evt.getSource() == paddle1Button) {
                    Color c = JColorChooser.showDialog(null, "Select Paddle 1 color :", null);
                    panel.paddle1Color = c;
                }
                if (evt.getSource() == paddle2Button) {
                    Color c = JColorChooser.showDialog(null, "Select Paddle 1 color :", null);
                    panel.paddle2Color = c;
                }
                if (evt.getSource() == ballButton) {
                    Color c = JColorChooser.showDialog(null, "Select Paddle 1 color :", null);
                    panel.ballColor = c;
                }
            };

            paddle1Button.addActionListener(buttonListener);
            paddle2Button.addActionListener(buttonListener);
            ballButton.addActionListener(buttonListener);
            speedBox.addItemListener(this);
            sizeBox.addItemListener(this);
            matchPointsBox.addItemListener(this);
            difficultyBox.addItemListener(this);

            frame.setSize(220,270);
            frame.setLocationRelativeTo(null);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        String selectedSpeed = (String)speedBox.getSelectedItem();
        String selectedSize = (String)sizeBox.getSelectedItem();
        int selectedMatchPoint = Integer.parseInt((String)matchPointsBox.getSelectedItem());
        String selectedDifficulty = (String)difficultyBox.getSelectedItem();

        switch (selectedSpeed) {
            case "Slow" :
                panel.ballSpeedInitialRange = 15;
                panel.ballSpeedFinalRange = 25;
                break;
            case "Normal" :
                panel.ballSpeedInitialRange = 25;
                panel.ballSpeedFinalRange = 35;
                break;
            case "Fast" :
                panel.ballSpeedInitialRange = 35;
                panel.ballSpeedFinalRange = 45;
                panel.PADDLE_SPEED = 40;
                break;
            case "Very fast" :
                panel.ballSpeedInitialRange = 45;
                panel.ballSpeedFinalRange = 55;
                panel.PADDLE_SPEED = 50;
                break;
        }
        switch (selectedSize) {
            case "Small" :
                panel.UNIT_SIZE = 10;
                break;
            case "Normal" :
                panel.UNIT_SIZE = 20;
                break;
            case "Large" :
                panel.UNIT_SIZE = 30;
                break;
        }
        switch (selectedDifficulty) {
            case "Easy" :
                panel.paddleLength = panel.UNIT_SIZE*6;
                break;
            case "Normal" :
                panel.paddleLength = panel.UNIT_SIZE*4;

                break;
            case "Tough" :
                panel.paddleLength = panel.UNIT_SIZE*3;

                break;
            case "hardest" :
                panel.paddleLength = panel.UNIT_SIZE*2;
                break;
        }
        panel.totalMatchPoints = selectedMatchPoint;
        // System.out.println(selectedMatchPoint);
    }
}