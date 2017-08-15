package com.poc.snakeladder.rule;

import com.poc.snakeladder.SquareGenerator;
import com.poc.snakeladder.model.Square;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * In case a square is marked as the spring, it will go back ward `m` steps.
 *
 * @author Jayaram
 */
public class SpringRule implements GameRule {

    private static final Logger LOG = LoggerFactory.getLogger(SpringRule.class);

    @Override
    public Square apply(Square square, int currentEnergyLevel, int currentDiceResult, SquareGenerator squareGenerator) {
        if (square.isSpring()) {
            LOG.debug("Applying Spring rule at position={} to move back till={}", square.getPosition(), currentDiceResult);
            return squareGenerator.generateASquare(square.getPosition() - 2*currentDiceResult );
        }
        return square;
    }
}
