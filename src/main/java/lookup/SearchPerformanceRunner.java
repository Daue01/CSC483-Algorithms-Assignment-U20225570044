package lookup;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class SearchPerformanceRunner {

    public static void main(String[] arguments) {
        int volume = 100000;
        Product[] inventoryRecord = new Product[volume];
        Random rng = new Random();

        for (int val = 0; val < volume; val++) {
            int randomKey = rng.nextInt(200000) + 1;
            inventoryRecord[val] = new Product(randomKey, "ProductXYZ_" + val, "Gadgets", 199.99, 25);
        }

        Arrays.sort(inventoryRecord, Comparator.comparingInt(Product::getIdentifier));

        int bestCaseLinearTarget = inventoryRecord[0].getIdentifier();
        int bestCaseLogTarget = inventoryRecord[volume / 2].getIdentifier();
        int randomAverageTarget = inventoryRecord[rng.nextInt(volume)].getIdentifier();
        int worstCaseTarget = 999999; 

        System.out.println("================================================================");
        System.out.println("ENTERPRISE SEARCH METRICS ANALYSIS (Records=" + volume + ")");
        System.out.println("================================================================");

        System.out.println("LINEAR SEARCH EXECUTION:");
        
        long markStart = System.nanoTime();
        ProductFinder.linearSearchById(inventoryRecord, bestCaseLinearTarget);
        long markEnd = System.nanoTime();
        System.out.printf("Optimal Scenario (index 0): %.3f ms\n", (markEnd - markStart) / 1_000_000.0);

        markStart = System.nanoTime();
        ProductFinder.linearSearchById(inventoryRecord, randomAverageTarget);
        markEnd = System.nanoTime();
        double linearAvg = (markEnd - markStart) / 1_000_000.0;
        System.out.printf("Standard Scenario (random pick): %.3f ms\n", linearAvg);

        markStart = System.nanoTime();
        ProductFinder.linearSearchById(inventoryRecord, worstCaseTarget);
        markEnd = System.nanoTime();
        System.out.printf("Degraded Scenario (missing pick): %.3f ms\n", (markEnd - markStart) / 1_000_000.0);

        System.out.println("\nLOGARITHMIC SEARCH EXECUTION:");

        markStart = System.nanoTime();
        ProductFinder.logarithmicSearchById(inventoryRecord, bestCaseLogTarget);
        markEnd = System.nanoTime();
        System.out.printf("Optimal Scenario (median pick): %.3f ms\n", (markEnd - markStart) / 1_000_000.0);

        markStart = System.nanoTime();
        ProductFinder.logarithmicSearchById(inventoryRecord, randomAverageTarget);
        markEnd = System.nanoTime();
        double logAvg = (markEnd - markStart) / 1_000_000.0;
        System.out.printf("Standard Scenario (random pick): %.3f ms\n", logAvg);

        markStart = System.nanoTime();
        ProductFinder.logarithmicSearchById(inventoryRecord, worstCaseTarget);
        markEnd = System.nanoTime();
        System.out.printf("Degraded Scenario (missing pick): %.3f ms\n", (markEnd - markStart) / 1_000_000.0);

        System.out.printf("\nEFFICIENCY MULTIPLIER: Logarithmic search scales ~%.0fx faster\n", (linearAvg / logAvg));
        System.out.println("================================================================");
    }
}