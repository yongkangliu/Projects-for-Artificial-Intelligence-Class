package PG1AStarAlg;


public class State {
    public static State rootState;
    public int[] puzzleData = new int[0];
    public int hCost = 0;
    public int gCost = 0;

    public State parent;
    public State[] children = new State[0];

    public boolean visited = false;

    public State(int[] puzzleDate) {
        this.puzzleData = puzzleDate;
    }

    public int getHCost(State goalState) {
        int totalCost = 0;
        for (int i = 1; i <= 9; i++) {
            int currentPos = findNumPosition(this.puzzleData, i % 9) + 1;
            int goalPos = findNumPosition(goalState.puzzleData, i % 9) + 1;
            totalCost = totalCost + Math.abs(goalPos - currentPos) / 3 + Math.abs(goalPos - currentPos) % 3;
        }

        return totalCost;
    }

    private int findNumPosition(int[] puzzleData, int number) {
        for (int i = 0; i < puzzleData.length; i++) {
            if (puzzleData[i] == number) {
                return i;
            }
        }
        return -1;
    }

}