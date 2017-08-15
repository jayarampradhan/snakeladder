package com.poc.snakeladder;

import com.poc.snakeladder.model.Square;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A basic Square generator, which has static map information regarding snake and ladder.
 *
 * @author Jayaram.
 */
public class SquareGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(CommonConstant.LOGGER_NAME);
    private final int totalSquares;
    private final Map<Integer, Integer> ladderMap;
    private final Map<Integer, Integer> snakeMap;
    private final Set<Integer> springMap;
    private final Set<Integer> pitStopMap;
    private final Set<Integer> trampolineMap;

    public SquareGenerator(int totalSquares) {
        this.totalSquares = totalSquares;
        this.ladderMap = Collections.unmodifiableMap(generateLadderMap());
        this.snakeMap= Collections.unmodifiableMap(generateSnakeMap());
        this.springMap= Collections.unmodifiableSet(generateSpringSet());
        this.pitStopMap= Collections.unmodifiableSet(generatePitStopSet());
        this.trampolineMap= Collections.unmodifiableSet(generateTrampolineSet());
        LOG.info("Ladder Path={}", ladderMap);
        LOG.info("Snake Path={}", snakeMap);
        LOG.info("Spring Path={}", springMap);
        LOG.info("PitStop Path={}", pitStopMap);
        LOG.info("Trampoline Path={}", trampolineMap);
    }

    private Map<Integer, Integer> generateLadderMap(){
        final Map<Integer, Integer> map = new HashMap<>();
        for (int i = 2; i < this.totalSquares; i += 4){
            int maxStep = i + 7;
            if (maxStep < this.totalSquares) {
                map.put(i, maxStep);
            }
        }
        return map;
    }

    private Map<Integer, Integer> generateSnakeMap(){
        final Map<Integer, Integer> map = new HashMap<>();
        for (int i = this.totalSquares - 3; i > 5; i --){
            int maxStep = i - 7;
            if (maxStep > 1 && !this.ladderMap.containsKey(i)
                    && !this.ladderMap.values().contains(i)) {
                map.put(i, maxStep);
            }
        }
        return map;
    }

    private Set<Integer> generateSpringSet(){
        final Set<Integer> s = new HashSet<>();
        for (int i = 2; i < this.totalSquares; i += 2){
            if (i - 6 <= 0) {
                continue;
            }
            s.add(i);
        }
        return s;
    }

    private Set<Integer> generatePitStopSet(){
        final Set<Integer> s = new HashSet<>();
        for (int i = 1; i < this.totalSquares; i += (this.totalSquares/3)){
            s.add(i);
        }
        return s;
    }

    private Set<Integer> generateTrampolineSet(){
        final Set<Integer> s = new HashSet<>();
        for (int i = 2; i < this.totalSquares; i += 3){
            if (i + 6 >= this.totalSquares) {
                continue;
            }
            s.add(i);
        }
        return s;
    }

    /**
     * Generate Square Based on the specified position and assign the appropriate level.
     * @param position position for which it needs to be generated.
     * @return {@link Square}
     */
    public Square generateASquare(int position){
        if (position > totalSquares || position < 1) {
            throw new IllegalArgumentException("No New Square is possible.");
        }
        final Square.Builder builder = new Square.Builder(position);
        if (pitStopMap.contains(position)) {
            builder.hasPitStop();
        }
        if (trampolineMap.contains(position)) {
            builder.hasTrampoline();
        }
        if (springMap.contains(position)) {
            builder.hasSpring();
        }
        if (snakeMap.keySet().contains(position)) {
            builder.hasSnake();
        }
        if (ladderMap.keySet().contains(position)) {
            builder.hasLadder();
        }
        return builder.build();
    }

    public Square getNextSquareFromLadder(Square square) {
        final int position = square.getPosition();
        final Integer newPos = this.ladderMap.get(position);
        if (newPos != null) {
            return generateASquare(newPos);
        }
        return square;
    }

    public Square getNextSquareFromSnake(Square square) {
        final int position = square.getPosition();
        final Integer newPos = this.snakeMap.get(position);
        if (newPos != null) {
            return generateASquare(newPos);
        }
        return square;
    }
}
