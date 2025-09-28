package Test;
import Algorithm.Insertion_sort;
import Metrics.PerformanceTrackerInsertion;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class Insertion_sort_testing {

    @Test
    void emptyArray_ok() {
        int[] a = {};
        var t = new PerformanceTrackerInsertion ();
        Insertion_sort.sort(a, t, new Insertion_sort.Options());
        assertTrue(Insertion_sort.isSorted(a));
    }

    @Test
    void singleElement_ok() {
        int[] a = {7};
        var t = new PerformanceTrackerInsertion();
        Insertion_sort.sort(a, t, new Insertion_sort.Options());
        assertArrayEquals(new int[]{7}, a);
    }

    @Test
    void duplicates_ok() {
        int[] a = {3, 1, 1, 2, 2, 2, 0, 0};
        var t = new PerformanceTrackerInsertion();
        Insertion_sort.sort(a, t, new Insertion_sort.Options());
        assertTrue(Insertion_sort.isSorted(a));
    }

    @Test
    void sorted_ok() {
        int[] a = {1,2,3,4,5,6};
        var t = new PerformanceTrackerInsertion();
        Insertion_sort.sort(a, t, Insertion_sort.Options.tunedForNearlySorted());
        assertTrue(Insertion_sort.isSorted(a));
    }

    @Test
    void reverse_ok() {
        int[] a = {6,5,4,3,2,1};
        var t = new PerformanceTrackerInsertion();
        Insertion_sort.sort(a, t, new Insertion_sort.Options());
        assertTrue(Insertion_sort.isSorted(a));
    }

    @Test
    void randomPropertyBased_matchesJdkSort() {
        Random rng = new Random(123);
        for (int n = 0; n <= 200; n++) {
            for (int trial = 0; trial < 20; trial++) {
                int[] a = new int[n];
                for (int i = 0; i < n; i++) a[i] = rng.nextInt(1000) - 500;
                int[] expected = a.clone();
                Arrays.sort(expected);

                var t = new PerformanceTrackerInsertion();
                Insertion_sort.sort(a, t, Insertion_sort.Options.tunedForNearlySorted());
                assertArrayEquals(expected, a);
            }
        }
    }

    @Test
    void crossValidate_medium() {
        Random rng = new Random(7);
        int[] a = new int[2000];
        for (int i = 0; i < a.length; i++) a[i] = rng.nextInt();
        int[] expected = a.clone();
        Arrays.sort(expected);

        var t = new PerformanceTrackerInsertion();
        Insertion_sort.sort(a, t, Insertion_sort.Options.tunedForNearlySorted());
        assertArrayEquals(expected, a);
    }
    public static void main(String[] args) {
        int[] a = {5,2,4,6,1,3};
        var t = new PerformanceTrackerInsertion();
        Insertion_sort.sort(a, t, Insertion_sort.Options.tunedForNearlySorted());
        System.out.println("Sorted: " + Arrays.toString(a));
        System.out.printf("metrics: cmp=%d shifts=%d reads=%d writes=%d ms=%.3f%n",
                t.comparisons, t.shifts, t.arrayReads, t.arrayWrites, t.elapsedMillis());
    }
}

