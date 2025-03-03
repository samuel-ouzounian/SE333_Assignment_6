package org.example.Amazon.Cost;

import org.example.Amazon.Item;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegularCostUnitTest {

    @Test
    void testEmptyCart() {
        RegularCost regularCost = new RegularCost();
        List<Item> cart = new ArrayList<>();
        assertEquals(0.0, regularCost.priceToAggregate(cart), 0.001);
    }

    @Test
    void testSingleItemSingleQuantity() {
        RegularCost regularCost = new RegularCost();
        List<Item> cart = List.of(new Item(ItemType.OTHER, "Book", 1, 10.0));
        assertEquals(10.0, regularCost.priceToAggregate(cart), 0.001);
    }

    @Test
    void testSingleItemMultipleQuantity() {
        RegularCost regularCost = new RegularCost();
        List<Item> cart = List.of(new Item(ItemType.OTHER, "T-Shirt", 3, 15.0));
        assertEquals(45.0, regularCost.priceToAggregate(cart), 0.001);
    }

    @Test
    void testMultipleItems() {
        RegularCost regularCost = new RegularCost();
        List<Item> cart = List.of(
                new Item(ItemType.OTHER, "Book", 1, 10.0),
                new Item(ItemType.OTHER, "T-Shirt", 2, 15.0),
                new Item(ItemType.ELECTRONIC, "Mouse", 1, 25.0)
        );
        assertEquals(65.0, regularCost.priceToAggregate(cart), 0.001);
    }

    @Test
    void testItemWithZeroPrice() {
        RegularCost regularCost = new RegularCost();
        List<Item> cart = List.of(new Item(ItemType.OTHER, "Free Item", 1, 0.0));
        assertEquals(0.0, regularCost.priceToAggregate(cart), 0.001);
    }

    @Test
    void testItemWithZeroQuantity() {
        RegularCost regularCost = new RegularCost();
        List<Item> cart = List.of(new Item(ItemType.OTHER, "Out of Stock Item", 0, 10.0));
        assertEquals(0.0, regularCost.priceToAggregate(cart), 0.001);
    }

    @Test
    void testItemWithFractionalPrice() {
        RegularCost regularCost = new RegularCost();
        List<Item> cart = List.of(new Item(ItemType.OTHER, "Cheap Item", 1, 0.5));
        assertEquals(0.5, regularCost.priceToAggregate(cart), 0.001);
    }
}