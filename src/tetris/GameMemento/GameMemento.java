package tetris.GameMemento;

import tetris.ScoringStrategy.ScoringStrategy;

import java.awt.*;

public class GameMemento {
    private final Color[][] boardState;
    private final int score;
    private final int linesCleared;

    private final String strategyType;
    private final int normal;

    public GameMemento(Color[][] boardState, int score, int linesCleared, int normal, ScoringStrategy strategy) {
        this.boardState = copyBoardState(boardState);
        this.score = score;
        this.linesCleared = linesCleared;
        this.normal = normal;
        this.strategyType = strategy.getClass().getName();
    }

    private Color[][] copyBoardState(Color[][] original) {
        Color[][] copy = new Color[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = new Color[original[i].length];
            System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
        }
        return copy;
    }

    public Color[][] getBoardState() {
        return boardState;
    }

    public int getScore() {
        return score;
    }

    public int getLinesCleared() {
        return linesCleared;
    }


    public int getNormal() {
        return normal;
    }

    public String getStrategyType() {
        return strategyType;
    }
}