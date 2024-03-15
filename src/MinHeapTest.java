import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

public class MinHeapTest {
    @Test
    public void testHeapOrder() {
        MinHeap minHeap = new MinHeap();
        minHeap.insert(new Interval(
                LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0),
                LocalDateTime.of(2024, Month.JANUARY, 8, 0, 0)));
        minHeap.insert(new Interval(
                LocalDateTime.of(2024, Month.MARCH, 1, 0, 0),
                LocalDateTime.of(2024, Month.MARCH, 25, 0, 0)));
        minHeap.insert(new Interval(
                LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0),
                LocalDateTime.of(2024, Month.FEBRUARY, 14, 0, 0)));
        minHeap.insert(new Interval(
                LocalDateTime.of(2024, Month.OCTOBER, 31, 0, 0),
                LocalDateTime.of(2024, Month.NOVEMBER, 20, 0, 0)));
        minHeap.insert(new Interval(
                LocalDateTime.of(2024, Month.AUGUST, 20, 0, 0),
                LocalDateTime.of(2024, Month.OCTOBER, 31, 0, 0)));

        ArrayList<Interval> sortedIntervals = new ArrayList<>();
        sortedIntervals.add(minHeap.extract());
        sortedIntervals.add(minHeap.extract());
        sortedIntervals.add(minHeap.extract());
        sortedIntervals.add(minHeap.extract());
        sortedIntervals.add(minHeap.extract());

        ArrayList<Interval> testList = new ArrayList<>();
        testList.add(new Interval(
                LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0),
                LocalDateTime.of(2024, Month.JANUARY, 8, 0, 0)));
        testList.add(new Interval(
                LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0),
                LocalDateTime.of(2024, Month.FEBRUARY, 14, 0, 0)));
        testList.add(new Interval(
                LocalDateTime.of(2024, Month.MARCH, 1, 0, 0),
                LocalDateTime.of(2024, Month.MARCH, 25, 0, 0)));
        testList.add(new Interval(
                LocalDateTime.of(2024, Month.AUGUST, 20, 0, 0),
                LocalDateTime.of(2024, Month.OCTOBER, 31, 0, 0)));
        testList.add(new Interval(
                LocalDateTime.of(2024, Month.OCTOBER, 31, 0, 0),
                LocalDateTime.of(2024, Month.NOVEMBER, 20, 0, 0)));

        assertEquals(testList, sortedIntervals);
    }

    @Test
    public void testHeapOrder_duplicates() {
        MinHeap minHeap = new MinHeap();
        minHeap.insert(new Interval(
                LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0),
                LocalDateTime.of(2024, Month.JANUARY, 8, 0, 0)));
        minHeap.insert(new Interval(
                LocalDateTime.of(2024, Month.MARCH, 1, 0, 0),
                LocalDateTime.of(2024, Month.MARCH, 25, 0, 0)));
        minHeap.insert(new Interval(
                LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0),
                LocalDateTime.of(2024, Month.FEBRUARY, 14, 0, 0)));
        minHeap.insert(new Interval(
                LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0),
                LocalDateTime.of(2024, Month.JANUARY, 8, 0, 0)));
        minHeap.insert(new Interval(
                LocalDateTime.of(2024, Month.AUGUST, 20, 0, 0),
                LocalDateTime.of(2024, Month.OCTOBER, 31, 0, 0)));

        ArrayList<Interval> sortedIntervals = new ArrayList<>();
        sortedIntervals.add(minHeap.extract());
        sortedIntervals.add(minHeap.extract());
        sortedIntervals.add(minHeap.extract());
        sortedIntervals.add(minHeap.extract());
        sortedIntervals.add(minHeap.extract());

        ArrayList<Interval> testList = new ArrayList<>();
        testList.add(new Interval(
                LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0),
                LocalDateTime.of(2024, Month.JANUARY, 8, 0, 0)));
        testList.add(new Interval(
                LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0),
                LocalDateTime.of(2024, Month.JANUARY, 8, 0, 0)));
        testList.add(new Interval(
                LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0),
                LocalDateTime.of(2024, Month.FEBRUARY, 14, 0, 0)));
        testList.add(new Interval(
                LocalDateTime.of(2024, Month.MARCH, 1, 0, 0),
                LocalDateTime.of(2024, Month.MARCH, 25, 0, 0)));
        testList.add(new Interval(
                LocalDateTime.of(2024, Month.AUGUST, 20, 0, 0),
                LocalDateTime.of(2024, Month.OCTOBER, 31, 0, 0)));

        assertEquals(testList, sortedIntervals);
    }

    @Test
    public void testHeapOrder_singleElement() {
        MinHeap minHeap = new MinHeap();
        minHeap.insert(new Interval(
                LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0),
                LocalDateTime.of(2024, Month.JANUARY, 8, 0, 0)));

        ArrayList<Interval> sortedIntervals = new ArrayList<>();
        sortedIntervals.add(minHeap.extract());

        ArrayList<Interval> testList = new ArrayList<>();
        testList.add(new Interval(
                LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0),
                LocalDateTime.of(2024, Month.JANUARY, 8, 0, 0)));

        assertEquals(testList, sortedIntervals);
    }

    @Test
    public void testOverlapping() {
        MinHeap minHeap = new MinHeap();
        Interval interval = minHeap.overlapping(
                new Interval(
                        LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0),
                        LocalDateTime.of(2024, Month.JANUARY, 8, 0, 0)),
                new Interval(
                        LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0),
                        LocalDateTime.of(2024, Month.FEBRUARY, 14, 0, 0)));

        Interval testInterval = new Interval(
                LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0),
                LocalDateTime.of(2024, Month.FEBRUARY, 14, 0, 0));

        assertEquals(interval, testInterval);
    }

    @Test
    public void testOverlapping_notOverlapping() {
        MinHeap minHeap = new MinHeap();
        Interval interval = minHeap.overlapping(
                new Interval(
                        LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0),
                        LocalDateTime.of(2024, Month.JANUARY, 8, 0, 0)),
                new Interval(
                        LocalDateTime.of(2024, Month.AUGUST, 20, 0, 0),
                        LocalDateTime.of(2024, Month.OCTOBER, 31, 0, 0)));

        Interval testInterval = new Interval(
                LocalDateTime.of(0, Month.JANUARY, 1, 0, 0),
                LocalDateTime.of(0, Month.JANUARY, 1, 0, 0));

        assertEquals(interval, testInterval);
    }

}
