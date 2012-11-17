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
        int[] result = new int[this.intervalNetReserves.length];
        for (int i = 0; i < this.intervalNetReserves.length; i++) {
            result[i] = this.intervalNetReserves[i] + ScheduleState.minNetReserve;
        }
        return result;
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

        int unitLength = ScheduleState.unitIntervals[iUnit];

        int[] oldNetReserves = new int[unitLength * 2];
        for (int i = 0; i < unitLength; i++) {
            oldNetReserves[i] = this.intervalNetReserves[iOldInterval + i];
            oldNetReserves[i + unitLength] = this.intervalNetReserves[iNewInterval + i];
        }

        this.unitScheduleState[iUnit] = iNewInterval;

        int[] newNetReserves = new int[unitLength * 2];
        for (int i = 0; i < unitLength; i++) {
            newNetReserves[i] = this.countSingleNetReserve(iOldInterval + i);
            newNetReserves[i + unitLength] = this.countSingleNetReserve(iNewInterval + i);
        }

        return isBetterMove(oldNetReserves, newNetReserves);
    }

    private boolean isBetterMove(int[] oldNetReserves, int[] newNetReserves) {
        int unitLength = oldNetReserves.length / 2;
        int oldGap = 0;
        int newGap = 0;
        for (int i = 0; i < unitLength; i++) {
            oldGap += Math.abs(oldNetReserves[i] - oldNetReserves[i + unitLength]);
            newGap += Math.abs(newNetReserves[i] - newNetReserves[i + unitLength]);
        }

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

    public static int[] getUnitIntervals() {
        return ScheduleState.unitIntervals;
    }

}
