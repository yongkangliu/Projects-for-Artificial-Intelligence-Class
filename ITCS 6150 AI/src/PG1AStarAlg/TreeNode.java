package PG1AStarAlg;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private static TreeNode rootNode;
    private static long numOfNodesGenerated = 0;
    private static long numOfNodesExpanded = 0;
    private static long numOfStepsToGoal = 0;
    private static String moveStepSequence = null;

    private State state;

    private TreeNode parent;
    private TreeNode[] successors = new TreeNode[0];

    public TreeNode(State state, TreeNode parent) {
        this.state = state;
        this.parent = parent;
        TreeNode.numOfNodesGenerated++;
    }

    public static void resetNumber() {
        TreeNode.numOfNodesExpanded = 0;
        TreeNode.numOfNodesGenerated = 0;
        TreeNode.numOfStepsToGoal = 0;
    }

    public static void setRootNode(TreeNode node) {
        TreeNode.rootNode = node;
    }

    public static long getNumOfNodesGenerated() {
        return TreeNode.numOfNodesGenerated;
    }

    public static long getNumOfNodesExpanded() {
        return TreeNode.numOfNodesExpanded;
    }

    public static long getNumOfStepsToGoal() {
        return TreeNode.numOfStepsToGoal;
    }

    public static String getMoveStepSequence() {
        return moveStepSequence;
    }

    public State getState() {
        return this.state;
    }

    public void printTreeNode() {
        this.state.printState();
    }

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
        TreeNode.numOfStepsToGoal = list.size();
        String sequence = new String();
        for (int i = 0; i < list.size(); i++) {
            sequence += list.get(i) + " -> ";
        }
        sequence += "Goal";
        TreeNode.moveStepSequence = sequence;
    }

    private boolean isStateExist(State state) {
        List<TreeNode> list = new ArrayList<TreeNode>();
        list.add(rootNode);
        while (!list.isEmpty()) {
            TreeNode treeNode = list.get(0);
            if (treeNode.getState().isSameState(state)) {
                return true;
            }
            if (treeNode.successors.length > 0) {
                for (int i = 0; i < treeNode.successors.length; i++) {
                    list.add(treeNode.successors[i]);
                }
            }
            list.remove(0);
        }

        return false;
    }

    public TreeNode[] setSuccessors() {
        TreeNode.numOfNodesExpanded++;
        int[] successorDirections = this.state.getMoveDirections();
        List<TreeNode> list = new ArrayList<TreeNode>();
        for (int i = 0; i < successorDirections.length; i++) {
            State tempState = this.state.moveToNextState(successorDirections[i]);
            if (!isStateExist(tempState)) {
                TreeNode newState = new TreeNode(tempState, this);
                list.add(newState);
            }
        }
        this.successors = (TreeNode[]) list.toArray(new TreeNode[] {});

        return this.successors;
    }
}
