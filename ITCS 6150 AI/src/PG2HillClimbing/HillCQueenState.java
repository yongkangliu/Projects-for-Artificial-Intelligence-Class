/*
 * UNC Charlotte ITCS 6150 Intelligence System Class, Programming Homework#2
 * 
 * by Yongkang Liu, 10/04/2012
 */
package PG2HillClimbing;

import java.util.Random;

public class HillCQueenState {

    protected int[] state = new int[] {};
    protected int numOfConflict = -1;

    public HillCQueenState(int[] state) {
        this.state = state;
    }

    public int[] getState() {
        return this.state;
    }

    public int getTotalNumOfConflict() {
        if (this.numOfConflict == -1) {
            this.numOfConflict = this.countConflict();
        }
        return this.numOfConflict;
    }

    private int countConflict() {
        int count = 0;
        for (int i = 0; i < this.state.length; i++) {
            for (int j = i + 1; j < this.state.length; j++) {
                if (isConflict(i, this.state[i], j, this.state[j])) {
                    count++;
                }
            }
        }
        return count;
    }

    protected boolean isConflict(int x1, int y1, int x2, int y2) {
        int xDistance = x1 - x2;
        int yDistance = y1 - y2;

        if (y1 == y2) {
            return true;
        } else if (Math.abs(xDistance) == Math.abs(yDistance)) {
            return true;
        } else {
            return false;
        }
    }

    public static HillCQueenState generateRandomQueen(int num) {
        int[] array = new int[num];
        Random r = new Random();
        for (int i = 0; i < num; i++) {
            array[i] = r.nextInt(num);
        }
        HillCQueenState q = new HillCQueenState(array);
        return q;
    }
}
