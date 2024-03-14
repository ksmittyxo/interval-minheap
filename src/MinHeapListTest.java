import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

public class MinHeapListTest {
    @Test
    public void testInsertAndExtract() {
        MinHeapList minHeap = new MinHeapList();
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

        assertEquals(testList, sortedIntervals);
    }

}
