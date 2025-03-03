package org.example.Amazon.Cost;

import org.example.Amazon.Item;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExtraCostForElectronicsUnitTest {

    @Test
    void testNoElectronics() {
        ExtraCostForElectronics extraCost = new ExtraCostForElectronics();
        List<Item> cart = List.of(
                new Item(ItemType.OTHER, "Book", 1, 10.0),
                new Item(ItemType.OTHER, "T-Shirt", 2, 15.0)
        );
        assertEquals(0.0, extraCost.priceToAggregate(cart), 0.001);
    }

    @Test
    void testWithElectronics() {
        ExtraCostForElectronics extraCost = new ExtraCostForElectronics();
        List<Item> cart = List.of(
                new Item(ItemType.ELECTRONIC, "Laptop", 1, 1000.0),
                new Item(ItemType.OTHER, "Mouse", 1, 25.0)
        );
        assertEquals(7.50, extraCost.priceToAggregate(cart), 0.001);
    }

    @Test
    void testEmptyCart() {
        ExtraCostForElectronics extraCost = new ExtraCostForElectronics();
        List<Item> cart = new ArrayList<>();        assertEquals(0.0, extraCost.priceToAggregate(cart), 0.001);
    }

    @Test
    void testOnlyElectronics() {
        ExtraCostForElectronics extraCost = new ExtraCostForElectronics();
        List<Item> cart = List.of(
                new Item(ItemType.ELECTRONIC, "Laptop", 1, 1000.0)
        );
        assertEquals(7.50, extraCost.priceToAggregate(cart), 0.001);
    }
}