package ordering;

public class MergeSorter {
    public static void execute(int[] dataBlock, int startIdx, int endIdx, PerformanceStats stats) {
        if (startIdx < endIdx) {
            int center = startIdx + (endIdx - startIdx) / 2;
            execute(dataBlock, startIdx, center, stats);
            execute(dataBlock, center + 1, endIdx, stats);
            combine(dataBlock, startIdx, center, endIdx, stats);
        }
    }

    private static void combine(int[] dataBlock, int startIdx, int center, int endIdx, PerformanceStats stats) {
        int leftSize = center - startIdx + 1;
        int rightSize = endIdx - center;

        int[] leftBuffer = new int[leftSize];
        int[] rightBuffer = new int[rightSize];

        for (int m = 0; m < leftSize; m++) {
            leftBuffer[m] = dataBlock[startIdx + m];
            stats.mutations++;
        }
        for (int n = 0; n < rightSize; n++) {
            rightBuffer[n] = dataBlock[center + 1 + n];
            stats.mutations++;
        }

        int scanL = 0, scanR = 0, scanMain = startIdx;

        while (scanL < leftSize && scanR < rightSize) {
            stats.conditionChecks++;
            if (leftBuffer[scanL] <= rightBuffer[scanR]) {
                dataBlock[scanMain] = leftBuffer[scanL];
                scanL++;
            } else {
                dataBlock[scanMain] = rightBuffer[scanR];
                scanR++;
            }
            stats.mutations++;
            scanMain++;
        }

        while (scanL < leftSize) {
            dataBlock[scanMain++] = leftBuffer[scanL++];
            stats.mutations++;
        }

        while (scanR < rightSize) {
            dataBlock[scanMain++] = rightBuffer[scanR++];
            stats.mutations++;
        }
    }
}