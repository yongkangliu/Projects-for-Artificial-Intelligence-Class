package PG1AStarAlg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AStar {

	private List<TreeNode> queueList = new ArrayList<TreeNode>();

	private void addNewSuccessors(TreeNode[] successors) {
		for (int i = 0; i < successors.length; i++) {
			for (int j = 0; j <= queueList.size(); j++) {
				if (j == queueList.size() || successors[i].getState().getFCost() <= queueList.get(j).getState().getFCost()) {
					queueList.add(j, successors[i]);
					System.out.println("** add new node, cost=" + successors[i].getState().getFCost() + " "
					        + Arrays.toString(successors[i].getState().getPuzzleDate()));
					break;
				}
			}
		}
	}

	public boolean run(int[] start, int[] goal) {
		this.queueList = new ArrayList<TreeNode>();
		State rootState = new State(start, 0);
		TreeNode rootNode = new TreeNode(rootState, null);
		TreeNode.setRootNode(rootNode);

		State goalState = new State(goal, -1);
		State.setGoalState(goalState);

		this.queueList.add(rootNode);

		while (this.queueList.size() > 0) {
			TreeNode tempState = this.queueList.get(0);
			this.queueList.remove(0);
			tempState.printTreeNode();

			if (tempState.getState().isSameState(State.getGoalState())) {
				System.out.println("Succeed!");
				tempState.printParentNodes();
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

	public static void main(String[] args) {
		int[] start = new int[] { 5, 4, 0, 6, 1, 8, 7, 3, 2 };
		int[] goal = new int[] { 1, 2, 3, 4, 0, 5, 6, 7, 8 };

		// int[] start = new int[] { 2, 8, 3, 1, 6, 4, 7, 0, 5 };
		// int[] goal = new int[] { 1, 2, 3, 8, 6, 4, 7, 5, 0 };

		// int[] start = new int[] { 1, 2, 3, 7, 5, 6, 8, 4, 0 };
		// int[] goal = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 };

		AStar aStar = new AStar();

		aStar.run(start, goal);
	}
}
