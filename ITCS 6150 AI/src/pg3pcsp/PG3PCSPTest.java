/*
 * UNC Charlotte ITCS 6150 Intelligence System Class, Final Project
 * 
 * by Yongkang Liu, 11/24/2012
 */
package pg3pcsp;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

/**
 * Unit Test cases for refactoring code.
 */
public class PG3PCSPTest {

    @Test
    public void testState1() {
        ScheduleState.initialize(new int[] { 2, 2, 1, 1, 1, 1, 1 }, new int[] { 20, 15, 35, 40, 15, 15, 10 },
                new int[] { 80, 90, 65, 70 });
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
                new int[] { 80, 90, 65, 70 });
        assertEquals(150, ScheduleState.getTotalCapacity());

        ScheduleState state = new ScheduleState(new int[] { 0, 2, 3, 2, 0, 1, 3 }, null);

        System.out.println(Arrays.toString(state.getIntervalNetReserves()));
        assertArrayEquals(new int[] { 35, 25, 30, 20 }, state.getIntervalNetReserves());

        state.moveUnit(4, 2);
        assertArrayEquals(new int[] { 50, 25, 15, 20 }, state.getIntervalNetReserves());
    }

    @Test
    public void testState3() {
        ScheduleState.initialize(new int[] { 2, 2, 1, 1, 1, 1, 1 }, new int[] { 20, 15, 35, 40, 15, 15, 10 },
                new int[] { 80, 90, 65, 70 });
        assertEquals(150, ScheduleState.getTotalCapacity());

        ScheduleState state = new ScheduleState(new int[] { 0, 2, 3, 2, 2, 1, 3 }, null);

        assertArrayEquals(new int[] { 50, 25, 15, 20 }, state.getIntervalNetReserves());
    }

    @Test
    public void testState4() {
        PCSP instance = PCSP.getInstance();
        assertNotNull(instance.run(new int[] { 2, 2, 1, 1, 1, 1, 1 }, new int[] { 20, 15, 35, 40, 15, 15, 10 },
                new int[] { 80, 90, 65, 70 }, 1000));
    }

    // @Test
    public void testState5a() {
        PCSP instance = PCSP.getInstance();
        int i = 0;
        while (i++ < 100) {
            ScheduleState state = instance.run(new int[] { 2, 2, 1, 1, 1, 1, 1, 1, 1, 1 }, new int[] { 20, 15, 35, 40,
                    15, 15, 10, 15, 15, 15 }, new int[] { 110, 90, 65, 70, 80, 80, 80, 75 }, 1000);
            assertArrayEquals(new int[] { 85, 85, 85, 85, 85, 85, 85, 85 }, state.getIntervalNetReserves());
        }
    }

    @Test
    public void testState5b() {
        PCSP instance = PCSP.getInstance();
        int i = 0;
        while (i++ < 100) {
            ScheduleState state = instance.run(new int[] { 2, 2, 1, 1, 1, 1, 1, 1 }, new int[] { 20, 15, 35, 40, 15,
                    15, 10, 30 }, new int[] { 80, 90, 65, 70 }, 500);
            assertArrayEquals(new int[] { 50, 50, 50, 50 }, state.getIntervalNetReserves());
        }
    }

    @Test
    public void testState6() {
        PCSP instance = PCSP.getInstance();
        assertNull(instance.run(new int[] { 2, 2, 1, 1, 1, 1, 1 }, new int[] { 20, 15, 35, 40, 15, 15, 10 }, new int[] {
                105, 115, 95, 105 }, 1000));
    }

    @Test
    public void testState7() {
        PCSP instance = PCSP.getInstance();
        assertNotNull(instance.run(new int[] { 2, 2, 1, 1, 1, 1, 1, 2, 2, 3, 1 }, new int[] { 20, 15, 35, 40, 15, 15,
                10, 20, 50, 30, 10 }, new int[] { 80, 90, 60, 70, 50, 40, 60 }, 1000));
    }

    @Test
    public void testState8() {
        PCSP instance = PCSP.getInstance();
        assertNotNull(instance.run(new int[] { 2, 2, 1, 1, 1, 1, 1 }, new int[] { 20, 15, 35, 40, 15, 15, 10 },
                new int[] { 80, 90, 65, 100 }, 1000));
    }
}
