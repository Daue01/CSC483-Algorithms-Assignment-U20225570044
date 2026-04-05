package lookup;

import java.util.Objects;

public class ProductFinder {

    public static Product linearSearchById(Product[] dataset, int targetIdentifier) {
        // Swapped enhanced for-loop for traditional for-loop
        for (int idx = 0; idx < dataset.length; idx++) {
            Product activeProduct = dataset[idx];
            if (Objects.equals(targetIdentifier, activeProduct.getIdentifier())) {
                return activeProduct;
            }
        }
        return null;
    }

    public static Product logarithmicSearchById(Product[] dataset, int targetIdentifier) {
        int leftBound = 0;
        int rightBound = dataset.length - 1;

        while (leftBound <= rightBound) {
            int midpoint = leftBound + ((rightBound - leftBound) / 2);
            Product pivotProduct = dataset[midpoint];

            if (pivotProduct.getIdentifier() == targetIdentifier) {
                return pivotProduct;
            } else if (pivotProduct.getIdentifier() < targetIdentifier) {
                leftBound = midpoint + 1;
            } else {
                rightBound = midpoint - 1;
            }
        }
        return null;
    }

    public static Product linearSearchByTitle(Product[] dataset, String targetTitle) {
        // Swapped enhanced for-loop for traditional for-loop
        for (int idx = 0; idx < dataset.length; idx++) {
            Product activeProduct = dataset[idx];
            if (Objects.equals(targetTitle, activeProduct.getTitle())) {
                return activeProduct;
            }
        }
        return null;
    }
}