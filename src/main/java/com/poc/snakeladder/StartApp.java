package com.poc.snakeladder;


import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main application to start the game.
 *
 * @author Jayaram
 */
public class StartApp {

    private static final Logger LOG = LoggerFactory.getLogger(CommonConstant.LOGGER_NAME);

    public static void main(String[] args) {

        try(Scanner sc = new Scanner(System.in)){
            LOG.info("Hello Welcome to Snake Ladder !!!");
            LOG.info("Any time of the game, use CTRL + D to terminate the session!!!");
            LOG.info("Please Enter total number of players: ");
            final int numberOfPlayers = sc.nextInt();
            LOG.info("Please Enter total number Squares: ");
            final int totalSquares = sc.nextInt();
            try {
                GameLauncher launcher = new GameLauncher(numberOfPlayers, totalSquares);
                launcher.launch();
            } catch (IllegalArgumentException e){
                LOG.error(e.getMessage());
            }
        }
    }
}
