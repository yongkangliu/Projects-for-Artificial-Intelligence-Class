/*
 * UNC Charlotte ITCS 6150 Intelligence System Class, Programming Homework#2
 * 
 * by Yongkang Liu, 10/04/2012
 */
package PG2HillClimbing;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit Test cases for refactoring code.
 */
public class PG2HillClimbingTest {

    @Test
    public void testQueenState1() {
        HillCQueenState q = new HillCQueenState(new int[] { 1, 2, 3 });
        assertEquals(3, q.getTotalNumOfConflict());
    }

    @Test
    public void testQueenState2() {
        HillCQueenState q = new HillCQueenState(new int[] { 7, 2, 6, 3, 1, 4, 0, 5 });
        assertEquals(1, q.getTotalNumOfConflict());
    }

    @Test
    public void testQueenState3() {
        HillCQueenState q = new HillCQueenState(new int[] { 4, 5, 6, 3, 4, 5, 6, 5 });
        assertEquals(17, q.getTotalNumOfConflict());
    }

    @Test
    public void testQueenState4() {
        HillCQueenState q = new HillCQueenState(new int[] { 0, 7, 4, 2, 5, 6, 1, 3 });
        assertEquals(1, q.getTotalNumOfConflict());
    }

    @Test
    public void testQueenState5() {
        HillCQueenState q = new HillCQueenState(new int[] { 77, 45, 41, 15, 21, 89, 76, 38, 30, 43, 6, 87, 62, 4, 72,
                60, 85, 53, 23, 44, 84, 93, 33, 74, 57, 2, 8, 79, 7, 64, 49, 17, 75, 25, 90, 83, 31, 94, 52, 46, 99,
                69, 27, 37, 97, 82, 19, 13, 1, 12, 18, 91, 50, 73, 78, 70, 3, 35, 14, 86, 59, 26, 71, 92, 51, 58, 47,
                11, 20, 40, 96, 32, 48, 0, 34, 88, 24, 98, 66, 68, 81, 56, 39, 55, 5, 65, 36, 42, 28, 22, 9, 61, 54,
                67, 63, 10, 16, 80, 95, 29 });
        assertEquals(0, q.getTotalNumOfConflict());
    }

    @Test
    public void testHillClimbingAlgorithm() {
        HillClimbing hc = new HillClimbing();
        assertEquals(true, hc.run(15));
    }

    @Test
    public void testMinConflictsAlgorithm() {
        MinConflicts mc = new MinConflicts();
        assertEquals(true, mc.run(150));
    }
}
