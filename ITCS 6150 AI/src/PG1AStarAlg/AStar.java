package PG1AStarAlg;

import java.util.ArrayList;
import java.util.List;

import PG1AStarAlgGUI.TileGUIPanels;

public class AStar {

    private List<TreeNode> queueList = new ArrayList<TreeNode>();

    private void addNewSuccessors(TreeNode[] successors) {
        for (int i = 0; i < successors.length; i++) {
            for (int j = 0; j <= queueList.size(); j++) {
                if (j == queueList.size()
                        || successors[i].getState().getFCost() <= queueList.get(j).getState().getFCost()) {
                    queueList.add(j, successors[i]);
                    break;
                }
            }
        }
    }

    public boolean run(int[] start, int[] goal) {
        TreeNode.resetNumber();
        TileGUIPanels.printLogReset("Running ...");

        this.queueList = new ArrayList<TreeNode>();
        State rootState = new State(start, 0);
        TreeNode rootNode = new TreeNode(rootState, null);
        TreeNode.setRootNode(rootNode);

        State goalState = new State(goal, -1);
        State.setGoalState(goalState);

        this.queueList.add(rootNode);

        int count = 0;
        while (this.queueList.size() > 0) {
            if (count++ > 20000) {
                // System.out.println("the puzzle is too complex. failed!");
                TileGUIPanels.printLog("The puzzle is too complex. Failed!");
                return false;
            }
            TileGUIPanels.printLogReset("searching node number:" + count);
            TreeNode tempState = this.queueList.get(0);
            this.queueList.remove(0);
            // tempState.printTreeNode();

            if (tempState.getState().isSameState(State.getGoalState())) {
                // System.out.println("Succeed!");
                TileGUIPanels.printLog("Succeed!");
                tempState.printParentNodes();
                // System.out.println("Number Of Nodes Generated:" + TreeNode.getNumOfNodesGenerated());
                // System.out.println("Number Of Nodes Expanded:" + TreeNode.getNumOfNodesExpanded());
                TileGUIPanels.printLog("Number Of Steps To Goal:" + TreeNode.getNumOfStepsToGoal());
                TileGUIPanels.printLog("Number Of Nodes Expanded:" + TreeNode.getNumOfNodesExpanded());
                TileGUIPanels.printLog("Number Of Nodes Generated:" + TreeNode.getNumOfNodesGenerated());
                return true;
            }

            addNewSuccessors(tempState.setSuccessors());

            if (this.queueList.size() == 0) {
                // System.out.println("failed!");
                TileGUIPanels.printLog("failed!");
                return false;
            }
        }
        return false;
    }
}
