package com.poc.snakeladder.rule;


import com.poc.snakeladder.SquareGenerator;
import com.poc.snakeladder.model.Square;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * In case of snake, it will climb down the position.
 *
 * @author Jayaram.
 */
public class SnakeRule implements GameRule {
    private static final Logger LOG = LoggerFactory.getLogger(SnakeRule.class);
    @Override
    public Square apply(Square square, int currentEnergyLevel, int currentDiceResult, SquareGenerator squareGenerator) {
        if (square.isSnake()) {
            LOG.info("Applying Snake rule at position={}", square.getPosition());
            return squareGenerator.getNextSquareFromSnake(square);
        }
        return square;
    }
}
