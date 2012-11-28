/*
 * UNC Charlotte ITCS 6150 Intelligence System Class, Final Project
 * 
 * by Yongkang Liu, 11/24/2012
 */
package pg3pcsp;

import java.util.Random;

/**
 * The state class of Hill Climbing algorithm for Maintenance Scheduling Problem.
 */
public class ScheduleState {
    // The total capacity of all power system components
    private static int totalCapacity = 0;

    // The capacity of each power system component in an array.
    private static int[] unitCapacities = new int[] {};

    // The interval of each power system component maintenance in an array.
    private static int[] unitIntervals = new int[] {};

    // The maximum loads expected during each interval in an array.
    private static int[] intervalMaxLoads = new int[] {};

    // The random number creator.
    private static Random r = new Random();

    // The schedule state data in an array.
    private int[] unitScheduleState = new int[] {};

    // The net reserves of each interval in an array.
    private int[] intervalNetReserves = new int[] {};

    /**
     * Constructor of ScheduleState class.
     * 
     * @param scheduleState
     *            The schedule state data.
     * @param netReserves
     *            The net reserves of each interval.
     */
    public ScheduleState(int[] scheduleState, int[] netReserves) {
        this.unitScheduleState = scheduleState;
        if (netReserves == null) {
            this.intervalNetReserves = new int[ScheduleState.intervalMaxLoads.length];
            this.countNetReserves();
        } else {
            this.intervalNetReserves = netReserves;
        }
    }

    /**
     * Initialize the state.
     * 
     * @param intervals
     *            The interval of each power system component maintenance.
     * @param capacities
     *            The capacity of each power system component.
     * @param maxLoads
     *            The maximum loads expected during each interval.
     */
    public static void initialize(int[] intervals, int[] capacities, int[] maxLoads) {
        ScheduleState.unitIntervals = intervals;
        ScheduleState.unitCapacities = capacities;
        ScheduleState.intervalMaxLoads = maxLoads;

        ScheduleState.totalCapacity = 0;
        for (int i = 0; i < capacities.length; i++) {
            ScheduleState.totalCapacity += capacities[i];
        }
    }

    /**
     * Return the total capacity of all power system components.
     * 
     * @return Return the total capacity of all power system components .
     */
    public static int getTotalCapacity() {
        return totalCapacity;
    }

    /**
     * Return the schedule state data
     * 
     * @return Return the schedule state data
     */
    public int[] getUnitScheduleState() {
        return this.unitScheduleState;
    }

    /**
     * Return the net reserves of each interval.
     * 
     * @return Return the net reserves of each interval.
     */
    public int[] getIntervalNetReserves() {
        return this.intervalNetReserves;
    }

    /**
     * Return the standard deviation of current state.
     * 
     * @return Return the standard deviation of current state.
     */
    public double getStdDev() {
        StandardDeviation count = new StandardDeviation(this.intervalNetReserves);
        return count.getStdDev();
    }

    /**
     * Return the number of negative net reserves.
     * 
     * @return Return the number of negative net reserves.
     */
    public int getNumberOfNegativeNetReserve() {
        int count = 0;
        for (int i = 0; i < this.intervalNetReserves.length; i++) {
            if (this.intervalNetReserves[i] < 0) {
                return -1;
            } else if (this.intervalNetReserves[i] > 0) {
                count++;
            }
        }
        return count;
    }

