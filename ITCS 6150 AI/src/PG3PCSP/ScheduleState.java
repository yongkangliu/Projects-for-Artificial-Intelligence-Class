package PG3PCSP;

import java.util.Random;

public class ScheduleState {

    private static int totalCapacity = 0;
    private static int[] unitCapacities = new int[] {};
    private static int[] unitIntervals = new int[] {};
    private static int[] intervalMaxLoads = new int[] {};
    private static int minNetReserve = 0;

    private int[] unitScheduleState = new int[] {};
    private int[] intervalNetReserves = new int[] {};
    private int numberOfNegtiveNetReserve = 0;

    public ScheduleState(int[] scheduleState, int[] netReserves) {
        this.unitScheduleState = scheduleState;
        if (netReserves == null) {
            this.intervalNetReserves = new int[ScheduleState.intervalMaxLoads.length];
            this.countNetReserves();
        } else {
            this.intervalNetReserves = netReserves;
        }
    }

    public static void initialize(int[] intervals, int[] capacities, int[] maxLoads, int minNetReserve) {
        ScheduleState.unitIntervals = intervals;
        ScheduleState.unitCapacities = capacities;
        ScheduleState.intervalMaxLoads = maxLoads;
        ScheduleState.minNetReserve = minNetReserve;

        ScheduleState.totalCapacity = 0;
        for (int i = 0; i < capacities.length; i++) {
            ScheduleState.totalCapacity += capacities[i];
        }
    }

    public static int getTotalCapacity() {
        return totalCapacity;
    }

    public int[] getUnitScheduleState() {
        return this.unitScheduleState;
    }

    public int[] getIntervalNetReserves() {
        return intervalNetReserves;
    }

    public int getNumberOfNegtiveNetReserve() {
        this.numberOfNegtiveNetReserve = 0;
        for (int i = 0; i < this.intervalNetReserves.length; i++) {
            if (this.intervalNetReserves[i] < 0) {
                this.numberOfNegtiveNetReserve++;
            }
        }
        return this.numberOfNegtiveNetReserve;
    }

    public boolean moveUnit(int iUnit, int iNewInterval) {
        if (iNewInterval == this.unitScheduleState[iUnit]) {
            return false;
        }

        int iOldInterval = this.unitScheduleState[iUnit];
        this.unitScheduleState[iUnit] = iNewInterval;

        int oldNetReserveA = this.intervalNetReserves[iOldInterval];
        int oldNetReserveB = this.intervalNetReserves[iNewInterval];

        int newNetReserveA = this.countSingleNetReserve(iOldInterval);
        int newNetReserveB = this.countSingleNetReserve(iNewInterval);
        return isBetterMove(oldNetReserveA, oldNetReserveB, newNetReserveA, newNetReserveB);
    }

    private boolean isBetterMove(int oldA, int oldB, int newA, int newB) {
        int oldGap = Math.abs(oldA - oldB);
        int newGap = Math.abs(newA - newB);

        if (newGap < oldGap) {
            this.countNetReserves();
            return true;
        } else {
            return false;
        }
    }

    private int countSingleNetReserve(int interval) {
        int netReserves = ScheduleState.totalCapacity - ScheduleState.intervalMaxLoads[interval]
                - ScheduleState.minNetReserve;

        for (int j = 0; j < this.unitScheduleState.length; j++) {
            int lowBound = this.unitScheduleState[j];
            int highBound = this.unitScheduleState[j] + (ScheduleState.unitIntervals[j] - 1);
            if (lowBound <= interval && interval <= highBound) {
                netReserves -= ScheduleState.unitCapacities[j];
            }
        }

        this.intervalNetReserves[interval] = netReserves;
        return netReserves;
    }

    private int[] countNetReserves() {
        for (int i = 0; i < this.intervalNetReserves.length; i++) {
            countSingleNetReserve(i);
        }

        return this.intervalNetReserves;
    }

    public static ScheduleState generateRandomState() {
        if (ScheduleState.unitIntervals == null || ScheduleState.unitCapacities == null
                || ScheduleState.intervalMaxLoads == null) {
            return null;
        }

        Random r = new Random();
        int numberOfUnits = ScheduleState.unitIntervals.length;

        int[] scheduleState = new int[numberOfUnits];

        for (int i = 0; i < numberOfUnits; i++) {
            scheduleState[i] = r.nextInt(ScheduleState.getPossibleInterval(i));
        }

        return new ScheduleState(scheduleState, null);
    }

    public static int getPossibleInterval(int unit) {
        return ScheduleState.intervalMaxLoads.length - (ScheduleState.unitIntervals[unit] - 1);
    }

}
