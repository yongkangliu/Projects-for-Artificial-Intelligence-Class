/*
 * UNC Charlotte ITCS 6150 Intelligence System Class, Programming Homework#2
 * 
 * by Yongkang Liu, 10/04/2012
 */
package PG2HillClimbing;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import PG2HillClimbingGUI.QueensGUI;

/**
 * The Min-Conflicts algorithem class
 */
public class MinConflicts {
    // The random instance.
    private Random random = new Random();

    // Record latest visited states in a list..
    private List<String> visitedList = new LinkedList<String>();

    /**
     * Record the visited state.
     * 
     * @param state
     *            The visited state.
     * @param num
     *            The number of queens in the problem.
     */
    private void addState(String state, int num) {
        this.visitedList.add(state);
        if (this.visitedList.size() >= num) {
            this.visitedList.remove(0);
        }
    }

    /**
     * Check if the state was visited.
     * 
     * @param state
     *            The state.
     * @return Return true if the state was visited. Return false if the state wasn't visited.
     */
    private boolean containState(String state) {
        for (int i = 0; i < this.visitedList.size(); i++) {
            if (state.equals(this.visitedList.get(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Create next better state.
     * 
     * @param currentQueen
     *            The current queen state.
     * @return Return the next better state.
     */
    private MinCQueenState getNextQueen(MinCQueenState currentQueen) {
        int[] currentState = currentQueen.getState();
        int[] newState = currentState;

        int i = this.random.nextInt(currentState.length);

        for (int j = 0; j < currentState.length; j++) {
            if (j == i) {
                continue;
            }
            int[] tempState = Arrays.copyOf(currentState, currentState.length);
            tempState[i] = j;

            MinCQueenState tempQueen = new MinCQueenState(tempState);

            if (tempQueen.getColumnOfConflictNumber(i) < currentQueen.getColumnOfConflictNumber(i)) {
                // if there is a better state, go to the new state.
                newState = tempState;
            } else if (tempQueen.getColumnOfConflictNumber(i) == currentQueen.getColumnOfConflictNumber(i)) {
                if (tempQueen.getColumnOfConflictNumber(i) == 0 && this.random.nextBoolean()) {
                    // if there isn't better state and there are other zero conflict positions, go to the new state.
                    newState = tempState;
                }
            }
        }

        if (newState == currentState) {
            return currentQueen;
        } else {
            return new MinCQueenState(newState);
        }
    }

    /**
     * The main function of Min-Conflicts algorithem.
     * 
     * @param num
     *            The number of queens.
     * @return Return the state data of the solved problem.
     */
    public int[] run(int num) {
        QueensGUI.resetLog("Running ...");

        // restart times.
        int numOfRestart = 0;
        // total number of changes.
        int totolNumOfChange = 0;

        while (numOfRestart++ < 10000) {
            // Start to solve a new random problem.
            this.visitedList.clear();

            // number of changes in one problem.
            int numOfChange = 0;
            // number of times get stuck.
            int stuckTimes = 0;

            MinCQueenState qState = MinCQueenState.generateRandomQueen(num);

            if (qState.getTotalNumOfConflict() == 0) {
                // The random problem happens to be successful.
                QueensGUI.appendLog("Succeed without search. The state data:" + Arrays.toString(qState.getState()));
                return qState.getState();
            }

            while (!qState.isConflictZero()) {
                MinCQueenState newQState = this.getNextQueen(qState);

                String stateStr = Arrays.toString(newQState.getState());

                if (containState(stateStr)) {
                    if (++stuckTimes > num) {
                        // The stuck times is over the number of queens. Start a new problem.
                        break;
                    }
                } else {
                    // Record this state in the list.
                    addState(stateStr, num);
                    // Reset stuck times to 0.
                    stuckTimes = 0;

                    numOfChange++;
                    totolNumOfChange++;
                }
                // Go to the next better state.
                qState = newQState;

                QueensGUI.resetLog("Restart times=" + numOfRestart + "\n\rTotal changes=" + totolNumOfChange
                        + "\n\rChanges in current try=" + numOfChange + "\n\rStuck times:" + stuckTimes);

                QueensGUI.moveQueens(qState.getState());

                if (newQState.isConflictZero()) {
                    // Found the goal state. There isn't conflict.
                    QueensGUI.appendLog("Succeed. The state data:" + Arrays.toString(qState.getState()));
                    return qState.getState();
                }
            }
        }

        // No result found after 10000 times restart.
        QueensGUI.appendLog("No result found after 10000 times restart. Failed!");
        return new int[] {};
    }
}
