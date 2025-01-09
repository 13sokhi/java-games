/*
*  Position 0:
* |0|1|2|
*   |3|
*
* Position 1:
*   |0|
* |3|1|
*   |2|
*
* Position 2:
*   |3|
* |2|1|0|
*
* Position 3:
* |2|
* |1|3|
* |0|
*/

package tetris_game.shapes;

import java.awt.*;
import java.util.Random;

public class T extends Shape {
    Color tColor = Color.MAGENTA;

    public T() {
        setShapeColor(tColor);
        for (Block block : getShapeBlocks()) {
            block.setBlockColor(shapeColor);
        }
    }

    @Override
    public void createNewShape(int game_left_x, int game_top_y, int frame_width, int unitSize) {
        Random r = new Random();
        setShapePosition(r.nextInt(4));

        switch (getShapePosition()) {
            case 0:
                getShapeBlocks()[1].setX(frame_width/2 - unitSize);
                getShapeBlocks()[1].setY(game_top_y);
                getShapeBlocks()[0].setX(getShapeBlocks()[1].getX() - unitSize);
                getShapeBlocks()[0].setY(getShapeBlocks()[1].getY());
                getShapeBlocks()[2].setX(getShapeBlocks()[1].getX() + unitSize);
                getShapeBlocks()[2].setY(getShapeBlocks()[1].getY());
                getShapeBlocks()[3].setX(getShapeBlocks()[1].getX());
                getShapeBlocks()[3].setY(getShapeBlocks()[1].getY() + unitSize);
                break;
            case 1:
                getShapeBlocks()[1].setX(frame_width/2 - unitSize);
                getShapeBlocks()[1].setY(game_top_y + unitSize);
                getShapeBlocks()[0].setX(getShapeBlocks()[1].getX());
                getShapeBlocks()[0].setY(getShapeBlocks()[1].getY() - unitSize);
                getShapeBlocks()[2].setX(getShapeBlocks()[1].getX());
                getShapeBlocks()[2].setY(getShapeBlocks()[1].getY() + unitSize);
                getShapeBlocks()[3].setX(getShapeBlocks()[1].getX() - unitSize);
                getShapeBlocks()[3].setY(getShapeBlocks()[1].getY());
                break;
            case 2:
                getShapeBlocks()[1].setX(frame_width/2 - unitSize);
                getShapeBlocks()[1].setY(game_top_y + unitSize);
                getShapeBlocks()[0].setX(getShapeBlocks()[1].getX() + unitSize);
                getShapeBlocks()[0].setY(getShapeBlocks()[1].getY());
                getShapeBlocks()[2].setX(getShapeBlocks()[1].getX() - unitSize);
                getShapeBlocks()[2].setY(getShapeBlocks()[1].getY());
                getShapeBlocks()[3].setX(getShapeBlocks()[1].getX());
                getShapeBlocks()[3].setY(getShapeBlocks()[1].getY() - unitSize);
                break;
            case 3:
                getShapeBlocks()[1].setX(frame_width/2 - unitSize);
                getShapeBlocks()[1].setY(game_top_y + unitSize);
                getShapeBlocks()[0].setX(getShapeBlocks()[1].getX());
                getShapeBlocks()[0].setY(getShapeBlocks()[1].getY() + unitSize);
                getShapeBlocks()[2].setX(getShapeBlocks()[1].getX());
                getShapeBlocks()[2].setY(getShapeBlocks()[1].getY() - unitSize);
                getShapeBlocks()[3].setX(getShapeBlocks()[1].getX() + unitSize);
                getShapeBlocks()[3].setY(getShapeBlocks()[1].getY());
                break;
            default:
                break;
        }
        for (int i=0; i<4; i++) {
            tempShapeBlocks[i].setX(shapeBlocks[i].getX());
            tempShapeBlocks[i].setY(shapeBlocks[i].getY());
        }
    }

    @Override
    public void rotate(int unitSize) {
        switch (getShapePosition()) {
            case 0:
                getTempShapeBlocks()[0].setX(getTempShapeBlocks()[1].getX());
                getTempShapeBlocks()[0].setY(getTempShapeBlocks()[1].getY() - unitSize);
                getTempShapeBlocks()[2].setX(getTempShapeBlocks()[1].getX());
                getTempShapeBlocks()[2].setY(getTempShapeBlocks()[1].getY() + unitSize);
                getTempShapeBlocks()[3].setX(getTempShapeBlocks()[1].getX() - unitSize);
                getTempShapeBlocks()[3].setY(getTempShapeBlocks()[1].getY());
                break;
            case 1:
                getTempShapeBlocks()[0].setX(getTempShapeBlocks()[1].getX() + unitSize);
                getTempShapeBlocks()[0].setY(getTempShapeBlocks()[1].getY());
                getTempShapeBlocks()[2].setX(getTempShapeBlocks()[1].getX() - unitSize);
                getTempShapeBlocks()[2].setY(getTempShapeBlocks()[1].getY());
                getTempShapeBlocks()[3].setX(getTempShapeBlocks()[1].getX());
                getTempShapeBlocks()[3].setY(getTempShapeBlocks()[1].getY() - unitSize);
                break;
            case 2:
                getTempShapeBlocks()[0].setX(getTempShapeBlocks()[1].getX());
                getTempShapeBlocks()[0].setY(getTempShapeBlocks()[1].getY() + unitSize);
                getTempShapeBlocks()[2].setX(getTempShapeBlocks()[1].getX());
                getTempShapeBlocks()[2].setY(getTempShapeBlocks()[1].getY() - unitSize);
                getTempShapeBlocks()[3].setX(getTempShapeBlocks()[1].getX() + unitSize);
                getTempShapeBlocks()[3].setY(getTempShapeBlocks()[1].getY());
                break;
            case 3:
                getTempShapeBlocks()[0].setX(getTempShapeBlocks()[1].getX() - unitSize);
                getTempShapeBlocks()[0].setY(getTempShapeBlocks()[1].getY());
                getTempShapeBlocks()[2].setX(getTempShapeBlocks()[1].getX() + unitSize);
                getTempShapeBlocks()[2].setY(getTempShapeBlocks()[1].getY());
                getTempShapeBlocks()[3].setX(getTempShapeBlocks()[1].getX());
                getTempShapeBlocks()[3].setY(getTempShapeBlocks()[1].getY() + unitSize);
                break;
            default:
                break;
        }
    }

