/*
 * UNC Charlotte ITCS 6150 Intelligence System Class, Programming Homework#2
 * 
 * by Yongkang Liu, 10/04/2012
 */
package PG2HillClimbing;

import java.util.Arrays;

import PG2HillClimbingGUI.QueensGUI;

public class HillClimbing {
    private HillCQueenState getNextQueen(HillCQueenState currentQueen) {
        int[] currentState = currentQueen.getState();
        int[] newState = currentState;
        for (int i = 0; i < currentState.length; i++) {
            for (int j = 0; j < currentState.length; j++) {
                int[] tempState = Arrays.copyOf(currentState, currentState.length);
                tempState[i] = j;
                HillCQueenState tempQueen = new HillCQueenState(tempState);
                if (tempQueen.getTotalNumOfConflict() < currentQueen.getTotalNumOfConflict()) {
                    newState = tempState;
                }
            }
        }
        return new HillCQueenState(newState);
    }

    public int[] run(int num) {
        QueensGUI.resetLog("Running ...");
        int numOfRestart = 0;
        int totolNumOfChange = 0;
        while (numOfRestart++ < 1000) {
            int numOfChange = 0;
            HillCQueenState qState = HillCQueenState.generateRandomQueen(num);
            while (qState.getTotalNumOfConflict() != 0) {
                numOfChange++;
                totolNumOfChange++;

                HillCQueenState newQState = this.getNextQueen(qState);

                if (newQState.getTotalNumOfConflict() == qState.getTotalNumOfConflict()) {
                    break;
                }
                qState = newQState;

                QueensGUI.resetLog("Restart times=" + numOfRestart + "\n\rTotal changes=" + totolNumOfChange
                        + "\n\rChanges in current try=" + numOfChange + "\n\rConflict="
                        + qState.getTotalNumOfConflict());

                QueensGUI.drawQueens(qState.getState());
                if (newQState.getTotalNumOfConflict() == 0) {
                    QueensGUI.appendLog("Succeed. The state data:" + Arrays.toString(qState.getState()));
                    return qState.getState();
                }
            }
        }
        QueensGUI.appendLog("Unknown reason. Failed!");
        return null;
    }
}
