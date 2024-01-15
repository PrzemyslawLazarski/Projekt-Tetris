package tetris.ScoringStrategy;

import tetris.ScoringStrategy.ScoringStrategy;

public class BeginnerScoringStrategy implements ScoringStrategy {

    @Override
    public int calculateScore(int linesCleared) {
        return linesCleared ;
    }

    @Override
    public int changeNormalSpeed() {
        return 600;
    }
}