    //    @Override
//    public void setShapeLocation(int game_left_x, int game_top_y, int frame_width, int unitSize) {
//        Random r = new Random();
//        setShapePosition(r.nextInt(4));
//
//        switch(getCurrentPosition()) {
//            case 0:
//                setBlockLocation(1, frame_width/2+game_left_x, game_top_y);
//                setBlockLocation(0, getCoordinates(1)[0]-unitSize, getCoordinates(1)[1]);
//                setBlockLocation(2, getCoordinates(1)[0]+unitSize, getCoordinates(1)[1]);
//                setBlockLocation(3, getCoordinates(1)[0], getCoordinates(1)[1]+unitSize);
//                break;
//            case 1:
//                setBlockLocation(1, frame_width/2+game_left_x, game_top_y+unitSize);
//                setBlockLocation(0, getCoordinates(1)[0], getCoordinates(1)[1]-unitSize);
//                setBlockLocation(2, getCoordinates(1)[0], getCoordinates(1)[1]+unitSize);
//                setBlockLocation(3, getCoordinates(1)[0]-unitSize, getCoordinates(1)[1]);
//                break;
//            case 2:
//                setBlockLocation(1, frame_width/2+game_left_x, game_top_y+unitSize);
//                setBlockLocation(0, getCoordinates(1)[0], getCoordinates(1)[1]);
//                setBlockLocation(2, getCoordinates(1)[0], getCoordinates(1)[1]);
//                setBlockLocation(3, getCoordinates(1)[0]+unitSize, getCoordinates(1)[1]-unitSize);
//                break;
//            case 3:
//                setBlockLocation(1, frame_width/2+game_left_x, game_top_y+unitSize);
//                setBlockLocation(0, getCoordinates(1)[0], getCoordinates(1)[1]+unitSize);
//                setBlockLocation(2, getCoordinates(1)[0], getCoordinates(1)[1]-unitSize);
//                setBlockLocation(3, getCoordinates(1)[0]+unitSize, getCoordinates(1)[1]);
//                break;
//            default:
//                break;
//        }
//
////        setting same values in temp array as OG array
//        for (int i=0; i<4; i++) {
//            tempShapeLocation[i] = Arrays.copyOf(shapeLocation[i], shapeLocation[i].length);
//        }
//    }
//
//    @Override
//    public void rotate(int unitSize) {
//        switch (getCurrentPosition()) {
//            case 0:
//                setTempBlockPosition(0, getCoordinates(1)[0], getCoordinates(1)[1]-unitSize);
//                setTempBlockPosition(2, getCoordinates(1)[0], getCoordinates(1)[1]+unitSize);
//                setTempBlockPosition(3, getCoordinates(1)[0]-unitSize, getCoordinates(1)[1]);
//                break;
//            case 1:
//                setTempBlockPosition(0, getCoordinates(1)[0]+unitSize, getCoordinates(1)[1]);
//                setTempBlockPosition(2, getCoordinates(1)[0]-unitSize, getCoordinates(1)[1]);
//                setTempBlockPosition(3, getCoordinates(1)[0], getCoordinates(1)[1]-unitSize);
//                break;
//            case 2:
//                setTempBlockPosition(0, getCoordinates(1)[0], getCoordinates(1)[1]+unitSize);
//                setTempBlockPosition(2, getCoordinates(1)[0], getCoordinates(1)[1]-unitSize);
//                setTempBlockPosition(3, getCoordinates(1)[0]+unitSize, getCoordinates(1)[1]);
//                break;
//            case 3:
//                setTempBlockPosition(0, getCoordinates(1)[0]-unitSize, getCoordinates(1)[1]);
//                setTempBlockPosition(2, getCoordinates(1)[0]+unitSize, getCoordinates(1)[1]);
//                setTempBlockPosition(3, getCoordinates(1)[0], getCoordinates(1)[1]+unitSize);
//                break;
//            default:
//                break;
//        }
//    }
}
