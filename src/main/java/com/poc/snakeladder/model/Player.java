package com.poc.snakeladder.model;

import java.io.Serializable;

/**
 * Basic model for a player.
 *
 * @author Jayaram
 */
public class Player implements Serializable {

    private static final long serialVersionUID = 2350465219678404940L;

    private final String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
