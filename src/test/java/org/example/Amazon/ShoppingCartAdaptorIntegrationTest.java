package org.example.Amazon;

import org.example.Amazon.Cost.ItemType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShoppingCartAdaptorIntegrationTest {
    private Database database;
    private ShoppingCartAdaptor shoppingCart;

    @BeforeEach
    void setUp() {
        database = new Database();
        shoppingCart = new ShoppingCartAdaptor(database);
    }

    @AfterEach
    void tearDown() {
        database.resetDatabase();
        database.close();
    }

    @Test
    void testAddAndGetItems() {
        Item item1 = new Item(ItemType.OTHER, "Book", 2, 12.50);
        Item item2 = new Item(ItemType.ELECTRONIC, "Laptop", 1, 999.99);

        shoppingCart.add(item1);
        shoppingCart.add(item2);

        List<Item> items = shoppingCart.getItems();
        assertEquals(2, items.size());

        Item retrievedItem1 = items.get(0);
        assertEquals("Book", retrievedItem1.getName());
        assertEquals(ItemType.OTHER, retrievedItem1.getType());
        assertEquals(2, retrievedItem1.getQuantity());
        assertEquals(12.50, retrievedItem1.getPricePerUnit(), 0.001);


        Item retrievedItem2 = items.get(1);
        assertEquals("Laptop", retrievedItem2.getName());
        assertEquals(ItemType.ELECTRONIC, retrievedItem2.getType());
        assertEquals(1, retrievedItem2.getQuantity());
        assertEquals(999.99, retrievedItem2.getPricePerUnit(), 0.001);
    }

    @Test
    void testNumberOfItems() {
        Item item1 = new Item(ItemType.OTHER, "Book", 2, 12.50);
        shoppingCart.add(item1);
        assertEquals(1, shoppingCart.numberOfItems());
        Item item2 = new Item(ItemType.ELECTRONIC, "Laptop", 1, 999.99);
        shoppingCart.add(item2);
        assertEquals(2, shoppingCart.numberOfItems());
    }

    @Test
    void testNumberOfItems_Empty() {
        assertEquals(0, shoppingCart.numberOfItems());
    }

}