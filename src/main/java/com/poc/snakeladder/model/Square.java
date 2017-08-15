package com.poc.snakeladder.model;


import java.io.Serializable;

/**
 * Basic Square/position model.
 *
 * @author Jayaram
 */
public class Square implements Serializable {

    private static final long serialVersionUID = 1038287368621543789L;

    private final int position;
    private final boolean snake;
    private final boolean ladder;
    private final boolean trampoline;
    private final boolean spring;
    private final boolean pitStop;

    private Square(Builder builder) {
        this.position = builder.position;
        this.snake = builder.snake;
        this.ladder = builder.ladder;
        this.trampoline = builder.trampoline;
        this.spring = builder.spring;
        this.pitStop = builder.pitStop;
    }

    public int getPosition() {
        return position;
    }

    public boolean isSnake() {
        return snake;
    }

    public boolean isLadder() {
        return ladder;
    }

    public boolean isTrampoline() {
        return trampoline;
    }

    public boolean isSpring() {
        return spring;
    }

    public boolean isPitStop() {
        return pitStop;
    }

    /**
     * In case of the current square is qualified for the energy level, it gives the current energy level for that position.
     * @param totalSquares number of squares in the game.
     * @return zero in case not qualified else result.
     */
    public int getEnergyLevel(int totalSquares) {
        if (this.pitStop) {
            return this.position*totalSquares/3;
        } else {
          return 0;
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Square{");
        sb.append("position=").append(position);
        sb.append('}');
        return sb.toString();
    }

    public static class Builder {
        private int position;
        private boolean snake;
        private boolean ladder;
        private boolean trampoline;
        private boolean spring;
        private boolean pitStop;

        public Builder(int position) {
            this.position = position;
        }
        public Builder hasSnake() {
            this.snake = Boolean.TRUE;
            return this;
        }

        public Builder hasLadder() {
            this.ladder = Boolean.TRUE;
            return this;
        }

        public Builder hasTrampoline() {
            this.trampoline = Boolean.TRUE;
            return this;
        }

        public Builder hasSpring() {
            this.spring = Boolean.TRUE;
            return this;
        }

        public Builder hasPitStop() {
            this.pitStop = Boolean.TRUE;
            return this;
        }

        public Square build() {
            return new Square(this);
        }

    }
}
