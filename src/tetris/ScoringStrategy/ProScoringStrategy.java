package tetris.ScoringStrategy;

import tetris.ScoringStrategy.ScoringStrategy;

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