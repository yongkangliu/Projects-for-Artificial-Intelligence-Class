/*
 * UNC Charlotte ITCS 6150 Intelligence System Class, Programming Homework
 * 
 * by Yongkang Liu, 9/21/2012
 */
package PG1AStarAlg;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit Test cases for refactor
 */
public class AStarTest {

    @Test
    public void test1() {
        int[] start = new int[] { 5, 4, 0, 6, 1, 8, 7, 3, 2 };
        int[] goal = new int[] { 1, 2, 3, 4, 0, 5, 6, 7, 8 };

        AStar aStar = new AStar();

        boolean result = aStar.run(start, goal);

        assertEquals(true, result);
        assertEquals(612, TreeNode.getNumOfNodesExpanded());
        assertEquals(
                "4 -> 5 -> 6 -> 1 -> 3 -> 2 -> 8 -> 3 -> 5 -> 4 -> 3 -> 5 -> 1 -> 6 -> 4 -> 1 -> 2 -> 7 -> 6 -> 4 -> 1 -> 2 -> Goal",
                TreeNode.getMoveStepSequence());
        assertEquals(1109, TreeNode.getNumOfNodesGenerated());
        assertEquals(22, TreeNode.getNumOfStepsToGoal());
    }

    @Test
    public void test2() {
        int[] start = new int[] { 2, 8, 3, 1, 6, 4, 7, 0, 5 };
        int[] goal = new int[] { 1, 2, 3, 8, 6, 4, 7, 5, 0 };

        AStar aStar = new AStar();

        boolean result = aStar.run(start, goal);

        assertEquals(true, result);
        assertEquals(11, TreeNode.getNumOfNodesExpanded());
        assertEquals("6 -> 8 -> 2 -> 1 -> 8 -> 6 -> 5 -> Goal", TreeNode.getMoveStepSequence());
        assertEquals(22, TreeNode.getNumOfNodesGenerated());
        assertEquals(7, TreeNode.getNumOfStepsToGoal());
    }

    @Test
    public void test3() {
        int[] start = new int[] { 1, 2, 3, 7, 5, 6, 8, 4, 0 };
        int[] goal = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 };

        AStar aStar = new AStar();

        boolean result = aStar.run(start, goal);

        assertEquals(true, result);
        assertEquals(17, TreeNode.getNumOfNodesExpanded());
        assertEquals("6 -> 5 -> 4 -> 8 -> 7 -> 4 -> 5 -> 6 -> Goal", TreeNode.getMoveStepSequence());
        assertEquals(33, TreeNode.getNumOfNodesGenerated());
        assertEquals(8, TreeNode.getNumOfStepsToGoal());
    }

    @Test
    public void test4() {
        int[] start = new int[] { 7, 2, 4, 5, 0, 6, 8, 3, 1 };
        int[] goal = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

        AStar aStar = new AStar();

        boolean result = aStar.run(start, goal);

        assertEquals(true, result);
        assertEquals(5291, TreeNode.getNumOfNodesExpanded());
        assertEquals(
                "5 -> 7 -> 2 -> 5 -> 3 -> 8 -> 7 -> 3 -> 6 -> 4 -> 5 -> 2 -> 3 -> 6 -> 4 -> 1 -> 8 -> 4 -> 1 -> 5 -> 2 -> 1 -> 4 -> 7 -> 6 -> 3 -> Goal",
                TreeNode.getMoveStepSequence());
        assertEquals(8376, TreeNode.getNumOfNodesGenerated());
        assertEquals(26, TreeNode.getNumOfStepsToGoal());
    }
}
