package HW1GreedyAlg;

import java.util.Arrays;

public class Greedy {

    public int[] startPuzzle;
    public int[] tempPuzzle;
    public int[] goalPuzzle;

    public State stateTree;

    public Greedy(int[] start, int[] goal) {
        this.startPuzzle = start;
        this.tempPuzzle = start;
        this.goalPuzzle = goal;
        this.stateTree = new State(null, tempPuzzle);
        State.rootState = this.stateTree;
    }

    public static void exchangeValue(int[] puzzle, int a, int b) {
        int temp = puzzle[a];
        puzzle[a] = puzzle[b];
        puzzle[b] = temp;
    }

    public static int[] movePuzzle(int direction, int[] puzzle) {
        // direction = 0 top
        // direction = 1 right
        // direction = 2 bottom
        // direction = 3 left
        int[] tempPuzzle = Arrays.copyOf(puzzle, puzzle.length);
        int zeroPosition = findNumPosition(tempPuzzle, 0);
        if (direction == 0) {
            exchangeValue(tempPuzzle, zeroPosition, zeroPosition - 3);
        } else if (direction == 1) {
            exchangeValue(tempPuzzle, zeroPosition, zeroPosition + 1);
        } else if (direction == 2) {
            exchangeValue(tempPuzzle, zeroPosition, zeroPosition + 3);
        } else if (direction == 3) {
            exchangeValue(tempPuzzle, zeroPosition, zeroPosition - 1);
        }
        return tempPuzzle;
    }

    public static int findNumPosition(int[] puzzle, int goal) {
        for (int i = 0; i < puzzle.length; i++) {
            if (puzzle[i] == goal) {
                return i;
            }
        }
        return -1;
    }

    public static int caculateCost(int[] puzzle) {
        int totalCost = 0;
        for (int i = 1; i <=9; i++) {
            int pos = findNumPosition(puzzle, i % 9) + 1;
            totalCost = totalCost + Math.abs(pos - i) / 3 + Math.abs(pos - i) % 3;
        }

        return totalCost;
    }

    public static int[] getDirections(int[] puzzle) {
        // direction = 0 top
        // direction = 1 right
        // direction = 2 bottom
        // direction = 3 left
        int zeroPosition = findNumPosition(puzzle, 0);

        if (zeroPosition == 0) {
            return new int[] { 1, 2 };
        } else if (zeroPosition == 1) {
            return new int[] { 1, 2, 3 };
        } else if (zeroPosition == 2) {
            return new int[] { 2, 3 };
        } else if (zeroPosition == 3) {
            return new int[] { 0, 1, 2 };
        } else if (zeroPosition == 5) {
            return new int[] { 0, 2, 3 };
        } else if (zeroPosition == 6) {
            return new int[] { 0, 1 };
        } else if (zeroPosition == 7) {
            return new int[] { 0, 1, 3 };
        } else if (zeroPosition == 8) {
            return new int[] { 0, 3 };
        } else {// zeroPosition == 4
            return new int[] { 0, 1, 2, 3 };
        }
    }

    public void run() {
        State tempState = this.stateTree;
        int count = 0;
        int k = 300;
        while (tempState != null && --k > 0) {
            if (!tempState.visited) {
                System.out.println();
                System.out.print("step:" + count++);
                tempState.printState();
            } else {
                System.out.println("*******************************************visitied");
            }
            if (tempState.hCost == 0) {
                System.out.println("succeed!");
                // tempState.printParent();
                return;
            } else {
                if (!tempState.visited) {
                    tempState.setChildren();
                }

                if (tempState.children.length == 0) {
                    System.out.println("no child!");
                    tempState = tempState.parent;
                }

                for (int i = 0; i < tempState.children.length; i++) {
                    if (!tempState.children[i].visited) {
                        tempState = tempState.children[i];
                        break;
                    }
                }
            }
        }
        System.out.println("fail!");
    }

    public static void main(String[] args) {
         int[] startPuzzle = new int[] { 1, 2, 3, 7, 5, 6, 8, 4, 0 };
        //int[] startPuzzle = new int[] { 2, 5, 3, 1, 0, 6, 4, 7, 8 };
        int[] goalPuzzle = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 };

        Greedy greedy = new Greedy(startPuzzle, goalPuzzle);
        // System.out.println(greedy.caculateCost(new int[] { 1, 2, 3, 7, 5, 0, 8, 4, 6 }));
        greedy.run();
    }
}
