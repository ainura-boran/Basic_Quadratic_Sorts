package insertion_sort_project;
import java.util.concurrent.TimeUnit;


public class Performance_Tracker {
    public long comparisons;
    public long swaps;
    public long shifts;
    public long arrayReads;
    public long arrayWrites;
    public long allocations;

    private long startNano;
    private long endNano;
    private long startUsedBytes;
    private long endUsedBytes;

    public void reset() {
        comparisons = swaps = shifts = arrayReads = arrayWrites = allocations = 0;
        startNano = endNano = 0;
        startUsedBytes = endUsedBytes = 0;
    }

    public void start() {
        System.gc();
        startUsedBytes = usedMemory();
        startNano = System.nanoTime();
    }

    public void stop() {
        endNano = System.nanoTime();
        System.gc();
        endUsedBytes = usedMemory();
    }

    public long elapsedNanos() { return endNano - startNano; }
    public double elapsedMillis() { return (endNano - startNano) / 1e6; }
    public long memoryDeltaBytes() { return Math.max(0, endUsedBytes - startUsedBytes); }

    private static long usedMemory() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }

    public static String csvHeader() {
        return String.join(",",
                "algo","n","distribution","trial",
                "millis","comparisons","swaps","shifts","reads","writes","allocBytes");
    }

    public String toCsv(String algo, int n, String dist, int trial) {
        return String.join(",",
                algo,
                Integer.toString(n),
                dist,
                Integer.toString(trial),
                String.format("%.3f", elapsedMillis()),
                Long.toString(comparisons),
                Long.toString(swaps),
                Long.toString(shifts),
                Long.toString(arrayReads),
                Long.toString(arrayWrites),
                Long.toString(memoryDeltaBytes())
        );
    }
}


