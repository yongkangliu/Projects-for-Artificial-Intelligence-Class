/*
 * UNC Charlotte ITCS 6150 Intelligence System Class, Programming Homework
 * 
 * by Yongkang Liu, 9/21/2012
 */
package PG1AStarAlg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The node in the search tree of A* algorithm.
 */
public class TreeNode {
    // The number of nodes generated.
    private static long numOfNodesGenerated = 0;

    // The number of nodes expanded
    private static long numOfNodesExpanded = 0;

    // The number of the best solution path
    private static long numOfStepsToGoal = 0;

    // The sequence of the best solution path
    private static String moveStepSequence = null;

    // The map stores all tree nodes. This is a hash map which increases search speed.
    private static Map<String, String> map = new HashMap<String, String>();

    // Each node represents a state.
    private State state;

    // The parent node in the search tree
    private TreeNode parent;

    // The successor nodes in the search tree
    private TreeNode[] successors = new TreeNode[0];

    /**
     * Constructor of TreeNode Class
     * 
     * @param state
     *            the state represented by tree node.
     * @param parent
     *            the parent tree node.
     */
    public TreeNode(State state, TreeNode parent) {
        this.state = state;
        this.parent = parent;
        if (parent != null) {
            // new node is generated.
            TreeNode.numOfNodesGenerated++;
        }

        // Convert the puzzle date into a string. And store it in the map.
        String key = Arrays.toString(state.getPuzzleDate());
        map.put(key, key);
    }

    /**
     * Reset all counter number when start a new calculation.
     */
    public static void resetNumber() {
        TreeNode.numOfNodesExpanded = 0;
        TreeNode.numOfNodesGenerated = 0;
        TreeNode.numOfStepsToGoal = 0;
        TreeNode.map.clear();
    }

    /**
     * Return the number of nodes generated.
     * 
     * @return the number of nodes generated.
     */
    public static long getNumOfNodesGenerated() {
        return TreeNode.numOfNodesGenerated;
    }

    /**
     * Return the number of nodes expanded
     * 
     * @return the number of nodes expanded
     */
    public static long getNumOfNodesExpanded() {
        return TreeNode.numOfNodesExpanded;
    }

    /**
     * Return the number of the best solution path
     * 
     * @return the number of the best solution path
     */
    public static long getNumOfStepsToGoal() {
        return TreeNode.numOfStepsToGoal;
    }

    /**
     * Return the sequence of the best solution path
     * 
     * @return the sequence of the best solution path
     */
    public static String getMoveStepSequence() {
        return moveStepSequence;
    }

    /**
     * Return the state included in the tree node
     * 
     * @return the state
     */
    public State getState() {
        return this.state;
    }

    /**
     * Print the tree node information.
     */
    public void printTreeNode() {
        this.state.printState();
    }

    /**
     * After reach the goal, print all parent tree nodes to get the best solution path.
     */
    public void printParentNodes() {
        TreeNode tempNode = this.parent;
        ArrayList<Integer> list = new ArrayList<Integer>();
        int moveStep = this.getState().getMovedNodeName();
        if (moveStep > 0) {
            list.add(0, moveStep);
        }
        while (tempNode != null) {
            tempNode.printTreeNode();
            moveStep = tempNode.getState().getMovedNodeName();
            if (moveStep > 0) {
                list.add(0, moveStep);
            }
            tempNode = tempNode.parent;
        }

        // Generate the best solution path.
        TreeNode.numOfStepsToGoal = list.size();
        String sequence = new String();
        for (int i = 0; i < list.size(); i++) {
            sequence += list.get(i) + " -> ";
        }
        sequence += "Goal";
        TreeNode.moveStepSequence = sequence;
    }

    /**
     * Search whole tree to check if the state was visited.
     * 
     * @param state
     *            the state
     * @return return true if the state was visited, otherwise return false.
     */
    private boolean isStateVisited(State state) {
        if (TreeNode.map.containsKey(Arrays.toString(state.getPuzzleDate()))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Generate the successor nodes of best node.
     * 
     * @return return the successor array.
     */
    public TreeNode[] setSuccessors() {
        // new node expanded.
        TreeNode.numOfNodesExpanded++;

        int[] successorDirections = this.state.getMoveDirections();

        // create new successor for each available move direction.
        List<TreeNode> list = new ArrayList<TreeNode>();
        for (int i = 0; i < successorDirections.length; i++) {
            State tempState = this.state.moveToNextState(successorDirections[i]);
            if (!isStateVisited(tempState)) {// if the state isn't visited, generate new tree node.
                TreeNode newState = new TreeNode(tempState, this);
                list.add(newState);
            }
        }
        this.successors = (TreeNode[]) list.toArray(new TreeNode[] {});

        return this.successors;
    }
}
