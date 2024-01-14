package tetris;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public interface GameState {
    void handleInput(KeyEvent e);
    void update();
    void render(Graphics g);
}
