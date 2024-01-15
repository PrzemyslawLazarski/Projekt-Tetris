package tetris;

import tetris.GameMemento.Caretaker;
import tetris.GameMemento.GameMemento;
import tetris.GameState.GameOverState;
import tetris.GameState.GameState;
import tetris.GameState.PausedState;
import tetris.GameState.RunningState;
import tetris.LineObserver.LineObservable;
import tetris.LineObserver.LineObserver;
import tetris.ScoringStrategy.AdvancedScoringStrategy;
import tetris.ScoringStrategy.BeginnerScoringStrategy;
import tetris.ScoringStrategy.ProScoringStrategy;
import tetris.ScoringStrategy.ScoringStrategy;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements KeyListener, MouseListener, MouseMotionListener, LineObserver {

    private static final long serialVersionUID = 1L;
    private GameState currentState;
    private GameState runningState;
    private GameState pausedState;
    private GameState gameOverState;
    private GameMemento memento;
    private Caretaker caretaker = new Caretaker();
    private int currentMementoIndex = -1;
    private BufferedImage pause, refresh, save, load;
    private final int boardHeight = 20, boardWidth = 10;
    public static final int blockSize = 30;
    private Color[][] board = new Color[boardHeight][boardWidth];
    private Shape[] shapes = new Shape[7];
    private static Shape currentShape, nextShape;
    private Timer looper;
    private int FPS = 60;
    private int delay = 1000 / FPS;
    private int mouseX, mouseY;
    private boolean leftClick = false;
    private Rectangle stopBounds, refreshBounds, saveBounds, loadBounds;
    private boolean gamePaused = false;
    private boolean gameOver = false;
    private Color[] colors = {Color.decode("#ff0000"), Color.decode("#00ff00"), Color.decode("#0000ff"),
            Color.decode("#fc4c00"), Color.decode("#e1fa05"), Color.decode("#02f5dd"), Color.decode("#de0afa")};
    private Random random = new Random();
    private Timer buttonLapse = new Timer(300, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonLapse.stop();
        }
    });

    private int score = 0;
    public int linesCleared = 0;
    public int normal = 600;
    public LineObservable lineObservable = new LineObservable();
    private ScoringStrategy scoringStrategy;
    public Board() {

        runningState = new RunningState();
        pausedState = new PausedState();
        gameOverState = new GameOverState();
        currentState = runningState;

        lineObservable.addObserver(this);

        pause = ImageLoader.getInstance().loadImage("/pause.png",50,50);
        refresh = ImageLoader.getInstance().loadImage("/refresh.png",50,50);
        save = ImageLoader.getInstance().loadImage("/save.png",50,50);
        load = ImageLoader.getInstance().loadImage("/load.png",50,50);

        this.scoringStrategy = new BeginnerScoringStrategy();

        mouseX = 0;
        mouseY = 0;

        stopBounds = new Rectangle(350, 500, pause.getWidth(), pause.getHeight() + pause.getHeight() / 2);
        refreshBounds = new Rectangle(350, 500 - refresh.getHeight() - 20, refresh.getWidth(),
                refresh.getHeight() + refresh.getHeight() / 2);

        saveBounds = new Rectangle(350, 100, save.getWidth(), save.getHeight() + save.getHeight() / 2);
        loadBounds = new Rectangle(350, 100 - load.getHeight() -20, load.getWidth(),
                load.getHeight() + load.getHeight() / 2);

        looper = new Timer(delay, new GameLooper());

        shapes[0] = new Shape(new int[][]{
                {1, 1, 1, 1} // I shape;
        }, this, colors[0]);

        shapes[1] = new Shape(new int[][]{
                {1, 1, 1},
                {0, 1, 0}, // T shape;
        }, this, colors[1]);

        shapes[2] = new Shape(new int[][]{
                {1, 1, 1},
                {1, 0, 0}, // L shape;
        }, this, colors[2]);

        shapes[3] = new Shape(new int[][]{
                {1, 1, 1},
                {0, 0, 1}, // J shape;
        }, this, colors[3]);

        shapes[4] = new Shape(new int[][]{
                {0, 1, 1},
                {1, 1, 0}, // S shape;
        }, this, colors[4]);

        shapes[5] = new Shape(new int[][]{
                {1, 1, 0},
                {0, 1, 1}, // Z shape;
        }, this, colors[5]);

        shapes[6] = new Shape(new int[][]{
                {1, 1},
                {1, 1}, // O shape;
        }, this, colors[6]);

    }

    public void setCurrentState(GameState state) {
        currentState = state;
    }

    @Override
    public void lineFilled(int lineNumber) {
        linesCleared++;
        updateScore(linesCleared);
    }


    public void setScoringStrategy(ScoringStrategy scoringStrategy) {
        this.scoringStrategy = scoringStrategy;
    }

    private void update() {
        if (stopBounds.contains(mouseX, mouseY) && leftClick && !buttonLapse.isRunning() && !gameOver) {
            setCurrentState((currentState == runningState) ? pausedState : runningState);
            buttonLapse.start();
            gamePaused = !gamePaused;
        }

        if (refreshBounds.contains(mouseX, mouseY) && leftClick && !gameOver) {
            caretaker.clearMementos();
            startGame();
        }
        if (refreshBounds.contains(mouseX, mouseY) && leftClick && gameOver) {
            caretaker.clearMementos();
            setCurrentState(runningState);
            startGame();
        }
        if (saveBounds.contains(mouseX, mouseY) && leftClick) {
            memento = saveToMemento();
            caretaker.addMemento(memento);
            currentMementoIndex = caretaker.mementoList.size() - 1;
        }
        if (loadBounds.contains(mouseX, mouseY) && leftClick) {
            if (!caretaker.mementoList.isEmpty()) {
                restoreFromMemento(caretaker.getMemento(caretaker.mementoList.size() - 1));
            }
        }
        if (gamePaused || gameOver) {
            return;
        }
        currentShape.update();
    }
