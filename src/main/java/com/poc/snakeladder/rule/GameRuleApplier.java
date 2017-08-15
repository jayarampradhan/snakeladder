package com.poc.snakeladder.rule;

import com.poc.snakeladder.CommonConstant;
import com.poc.snakeladder.SquareGenerator;
import com.poc.snakeladder.model.Square;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A basic rule template.
 *
 * @author Jayaram.
 */
public class GameRuleApplier {

    private static final Logger LOG = LoggerFactory.getLogger(CommonConstant.LOGGER_NAME);

    private final List<GameRule> rules;
    private final SquareGenerator generator;

    public GameRuleApplier(SquareGenerator generator) {
        rules = new ArrayList<>();
        rules.add(new SnakeRule());
        rules.add(new LadderRule());
        rules.add(new SpringRule());
        rules.add(new TrampolineRule());
        rules.add(new EnergyLevelRule());
        this.generator = generator;
    }

    public Square applyRules(Square current, int currentEnergyLevel, int currentDiceNumber){
        final int newPosition = current.getPosition() + currentDiceNumber;
        try {
            final Square squareRuleToBeApplied = this.generator.generateASquare(newPosition);
            return doApply(squareRuleToBeApplied, currentEnergyLevel, currentDiceNumber, squareRuleToBeApplied.getPosition());
        } catch (IllegalArgumentException e) {
            LOG.warn("New Move for the dice result={} is not possible", currentDiceNumber);
        }
        return current;
    }

    private Square doApply(Square square, int currentEnergyLevel, int diceResult, int position) {
        Square result = square;
        for (GameRule rule: this.rules) {
            result = rule.apply(result, currentEnergyLevel, diceResult, this.generator);
            if (result.getPosition() != position) {
                break;
            }
        }
        if (result.getPosition() != position) {
            result = doApply(result, currentEnergyLevel, diceResult, result.getPosition());
        }
        return result;
    }


}
