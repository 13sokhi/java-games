package tetris_game.shapes;

import java.awt.*;

public class Block {
    Color blockColor;
    int x;
    int y;

    public Block() {}

    public Color getBlockColor() {
        return blockColor;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setBlockColor(Color blockColor) {
        this.blockColor = blockColor;
    }
}
