package com.poc.snakeladder.rule;

import com.poc.snakeladder.SquareGenerator;
import com.poc.snakeladder.model.Square;

/**
 * A basic rule template.
 *
 * @author Jayaram.
 */
public interface GameRule {

    /**
     * For the given square apply the rules.
     * @param square {@link Square}
     * @param currentEnergyLevel Current Energy Level.
     * @param currentDiceResult Current dice result.
     * @param squareGenerator helps to generate the next square
     * @return resultant square or the same square in case no change.
     */
    Square apply(Square square, int currentEnergyLevel, int currentDiceResult, SquareGenerator squareGenerator);

}
