/*
 * UNC Charlotte ITCS 6150 Intelligence System Class, Programming Homework#2
 * 
 * by Yongkang Liu, 10/04/2012
 */
package PG2HillClimbing;

import java.util.Arrays;

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

    public void run(int num) {
        int i = 1000;

        while (i-- > 0) {
            int j = 0;
            HillCQueenState qState = HillCQueenState.generateRandomQueen(num);
            while (qState.getTotalNumOfConflict() != 0) {
                j++;
                System.out.println("i=" + i + " j=" + j + " conflict=" + qState.getTotalNumOfConflict());
                HillCQueenState newQState = this.getNextQueen(qState);

                if (newQState.getTotalNumOfConflict() == qState.getTotalNumOfConflict()) {
                    break;
                }
                qState = newQState;
                if (newQState.getTotalNumOfConflict() == 0) {
                    System.out.println("i=" + i + " j=" + j + " conflict=" + qState.getTotalNumOfConflict());
                    System.out.println(Arrays.toString(qState.getState()));
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        HillClimbing hb = new HillClimbing();
        hb.run(20);
    }
}
