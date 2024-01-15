package tetris.GameState;

import tetris.GameState.GameState;
import tetris.WindowGame;

import java.awt.*;
import java.awt.Color;
public class GameOverState implements GameState {


    @Override
    public void draw(Graphics g, Color[][] board) {
        String gameOverString = "GAME OVER";
        g.setColor(Color.RED);
        g.setFont(new Font("Georgia", Font.BOLD, 14));
        g.drawString(gameOverString, WindowGame.WIDTH - 130, WindowGame.HEIGHT / 2 - 100);
    }
}