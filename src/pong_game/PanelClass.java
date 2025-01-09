package pong_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class PanelClass extends JPanel implements ActionListener {
    JPanel infoPanel = new JPanel();
    JPanel finalPanel = new JPanel();
    JLabel pointsLabel = new JLabel("Press ENTER to start!");
    JLabel pauseLabel = new JLabel("P for pause");
    JLabel matchPointLabel = new JLabel();
    JLabel resultLabel = new JLabel();

    JButton play = new JButton("Play again!");
    JButton exit = new JButton("Exit");
    JButton settings = new JButton();

    static int SCREEN_WIDTH = 1000;
    static int SCREEN_HEIGHT = 600;
    static int UNIT_SIZE = 20;
    static int PADDLE_SPEED = 30;
    float ballX = SCREEN_WIDTH / 2 - UNIT_SIZE / 2;
    float ballY = SCREEN_HEIGHT / 2 - UNIT_SIZE / 2;
    static int paddleLength = UNIT_SIZE * 4;
    int paddle1Y = SCREEN_HEIGHT / 2 - paddleLength / 2;
    int paddle2Y = SCREEN_HEIGHT / 2 - paddleLength / 2;
    char paddle1Direction ;
    char paddle2Direction ;
    int paddlePositionChange = PADDLE_SPEED;
    Timer timerForBallSpeed;
    int ballTimerDelay = 10;
    Random ballSpeedRandom = new Random();
    float ballSpeedX ;
    float ballSpeedY ;
    boolean gameStarted = false;
    boolean gamePaused = true;
    boolean gameEnd = false;
    char horizontalBallDirection ;
    char verticalalBallDirection ;
    int player1Points = 0;
    int player2Points = 0;
    String WINNER ;
    int randomStartHorizontalDirection ;
    int randomStartVerticalDirection ;
    static int ballSpeedInitialRange = 25;
    static int ballSpeedFinalRange = 35;
    static int totalMatchPoints = 5;

    static Color paddle1Color = Color.BLUE;
    static Color paddle2Color = Color.RED;
    static Color ballColor = Color.WHITE;

    public PanelClass() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);

        infoPanel.setPreferredSize(new Dimension(200, 80));
        finalPanel.setVisible(false);
        infoPanel.setBackground(Color.ORANGE);

        infoPanel.setLayout(null);
        infoPanel.add(pointsLabel);
        pointsLabel.setFont(new Font("Ink free", Font.BOLD, 32));
        pointsLabel.setBounds(340, 20, 400, 40);
        infoPanel.add(pauseLabel);
        pauseLabel.setBounds(20,10,100,30);
        infoPanel.add(matchPointLabel);
        matchPointLabel.setBounds(20,40,100,30);
        infoPanel.add(settings);
        settings.setBounds(800,20,40,39);
        settings.setIcon(new ImageIcon("D:\\Coding\\Java\\My java projects\\Games\\images\\settings.jpg"));
