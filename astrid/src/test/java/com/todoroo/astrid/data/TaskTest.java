package com.todoroo.astrid.data;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static com.todoroo.astrid.data.Task.URGENCY_DAY_AFTER;
import static com.todoroo.astrid.data.Task.URGENCY_IN_TWO_WEEKS;
import static com.todoroo.astrid.data.Task.URGENCY_NEXT_MONTH;
import static com.todoroo.astrid.data.Task.URGENCY_NEXT_WEEK;
import static com.todoroo.astrid.data.Task.URGENCY_NONE;
import static com.todoroo.astrid.data.Task.URGENCY_SPECIFIC_DAY;
import static com.todoroo.astrid.data.Task.URGENCY_SPECIFIC_DAY_TIME;
import static com.todoroo.astrid.data.Task.URGENCY_TODAY;
import static com.todoroo.astrid.data.Task.URGENCY_TOMORROW;
import static com.todoroo.astrid.data.Task.createDueDate;
import static com.todoroo.astrid.data.Task.hasDueTime;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.tasks.Freeze.freezeAt;
import static org.tasks.Freeze.thaw;

@RunWith(RobolectricTestRunner.class)
public class TaskTest {

    private static final DateTime now = new DateTime(2013, 12, 31, 16, 10, 53, 452);
    private static final DateTime specificDueDate = new DateTime(2014, 3, 17, 9, 54, 27, 959);

    @Before
    public void before() {
        freezeAt(now);
    }

    @After
    public void after() {
        thaw();
    }

    @Test
    public void createDueDateNoUrgency() {
        assertEquals(0, createDueDate(URGENCY_NONE, 1L));
    }

    @Test
    public void createDueDateToday() {
        long expected = new DateTime(2013, 12, 31, 12, 0, 0, 0).getMillis();
        assertEquals(expected, createDueDate(URGENCY_TODAY, -1L));
    }

    @Test
    public void createDueDateTomorrow() {
        long expected = new DateTime(2014, 1, 1, 12, 0, 0, 0).getMillis();
        assertEquals(expected, createDueDate(URGENCY_TOMORROW, -1L));
    }

    @Test
    public void createDueDateDayAfter() {
        long expected = new DateTime(2014, 1, 2, 12, 0, 0, 0).getMillis();
        assertEquals(expected, createDueDate(URGENCY_DAY_AFTER, -1L));
    }

    @Test
    public void createDueDateNextWeek() {
        long expected = new DateTime(2014, 1, 7, 12, 0, 0, 0).getMillis();
        assertEquals(expected, createDueDate(URGENCY_NEXT_WEEK, -1L));
    }

    @Test
    public void createDueDateInTwoWeeks() {
        long expected = new DateTime(2014, 1, 14, 12, 0, 0, 0).getMillis();
        assertEquals(expected, createDueDate(URGENCY_IN_TWO_WEEKS, -1L));
    }

    @Test
    public void createDueDateNextMonth() {
        long expected = new DateTime(2014, 1, 31, 12, 0, 0, 0).getMillis();
        assertEquals(expected, createDueDate(URGENCY_NEXT_MONTH, -1L));
    }

    @Test
    public void removeTimeForSpecificDay() {
        long expected = specificDueDate
                .withHourOfDay(12)
                .withMinuteOfHour(0)
                .withSecondOfMinute(0)
                .withMillisOfSecond(0)
                .getMillis();
        assertEquals(expected, createDueDate(URGENCY_SPECIFIC_DAY, specificDueDate.getMillis()));
    }

    @Test
    public void removeSecondsForSpecificTime() {
        long expected = specificDueDate
                .withSecondOfMinute(1)
                .withMillisOfSecond(0)
                .getMillis();
        assertEquals(expected, createDueDate(URGENCY_SPECIFIC_DAY_TIME, specificDueDate.getMillis()));
    }

    @Test
    public void doesHaveDueTime() {
        assertTrue(hasDueTime(1388516076000L));
    }

    @Test
    public void doesNotHaveDueTime() {
        assertFalse(hasDueTime(1388469600000L));
    }
}