// RYSOWANIE PLANSZY
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {

                if (board[row][col] != null) {
                    g.setColor(board[row][col]);
                    g.fillRect(col * blockSize, row * blockSize, blockSize, blockSize);
                }

            }
        }

        currentShape.render(g);

        g.drawImage(pause, stopBounds.x, stopBounds.y, null);
        g.drawImage(refresh, refreshBounds.x, refreshBounds.y, null);
        g.drawImage(save, saveBounds.x, saveBounds.y, null);
        g.drawImage(load, loadBounds.x, loadBounds.y, null);

        currentState.draw(g,board);

        g.setColor(Color.WHITE);

        g.setFont(new Font("Georgia", Font.BOLD, 18));

        g.drawString("LEVEL", WindowGame.WIDTH - 130, WindowGame.HEIGHT / 2 - 50);
        if(scoringStrategy instanceof BeginnerScoringStrategy) {
            g.setColor(Color.GREEN);
            g.drawString("BEGINNER", WindowGame.WIDTH - 130, WindowGame.HEIGHT / 2 - 30);
        }
        else if (scoringStrategy instanceof AdvancedScoringStrategy)
        {
            g.setColor(Color.YELLOW);
            g.drawString("ADVANCED", WindowGame.WIDTH - 130, WindowGame.HEIGHT / 2 - 30);
        }
        else
        {
            g.setColor(Color.RED);
            g.drawString("PRO", WindowGame.WIDTH - 130, WindowGame.HEIGHT / 2 - 30);
        }
        g.setColor(Color.WHITE);

        g.drawString("SCORE", WindowGame.WIDTH - 130, WindowGame.HEIGHT / 2);
        g.drawString(score + "", WindowGame.WIDTH - 130, WindowGame.HEIGHT / 2 + 20);

        g.drawString("LINES", WindowGame.WIDTH - 130, WindowGame.HEIGHT / 2 + 50);
        g.drawString(linesCleared + "", WindowGame.WIDTH - 130, WindowGame.HEIGHT / 2 + 70);

        g.setColor(Color.WHITE);

        for (int i = 0; i <= boardHeight; i++) {
            g.drawLine(0, i * blockSize, boardWidth * blockSize, i * blockSize);
        }
        for (int j = 0; j <= boardWidth; j++) {
            g.drawLine(j * blockSize, 0, j * blockSize, boardHeight * 30);
        }
    }

    public void setNextShape() {
        int index = random.nextInt(shapes.length);
        int colorIndex = random.nextInt(colors.length);
        nextShape = new Shape(shapes[index].getCoords(), this, colors[colorIndex]);
    }

    public void setCurrentShape() {
        currentShape = nextShape;
        setNextShape();

        for (int row = 0; row < currentShape.getCoords().length; row++) {
            for (int col = 0; col < currentShape.getCoords()[0].length; col++) {
                if (currentShape.getCoords()[row][col] != 0) {
                    if (board[currentShape.getY() + row][currentShape.getX() + col] != null) {
                        gameOver = true;
                        setCurrentState(gameOverState);
                    }
                }
            }
        }

    }

    public Color[][] getBoard() {
        return board;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            currentShape.rotateShape();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            currentShape.setDeltaX(1);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            currentShape.setDeltaX(-1);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            currentShape.speedUp();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            currentShape.speedDown();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void startGame() {
        stopGame();
        setNextShape();
        setCurrentShape();
        gameOver = false;
        looper.start();

    }

    public void stopGame() {
        score = 0;
        linesCleared =0;
        this.scoringStrategy = new BeginnerScoringStrategy();
        normal = scoringStrategy.changeNormalSpeed();


        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col] = null;
            }
        }
        looper.stop();
    }

    public void addScore() {
        score++;
    }

    public void updateScore(int linesCleared) {
        score += scoringStrategy.calculateScore(linesCleared);
        checkForStrategyChange();
    }

    private void checkForStrategyChange() {
        if (linesCleared >= 2 && scoringStrategy instanceof AdvancedScoringStrategy) {
            setScoringStrategy(new ProScoringStrategy());
            normal = scoringStrategy.changeNormalSpeed();
        }
        if (linesCleared >= 1 && scoringStrategy instanceof BeginnerScoringStrategy) {
            setScoringStrategy(new AdvancedScoringStrategy());
            normal = scoringStrategy.changeNormalSpeed();
        }
    }

    public GameMemento saveToMemento() {
        return new GameMemento(this.board, this.score, this.linesCleared,this.normal,this.scoringStrategy);
    }

    public void restoreFromMemento(GameMemento memento) {

        this.score = memento.getScore();
        this.board = copyBoardState(memento.getBoardState());
        this.linesCleared = memento.getLinesCleared();
        this.normal = memento.getNormal();

        String strategyType = memento.getStrategyType();
        if (strategyType.equals(BeginnerScoringStrategy.class.getName())) {
            setScoringStrategy(new BeginnerScoringStrategy());
        } else if (strategyType.equals(AdvancedScoringStrategy.class.getName())) {
            setScoringStrategy(new AdvancedScoringStrategy());
        } else if (strategyType.equals(ProScoringStrategy.class.getName())) {
            setScoringStrategy(new ProScoringStrategy());
        }

        normal = scoringStrategy.changeNormalSpeed();
    }

    private Color[][] copyBoardState(Color[][] original) {
        Color[][] copy = new Color[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = new Color[original[i].length];
            System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
        }
        return copy;
    }

    class GameLooper implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            update();
            repaint();
        }

    }
    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClick = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClick = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
