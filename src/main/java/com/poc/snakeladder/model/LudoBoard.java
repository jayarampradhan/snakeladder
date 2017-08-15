package com.poc.snakeladder.model;


import java.io.Serializable;

/**
 * Ludo board.
 *
 * @author Jayaram
 */
public class LudoBoard implements Serializable {

    private int currentEnergyLevel;
    private Square square;

    public int getCurrentEnergyLevel() {
        return currentEnergyLevel;
    }

    public void setCurrentEnergyLevel(int currentEnergyLevel) {
        this.currentEnergyLevel = currentEnergyLevel;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LudoBoard{");
        sb.append("currentEnergyLevel=").append(currentEnergyLevel);
        sb.append(", square=").append(square);
        sb.append('}');
        return sb.toString();
    }
}
