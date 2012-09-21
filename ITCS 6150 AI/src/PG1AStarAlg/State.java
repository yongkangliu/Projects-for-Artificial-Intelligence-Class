package PG1AStarAlg;

import java.util.Arrays;

public class State {
    private static State goalState;

    private int[] puzzleData = new int[0];

    private int hCost = 0;
    private int gCost = 0;
    private int fCost = 0;

    private int movedNodeName = -1;

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

    public State(int[] puzzleDate, int gCost) {
        this.puzzleData = puzzleDate;
        this.gCost = gCost;
        if (State.goalState != null) {
            this.hCost = calculateHCost(State.goalState);
        }

        this.fCost = this.hCost + this.gCost;
    }

    public int[] getPuzzleDate() {
        return this.puzzleData;
    }

    public int getFCost() {
        return this.fCost;
    }

    public static State getGoalState() {
        return State.goalState;
    }

    public static void setGoalState(State state) {
        State.goalState = state;
    }

    public int getMovedNodeName() {
        return movedNodeName;
    }

    public void setMovedNodeName(int movedNodeName) {
        this.movedNodeName = movedNodeName;
    }

    public void printState() {
        System.out.println();
        System.out.println("cost:" + hCost + " puzzle:" + Arrays.toString(this.puzzleData));
    }

    public boolean isSameState(State state) {
        if (Arrays.equals(state.puzzleData, this.puzzleData)) {
            return true;
        } else {
            return false;
        }
    }

    public int[] getMoveDirections() {
        // if direction = 0, top tile moves down.
        // if direction = 1, right tile moves to left.
        // if direction = 2, bottom tile moves up.
        // if direction = 3, left tile moves to right.
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
        newState.setMovedNodeName(movedTileName);
        return newState;
    }
}