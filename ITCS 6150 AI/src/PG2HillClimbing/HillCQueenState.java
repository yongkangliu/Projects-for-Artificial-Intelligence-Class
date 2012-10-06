/*
 * UNC Charlotte ITCS 6150 Intelligence System Class, Programming Homework#2
 * 
 * by Yongkang Liu, 10/04/2012
 */
package PG2HillClimbing;

import java.util.Random;

/**
 * The state class of 8-queen problem
 */
public class HillCQueenState {

    // The state data in an array. In the 8-queen problem,
    // x-axis is the suffix of the array and the range is from 0 to array length
    // y-axis is the value of the array element and the range is from 0 to array length.
    protected int[] state = new int[] {};

    // The total number of conflict. The initial value is -1, which means that the value hasn't been calculated.
    protected int numOfConflict = -1;

    /**
     * Constructor of HillCQueenState class.
     * 
     * @param state
     *            The state data in an array.
     */
    public HillCQueenState(int[] state) {
        this.state = state;
    }

    /**
     * Return the state data.
     * 
     * @return Return the state data in an array.
     */
    public int[] getState() {
        return this.state;
    }

    /**
     * Return the total number of conflict.
     * 
     * @return Return the total number of conflict.
     */
    public int getTotalNumOfConflict() {
        if (this.numOfConflict == -1) {
            // if the numOfConflict is -1, which means that the number hasn't been calculated, count the number.
            this.numOfConflict = this.countConflict();
        }

        return this.numOfConflict;
    }

    /**
     * Count the total number of conflict.
     * 
     * @return Return the total number of conflict.
     */
    private int countConflict() {
        int count = 0;

        for (int i = 0; i < this.state.length; i++) {
            // i - search all array elements in x-axis
            for (int j = i + 1; j < this.state.length; j++) {
                // j - search all possible values in y-axis
                if (isConflict(i, this.state[i], j, this.state[j])) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * Check if two queens conflict.
     * 
     * @param x1
     *            the first queen x value.
     * @param y1
     *            the first queen y value.
     * @param x2
     *            the second queen x value.
     * @param y2
     *            the second queen y value.
     * @return Return true if two queens conflict. Return false if they don't conflict.
     */
    protected boolean isConflict(int x1, int y1, int x2, int y2) {
        int xDistance = x1 - x2;
        int yDistance = y1 - y2;

        if (y1 == y2) {
            // The two queens conflict horizontally.
            return true;
        } else if (Math.abs(xDistance) == Math.abs(yDistance)) {
            // The two queens conflict diagonally.
            return true;
        } else {
            // The two queens don't conflict.
            return false;
        }
    }

    /**
     * Generate a random queen problem.
     * 
     * @param num
     *            The number of queens.
     * @return Return the state of the random problem.
     */
    public static HillCQueenState generateRandomQueen(int num) {
        int[] array = new int[num];

        Random r = new Random();
        for (int i = 0; i < num; i++) {
            // set a random position for a queen. The position range is from 0 to num.
            array[i] = r.nextInt(num);
        }

        HillCQueenState q = new HillCQueenState(array);

        return q;
    }
}
