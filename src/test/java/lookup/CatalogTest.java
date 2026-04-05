package lookup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CatalogTest {

    private Product[] mockInventory;
    private DualIndexCatalog testCatalog;

    @BeforeEach
    public void prepareEnvironment() {
        mockInventory = new Product[5];
        mockInventory[0] = new Product(100, "Adapter", "Tech", 15.0, 50);
        mockInventory[1] = new Product(200, "Tablet", "Tech", 300.0, 10);
        mockInventory[2] = new Product(300, "Desk", "Furniture", 120.0, 5);

        testCatalog = new DualIndexCatalog(5);
        testCatalog.initializeDictionary(mockInventory);
    }

    @Test
    public void validateLinearSearchSuccess() {
        Product resolved = ProductFinder.linearSearchById(mockInventory, 200);
        assertNotNull(resolved);
        assertEquals("Tablet", resolved.getTitle());
    }

    @Test
    public void validateLogarithmicSearchFailure() {
        Product resolved = ProductFinder.logarithmicSearchById(mockInventory, 999);
        assertNull(resolved);
    }

    @Test
    public void validateDictionaryImmediateLookup() {
        Product resolved = testCatalog.immediateRetrievalByTitle("Desk");
        assertNotNull(resolved);
        assertEquals(300, resolved.getIdentifier());
    }

    @Test
    public void validateOrderedInsertionLogic() {
        Product freshProduct = new Product(150, "Cables", "Tech", 10.0, 100);
        testCatalog.insertNewProduct(mockInventory, freshProduct);

        assertEquals(100, mockInventory[0].getIdentifier());
        assertEquals(150, mockInventory[1].getIdentifier()); 
        assertEquals(200, mockInventory[2].getIdentifier()); 

        assertNotNull(testCatalog.immediateRetrievalByTitle("Cables"));
    }

    @Test
    public void validateNullHandlingOnEmptyCollection() {
        Product[] voidArray = new Product[0];
        assertNull(ProductFinder.logarithmicSearchById(voidArray, 50)); 
    }
}