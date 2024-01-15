package tetris;

import javax.swing.JFrame;

public class WindowGame {
    public static final int WIDTH = 445, HEIGHT = 629;

    public static WindowGame game;
    private Board board;
    private JFrame window;

    public WindowGame() {

        window = new JFrame("Tetris");
        window.setSize(WIDTH, HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);

        board = new Board();

        window.addKeyListener(board);

        window.setVisible(true);
    }


    public void startTetris() {

        window.addMouseMotionListener(board);
        window.addMouseListener(board);
        window.add(board);
        board.startGame();
        window.revalidate();
    }

    public static void main(String[] args) {
        game = new WindowGame();
        game.startTetris();
    }

}
