package tetris_game;

import javax.swing.*;
import java.awt.*;

public class PointsPanel extends JPanel {
    private int width = 200;
    private int height = 400;

    Color BG_COLOR = Color.BLUE;

    public PointsPanel() {
        this.setSize(width, height);
        this.setBackground(BG_COLOR);
    }
}
