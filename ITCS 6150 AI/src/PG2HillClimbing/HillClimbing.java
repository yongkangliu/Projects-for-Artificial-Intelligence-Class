/*
 * UNC Charlotte ITCS 6150 Intelligence System Class, Programming Homework#2
 * 
 * by Yongkang Liu, 10/04/2012
 */
package PG2HillClimbing;

import java.util.Arrays;

import PG2HillClimbingGUI.QueensGUI;

/**
 * The Hill Climbing algorithm class
 */
public class HillClimbing {

    /**
     * Create next better state.
     * 
     * @param currentQueen
     *            The current queen state.
     * @return Return the next better state.
     */
    private HillCQueenState getNextQueen(HillCQueenState currentQueen) {
        int[] currentState = currentQueen.getState();
        int[] newState = currentState;

        for (int x = 0; x < currentState.length; x++) {
            // traversing x axis.
            for (int y = 0; y < currentState.length; y++) {
                // traversing y axis.

                int[] tempState = Arrays.copyOf(currentState, currentState.length);
                tempState[x] = y;

                HillCQueenState tempQueen = new HillCQueenState(tempState);
                if (tempQueen.getTotalNumOfConflict() < currentQueen.getTotalNumOfConflict()) {
                    // when new state is better than current state.
                    newState = tempState;
                }
            }
        }
        return new HillCQueenState(newState);
    }

    /**
     * The main function of Hill Climbing algorithm.
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

        while (numOfRestart++ < 1000) {
            // Start to solve a new random problem.

            // number of changes in one problem
            int numOfChange = 0;

            HillCQueenState qState = HillCQueenState.generateRandomQueen(num);

            if (qState.getTotalNumOfConflict() == 0) {
                // The random problem happens to be successful.
                QueensGUI.appendLog("Succeed without search. The state data:" + Arrays.toString(qState.getState()));
                return qState.getState();
            }

            while (qState.getTotalNumOfConflict() != 0) {
                numOfChange++;
                totolNumOfChange++;

                // Create next better state.
                HillCQueenState newQState = this.getNextQueen(qState);

                if (newQState.getTotalNumOfConflict() == qState.getTotalNumOfConflict()) {
                    // There isn't better state.
                    break;
                }

                // Go to the next better state.
                qState = newQState;

                QueensGUI.resetLog("Restart times=" + numOfRestart + "\n\rTotal changes=" + totolNumOfChange
                        + "\n\rChanges in current try=" + numOfChange + "\n\rConflict="
                        + qState.getTotalNumOfConflict());

                QueensGUI.moveQueens(qState.getState());

                if (newQState.getTotalNumOfConflict() == 0) {
                    // Found the goal state.
                    QueensGUI.appendLog("Succeed. The state data:" + Arrays.toString(qState.getState()));
                    return qState.getState();
                }
            }
        }

        // No result found after 1000 times restart.
        QueensGUI.appendLog("No result found after 1000 times restart. Failed!");
        return new int[] {};
    }
}
