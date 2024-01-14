package tetris;

public interface ScoringStrategy {
    int calculateScore(int linesCleared);
    int changeNormalSpeed();
}