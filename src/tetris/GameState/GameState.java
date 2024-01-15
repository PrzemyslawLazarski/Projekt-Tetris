package tetris.GameState;

import java.awt.Graphics;
import java.awt.Color;
public interface GameState {

    void draw(Graphics g, Color[][] board);
}