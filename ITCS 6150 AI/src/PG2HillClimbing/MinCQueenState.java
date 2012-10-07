/*
 * UNC Charlotte ITCS 6150 Intelligence System Class, Programming Homework#2
 * 
 * by Yongkang Liu, 10/04/2012
 */
package PG2HillClimbing;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * The state class of 8-queen problem for Min-Conflicts algorithem.<br>
 * Extends the HillCQueenState class.
 */
public class MinCQueenState extends HillCQueenState {

    // The column A is conflict with column B.
    private int conflictColA = 0;
    private int conflictColB = 0;

    // Record the conflict number of each column in a HashMap.
    private Map<Integer, Integer> columnMap = new HashMap<Integer, Integer>();

    /**
     * Constructor of MinCQueenState class.
     * 
     * @param state
     *            The state data in an array.
     */
    public MinCQueenState(int[] state) {
        super(state);
    }

    /**
     * Return the column A of the conflict.
     * 
     * @return Return the column A of the conflict.
     */
    public int getConflictColA() {
        return this.conflictColA;
    }

    /**
     * Return the column B of the conflict.
     * 
     * @return Return the column B of the conflict.
     */
    public int getConflictColB() {
        return this.conflictColB;
    }

    /**
     * Check if the conflict is zero.
     * 
     * @return Return true if there isn't a conflict. Return false if there is a conflict.
     */
    public boolean isConflictZero() {
        for (int x = 0; x < this.state.length; x++) {
            for (int y = x + 1; y < this.state.length; y++) {
                if (isConflict(x, this.state[x], y, this.state[y])) {
                    // There is a conflict between a queen in A column and B column.

                    // Record the column A.
                    this.conflictColA = x;
                    // Record the column B.
                    this.conflictColB = y;

                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Calculate the conflict number of a column.
     * 
     * @param column
     *            The number of the column.
     * @return Return the number of conflicts.
     */
    public int getColumnOfConflictNumber(int column) {
        if (this.columnMap.containsKey(column)) {
            // if the column already was calculated.
            return this.columnMap.get(column);
        } else {
            // if the column hadn't been calculated.

            int count = 0;
            boolean hasHorizontalConflict = false;
            boolean hasDiagonal1 = false;
            boolean hasDiagonal2 = false;

            for (int y = 0; y < this.state.length; y++) {
                // traversing y axis.
                if (column != y && isConflict(column, this.state[column], y, this.state[y])) {
                    int xDistance = column - y;
                    int yDistance = this.state[column] - this.state[y];

                    if (yDistance == 0) {
                        // there is a conflict horizontally.
                        hasHorizontalConflict = true;
                    } else if ((xDistance * yDistance) > 0) {
                        // there is a conflict diagonally.
                        hasDiagonal1 = true;
                    } else {
                        // (xDistance * yDistance) < 0), there is a conflict diagonally in another direction.
                        hasDiagonal2 = true;
                    }

                    if (hasHorizontalConflict && hasDiagonal1 && hasDiagonal2) {
                        // there are three conflicts in three directions.
                        break;
                    }
                }
            }

            if (hasHorizontalConflict == true) {
                // there is a conflict horizontally.
                count++;
            }
            if (hasDiagonal1 == true) {
                // there is a conflict diagonally.
                count++;
            }
            if (hasDiagonal2 == true) {
                // there is a conflict diagonally in another direction.
                count++;
            }

            // Record the conflict number in the HashMap.
            this.columnMap.put(column, count);

            return count;
        }
    }

    /**
     * Generate a random queen problem for Min-Conflicts algorithem.
     * 
     * @param num
     *            The number of queens.
     * @return Return the state of the random problem.
     */
    public static MinCQueenState generateRandomQueen(int num) {
        int[] array = new int[num];

        Random r = new Random();
        for (int i = 0; i < num; i++) {
            // set a random position for a queen. The position range is from 0 to num.
            array[i] = r.nextInt(num);
        }

        MinCQueenState qState = new MinCQueenState(array);

        return qState;
    }
}
