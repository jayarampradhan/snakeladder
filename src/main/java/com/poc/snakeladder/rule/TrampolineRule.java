package com.poc.snakeladder.rule;


import com.poc.snakeladder.SquareGenerator;
import com.poc.snakeladder.model.Square;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * In case of trampoline, player is required to go forward ‘m’ steps, where m is the value of the dice throw.
 *
 * @author Jayaram.
 */
public class TrampolineRule implements GameRule {
    private static final Logger LOG = LoggerFactory.getLogger(TrampolineRule.class);
    @Override
    public Square apply(Square square, int currentEnergyLevel, int currentDiceResult, SquareGenerator squareGenerator) {
        if (square.isTrampoline()) {
            LOG.debug("Applying Spring rule at position={} to move forward till={}", square.getPosition(), currentDiceResult);
            return squareGenerator.generateASquare(square.getPosition() + currentDiceResult);
        }
        return square;
    }
}
