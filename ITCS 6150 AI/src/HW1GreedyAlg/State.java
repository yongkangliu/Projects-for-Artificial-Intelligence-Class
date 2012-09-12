package HW1GreedyAlg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class State {
    public static State rootState;
    public int[] puzzle = new int[0];
    public boolean visited = false;
    public int hCost = 0;

    public State parent;
    public State[] children = new State[0];

    public State(State parent, int[] puzzle) {
        this.puzzle = puzzle;
        this.parent = parent;
        this.hCost = Greedy.caculateCost(puzzle);
    }

    public void printState() {
        System.out.println();
        System.out.println("cost:" + hCost + " puzzle:" + Arrays.toString(puzzle));

    }

    public void printParent() {
        System.out.println("list all parents");
        State state = this;
        int step = 0;
        while (state != null) {
            System.out.println("***" + (++step));
            state.printState();
            state = state.parent;
        }
    }

    public void setChildren() {
        this.visited = true;
        int[] childrenDirections = Greedy.getDirections(this.puzzle);
        List<State> list = new ArrayList<State>();
        for (int i = 0; i < childrenDirections.length; i++) {
            int[] tempPuzzle = Greedy.movePuzzle(childrenDirections[i], this.puzzle);
            if (!findState(tempPuzzle)) {
                State newState = new State(this, tempPuzzle);

                for (int j = 0; j <= list.size(); j++) {
                    if (j == list.size() || newState.hCost <= list.get(j).hCost) {
                        list.add(j, newState);
                        System.out.println("******** add new child, cost=" + newState.hCost + "    "
                                + Arrays.toString(newState.puzzle));
                        break;
                    }
                }
            }
        }
        this.children = (State[]) list.toArray(new State[] {});
    }

    public boolean findState(int[] puzzle) {
        List<State> list = new ArrayList<State>();
        list.add(rootState);
        while (!list.isEmpty()) {
            State state = list.get(0);
            if (Arrays.equals(state.puzzle, puzzle)) {
                return true;
            }
            if (state.children.length > 0) {
                for (int i = 0; i < state.children.length; i++) {
                    list.add(state.children[i]);
                }
            }
            list.remove(0);
        }

        return false;
    }
}