/*
 * UNC Charlotte ITCS 6150 Intelligence System Class, Programming Homework
 * 
 * by Yongkang Liu, 9/21/2012
 */
package PG1AStarAlg;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit Test cases for A* algorithm
 */
public class AStarTest {

    @Test
    public void test1() {
        int[] start = new int[] { 5, 4, 0, 6, 1, 8, 7, 3, 2 };
        int[] goal = new int[] { 1, 2, 3, 4, 0, 5, 6, 7, 8 };

        AStar aStar = new AStar();

        boolean result = aStar.run(start, goal);

        assertEquals(true, result);
    }

    @Test
    public void test2() {
        int[] start = new int[] { 2, 8, 3, 1, 6, 4, 7, 0, 5 };
        int[] goal = new int[] { 1, 2, 3, 8, 6, 4, 7, 5, 0 };

        AStar aStar = new AStar();

        boolean result = aStar.run(start, goal);

        assertEquals(true, result);
    }

    @Test
    public void test3() {
        int[] start = new int[] { 1, 2, 3, 7, 5, 6, 8, 4, 0 };
        int[] goal = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 };

        AStar aStar = new AStar();

        boolean result = aStar.run(start, goal);

        assertEquals(true, result);
    }

    @Test
    public void test4() {
        int[] start = new int[] { 7, 2, 4, 5, 0, 6, 8, 3, 1 };
        int[] goal = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

        AStar aStar = new AStar();

        boolean result = aStar.run(start, goal);

        assertEquals(true, result);
    }
}
