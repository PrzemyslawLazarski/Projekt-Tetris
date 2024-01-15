package tetris;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public interface GameState {
    void update(Board board);
    void draw(Graphics g, Board board);
}