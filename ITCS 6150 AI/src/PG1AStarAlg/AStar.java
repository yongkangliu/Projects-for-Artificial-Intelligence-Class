package PG1AStarAlg;

import java.util.ArrayList;
import java.util.List;

public class AStar {

	private List<State> queueList = new ArrayList<State>();

	public void run(int[] start, int[] goal) {
		State rootState = new State(start);
		State goalState = new State(goal);

		this.queueList.add(rootState);
		State.rootState = rootState;

		while (this.queueList.size() > 0) {

		}
	}

	public static void main(String[] args) {
		int[] start = new int[] { 2, 8, 3, 1, 6, 4, 7, 0, 5 };
		int[] goal = new int[] { 1, 2, 3, 8, 6, 4, 7, 5, 0 };

		AStar aStar = new AStar();

		aStar.run(start, goal);
	}
}
