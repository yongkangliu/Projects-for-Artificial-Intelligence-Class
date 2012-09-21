package PG1AStarAlg;

import java.util.ArrayList;
import java.util.List;

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
                System.out.println("the puzzle is too complex. failed!");
                return false;
            }
            System.out.println(count);
            TreeNode tempState = this.queueList.get(0);
            this.queueList.remove(0);
            // tempState.printTreeNode();

            if (tempState.getState().isSameState(State.getGoalState())) {
                System.out.println("Succeed!");
                tempState.printParentNodes();
                System.out.println("numOfNodesGenerated" + TreeNode.getNumOfNodesGenerated());
                System.out.println("numOfNodesExpanded" + TreeNode.getNumOfNodesExpanded());
                return true;
            }

            addNewSuccessors(tempState.setSuccessors());

            if (this.queueList.size() == 0) {
                System.out.println("failed!");
                return false;
            }
        }
        return false;
    }
}
