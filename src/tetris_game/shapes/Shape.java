package tetris_game.shapes;

import java.awt.*;

public abstract class Shape {
    Color shapeColor;
    Block [] shapeBlocks = new Block[4];
    Block [] tempShapeBlocks = new Block[4];
    int shapePosition;

    public Shape() {
        for (int i=0; i<4; i++) {
            shapeBlocks[i] = new Block();
            tempShapeBlocks[i] = new Block();
        }
    }

    public Color getShapeColor() {
        return shapeColor;
    }
    public Block [] getShapeBlocks() {
        return shapeBlocks;
    }
    public Block [] getTempShapeBlocks() {
        return tempShapeBlocks;
    }
    public int getShapePosition() {
        return shapePosition;
    }

    public void setShapeColor(Color shapeColor) {
        this.shapeColor = shapeColor;
    }
    public void setShapePosition(int shapePosition) {
        this.shapePosition = shapePosition;
    }

    public void moveLeft(int movement) {
        for (Block block : tempShapeBlocks) {
            block.setX(block.getX() - movement);
        }
    }
    public void moveRight(int movement) {
        for (Block block : tempShapeBlocks) {
            block.setX(block.getX() + movement);
        }
    }
    public void moveDown(int movement) {
        for (Block block : tempShapeBlocks) {
            block.setY(block.getY() + movement);
        }
    }

    public abstract void createNewShape(int game_left_x, int game_top_y, int frame_width, int unitSize);
    public abstract void rotate(int unitSize);
}
