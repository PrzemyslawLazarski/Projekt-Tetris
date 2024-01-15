package tetris.GameState;

import tetris.GameState.GameState;
import tetris.WindowGame;

import java.awt.*;
import java.awt.Color;
public class RunningState implements GameState {

    @Override
    public void draw(Graphics g,Color[][] board) {
        String gamePausedString = "GAME RUNNING";
        g.setColor(Color.GREEN);
        g.setFont(new Font("Georgia", Font.BOLD, 13));
        g.drawString(gamePausedString, WindowGame.WIDTH - 140, WindowGame.HEIGHT / 2 - 100);
    }
}
