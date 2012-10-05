/*
 * UNC Charlotte ITCS 6150 Intelligence System Class, Programming Homework#2
 * 
 * by Yongkang Liu, 10/04/2012
 */
package PG2HillClimbing;

import static org.junit.Assert.*;

import org.junit.Test;

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
        MinConflicts mc = new MinConflicts();
        mc.run(150);
    }
}
