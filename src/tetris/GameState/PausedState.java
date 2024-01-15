package tetris.GameState;

import tetris.GameState.GameState;
import tetris.WindowGame;

import java.awt.*;
import java.awt.Color;
public class PausedState implements GameState {

    @Override
    public void draw(Graphics g,Color[][] board) {
        String gamePausedString = "GAME PAUSED";
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Georgia", Font.BOLD, 14));
        g.drawString(gamePausedString, WindowGame.WIDTH - 135, WindowGame.HEIGHT / 2 - 100);
    }
}