package tetris_game;

import tetris_game.shapes.Block;
import tetris_game.shapes.Shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GameManager {
    public static int unitSize = 20;
    int game_width = 320;  // width  of game box, not the panel
    int game_height = 400; // height of game box, not the panel
    int game_left_x = 40;  // where game border starts
    int game_right_x = game_width + game_left_x; // where game border ends
    int game_top_y = 50;
    int game_bottom_y = game_height + game_top_y;
    int margin = 5;
    public static int fallSpeed = 1;
    int points;

    boolean gameStated = false;  // if game has started or not (by pressing ENTER)
    boolean gameRunning = false; // if game not paused or ended or not started
    boolean gamePaused = false;  // if game paused
    boolean gameOver = false;     // if game over
    boolean gameWon = false;     // if player wins

    Shape currentShape;

    ArrayList<Block> filledPositions = new ArrayList<Block>();

    public GameManager() {
        selectNewShape();
        currentShape.createNewShape(game_left_x, game_top_y, 400, unitSize);
    }

    public void startGame() {
        gameStated = true;
        gameRunning = true;
    }

    public void pauseGame() {
        gamePaused = true;
        gameRunning = false;
    }

    public void unPauseGame(){
        gamePaused = false;
        gameRunning = true;
    }

    public void gameOver() {
        System.out.println("Game Over!");
        gameRunning = false;
        gameOver = true;
    }

//    if current shape collided with floor or another block
    public void collided() {
        for (Block block : currentShape.getShapeBlocks()) {
            filledPositions.add(block);
        }
        deleteCompletedRows();
        checkGameOver();
        selectNewShape();
        currentShape.createNewShape(game_left_x, game_top_y, 400, unitSize);
    }

    public void deleteCompletedRows() {
        for (int i=game_top_y; i<game_bottom_y; i+=unitSize) {
            int c = 0; // counter to see if row is full
            for (int j=game_left_x; j<game_right_x; j+=unitSize) {
                int x = j; // x coordinate of each block space
                int y = i; // y coordinate of each block space

                Iterator<Block> it = filledPositions.iterator();
                while (it.hasNext()) { // loop for filledBlocks
                    Block block = it.next();
                    int blockX = block.getX();
                    int blockY = block.getY();
                    if (blockX == x && blockY == y) {
                        c++;
                    }
                }
                if (c == game_width / unitSize) { // if for a row each block is filled
                    points += 10;
                    Iterator<Block> it2 = filledPositions.iterator();
                    while (it2.hasNext()) {
                        Block eachBlock = it2.next();
                        if (eachBlock.getY() == y) { // removing blocks in filled row
                            it2.remove();
                        } else if (eachBlock.getY() < y) { // lowering the blocks above deleted row by unitSize
                            eachBlock.setY(eachBlock.getY() + unitSize);
                        }
                    }
                }
            }
            c = 0;
        }
    }

    public boolean checkGameOver() {
        Iterator<Block> it = filledPositions.iterator();
        while (it.hasNext()) {
            Block block = it.next();
            if (block.getY() == game_top_y) {
                gameOver();
                return true;
            }
        }
        return false;
    }

    public void checkWin() {
        if (points > 0 && filledPositions.size() == 0) {
            System.out.println("You won!");
            gameWon = true;
        }
    }

    public void selectNewShape() {
        currentShape = getRandomShape();
    }

    public Shape getRandomShape() {
        Random r = new Random();
        Shape shape = null;
        try {
            shape = Shapes.values()[r.nextInt(7)].getShape().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println(e);
        }
        return shape;
    }

//    checks left, right and top wall collision (top wall collision for rotation)
    public boolean checkWallCollision() {
        for (Block block : currentShape.getTempShapeBlocks()) {
            int x = block.getX();
            int y = block.getY();

//            if (x<game_left_x || x>game_right_x-unitSize || y>game_bottom_y-unitSize || y<game_top_y || checkBlockCollision() == true) {
            if (x<game_left_x || x>game_right_x-unitSize || y<game_top_y || checkBlockCollision() == true) {
                for (int i=0; i<4; i++) {
                    currentShape.getTempShapeBlocks()[i].setX(currentShape.getShapeBlocks()[i].getX());
                    currentShape.getTempShapeBlocks()[i].setY(currentShape.getShapeBlocks()[i].getY());
                }
                return true;
            }
        }
        for (int i=0; i<4; i++) {
            currentShape.getShapeBlocks()[i].setX(currentShape.getTempShapeBlocks()[i].getX());
            currentShape.getShapeBlocks()[i].setY(currentShape.getTempShapeBlocks()[i].getY());
        }
        return false;
    }

    public boolean checkRotationCollision() {
        if (checkWallCollision() == false && checkBlockCollision() == false) {
            if (currentShape.getShapePosition()==3) {
                currentShape.setShapePosition(0);
            } else {
                currentShape.setShapePosition(currentShape.getShapePosition() + 1);
            }
            return false;
        }
        return true;
    }

//    checking bottom collision with floor and static blocks
    public boolean checkBottomCollision() {
        for (Block block : currentShape.getTempShapeBlocks()) {
            int y = block.getY();
            if (y > game_bottom_y - unitSize) {
                for (int i=0; i<4; i++) {
                    currentShape.getTempShapeBlocks()[i].setX(currentShape.getShapeBlocks()[i].getX());
                    currentShape.getTempShapeBlocks()[i].setY(currentShape.getShapeBlocks()[i].getY());
                }
                collided();
                return true;
            }
        }
        for (Block staticBlock : filledPositions) {
            for (Block tempShapeBlock : currentShape.getTempShapeBlocks()) {
//                if (staticBlock.getX() == tempShapeBlock.getX() && staticBlock.getY() == tempShapeBlock.getY()) {
                if (tempShapeBlock.getY() + unitSize > staticBlock.getY() && tempShapeBlock.getX() == staticBlock.getX()) {
                    for (int i=0; i<4; i++) {
                        currentShape.getTempShapeBlocks()[i].setX(currentShape.getShapeBlocks()[i].getX());
                        currentShape.getTempShapeBlocks()[i].setY(currentShape.getShapeBlocks()[i].getY());
                    }
                    collided();
                    return true;
                }
            }
        }
        for (int i=0; i<4; i++) {
            currentShape.getShapeBlocks()[i].setX(currentShape.getTempShapeBlocks()[i].getX());
            currentShape.getShapeBlocks()[i].setY(currentShape.getTempShapeBlocks()[i].getY());
        }
        return false;
    }

    public boolean checkBlockCollision() {
        for (Block block : filledPositions) {
            for (int i=0; i<4; i++) {
                if (currentShape.getTempShapeBlocks()[i].getX() == block.getX() && currentShape.getTempShapeBlocks()[i].getY() == block.getY()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void update() {
        currentShape.moveDown(unitSize);
        checkBottomCollision();
//        deleteCompletedRows();
//        checkGameOver();
    }

    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D)g;
        g2D.setColor(Color.WHITE);
        g2D.drawRect(game_left_x + margin, game_top_y + margin, game_width, game_height);

//        drawing all the static blocks
        for (Block filledBlock : filledPositions) {
            int x = filledBlock.getX();
            int y = filledBlock.getY();
            g2D.setColor(filledBlock.getBlockColor());
            g2D.fillRect(x + margin, y + margin, unitSize - margin/2, unitSize - margin/2 );
        }

//        drawing current shape
        for (Block block : currentShape.getShapeBlocks()) {
            int x = block.getX();
            int y = block.getY();
            g2D.setColor(currentShape.getShapeColor());
            g2D.fillRect(x + margin, y + margin, unitSize - margin/2, unitSize - margin/2 );
        }
    }

    public static void main(String[] args) {
        GameManager g = new GameManager();
        g.deleteCompletedRows();
    }
}
