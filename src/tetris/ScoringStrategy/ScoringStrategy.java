package tetris.ScoringStrategy;

public interface ScoringStrategy {

    int calculateScore(int linesCleared);
    int changeNormalSpeed();
}