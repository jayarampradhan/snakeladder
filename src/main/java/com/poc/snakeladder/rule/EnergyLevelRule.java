package com.poc.snakeladder.rule;


import com.poc.snakeladder.SquareGenerator;
import com.poc.snakeladder.model.Square;

/**
 * In case of trampoline, player is required to go forward ‘m’ steps, where m is the value of the dice throw.
 *
 * @author Jayaram.
 */
public class EnergyLevelRule implements GameRule {
    @Override
    public Square apply(Square square, int currentEnergyLevel, int currentDiceResult, SquareGenerator squareGenerator) {
        if (currentEnergyLevel == 0) {
            return squareGenerator.generateASquare(1);
        }
        return square;
    }
}
