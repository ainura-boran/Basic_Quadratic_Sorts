package CLI;
import Algorithm.Insertion_sort;
import Metrics.PerformanceTrackerInsertion;

import java.io.*;
import java.util.*;

public class Benchmark_runner_insertion {

    private static final Random RNG = new Random(42);

    public static void main(String[] args) throws Exception {
        int minN = getIntArg(args, "--minN", 100);
        int maxN = getIntArg(args, "--maxN", 100000);
        int factor = getIntArg(args, "--factor", 10);
        int trials = getIntArg(args, "--trials", 5);
        String out = getStrArg(args, "--out", "benchmarks_insertion.csv");

        try (PrintWriter pw = new PrintWriter(new FileWriter(out))) {
            pw.println(PerformanceTrackerInsertion.csvHeader());

            for (int n = minN; n <= maxN; n = Math.max(n * factor, n + 1)) {
                for (String dist : List.of("random", "sorted", "reverse", "nearly_sorted", "few_unique")) {
                    for (int t = 1; t <= trials; t++) {
                        int[] arr = genArray(n, dist);
                        PerformanceTrackerInsertion tracker = new PerformanceTrackerInsertion();
                        Insertion_sort.Options opts = Insertion_sort.Options.tunedForNearlySorted();

                        Insertion_sort.sort(arr, tracker, opts);

                        if (!Insertion_sort.isSorted(arr)) {
                            throw new AssertionError("Array not sorted for n=" + n + " dist=" + dist);
                        }
                        pw.println(tracker.toCsv("InsertionSort", n, dist, t));
                    }
                }
            }
        }
        System.out.println("Done. CSV -> " + out);
    }

    private static int[] genArray(int n, String dist) {
        int[] a = new int[n];
        switch (dist) {
            case "random":
                for (int i = 0; i < n; i++) a[i] = RNG.nextInt();
                break;
            case "sorted":
                for (int i = 0; i < n; i++) a[i] = i;
                break;
            case "reverse":
                for (int i = 0; i < n; i++) a[i] = n - i;
                break;
            case "nearly_sorted": {
                for (int i = 0; i < n; i++) a[i] = i;

                int swaps = Math.max(1, n / 10);
                for (int s = 0; s < swaps; s++) {
                    int i = RNG.nextInt(n);
                    int j = Math.min(n - 1, Math.max(0, i + RNG.nextInt(7) - 3));
                    int tmp = a[i]; a[i] = a[j]; a[j] = tmp;
                }
                break;
            }
            case "few_unique":
                for (int i = 0; i < n; i++) a[i] = RNG.nextInt(8);
                break;
            default:
                throw new IllegalArgumentException("Unknown distribution: " + dist);
        }
        return a;
    }

    private static int getIntArg(String[] args, String key, int def) {
        for (int i = 0; i < args.length - 1; i++) if (args[i].equals(key)) {
            try { return Integer.parseInt(args[i+1]); } catch (Exception ignored) {}
        }
        return def;
    }

    private static String getStrArg(String[] args, String key, String def) {
        for (int i = 0; i < args.length - 1; i++) if (args[i].equals(key)) {
            return args[i+1];
        }
        return def;
    }
}