    /**
     * Move the schedule of one component.
     * 
     * @param iUnit
     *            The index of the component.
     * @param iNewInterval
     *            The new schedule position.
     * @return Return true if it is a better position. Otherwise return false.
     */
    public boolean moveUnit(int iUnit, int iNewInterval) {
        if (iNewInterval == this.unitScheduleState[iUnit]) {
            return false;
        }

        int iOldInterval = this.unitScheduleState[iUnit];

        int unitLength = ScheduleState.unitIntervals[iUnit];

        int[] oldNetReserves = new int[unitLength * 2];
        for (int i = 0; i < unitLength; i++) {
            oldNetReserves[i] = this.intervalNetReserves[iOldInterval + i];
            oldNetReserves[i + unitLength] = this.intervalNetReserves[iNewInterval + i];
        }

        this.unitScheduleState[iUnit] = iNewInterval;
        moveNetReserve(iUnit, iOldInterval, iNewInterval);

        int[] newNetReserves = new int[unitLength * 2];
        for (int i = 0; i < unitLength; i++) {
            newNetReserves[i] = this.intervalNetReserves[iOldInterval + i];
            newNetReserves[i + unitLength] = this.intervalNetReserves[iNewInterval + i];
        }

        return isBetterMove(oldNetReserves, newNetReserves);
    }

    /**
     * Change the schedule position value.
     * 
     * @param iUnit
     *            The index of the component.
     * @param iOldInterval
     *            The old schedule position.
     * @param iNewInterval
     *            The new schedule position.
     */
    private void moveNetReserve(int iUnit, int iOldInterval, int iNewInterval) {
        for (int i = 0; i < ScheduleState.unitIntervals[iUnit]; i++) {
            this.intervalNetReserves[iNewInterval + i] -= ScheduleState.unitCapacities[iUnit];
            this.intervalNetReserves[iOldInterval + i] += ScheduleState.unitCapacities[iUnit];
        }
    }

    /**
     * Check if the new state is a better state.
     * 
     * @param oldNetReserves
     *            The old net reserves.
     * @param newNetReserves
     *            The new net reserves.
     * @return Return true if it is a better position. Otherwise return false.
     */
    private boolean isBetterMove(int[] oldNetReserves, int[] newNetReserves) {
        int unitLength = oldNetReserves.length / 2;
        int oldGap = 0;
        int newGap = 0;
        for (int i = 0; i < unitLength; i++) {
            oldGap += Math.abs(oldNetReserves[i] - oldNetReserves[i + unitLength]);
            newGap += Math.abs(newNetReserves[i] - newNetReserves[i + unitLength]);
        }

        if (newGap < oldGap) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Calculate the net reserves of current state.
     */
    private void countNetReserves() {
        for (int i = 0; i < this.intervalNetReserves.length; i++) {
            this.intervalNetReserves[i] = ScheduleState.totalCapacity - ScheduleState.intervalMaxLoads[i];
        }

        for (int i = 0; i < this.unitScheduleState.length; i++) {
            for (int j = 0; j < ScheduleState.unitIntervals[i]; j++) {
                this.intervalNetReserves[this.unitScheduleState[i] + j] -= ScheduleState.unitCapacities[i];
            }
        }
    }

    /**
     * Generate a random state.
     * 
     * @return Return the ScheduleState instance.
     */
    public static ScheduleState generateRandomState() {
        if (ScheduleState.unitIntervals == null || ScheduleState.unitCapacities == null
                || ScheduleState.intervalMaxLoads == null) {
            return null;
        }

        int numberOfUnits = ScheduleState.unitIntervals.length;

        int[] scheduleState = new int[numberOfUnits];

        for (int i = 0; i < numberOfUnits; i++) {
            scheduleState[i] = r.nextInt(ScheduleState.getPossibleInterval(i));
        }

        return new ScheduleState(scheduleState, null);
    }

    /**
     * Calculate the possible interval schedules.
     * 
     * @param unit
     *            The index of component.
     * @return Return the number of possible interval schedules.
     */
    public static int getPossibleInterval(int unit) {
        return ScheduleState.intervalMaxLoads.length - (ScheduleState.unitIntervals[unit] - 1);
    }

    /**
     * Return the interval of each power system component maintenance.
     * 
     * @return Return the interval of each power system component maintenance.
     */
    public static int[] getUnitIntervals() {
        return ScheduleState.unitIntervals;
    }
}
