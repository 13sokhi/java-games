/*
* Position 0,1,2,3:
* |0|1|
* |2|3|
* */

package tetris_game.shapes;

import java.awt.*;
import java.util.Random;

public class O extends Shape {
    Color oColor = Color.YELLOW;

    public O() {
        setShapeColor(oColor);
        for (Block block : getShapeBlocks()) {
            block.setBlockColor(shapeColor);
        }
    }

    @Override
    public void createNewShape(int game_left_x, int game_top_y, int frame_width, int unitSize) {
        Random r = new Random();
        setShapePosition(r.nextInt(4));

        getShapeBlocks()[0].setX(frame_width /2 - unitSize);
        getShapeBlocks()[0].setY(game_top_y);
        getShapeBlocks()[1].setX(frame_width /2 - unitSize + unitSize);
        getShapeBlocks()[1].setY(game_top_y);
        getShapeBlocks()[2].setX(frame_width /2 - unitSize);
        getShapeBlocks()[2].setY(game_top_y + unitSize);
        getShapeBlocks()[3].setX(frame_width /2 - unitSize + unitSize);
        getShapeBlocks()[3].setY(game_top_y + unitSize);

        for (int i=0; i<4; i++) {
            tempShapeBlocks[i].setX(shapeBlocks[i].getX());
            tempShapeBlocks[i].setY(shapeBlocks[i].getY());
        }
    }

    @Override
    public void rotate(int unitSize) {

    }
}
