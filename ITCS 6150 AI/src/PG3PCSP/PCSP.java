package PG3PCSP;

import java.util.Arrays;
import java.util.Random;

public class PCSP {
    // The random instance.
    private Random random = new Random();

    private int calculateSpan(int[] number) {
        if (number.length == 0) {
            return -1;
        }

        int span = Math.abs(number[0] - number[1]);
        for (int i = 0; i < number.length; i++) {
            for (int j = i + 1; j < number.length; j++) {
                if (number[i] != number[j]) {
                    int temp = Math.abs(number[i] - number[j]);
                    if (temp < span) {
                        span = temp;
                    }
                }
            }
        }
        return span;
    }

    public ScheduleState gotoBetterState(ScheduleState current) {
        ScheduleState newScheduleState = current;

        int iUnit = this.random.nextInt(current.getUnitScheduleState().length);

        for (int i = 0; i < ScheduleState.getPossibleInterval(iUnit); i++) {
            // traversing all intervals for next possible state.

            if (i == current.getUnitScheduleState()[iUnit]) {
                // don't check the same state.
                continue;
            }

            int[] currentState = newScheduleState.getUnitScheduleState();
            int[] currentNetReserves = newScheduleState.getIntervalNetReserves();

            int[] tempNetReserves = Arrays.copyOf(currentNetReserves, currentNetReserves.length);
            int[] tempState = Arrays.copyOf(currentState, currentState.length);

            ScheduleState tempSchedule = new ScheduleState(tempState, tempNetReserves);
            boolean isBetterMove = tempSchedule.moveUnit(iUnit, i);

            if (isBetterMove) {
                newScheduleState = tempSchedule;
            }
        }

        return newScheduleState;
    }

    private ScheduleState search() {
        int numOfRestart = 0;

        while (numOfRestart++ < 1000) {
            int numOfMove = 0;

            ScheduleState state = ScheduleState.generateRandomState();
            // System.out.println("numOfRestart:" + numOfRestart + " numOfMove:" + numOfMove
            // + Arrays.toString(state.getUnitScheduleState()) + "negtive number:"
            // + state.getNumberOfNegtiveNetReserve());
            if (state.getNumberOfNegtiveNetReserve() == 0) {
                return state;
            }

            while (numOfMove++ < 1000 && state.getNumberOfNegtiveNetReserve() != 0) {
                state = gotoBetterState(state);

                // System.out.println("numOfRestart:" + numOfRestart + " numOfMove:" + numOfMove
                // + Arrays.toString(state.getUnitScheduleState()) + "negtive number:"
                // + state.getNumberOfNegtiveNetReserve());
                if (state.getNumberOfNegtiveNetReserve() == 0) {
                    return state;
                }
            }
        }

        return null;
    }

    public ScheduleState run(int[] intervals, int[] capacities, int[] maxLoads) {
        ScheduleState.initialize(intervals, capacities, maxLoads, 0);

        ScheduleState state = ScheduleState.generateRandomState();
        int[] intervalNetReserves = state.getIntervalNetReserves();
        int sumOfNetReserves = 0;
        for (int i = 0; i < intervalNetReserves.length; i++) {
            sumOfNetReserves += intervalNetReserves[i];
        }

        int span = this.calculateSpan(capacities);
        int initialNumber = ((sumOfNetReserves / maxLoads.length) / span) * span;

        for (int i = initialNumber; i >= 0; i -= span) {
            ScheduleState.initialize(intervals, capacities, maxLoads, i);
            ScheduleState finalState = search();
            if (finalState != null) {
                System.out.println("****" + i + "  " + Arrays.toString(finalState.getIntervalNetReserves()));
                System.out.println("****" + i + "  " + Arrays.toString(finalState.getUnitScheduleState()));
                return finalState;
            }
        }
        System.out.println("failed");
        return null;
    }
}