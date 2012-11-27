/*
 * UNC Charlotte ITCS 6150 Intelligence System Class, Final Project
 * 
 * by Yongkang Liu, 11/24/2012
 */
package PG3PCSP;

import java.util.Arrays;
import java.util.Random;

import PG3PCSPGUI.PCSPGUI;

/**
 * The Hill Climbing algorithm class to solve the PCSP
 */
public class PCSP {
    // The random instance.
    private Random r = new Random();

    // PCSP instance in singleton pattern
    private static PCSP instance = new PCSP();

    // The private constructor in singleton pattern
    private PCSP() {
    }

    /**
     * The getInstance function in singleton pattern..
     * 
     * @return return the PCSP instance.
     */
    public static PCSP getInstance() {
        return PCSP.instance;
    }

    /**
     * Create next better state.
     * 
     * @param current
     *            The current state.
     * @return Return the next better state.
     */
    public ScheduleState gotoBetterState(ScheduleState current) {
        int iUnit = this.r.nextInt(current.getUnitScheduleState().length);

        for (int i = 0; i < ScheduleState.getPossibleInterval(iUnit); i++) {
            // traversing all intervals for next possible state.

            if (i == current.getUnitScheduleState()[iUnit]) {
                // don't check the same state.
                continue;
            }

            int[] currentState = current.getUnitScheduleState();
            int[] currentNetReserves = current.getIntervalNetReserves();

            int[] tempNetReserves = Arrays.copyOf(currentNetReserves, currentNetReserves.length);
            int[] tempState = Arrays.copyOf(currentState, currentState.length);

            // Create new state
            ScheduleState tempSchedule = new ScheduleState(tempState, tempNetReserves);
            boolean isBetterMove = tempSchedule.moveUnit(iUnit, i);

            if (isBetterMove) {
                return tempSchedule;
            }
        }
        return current;
    }

    /**
     * Hill Climbing algorithm
     * 
     * @param restartTimes
     *            The max number of random restart times.
     * @return Return the ScheduleState instance.
     */
    private ScheduleState search(int restartTimes) {
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

                if (!sameState && state.getNumberOfNegativeNetReserve() >= 0) {
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

    /**
     * The main function of Hill Climbing algorithm.
     * 
     * @param intervals
     *            The interval of each power system component maintenance.
     * @param capacities
     *            The capacity of each power system component.
     * @param maxLoads
     *            The maximum loads expected during each interval.
     * @param restartTimes
     *            The max number of random restart times.
     * @return Return ScheduleState instance.
     */
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

                ScheduleState finalState = search(restartTimes);
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
