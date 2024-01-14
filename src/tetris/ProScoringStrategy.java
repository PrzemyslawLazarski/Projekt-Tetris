package tetris;

public class ProScoringStrategy implements ScoringStrategy {
    @Override
    public int calculateScore(int linesCleared) {
        return linesCleared * 10;
    }

    @Override
    public int changeNormalSpeed() {
        return 300;
    }
}