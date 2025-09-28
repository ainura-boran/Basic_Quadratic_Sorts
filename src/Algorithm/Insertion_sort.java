package Algorithm;

import Metrics.PerformanceTrackerInsertion;

public final class Insertion_sort {

    private Insertion_sort() {}

    public static class Options {
        public boolean useSentinel = true;
        public boolean useBinarySearch = true;
        public boolean earlyExitIfSorted = true;

        public static Options tunedForNearlySorted() {
            Options o = new Options();
            o.useSentinel = true;
            o.useBinarySearch = true;
            o.earlyExitIfSorted = true;
            return o;
        }
    }

    public static void sort(int[] a, PerformanceTrackerInsertion t, Options opts) {
        if (a == null)
            throw new IllegalArgumentException("array is null");
        if (t == null)
            throw new IllegalArgumentException("tracker is null");
        if (opts == null) opts = new Options();

        t.start();
        if (a.length <= 1) { t.stop(); return; }

        if (opts.earlyExitIfSorted && isAlreadySorted(a, t)) {
            t.stop(); return;
        }

        if (opts.useSentinel) placeSentinelMin(a, t);

        if (opts.useBinarySearch) {
            binaryInsertionSort(a, t);
        } else {
            plainInsertionSort(a, t);
        }
        t.stop();
    }

    private static boolean isAlreadySorted(int[] a, PerformanceTrackerInsertion t) {
        for (int i = 1; i < a.length; i++) {
            t.arrayReads += 2;
            t.comparisons++;
            if (a[i-1] > a[i]) return false;
        }
        return true;
    }

    private static void placeSentinelMin(int[] a, PerformanceTrackerInsertion t) {
        int minIdx = 0;
        int minVal = read(a, 0, t);
        for (int i = 1; i < a.length; i++) {
            int v = read(a, i, t);
            t.comparisons++;
            if (v < minVal) { minVal = v; minIdx = i; }
        }
        if (minIdx != 0) {
            swap(a, 0, minIdx, t);
        }
    }

    private static void plainInsertionSort(int[] a, PerformanceTrackerInsertion t) {
        for (int i = 1; i < a.length; i++) {
            int key = read(a, i, t);
            int j = i - 1;

            while (j >= 0) {
                t.arrayReads++;
                t.comparisons++;
                if (a[j] <= key) break;
                write(a, j + 1, a[j], t);
                t.shifts++;
                j--;
            }
            write(a, j + 1, key, t);
        }
    }

    private static void binaryInsertionSort(int[] a, PerformanceTrackerInsertion t) {
        for (int i = 1; i < a.length; i++) {
            int key = read(a, i, t);

            int lo = 0, hi = i;
            while (lo < hi) {
                int mid = (lo + hi) >>> 1;
                int mv = read(a, mid, t);
                t.comparisons++;
                if (mv <= key) lo = mid + 1;
                else hi = mid;
            }
            int idx = lo;


            for (int j = i - 1; j >= idx; j--) {
                int v = read(a, j, t);
                write(a, j + 1, v, t);
                t.shifts++;
            }
            write(a, idx, key, t);
        }
    }

    private static int read(int[] a, int i, PerformanceTrackerInsertion t) {
        t.arrayReads++;
        return a[i];
    }
    private static void write(int[] a, int i, int v, PerformanceTrackerInsertion t) {
        t.arrayWrites++;
        a[i] = v;
    }
    private static void swap(int[] a, int i, int j, PerformanceTrackerInsertion t) {
        int tmp = read(a, i, t);
        write(a, i, a[j], t); t.arrayReads++;
        write(a, j, tmp, t);
        t.swaps++;
    }

    public static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) if (a[i-1] > a[i]) return false;
        return true;
    }
}

