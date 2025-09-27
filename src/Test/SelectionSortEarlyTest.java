package Test;

import Algorithm.SelectionSortEarly;
import Metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SelectionSortEarlyTest {

    @Test
    void testSortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        PerformanceTracker tracker = new PerformanceTracker();
        SelectionSortEarly.sort(arr, tracker);
        assertTrue(SelectionSortEarly.isSorted(arr));
    }

    @Test
    void testReverseArray() {
        int[] arr = {5, 4, 3, 2, 1};
        PerformanceTracker tracker = new PerformanceTracker();
        SelectionSortEarly.sort(arr, tracker);
        assertTrue(SelectionSortEarly.isSorted(arr));
    }

    @Test
    void testRandomArray() {
        int[] arr = {3, 1, 4, 1, 5, 9};
        PerformanceTracker tracker = new PerformanceTracker();
        SelectionSortEarly.sort(arr, tracker);
        assertTrue(SelectionSortEarly.isSorted(arr));
    }

    @Test
    void testEmptyArray() {
        int[] arr = {};
        PerformanceTracker tracker = new PerformanceTracker();
        SelectionSortEarly.sort(arr, tracker);
        assertTrue(SelectionSortEarly.isSorted(arr));
    }

    @Test
    void testSingleElement() {
        int[] arr = {42};
        PerformanceTracker tracker = new PerformanceTracker();
        SelectionSortEarly.sort(arr, tracker);
        assertTrue(SelectionSortEarly.isSorted(arr));
    }
}
