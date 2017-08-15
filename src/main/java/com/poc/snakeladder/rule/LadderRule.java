package com.poc.snakeladder.rule;


import com.poc.snakeladder.SquareGenerator;
import com.poc.snakeladder.model.Square;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * In case of ladder, it will climb the ladder.
 *
 * @author Jayaram.
 */
public class LadderRule implements GameRule {
    private static final Logger LOG = LoggerFactory.getLogger(LadderRule.class);
    @Override
    public Square apply(Square square, int currentEnergyLevel, int currentDiceResult, SquareGenerator squareGenerator) {
        if (square.isLadder()) {
            LOG.info("Applying Ladder rule at position={}", square.getPosition());
            return squareGenerator.getNextSquareFromLadder(square);
        }
        return square;
    }
}
