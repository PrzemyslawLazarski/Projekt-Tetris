package tetris;

import javax.swing.JFrame;

public class WindowGame {
    public static final int WIDTH = 445, HEIGHT = 629;

    public static WindowGame instance;
    private Board board;
    private Title title;
    private JFrame window;

    private WindowGame() {

        window = new JFrame("Tetris");
        window.setSize(WIDTH, HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);

        board = new Board();
        title = new Title(this);

        window.addKeyListener(board);
        window.addKeyListener(title);

        window.add(title);

        window.setVisible(true);
    }

    public static WindowGame getInstance() {
        if (instance == null) {
            instance = new WindowGame();
        }
        return instance;
    }
    public void startTetris() {
        window.remove(title);
        window.addMouseMotionListener(board);
        window.addMouseListener(board);
        window.add(board);
        board.startGame();
        window.revalidate();
    }

    public static void main(String[] args) {
        WindowGame.getInstance();
    }

}
