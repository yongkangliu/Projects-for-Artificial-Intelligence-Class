package PG3PCSP;

import java.util.Arrays;
import java.util.Random;

public class ScheduleState {

    private static int totalCapacity = 0;
    private static int[] unitCapacities = new int[] {};
    private static int[] unitIntervals = new int[] {};
    private static int[] intervalMaxLoads = new int[] {};

    private static Random r = new Random();

    private int[] unitScheduleState = new int[] {};
    private int[] intervalNetReserves = new int[] {};

    public ScheduleState(int[] scheduleState, int[] netReserves) {
        this.unitScheduleState = scheduleState;
        if (netReserves == null) {
            this.intervalNetReserves = new int[ScheduleState.intervalMaxLoads.length];
            this.countNetReserves();
        } else {
            this.intervalNetReserves = netReserves;
        }
    }

    public void print(int a) {
        System.out.println(a + Arrays.toString(this.unitScheduleState) + Arrays.toString(this.intervalNetReserves));
    }

    public static void initialize(int[] intervals, int[] capacities, int[] maxLoads) {
        ScheduleState.unitIntervals = intervals;
        ScheduleState.unitCapacities = capacities;
        ScheduleState.intervalMaxLoads = maxLoads;

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
        return this.intervalNetReserves;
    }

    public double getStdDev() {
        StandardDeviation count = new StandardDeviation(this.intervalNetReserves);
        return count.getStdDev();
    }

    public int getNumberOfNegtiveNetReserve() {
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

    private void moveNetReserve(int iUnit, int iOldInterval, int iNewInterval) {
        for (int i = 0; i < ScheduleState.unitIntervals[iUnit]; i++) {
            this.intervalNetReserves[iNewInterval + i] -= ScheduleState.unitCapacities[iUnit];
            this.intervalNetReserves[iOldInterval + i] += ScheduleState.unitCapacities[iUnit];
        }
    }

    private boolean isBetterMove(int[] oldNetReserves, int[] newNetReserves) {
        StandardDeviation oldGap = new StandardDeviation(oldNetReserves);
        StandardDeviation newGap = new StandardDeviation(newNetReserves);
        if (newGap.getStdDev() < oldGap.getStdDev()) {
            return true;
        } else {
            return false;
        }
    }

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

    public static int getPossibleInterval(int unit) {
        return ScheduleState.intervalMaxLoads.length - (ScheduleState.unitIntervals[unit] - 1);
    }

    public static int[] getUnitIntervals() {
        return ScheduleState.unitIntervals;
    }
}
