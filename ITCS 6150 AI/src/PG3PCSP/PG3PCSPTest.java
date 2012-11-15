package PG3PCSP;

import static org.junit.Assert.*;

import org.junit.Test;

public class PG3PCSPTest {

    @Test
    public void testState1() {
        ScheduleState.initialize(new int[] { 2, 2, 1, 1, 1, 1, 1 }, new int[] { 20, 15, 35, 40, 15, 15, 10 },
                new int[] { 80, 90, 65, 70 }, 0);
        assertEquals(150, ScheduleState.getTotalCapacity());

        int i = 100;
        while (i-- > 0) {
            ScheduleState state = ScheduleState.generateRandomState();
            int[] stateData = state.getUnitScheduleState();
            assertNotSame(3, stateData[0]);
            assertNotSame(3, stateData[1]);
            assertNotSame(4, stateData[2]);
            assertNotSame(4, stateData[3]);
            assertNotSame(4, stateData[4]);
            assertNotSame(4, stateData[5]);
            assertNotSame(4, stateData[6]);

            // System.out.println(Arrays.toString(state.countNetReserves()));
        }
    }

    @Test
    public void testState2() {
        ScheduleState.initialize(new int[] { 2, 2, 1, 1, 1, 1, 1 }, new int[] { 20, 15, 35, 40, 15, 15, 10 },
                new int[] { 80, 90, 65, 70 }, 0);
        assertEquals(150, ScheduleState.getTotalCapacity());

        ScheduleState state = new ScheduleState(new int[] { 0, 2, 3, 2, 0, 1, 3 }, null);

        assertArrayEquals(new int[] { 35, 25, 30, 20 }, state.getIntervalNetReserves());

        state.moveUnit(4, 2);
        assertArrayEquals(new int[] { 50, 25, 15, 20 }, state.getIntervalNetReserves());
    }

    @Test
    public void testState3() {
        ScheduleState.initialize(new int[] { 2, 2, 1, 1, 1, 1, 1 }, new int[] { 20, 15, 35, 40, 15, 15, 10 },
                new int[] { 80, 90, 65, 70 }, 0);
        assertEquals(150, ScheduleState.getTotalCapacity());

        ScheduleState state = new ScheduleState(new int[] { 0, 2, 3, 2, 2, 1, 3 }, null);

        assertArrayEquals(new int[] { 50, 25, 15, 20 }, state.getIntervalNetReserves());
    }

    @Test
    public void testState4() {
        PCSP instance = new PCSP();
        assertNotNull(instance.run(new int[] { 2, 2, 1, 1, 1, 1, 1 }, new int[] { 20, 15, 35, 40, 15, 15, 10 },
                new int[] { 80, 90, 65, 70 }));
    }

    @Test
    public void testState5() {
        PCSP instance = new PCSP();
        assertNotNull(instance.run(new int[] { 2, 2, 1, 1, 1, 1, 1, 1 }, new int[] { 20, 15, 35, 40, 15, 15, 10, 30 },
                new int[] { 80, 90, 65, 70 }));
    }
}
