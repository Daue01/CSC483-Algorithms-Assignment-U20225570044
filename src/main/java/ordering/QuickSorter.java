package ordering;

public class QuickSorter {
    public static void execute(int[] dataBlock, int startIdx, int endIdx, PerformanceStats stats) {
        if (startIdx < endIdx) {
            int partitionPivot = divide(dataBlock, startIdx, endIdx, stats);
            execute(dataBlock, startIdx, partitionPivot - 1, stats);
            execute(dataBlock, partitionPivot + 1, endIdx, stats);
        }
    }

    private static int divide(int[] dataBlock, int startIdx, int endIdx, PerformanceStats stats) {
        int middle = startIdx + (endIdx - startIdx) / 2;
        int hold = dataBlock[middle];
        dataBlock[middle] = dataBlock[endIdx];
        dataBlock[endIdx] = hold;
        stats.mutations++;

        int anchor = dataBlock[endIdx];
        int tracker = (startIdx - 1);

        for (int iter = startIdx; iter < endIdx; iter++) {
            stats.conditionChecks++;
            if (dataBlock[iter] < anchor) {
                tracker++;
                int buffer = dataBlock[tracker];
                dataBlock[tracker] = dataBlock[iter];
                dataBlock[iter] = buffer;
                stats.mutations++;
            }
        }

        int buffer = dataBlock[tracker + 1];
        dataBlock[tracker + 1] = dataBlock[endIdx];
        dataBlock[endIdx] = buffer;
        stats.mutations++;

        return tracker + 1;
    }
}