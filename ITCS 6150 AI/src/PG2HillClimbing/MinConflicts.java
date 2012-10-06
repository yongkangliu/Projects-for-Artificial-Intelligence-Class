/*
 * UNC Charlotte ITCS 6150 Intelligence System Class, Programming Homework#2
 * 
 * by Yongkang Liu, 10/04/2012
 */
package PG2HillClimbing;

import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MinConflicts {
    private Random random = new Random();

    private List<String> visitedList = new LinkedList<String>();

    private void addState(String state, int num) {
        this.visitedList.add(state);
        if (this.visitedList.size() >= num) {
            this.visitedList.remove(0);
        }
    }

    private boolean containState(String state) {
        for (int i = 0; i < this.visitedList.size(); i++) {
            if (state.equals(this.visitedList.get(i))) {
                return true;
            }
        }
        return false;
    }

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
                newState = tempState;
            } else if (tempQueen.getColumnOfConflictNumber(i) == currentQueen.getColumnOfConflictNumber(i)) {
                if (tempQueen.getColumnOfConflictNumber(i) == 0 && this.random.nextBoolean()) {
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

    public boolean run(int num) {
        int numOfRestart = 0;
        int totolNumOfChange = 0;

        while (numOfRestart++ < 10000) {
            int numOfChange = 0;
            int stuckTimes = 0;
            this.visitedList.clear();
            MinCQueenState qState = MinCQueenState.generateRandomQueen(num);

            while (!qState.isConflictZero()) {
                MinCQueenState newQState = this.getNextQueen(qState);

                String stateStr = Arrays.toString(newQState.getState());

                if (containState(stateStr)) {
                    if (++stuckTimes > num) {
                        // System.out.println("stuckTimes " + stuckTimes + " " + Arrays.toString(qState.getState()));
                        break;
                    }
                } else {
                    addState(stateStr, num);
                    stuckTimes = 0;
                    numOfChange++;
                    totolNumOfChange++;
                }

                // System.out.println("restart=" + numOfRestart + " total change:" + totolNumOfChange + " change="
                // + numOfChange + "  stuckTimes:" + stuckTimes);
                qState = newQState;
                if (newQState.isConflictZero()) {
                    System.out.println("restart=" + numOfRestart + " total change:" + totolNumOfChange + " change="
                            + numOfChange);
                    System.out.println("succeed. j=" + numOfChange + " " + Arrays.toString(qState.getState()));
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Calendar startTime = Calendar.getInstance();
            MinConflicts mc = new MinConflicts();
            mc.run(300);
            Calendar endTime = Calendar.getInstance();
            System.out.println(i + "Time: " + (endTime.getTimeInMillis() - startTime.getTimeInMillis()) / 1000.0);
        }
    }
}
