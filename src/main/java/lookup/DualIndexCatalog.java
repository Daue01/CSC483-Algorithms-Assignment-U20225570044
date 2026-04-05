package lookup;

import java.util.HashMap;

public class DualIndexCatalog {
    private Product[] coreArray;
    private HashMap<String, Product> titleDictionary;
    private int activeCount;

    public DualIndexCatalog(int maximumCapacity) {
        this.coreArray = new Product[maximumCapacity];
        this.titleDictionary = new HashMap<>();
        this.activeCount = 0;
    }

    public Product immediateRetrievalByTitle(String desiredTitle) {
        return titleDictionary.get(desiredTitle);
    }

    public void initializeDictionary(Product[] baseDataset) {
        for (int k = 0; k < baseDataset.length; k++) {
            Product element = baseDataset[k];
            if (element != null) {
                titleDictionary.put(element.getTitle(), element);
            }
        }
    }

    public void insertNewProduct(Product[] catalogProducts, Product incomingProduct) {
        int usage = 0;
        while (usage < catalogProducts.length && catalogProducts[usage] != null) {
            usage++;
        }

        if (usage == catalogProducts.length) {
            System.out.println("System Alert: Catalog capacity reached.");
            return;
        }

        int shiftPointer = usage - 1;

        while (shiftPointer >= 0 && catalogProducts[shiftPointer].getIdentifier() > incomingProduct.getIdentifier()) {
            catalogProducts[shiftPointer + 1] = catalogProducts[shiftPointer];
            shiftPointer--;
        }

        catalogProducts[shiftPointer + 1] = incomingProduct;
        titleDictionary.put(incomingProduct.getTitle(), incomingProduct);

        System.out.println("Registration successful for: " + incomingProduct.getTitle());
    }
}