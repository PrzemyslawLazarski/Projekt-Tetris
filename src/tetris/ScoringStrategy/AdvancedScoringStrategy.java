package tetris.ScoringStrategy;

import tetris.ScoringStrategy.ScoringStrategy;

public class AdvancedScoringStrategy implements ScoringStrategy {

    @Override
    public int calculateScore(int linesCleared) {
        return linesCleared *5;
    }

    @Override
    public int changeNormalSpeed() {
        return 450;
    }
}