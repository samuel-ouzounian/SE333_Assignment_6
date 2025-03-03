package org.example.Amazon;

import org.example.Amazon.Cost.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmazonIntegrationTest {

    private Database database;
    private ShoppingCartAdaptor shoppingCart;
    private Amazon amazon;

    @BeforeEach
    void setUp() {
        database = new Database();
        shoppingCart = new ShoppingCartAdaptor(database);
        List<PriceRule> rules = List.of(new RegularCost(), new DeliveryPrice(), new ExtraCostForElectronics());
        amazon = new Amazon(shoppingCart, rules);
    }

    @AfterEach
    void tearDown() {
        database.resetDatabase();
        database.close();
    }

    @Test
    void testAddItemsAndCalculatePrice_NoElectronics() {
        Item item1 = new Item(ItemType.OTHER, "Book", 2, 12.50);
        Item item2 = new Item(ItemType.OTHER, "T-Shirt", 1, 15.00);
        amazon.addToCart(item1);
        amazon.addToCart(item2);
        double totalPrice = amazon.calculate();
        assertEquals(45.00, totalPrice, 0.001);
    }

    @Test
    void testAddItemsAndCalculatePrice_WithElectronics() {
        Item item1 = new Item(ItemType.OTHER, "Book", 1, 10.00);
        Item item2 = new Item(ItemType.ELECTRONIC, "Laptop", 1, 1000.00);
        amazon.addToCart(item1);
        amazon.addToCart(item2);

        double totalPrice = amazon.calculate();

        assertEquals(1022.50, totalPrice, 0.001);
    }
    @Test
    void testAddItemsAndCalculatePrice_MultipleElectronics() {
        Item item1 = new Item(ItemType.ELECTRONIC, "Tablet", 4, 100.00);
        Item item2 = new Item(ItemType.ELECTRONIC, "Laptop", 1, 1000.00);
        amazon.addToCart(item1);
        amazon.addToCart(item2);

        double totalPrice = amazon.calculate();

        assertEquals(1412.5, totalPrice);
    }

    @Test
    void testAddItemsAndCalculatePrice_Empty() {
        double totalPrice = amazon.calculate();

        assertEquals(0, totalPrice, 0.001);
    }
    @Test
    void testAddItemsAndCalculatePrice_Boundary() {
        Item item1 = new Item(ItemType.OTHER, "Book", 10, 10.00);

        amazon.addToCart(item1);


        double totalPrice = amazon.calculate();

        assertEquals(105.0, totalPrice);
    }
}