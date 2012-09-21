/*
 * UNC Charlotte ITCS 6150 Intelligence System Class, Programming Homework
 * 
 * by Yongkang Liu, 9/21/2012
 */
package PG1AStarAlg;

import java.util.Arrays;

/**
 * 8-puzzle State Class. In this class, include the puzzle data, g cost value, h cost value and f cost value.
 */
public class State {
    // The goal State.
    private static State goalState;

    // the puzzle data including the 9 tiles.
    private int[] puzzleData = new int[9];

    // g cost represents the search tree level.
    private int gCost = 0;

    // h cost represents the heuristic function value.
    private int hCost = 0;

    // f cost is the sum of g cost and h cost.
    private int fCost = 0;

    // record the name of the tile moved to reach this state.
    private int movedNodeName = -1;

    /**
     * Constructor of State class.
     * 
     * @param puzzleDate
     *            the 8-puzzle data is represented by a 9-length array.
     * @param gCost
     *            the gCost represents the current level in the search tree.
     */
    public State(int[] puzzleDate, int gCost) {
        this.puzzleData = puzzleDate;
        this.gCost = gCost;
        if (State.goalState != null) {
            this.hCost = calculateHCost(State.goalState);
        }

        this.fCost = this.hCost + this.gCost;
    }

    private int calculateHCost(State goalState) {
        int totalCost = 0;
        for (int i = 1; i <= 9; i++) {
            int currentPos = findNumberPosition(this.puzzleData, i % 9) + 1;
            int goalPos = findNumberPosition(goalState.puzzleData, i % 9) + 1;
            totalCost = totalCost + Math.abs(goalPos - currentPos) / 3 + Math.abs(goalPos - currentPos) % 3;
        }

        return totalCost;
    }

    private int findNumberPosition(int[] puzzleData, int number) {
        for (int i = 0; i < puzzleData.length; i++) {
            if (puzzleData[i] == number) {
                return i;
            }
        }
        return -1;
    }

    private void exchangeValue(int[] puzzle, int a, int b) {
        int temp = puzzle[a];
        puzzle[a] = puzzle[b];
        puzzle[b] = temp;
    }

    /**
     * Return the goal state.
     * 
     * @return return the goal state.
     */
    public static State getGoalState() {
        return State.goalState;
    }

    /**
     * Set the goal state.
     * 
     * @param state
     *            the goal state.
     */
    public static void setGoalState(State state) {
        State.goalState = state;
    }

    /**
     * Return the puzzle data
     * 
     * @return return the puzzle data.
     */
    public int[] getPuzzleDate() {
        return this.puzzleData;
    }

    /**
     * Return the f cost, which is the sum of g cost and h cost.
     * 
     * @return return the f cost value.
     */
    public int getFCost() {
        return this.fCost;
    }

    /**
     * Return the name of the tile moved to reach this state.
     * 
     * @return return the name of the tile moved to reach this state.
     */
    public int getMovedNodeName() {
        return movedNodeName;
    }

    /**
     * Set the name of the tile moved to reach this state.
     * 
     * @param movedNodeName
     *            the name of the tile moved to reach this state.
     */
    public void setMovedNodeName(int movedNodeName) {
        this.movedNodeName = movedNodeName;
    }

    /**
     * Print current state's puzzle data.
     */
    public void printState() {
        System.out.println();
        System.out.println("cost:" + hCost + " puzzle:" + Arrays.toString(this.puzzleData));
    }

    /**
     * If another state's puzzle data is same as the puzzle data of this data, return true.
     * 
     * @param state
     *            another state.
     * @return if the puzzle data are same, return true. Otherwise, return false.
     */
    public boolean isSameState(State state) {
        if (Arrays.equals(state.puzzleData, this.puzzleData)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return the available move directions <br>
     * <br>
     * The direction = 0 represents top tile moves down. <br>
     * The direction = 1 represents right tile moves to left. <br>
     * The direction = 2 represents bottom tile moves up. <br>
     * The direction = 3 represents left tile moves to right.
     * 
     * @return return the direction array.
     */
    public int[] getMoveDirections() {
        // find the position of empty tile.
        int zeroPosition = findNumberPosition(this.puzzleData, 0);

        if (zeroPosition == 0) {
            return new int[] { 1, 2 };
        } else if (zeroPosition == 1) {
            return new int[] { 1, 2, 3 };
        } else if (zeroPosition == 2) {
            return new int[] { 2, 3 };
        } else if (zeroPosition == 3) {
            return new int[] { 0, 1, 2 };
        } else if (zeroPosition == 5) {
            return new int[] { 0, 2, 3 };
        } else if (zeroPosition == 6) {
            return new int[] { 0, 1 };
        } else if (zeroPosition == 7) {
            return new int[] { 0, 1, 3 };
        } else if (zeroPosition == 8) {
            return new int[] { 0, 3 };
        } else {// when zeroPosition == 4
            return new int[] { 0, 1, 2, 3 };
        }
    }

    /**
     * Create new state for the next step after move the tile along with the direction.
     * 
     * @param direction
     *            the move direction<br>
     *            The direction = 0 represents top tile moves down. <br>
     *            The direction = 1 represents right tile moves to left. <br>
     *            The direction = 2 represents bottom tile moves up. <br>
     *            The direction = 3 represents left tile moves to right.
     * @return return the new state.
     */
    public State moveToNextState(int direction) {
        int[] tempPuzzle = Arrays.copyOf(this.puzzleData, this.puzzleData.length);
        int zeroPosition = findNumberPosition(tempPuzzle, 0);

        int movedTileName = -1;
        if (direction == 0) {
            // When direction = 0, top tile moves down.
            movedTileName = tempPuzzle[zeroPosition - 3];
            exchangeValue(tempPuzzle, zeroPosition, zeroPosition - 3);
        } else if (direction == 1) {
            // When direction = 1, right tile moves to left.
            movedTileName = tempPuzzle[zeroPosition + 1];
            exchangeValue(tempPuzzle, zeroPosition, zeroPosition + 1);
        } else if (direction == 2) {
            // When direction = 2, bottom tile moves up.
            movedTileName = tempPuzzle[zeroPosition + 3];
            exchangeValue(tempPuzzle, zeroPosition, zeroPosition + 3);
        } else if (direction == 3) {
            // When direction = 3, left tile moves to right.
            movedTileName = tempPuzzle[zeroPosition - 1];
            exchangeValue(tempPuzzle, zeroPosition, zeroPosition - 1);
        }

        State newState = new State(tempPuzzle, this.gCost + 1);

        // set the name of the tile moved to reach new state.
        newState.setMovedNodeName(movedTileName);

        return newState;
    }
}