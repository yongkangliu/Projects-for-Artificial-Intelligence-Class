/*
 * UNC Charlotte ITCS 6150 Intelligence System Class, Programming Homework#2
 * 
 * by Yongkang Liu, 10/04/2012
 */
package PG2HillClimbing;

import java.util.Arrays;
import java.util.Calendar;

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

    public boolean run(int num) {
        int numOfRestart = 0;
        int totolNumOfChange = 0;
        while (numOfRestart++ < 10000) {
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

                // System.out.println("restart=" + numOfRestart + " total change:" + totolNumOfChange + " change="
                // + numOfChange + " conflict=" + qState.getTotalNumOfConflict());
                if (newQState.getTotalNumOfConflict() == 0) {
                    System.out.println("restart=" + numOfRestart + " total change:" + totolNumOfChange + " change="
                            + numOfChange);
                    System.out.println("suceed." + Arrays.toString(qState.getState()));
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Calendar startTime = Calendar.getInstance();
            HillClimbing hb = new HillClimbing();
            hb.run(30);
            Calendar endTime = Calendar.getInstance();
            System.out.println(i + "Time: " + (endTime.getTimeInMillis() - startTime.getTimeInMillis()) / 1000.0);
        }
    }
}
