package snake_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class PanelClass extends JPanel implements KeyListener, ActionListener {
    JPanel infoPanel = new JPanel();
    JLabel pauseLabel = new JLabel("P for pause");
    JPanel finalPanel = new JPanel();
    JLabel infoLabel = new JLabel("Press ENTER to start!");
    JLabel finalLabel1 = new JLabel("GAME OVER!!!");
    JLabel finalLabel2 = new JLabel();
    JLabel finalLabel3 = new JLabel();
    JButton playButton = new JButton("Play again");
    JButton exitButton = new JButton("Exit?");
    JButton settingsButton = new JButton();

    // static keyword is used so that the below variables will retain their vales, if changed:
    static Color snakeHeadColor = Color.GREEN;
    static Color snakeBodyColor = new Color(153, 255, 102);

    int SCREEN_WIDTH = 800;
    int SCREEN_HEIGHT = 500;
    static int UNIT_SIZE = 30;
    int NO_OF_BODY_PARTS = 3;
    int [] xBodyPositions ;
    int [] yBodyPositions ;
    int [] xPositionUpdated;
    int [] yPositionUpdated;
    char direction = 'R' ;
    static int delayForTimer = 125;
    Timer timerForSnakeMovement;
    int points;
    boolean gameRunning ;
    boolean gamePaused ;

    Random appleRandom = new Random();
    int appleX;
    int appleY;
    int snakeHeadX;
    int snakeHeadY;

    public PanelClass() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        this.setFocusable(true);


        // The infoPanel:
        infoPanel.setLayout(null);
        infoPanel.setBackground(Color.orange);
        infoPanel.setPreferredSize(new Dimension(SCREEN_WIDTH, 50));
        infoPanel.add(infoLabel);
        infoLabel.setFont(new Font(null,Font.BOLD,26));
        infoLabel.setBounds(200 , 15 , 480 , 25);
        infoPanel.add(pauseLabel);
        pauseLabel.setBounds(10,20,100,30);

        infoPanel.add(settingsButton);
        settingsButton.setFocusable(false);
        settingsButton.setBounds(700, 5, 40, 39);
        settingsButton.setIcon(new ImageIcon("D:\\Coding\\Java\\My java projects\\Games\\images\\settings.jpg"));


        // The finalPanel:
        finalPanel.setLayout(null);
        finalPanel.setVisible(false);
        finalPanel.setBackground(Color.ORANGE);
        finalPanel.add(finalLabel1);
        finalPanel.add(finalLabel2);
        finalPanel.add(finalLabel3);
        finalPanel.add(playButton);
        finalPanel.add(exitButton);
        finalLabel1.setFont(new Font("MV Boli", Font.BOLD, 36));
        finalLabel1.setBounds(270,50,300,50);
        finalLabel2.setFont(new Font("MV Boli", Font.BOLD, 28));
        finalLabel2.setBounds(270,100,300,50);
        finalLabel3.setFont(new Font("MV Boli", Font.BOLD, 28));
        finalLabel3.setBounds(270,140,300,50);
        playButton.setBounds(250, 400, 150, 40);
        exitButton.setBounds(450, 400, 150, 40);
        playButton.setFont(new Font("MV Boli", Font.BOLD, 20));
        exitButton.setFont(new Font("MV Boli", Font.BOLD, 20));
        playButton.setBackground(Color.LIGHT_GRAY);
        exitButton.setBackground(Color.LIGHT_GRAY);
        playButton.setForeground(Color.RED);
        exitButton.setForeground(Color.RED);
        playButton.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        exitButton.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        playButton.setFocusable(false);
        exitButton.setFocusable(false);

        xBodyPositions = new int [NO_OF_BODY_PARTS];
        yBodyPositions = new int [NO_OF_BODY_PARTS];

        this.addKeyListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;

        // for(int i=0; i<SCREEN_WIDTH/UNIT_SIZE; i++) {
        //     g2D.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
        //     g2D.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
        // }

        g2D.setPaint(Color.RED);
        g2D.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

        // Making snake head and body position such that they are according to the UNIT_SIZE:
        snakeHeadX = snakeHeadX/UNIT_SIZE*UNIT_SIZE;
        snakeHeadY = snakeHeadY/UNIT_SIZE*UNIT_SIZE;
        for (int i = 0; i < NO_OF_BODY_PARTS; i++) {
            xBodyPositions[i] = xBodyPositions[i]/UNIT_SIZE*UNIT_SIZE;
            yBodyPositions[i] = yBodyPositions[i]/UNIT_SIZE*UNIT_SIZE;
        }



        g2D.setPaint(snakeHeadColor);
        g2D.fillRect(snakeHeadX, snakeHeadY, UNIT_SIZE, UNIT_SIZE);

        g2D.setPaint(snakeBodyColor);
        for (int i = 0; i < NO_OF_BODY_PARTS; i++) {
            g2D.fillRect(xBodyPositions[i], yBodyPositions[i], UNIT_SIZE, UNIT_SIZE);
        }
    }

    public void startGame() {
        timerForSnakeMovement = new Timer(delayForTimer,this);
        timerForSnakeMovement.start();
        if (!gamePaused) {
            makeApple();
        }
        gamePaused = false;
        repaint();
    }

    public void pauseGame() {
        if (gameRunning) {
            timerForSnakeMovement.stop();
        }
        gameRunning = false;
        gamePaused = true;
        infoLabel.setText("Game paused! Press ENTER to resume");
    }

    public void makeApple() {
        // We are making apple according to our grid size so that the positions are a multiple of UNIT_SIZE.
        appleX = appleRandom.nextInt(SCREEN_WIDTH)/UNIT_SIZE*UNIT_SIZE;
        appleY = appleRandom.nextInt(SCREEN_HEIGHT)/UNIT_SIZE*UNIT_SIZE;
        repaint();
    }

    public void checkAppleEaten() {
        if (snakeHeadX == appleX && snakeHeadY == appleY) {
            makeApple();
            points += 10;
            infoLabel.setText("Points : " + points);

            // Below is code for adding a new part in snake body:
            NO_OF_BODY_PARTS ++;
            xPositionUpdated = new int[NO_OF_BODY_PARTS];
            yPositionUpdated = new int[NO_OF_BODY_PARTS];

            // Making the newly instantiated array of 1 more length equals to original array:
            switch (direction) {
                case 'U' :
                    xPositionUpdated[0] = xBodyPositions[NO_OF_BODY_PARTS-2];
                    yPositionUpdated[0] = yBodyPositions[NO_OF_BODY_PARTS-2] + UNIT_SIZE;
                    break;
                case 'D' :
                    xPositionUpdated[0] = xBodyPositions[NO_OF_BODY_PARTS-2];
                    yPositionUpdated[0] = yBodyPositions[NO_OF_BODY_PARTS-2] - UNIT_SIZE;
                    break;
                case 'L' :
                    xPositionUpdated[0] = xBodyPositions[NO_OF_BODY_PARTS-2] + UNIT_SIZE;
                    yPositionUpdated[0] = yBodyPositions[NO_OF_BODY_PARTS-2];
                    break;
                case 'R' :
                    xPositionUpdated[0] = xBodyPositions[NO_OF_BODY_PARTS-2];
                    yPositionUpdated[0] = yBodyPositions[NO_OF_BODY_PARTS-2] - UNIT_SIZE;
                    break;
            }
            for (int i = 1; i < NO_OF_BODY_PARTS; i++) {
                xPositionUpdated[i] = xBodyPositions[i-1];
                yPositionUpdated[i] = yBodyPositions[i-1];
            }
            // Making original array equal to new array, so that length can be increased:
            xBodyPositions = xPositionUpdated;
            yBodyPositions = yPositionUpdated;
            repaint();
        }
    }

    public void checkSelfCollided() {
        for (int i=0; i<NO_OF_BODY_PARTS; i++) {
            if (snakeHeadX == xBodyPositions[i] && snakeHeadY == yBodyPositions[i]) {
                //System.out.println("snake bite itself");
                finalLabel2.setText("Snake bite itself!");
                gameOver();
            }
        }
    }

    public void move() {
        for (int i = 0; i < NO_OF_BODY_PARTS-1; i++) {
            xBodyPositions[i] = xBodyPositions[i+1];
            yBodyPositions[i] = yBodyPositions[i+1];
        }
        xBodyPositions[NO_OF_BODY_PARTS-1] = snakeHeadX;
        yBodyPositions[NO_OF_BODY_PARTS-1] = snakeHeadY;
        switch (direction) {
            case 'U' :
                snakeHeadY = snakeHeadY - UNIT_SIZE;
                break;
            case 'D' :
                snakeHeadY = snakeHeadY + UNIT_SIZE;
                break;
            case 'L' :
                snakeHeadX = snakeHeadX - UNIT_SIZE;
                break;
            case 'R' :
                snakeHeadX = snakeHeadX + UNIT_SIZE;
                break;
        }
        repaint();
    }

    public boolean collided() {
        if (snakeHeadX < 0 || snakeHeadX + UNIT_SIZE > SCREEN_WIDTH || snakeHeadY < 0 || snakeHeadY +UNIT_SIZE > SCREEN_HEIGHT) {
            gameOver();
            finalLabel2.setText("Collided!");
            // System.out.println(snakeHeadX + " / " + snakeHeadY);
            return true;
        }
        return false;
    }

    public void gameOver() {
        timerForSnakeMovement.stop();
        // gameEnd = true;
        this.setVisible(false);
        infoPanel.setVisible(false);
        finalPanel.setVisible(true);
        finalLabel3.setText("Score -> " + points);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'w') {
            System.out.println("w");
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER :
                if (!gameRunning) {
                    gameRunning = true;
                    startGame();
                    infoLabel.setText("Points : " + points);
                }
                break;
            case KeyEvent.VK_UP :
                if (direction != 'D') {
                    direction = 'U';
                }
                break;
            case KeyEvent.VK_DOWN :
                if (direction != 'U') {
                    direction = 'D';
                }
                break;
            case KeyEvent.VK_LEFT :
                if (direction != 'R') {
                    direction = 'L';
                }
                break;
            case KeyEvent.VK_RIGHT :
                if (direction != 'L') {
                    direction = 'R';
                }
                break;
            case KeyEvent.VK_P :
                if (gameRunning) {
                    // gamePaused = true;
                    pauseGame();
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    // ActionListener implementation for Timer:
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!collided()) {
            move();
            checkAppleEaten();
            checkSelfCollided();
        }
        else {
            System.out.println("Collided");
        }
    }
}
