package PG3PCSP;

import java.util.Arrays;
import java.util.Random;

import PG3PCSPGUI.PCSPGUI;

public class PCSP {
    // The random instance.
    private Random random = new Random();

    private static PCSP instance = new PCSP();

    private PCSP() {

    }

    public static PCSP getInstance() {
        return PCSP.instance;
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

    private ScheduleState search(int numOfIntervals, int restartTimes) {
        int numOfRestart = 0;
        ScheduleState result = null;

        while (numOfRestart++ < restartTimes) {
            PCSPGUI.showProgress(numOfRestart);
            int numOfMove = 0;

            ScheduleState state = ScheduleState.generateRandomState();

            while (numOfMove++ < restartTimes) {
                ScheduleState tempState = gotoBetterState(state);
                boolean sameState = false;
                if (state == tempState) {
                    sameState = true;
                } else {
                    state = tempState;
                }

                if (!sameState && state.getNumberOfNegtiveNetReserve() >= 0) {
                    if (result == null) {
                        result = state;
                    } else {
                        double oldStdDev = result.getStdDev();
                        double newStdDev = state.getStdDev();
                        if (newStdDev == 0) {
                            PCSPGUI.appendLog("Restart:" + numOfRestart
                                    + " times. Found the best step, Standard Deviation=0. Stop running.");
                            return state;
                        } else if (newStdDev < oldStdDev) {
                            PCSPGUI.appendLog("Restart:" + numOfRestart
                                    + " times. Goto better step. Standard Deviation=" + state.getStdDev());
                            result = state;
                        }
                    }
                }
            }
        }
        return result;
    }

    public ScheduleState run(int[] intervals, int[] capacities, int[] maxLoads, int restartTimes) {
        if (intervals != null && maxLoads != null && intervals.length > 0 && maxLoads.length > 0) {
            PCSPGUI.resetLog("Starting ...");
            ScheduleState.initialize(intervals, capacities, maxLoads);

            ScheduleState state = ScheduleState.generateRandomState();
            int[] intervalNetReserves = state.getIntervalNetReserves();
            int sumOfNetReserves = 0;
            for (int i = 0; i < intervalNetReserves.length; i++) {
                sumOfNetReserves += intervalNetReserves[i];
            }

            if (sumOfNetReserves >= 0) {
                ScheduleState.initialize(intervals, capacities, maxLoads);

                ScheduleState finalState = search(0, restartTimes);
                if (finalState != null) {
                    PCSPGUI.appendLog("Successful. Standard Deviation=" + finalState.getStdDev());
                    return finalState;
                }
            }
            PCSPGUI.appendLog("Can't find solution. Failed.");
        }
        return null;
    }
}
