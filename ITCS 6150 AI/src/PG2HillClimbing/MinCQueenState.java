/*
 * UNC Charlotte ITCS 6150 Intelligence System Class, Programming Homework#2
 * 
 * by Yongkang Liu, 10/04/2012
 */
package PG2HillClimbing;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MinCQueenState extends HillCQueenState {
    private int conflictColA = 0;
    private int conflictColB = 0;

    private Map<Integer, Integer> columnMap = new HashMap<Integer, Integer>();

    public MinCQueenState(int[] state) {
        super(state);
    }

    public int getConflictColA() {
        return this.conflictColA;
    }

    public int getConflictColB() {
        return this.conflictColB;
    }

    public boolean isConflictZero() {
        for (int i = 0; i < this.state.length; i++) {
            for (int j = i + 1; j < this.state.length; j++) {
                if (isConflict(i, this.state[i], j, this.state[j])) {
                    this.conflictColA = i;
                    this.conflictColB = j;
                    return false;
                }
            }
        }
        return true;
    }

    public int getColumnOfConflictNumber(int col) {
        if (this.columnMap.containsKey(col)) {
            return this.columnMap.get(col);
        } else {
            int count = 0;
            boolean hasHorizontalConflict = false;
            boolean hasDiagonal1 = false;
            boolean hasDiagonal2 = false;

            for (int j = 0; j < this.state.length; j++) {
                if (col != j && isConflict(col, this.state[col], j, this.state[j])) {
                    int xDistance = col - j;
                    int yDistance = this.state[col] - this.state[j];
                    if (yDistance == 0) {
                        hasHorizontalConflict = true;
                    } else if ((xDistance * yDistance) > 0) {
                        hasDiagonal1 = true;
                    } else {
                        hasDiagonal2 = true;
                    }

                    if (hasHorizontalConflict && hasDiagonal1 && hasDiagonal2) {
                        break;
                    }
                }
            }

            if (hasHorizontalConflict == true) {
                count++;
            }
            if (hasDiagonal1 == true) {
                count++;
            }
            if (hasDiagonal2 == true) {
                count++;
            }
            this.columnMap.put(col, count);
            return count;
        }
    }

    public static MinCQueenState generateRandomQueen(int num) {
        int[] array = new int[num];
        Random r = new Random();
        for (int i = 0; i < num; i++) {
            array[i] = r.nextInt(num);
        }
        MinCQueenState q = new MinCQueenState(array);
        return q;
    }
}
