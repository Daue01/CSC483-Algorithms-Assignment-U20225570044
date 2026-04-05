package ordering;

public class InsertionSorter {
    public static void execute(int[] dataBlock, PerformanceStats stats) {
        int length = dataBlock.length;
        for (int step = 1; step < length; step++) {
            int anchor = dataBlock[step];
            int pointer = step - 1;

            while (pointer >= 0) {
                stats.conditionChecks++;
                if (dataBlock[pointer] > anchor) {
                    dataBlock[pointer + 1] = dataBlock[pointer];
                    stats.mutations++;
                    pointer--;
                } else {
                    break;
                }
            }
            dataBlock[pointer + 1] = anchor;
            stats.mutations++;
        }
    }
}