package Algorithm;

import Metrics.PerformanceTracker;

public class SelectionSortEarly {

    public static void sort(int[] arr, PerformanceTracker tracker) {
        int n = arr.length;
        tracker.reset();

        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            boolean isSorted = true;

            for (int j = i + 1; j < n; j++) {
                tracker.incComparisons();
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
                tracker.incComparisons();
                if (arr[j] < arr[j - 1]) {
                    isSorted = false;
                }
                tracker.incArrayAccesses(2);
            }

            if (minIdx != i) {
                int temp = arr[i];
                arr[i] = arr[minIdx];
                arr[minIdx] = temp;
                tracker.incSwaps();
                tracker.incArrayAccesses(4);
            } else if (isSorted) {
                break; // early termination if already sorted
            }
        }
    }

    public static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) return false;
        }
        return true;
    }
}
