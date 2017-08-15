package com.poc.snakeladder;


import com.poc.snakeladder.model.LudoBoard;
import com.poc.snakeladder.model.Player;
import com.poc.snakeladder.model.Square;
import com.poc.snakeladder.rule.GameRuleApplier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Process to initialize the game plan and holds the state of the game.
 *
 * @author Jayaram
 */
public class GameLauncher {

    private static final Logger LOG = LoggerFactory.getLogger(CommonConstant.LOGGER_NAME);

    private Map<Player, LudoBoard> board;
    private final int totalSquares;
    private final int totalPlayers;
    private final SquareGenerator squareGenerator;
    private final GameRuleApplier gameRuleApplier;

    public GameLauncher(int numberOfPlayers, int totalSquares) {
        if (numberOfPlayers < 1 || totalSquares < 1) {
            throw new IllegalArgumentException("Game Can't be initialized, at minimum 1 player needed and with minimum 1 square");
        }
        this.totalPlayers = numberOfPlayers;
        this.totalSquares = totalSquares;
        this.squareGenerator = new SquareGenerator(this.totalSquares);
        this.gameRuleApplier = new GameRuleApplier(this.squareGenerator);
    }

    /**
     * Initialize the state of the player and game board.
     */
    public void initialize() {
        Map<Player, LudoBoard> tempBoard = new HashMap<>();
        final Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < this.totalPlayers; i++) {
            LOG.info("Please enter player[{}] Name", (i+1));
            final String name = scanner.next();
            Player p = new Player(name);
            final LudoBoard board = new LudoBoard();
            final Square square = this.squareGenerator.generateASquare(1);
            board.setSquare(square);
            board.setCurrentEnergyLevel(square.getEnergyLevel(this.totalSquares));
            tempBoard.put(p, board);
        }
        this.board = Collections.unmodifiableMap(tempBoard);
//        scanner.close();
    }

    public void launch() {
        //Step 1: Lunch the game
        initialize();
        //Step 2: Do play the moves and enjoy.
        doPlay();

    }

    private void doPlay() {

        boolean gameCompleted = Boolean.FALSE;
        while(!gameCompleted) {
            for (Player player: board.keySet()) {
                if (gameCompleted) {
                    break;
                }
                LOG.info("{}: Enter the Dice result", player.getName());
                int diceMove = getNextDiceThrowOutCome();
                final LudoBoard ludoBoard = board.get(player);
                final Square lastSquare = ludoBoard.getSquare();
                final Square newSquare = this.gameRuleApplier.applyRules(lastSquare, ludoBoard.getCurrentEnergyLevel(), diceMove);
                if (lastSquare.getPosition() != newSquare.getPosition()) {
                    ludoBoard.setSquare(newSquare);
                }
                //Always possible that move return back to same position
                ludoBoard.setCurrentEnergyLevel(ludoBoard.getCurrentEnergyLevel() + newSquare.getEnergyLevel(this.totalSquares));

                if (newSquare.getPosition() == this.totalSquares) {
                    LOG.info("{}: won!!!", player.getName());
                    gameCompleted = Boolean.TRUE;
                } else {
                    LOG.info("{}: New State: {}", player.getName(), ludoBoard.toString());
                    //Always Each Dice through reduce one energy
                    ludoBoard.setCurrentEnergyLevel(ludoBoard.getCurrentEnergyLevel() -1);
                }
            }
        }
    }

    private int getNextDiceThrowOutCome(){
        Scanner sc = new Scanner(System.in);
        int diceMove = sc.nextInt();
        if(diceMove > 6 || diceMove < 1){
            LOG.warn("Please enter a valid number in (1-6) range.");
            diceMove = getNextDiceThrowOutCome();
        }
        return diceMove;
    }


}
