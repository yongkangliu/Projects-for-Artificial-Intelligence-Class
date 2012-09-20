package PG1AStarAlg;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
	private static TreeNode rootNode;

	private State state;

	private TreeNode parent;
	private TreeNode[] successors = new TreeNode[0];

	public TreeNode(State state, TreeNode parent) {
		this.state = state;
		this.parent = parent;
	}

	public static void setRootNode(TreeNode node) {
		TreeNode.rootNode = node;
	}

	public State getState() {
		return this.state;
	}

	public void printTreeNode() {
		this.state.printState();
	}

	public void printParentNodes() {
		TreeNode tempNode = this;
		while (tempNode != null) {
			tempNode.printTreeNode();
			tempNode = tempNode.parent;
		}
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
