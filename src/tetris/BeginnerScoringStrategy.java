package tetris;

public class BeginnerScoringStrategy implements ScoringStrategy {
    @Override
    public int calculateScore(int linesCleared) {
        return linesCleared * 1;
    }

    @Override
    public int changeNormalSpeed() {
        return 600;
    }
}