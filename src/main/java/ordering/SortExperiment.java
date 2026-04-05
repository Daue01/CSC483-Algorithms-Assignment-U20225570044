package ordering;

import java.util.Arrays;
import java.util.Random;

public class SortExperiment {

    public static void main(String[] args) {
        int[] milestones = {100, 1000, 10000, 100000}; 

        System.out.println("Initiating Evaluation Sequences...");
        System.out.println("Volume,Profile,Method,Mean Duration(ms),Mean Checks,Mean Mutations");

        for (int volume = 0; volume < milestones.length; volume++) {
            int targetSize = milestones[volume];
            int[][] topologies = {
                    buildRandom(targetSize),
                    buildOrdered(targetSize),
                    buildReversed(targetSize),
                    buildNearlyOrdered(targetSize),
                    buildRedundant(targetSize)
            };
            String[] topologyLabels = {"Randomized", "Ordered", "Reversed", "Mostly Ordered", "Redundant"};

            for (int topo = 0; topo < topologies.length; topo++) {
                int[] originSequence = topologies[topo];
                triggerEvaluation(originSequence, targetSize, topologyLabels[topo], "QuickSort");
                triggerEvaluation(originSequence, targetSize, topologyLabels[topo], "MergeSort");
                triggerEvaluation(originSequence, targetSize, topologyLabels[topo], "InsertionSort");
            }
        }
    }

    private static void triggerEvaluation(int[] originSequence, int scale, String profile, String engineType) {
        long accumulatedTime = 0, accumulatedChecks = 0, accumulatedMutations = 0;
        int passes = 5; 

        for (int cycle = 0; cycle < passes; cycle++) {
            int[] workingClone = Arrays.copyOf(originSequence, originSequence.length);
            PerformanceStats trackingData = new PerformanceStats();
            
            long startTick = System.nanoTime();

            if (engineType.equals("QuickSort")) {
                QuickSorter.execute(workingClone, 0, workingClone.length - 1, trackingData);
            } else if (engineType.equals("MergeSort")) {
                MergeSorter.execute(workingClone, 0, workingClone.length - 1, trackingData);
            } else if (engineType.equals("InsertionSort")) {
                InsertionSorter.execute(workingClone, trackingData);
            }

            long endTick = System.nanoTime();

            accumulatedTime += (endTick - startTick);
            accumulatedChecks += trackingData.conditionChecks;
            accumulatedMutations += trackingData.mutations;
        }

        double meanDuration = (accumulatedTime / (double) passes) / 1_000_000.0;
        long meanChecks = accumulatedChecks / passes;
        long meanMutations = accumulatedMutations / passes;

        System.out.printf("%d,%s,%s,%.4f,%d,%d\n", scale, profile, engineType, meanDuration, meanChecks, meanMutations);
    }

    private static int[] buildRandom(int bounds) {
        Random rng = new Random();
        int[] sequence = new int[bounds];
        for (int val = 0; val < bounds; val++) sequence[val] = rng.nextInt(bounds);
        return sequence;
    }

    private static int[] buildOrdered(int bounds) {
        int[] sequence = buildRandom(bounds);
        Arrays.sort(sequence);
        return sequence;
    }

    private static int[] buildReversed(int bounds) {
        int[] sequence = buildOrdered(bounds);
        for (int val = 0; val < sequence.length / 2; val++) {
            int hold = sequence[val];
            sequence[val] = sequence[sequence.length - 1 - val];
            sequence[sequence.length - 1 - val] = hold;
        }
        return sequence;
    }

    private static int[] buildNearlyOrdered(int bounds) {
        int[] sequence = buildOrdered(bounds);
        Random rng = new Random();
        int swapLimit = (int) (bounds * 0.1);
        for (int val = 0; val < swapLimit; val++) {
            int posA = rng.nextInt(bounds);
            int posB = rng.nextInt(bounds);
            int hold = sequence[posA];
            sequence[posA] = sequence[posB];
            sequence[posB] = hold;
        }
        return sequence;
    }

    private static int[] buildRedundant(int bounds) {
        Random rng = new Random();
        int[] sequence = new int[bounds];
        for (int val = 0; val < bounds; val++) {
            sequence[val] = rng.nextInt(10); 
        }
        return sequence;
    }
}