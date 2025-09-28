package CLI;

import Algorithm.SelectionSortEarly;
import Metrics.PerformanceTrackerSelection;
import java.util.*;

public class BenchmarkRunner {
    public static void main(String[] args) {
        int[] sizes = {100, 1000, 10000, 100000};
        String[] dists = {"random", "sorted", "reversed"};

        PerformanceTrackerSelection tracker = new PerformanceTrackerSelection();

        for (int n : sizes) {
            System.out.println("[SelectionSort] n=" + n);

            for (String dist : dists) {
                int[] arr = generateArray(n, dist);

                tracker.startTimer();
                SelectionSortEarly.sort(arr, tracker);
                tracker.stopTimer();

                System.out.println(" " + dist + " -> " +
                        "Time=" + tracker.getElapsedNs() + "ns | " +
                        "Comp=" + tracker.getComparisons() + " | " +
                        "Swaps=" + tracker.getSwaps() + " | " +
                        "Accesses=" + tracker.getArrayAccesses());

                System.out.println(" CSV: selection," + n + "," + dist + "," +
                        tracker.getElapsedNs() + "," +
                        tracker.getComparisons() + "," +
                        tracker.getSwaps() + "," +
                        tracker.getArrayAccesses() + ",1,0");
            }
            System.out.println("-------------------------------------------------");
        }
    }

    private static int[] generateArray(int n, String dist) {
        int[] arr = new int[n];
        Random rnd = new Random();

        switch (dist) {
            case "sorted":
                for (int i = 0; i < n; i++) arr[i] = i;
                break;
            case "reversed":
                for (int i = 0; i < n; i++) arr[i] = n - i;
                break;
            case "random":
            default:
                for (int i = 0; i < n; i++) arr[i] = rnd.nextInt(1000);
        }
        return arr;
    }
}