//        settings.setIcon(new ImageIcon("F:\\e\\Java\\My java projects\\ball_game_project\\images\\settings.jpg"));
        settings.setFocusable(false);

        finalPanel.setBackground(Color.YELLOW);
        finalPanel.setLayout(null);
        finalPanel.add(resultLabel);
        finalPanel.add(play);
        finalPanel.add(exit);
        resultLabel.setFont(new Font("Ink free", Font.BOLD, 44));
        resultLabel.setBounds(370,100,400,50);
        play.setBounds(310,400,200,60);
        exit.setBounds(570,400,150,60);
        play.setFont(new Font("MV Boli", Font.BOLD, 24));
        exit.setFont(new Font("MV Boli", Font.BOLD, 24));

        this.setFocusable(true);

        KeyListener wsKeys = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER :
                        if (!gameStarted && !gameEnd) {
                            startGame();
                        }
                        else if (gamePaused && !gameEnd) {
                            resumeGame();
                        }
                        break;
                    case KeyEvent.VK_P :
                        if (gameStarted) {
                            pauseGame();
                        }
                        break;
                    case KeyEvent.VK_W :
                        if (gameStarted && !gameEnd && !gamePaused) {
                            paddle1Direction = 'U';
                            movePaddle1();
                        }
                        break;
                    case KeyEvent.VK_S :
                        if (gameStarted && !gameEnd && !gamePaused) {
                            paddle1Direction = 'D';
                            movePaddle1();
                        }
                        break;
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        };
        KeyListener updownKeys = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP :
                        if (gameStarted && !gameEnd && !gamePaused) {
                            paddle2Direction = 'U';
                            movePaddle2();
                        }
                        break;
                    case KeyEvent.VK_DOWN :
                        if (gameStarted && !gameEnd && !gamePaused) {
                            paddle2Direction = 'D';
                            movePaddle2();
                        }
                        break;
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        };

        this.addKeyListener(wsKeys);
        this.addKeyListener(updownKeys);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawLine(SCREEN_WIDTH / 2, 0, SCREEN_WIDTH / 2, SCREEN_HEIGHT);

        g2D.setPaint(ballColor);
        g2D.fillOval((int)ballX, (int)ballY, UNIT_SIZE, UNIT_SIZE);

        g2D.setPaint(paddle1Color);
        g2D.fillRect(0, paddle1Y, UNIT_SIZE, paddleLength);
        g2D.setPaint(paddle2Color);
        g2D.fillRect(SCREEN_WIDTH - UNIT_SIZE, paddle2Y, UNIT_SIZE, paddleLength);
    }

    public void startGame() {
        gameStarted = true;
        gamePaused = false;
        pointsLabel.setLocation(470, 20);
        pointsLabel.setText(player1Points + "|" + player2Points);

        // 1 will move ball towards left and 2 towards right:
        randomStartHorizontalDirection = ballSpeedRandom.nextInt(1,3);
        // 1 will move ball towards up and 2 towards down:
        randomStartVerticalDirection = ballSpeedRandom.nextInt(1,3);
        makeRandomBallSpeed();
        if (randomStartHorizontalDirection == 1) {
            ballSpeedX *= -1;
        }
        if (randomStartVerticalDirection == 1) {
            ballSpeedY *= -1;
        }

        timerForBallSpeed = new Timer(ballTimerDelay, this);
        timerForBallSpeed.start();
    }

    public void pauseGame() {
        gamePaused = true;
        if (gameStarted) {timerForBallSpeed.stop();}
        pointsLabel.setText("Press ENTER to start!");
        pointsLabel.setLocation(340, 20);
    }

    public void resumeGame() {
        gamePaused = false;
        timerForBallSpeed.start();
        pointsLabel.setLocation(470, 20);
        pointsLabel.setText(player1Points + "|" + player2Points);
    }

    public void makeRandomBallSpeed() {
        ballSpeedX = ballSpeedRandom.nextInt(ballSpeedInitialRange,ballSpeedFinalRange)/10;
        ballSpeedY = ballSpeedRandom.nextInt(ballSpeedInitialRange,ballSpeedFinalRange)/10;
    }
    public void moveBall() {
        ballX += ballSpeedX;
        ballY += ballSpeedY;
        repaint();
    }

    public void movePaddle1() {
        switch (paddle1Direction) {
            case 'U' :
                if (paddle1Y - PADDLE_SPEED >= 0) {
                    paddle1Y -= PADDLE_SPEED;
                }
                break;
            case 'D' :
                if (paddle1Y + PADDLE_SPEED + paddleLength <= SCREEN_HEIGHT) {
                    paddle1Y += PADDLE_SPEED;
                }
                break;
        }
        repaint();
    }
    public void movePaddle2() {
        switch (paddle2Direction) {
            case 'U' :
                if (paddle2Y - PADDLE_SPEED >= 0) {
                    paddle2Y -= PADDLE_SPEED;
                }
                break;
            case 'D' :
                if (paddle2Y + PADDLE_SPEED + paddleLength <= SCREEN_HEIGHT) {
                    paddle2Y += PADDLE_SPEED;
                    break;
                }
        }
        repaint();
    }

    public void checkBallDirection() {
        if (ballSpeedX > 0) {horizontalBallDirection = 'R';}
        else if (ballSpeedX < 0) {horizontalBallDirection = 'L';}
        if (ballSpeedY > 0) {verticalalBallDirection = 'D';}
        else if (ballSpeedY < 0) {verticalalBallDirection = 'U';}
    }

    public void checkBounce() {
        if (ballY < 0 || ballY > SCREEN_HEIGHT) {
            bounce();
        }
    }

    public void checkPoints() {
        matchPointLabel.setText("max. points : "+totalMatchPoints);
        switch (horizontalBallDirection) {
            case 'R' :
                if (ballX+UNIT_SIZE >= SCREEN_WIDTH-UNIT_SIZE && ballX+UNIT_SIZE <= SCREEN_WIDTH && ballY+UNIT_SIZE >= paddle2Y && ballY+UNIT_SIZE <= paddle2Y+paddleLength) {
                    defended();
                }
                else if (ballX+UNIT_SIZE > SCREEN_WIDTH) {
                    updatePoints();
                }
                break;
            case 'L' :
                if (ballX <= UNIT_SIZE && ballX >= 0 && ballY+UNIT_SIZE >= paddle1Y && ballY+UNIT_SIZE <= paddle1Y+paddleLength) {
                    defended();
                }
                else if (ballX < 0) {
                    updatePoints();
                }
                break;
        }
    }

    public void checkWinner() {
        if (player1Points == totalMatchPoints) {
            WINNER = "Player 1";
            endGame();
        }
        else if (player2Points == totalMatchPoints) {
            WINNER = "Player 2";
            endGame();
        }
    }

    public void updatePoints() {
        switch (horizontalBallDirection) {
            case 'R' :
                player1Points += 1;
                break;
            case 'L' :
                player2Points += 1;
                break;
        }
        pointsLabel.setText(player1Points + "|" + player2Points);
        ballX = SCREEN_WIDTH / 2 - UNIT_SIZE / 2;
        ballY = SCREEN_HEIGHT / 2 - UNIT_SIZE / 2;
        repaint();

        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        makeRandomBallSpeed();

        // Below code will make sure that after scoring point, ball moves towards the player who took point:
        if (horizontalBallDirection == 'R') {ballSpeedX *= -1;}
        randomStartVerticalDirection = ballSpeedRandom.nextInt(1,3);
        if (randomStartVerticalDirection == 1) {ballSpeedY *= -1;}
    }

    public void bounce() {
        makeRandomBallSpeed();
        if (horizontalBallDirection == 'L') {ballSpeedX *= -1;}
        if (verticalalBallDirection == 'D') {ballSpeedY *= -1;}
    }
    public void defended() {
        makeRandomBallSpeed();
        if (horizontalBallDirection == 'R') {ballSpeedX *= -1;}
        if (verticalalBallDirection == 'U') {ballSpeedY *= -1;}
    }

    public void endGame() {
        gameEnd = true;
        timerForBallSpeed.stop();

        this.setVisible(false);
        infoPanel.setVisible(false);
        finalPanel.setVisible(true);
        resultLabel.setText(WINNER + " Won!!!");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveBall();
        checkBallDirection();
        checkBounce();
        checkPoints();
        checkWinner();
    }
}