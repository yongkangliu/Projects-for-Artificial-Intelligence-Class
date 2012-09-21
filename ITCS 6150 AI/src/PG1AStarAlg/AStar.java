/*
 * UNC Charlotte ITCS 6150 Intelligence System Class, Programming Homework
 * 
 * by Yongkang Liu, 9/21/2012
 */
package PG1AStarAlg;

import java.util.ArrayList;
import java.util.List;

import PG1AStarAlgGUI.TileGUIPanels;

/**
 * The main class of A* algorithm
 */
public class AStar {

    // The queue nodes order by f cost value. The lowest f cost value will be processed firstly.
    private List<TreeNode> queueList = new ArrayList<TreeNode>();

    /**
     * Add the successor nodes the queue order by f cost value.
     * 
     * @param successors
     *            the
     */
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

    /**
     * The main function of A* algorithm.
     * 
     * The state of 8-puzzle is represented by a 9-length array. The tile number is an integer number. The empty tile is
     * represented by number 0.
     * 
     * For example, [5, 4, 0, 6, 1, 8, 7, 3, 2] represents the puzzle below:<br>
     * 5 4 <br>
     * 6 1 8 <br>
     * 7 3 2 <br>
     * 
     * @param start
     *            the initial state of 8-puzzle.
     * @param goal
     *            the goal state of 8-puzzle.
     * @return return true if the solution is found, return false if the solution can't be found.
     */
    public boolean run(int[] start, int[] goal) {
        // reset all counter variables.
        TreeNode.resetNumber();
        TileGUIPanels.printResetLog("Running ...");

        // set the initial state
        this.queueList = new ArrayList<TreeNode>();
        State rootState = new State(start, 0);
        TreeNode rootNode = new TreeNode(rootState, null);
        TreeNode.setRootNode(rootNode);
        this.queueList.add(rootNode);

        // set the goal state
        State goalState = new State(goal, -1);
        State.setGoalState(goalState);

        int count = 0;
        while (this.queueList.size() > 0) {
            // the main loop of A* algorithm.

            if (count++ >= 10000) {
                // count loop steps. if the loop takes too long, return false and exit;
                TileGUIPanels.printLog("The puzzle is too complex or unsolvable. Failed!");
                TileGUIPanels.printLog("(Modify code to extend search time.)");
                return false;
            }

            TileGUIPanels.printResetLog("Searching nodes:" + count);

            TreeNode tempState = this.queueList.get(0);
            this.queueList.remove(0);

            if (tempState.getState().isSameState(State.getGoalState())) {
                // found the goal state.
                TileGUIPanels.printLog("Succeed!");
                tempState.printParentNodes();
                TileGUIPanels.printLog("Number Of Steps To Goal:" + TreeNode.getNumOfStepsToGoal());
                TileGUIPanels.printLog("Number Of Nodes Expanded:" + TreeNode.getNumOfNodesExpanded());
                TileGUIPanels.printLog("Number Of Nodes Generated:" + TreeNode.getNumOfNodesGenerated());
                TileGUIPanels.printLog("The best solution path sequence of moves:" + TreeNode.getMoveStepSequence());
                return true;
            }

            // add the successor nodes the queue order by f cost value.
            addNewSuccessors(tempState.setSuccessors());

            if (this.queueList.size() == 0) {
                TileGUIPanels.printLog("Unknow reason. Failed!");
                return false;
            }
        }
        return false;
    }
}
