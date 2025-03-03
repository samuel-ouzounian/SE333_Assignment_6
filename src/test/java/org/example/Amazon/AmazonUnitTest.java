package org.example.Amazon;

import org.example.Amazon.Cost.PriceRule;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AmazonUnitTest {

    @Test
    void testEmptyCartNoRules() {
        ShoppingCart mockCart = mock(ShoppingCart.class);
        when(mockCart.getItems()).thenReturn(new ArrayList<>());

        List<PriceRule> rules = new ArrayList<>();
        Amazon amazon = new Amazon(mockCart, rules);

        assertEquals(0.0, amazon.calculate(), 0.001);
    }

    @Test
    void testEmptyCartWithRules() {
        ShoppingCart mockCart = mock(ShoppingCart.class);
        when(mockCart.getItems()).thenReturn(new ArrayList<>());

        PriceRule mockRule1 = mock(PriceRule.class);
        when(mockRule1.priceToAggregate(anyList())).thenReturn(5.0);

        List<PriceRule> rules = List.of(mockRule1);
        Amazon amazon = new Amazon(mockCart, rules);

        assertEquals(5.0, amazon.calculate(), 0.001);
    }

    @Test
    void testNonEmptyCartNoRules() {
        ShoppingCart mockCart = mock(ShoppingCart.class);
        List<Item> items = List.of(new Item(null, "item", 1, 1));
        when(mockCart.getItems()).thenReturn(items);

        List<PriceRule> rules = new ArrayList<>();
        Amazon amazon = new Amazon(mockCart, rules);
        assertEquals(0.0, amazon.calculate(), 0.001);
    }

    @Test
    void testNonEmptyCartSingleRule() {
        ShoppingCart mockCart = mock(ShoppingCart.class);
        Item item = new Item(null, "item", 1, 1);
        List<Item> items = List.of(item);
        when(mockCart.getItems()).thenReturn(items);

        PriceRule mockRule = mock(PriceRule.class);
        when(mockRule.priceToAggregate(items)).thenReturn(10.0);

        Amazon amazon = new Amazon(mockCart, List.of(mockRule));
        assertEquals(10.0, amazon.calculate(), 0.001);
    }

    @Test
    void testNonEmptyCartMultipleRules() {
        ShoppingCart mockCart = mock(ShoppingCart.class);
        Item item = new Item(null, "item", 1, 1);
        List<Item> items = List.of(item);
        when(mockCart.getItems()).thenReturn(items);

        PriceRule mockRule1 = mock(PriceRule.class);
        when(mockRule1.priceToAggregate(items)).thenReturn(10.0);
        PriceRule mockRule2 = mock(PriceRule.class);
        when(mockRule2.priceToAggregate(items)).thenReturn(5.0);

        Amazon amazon = new Amazon(mockCart, List.of(mockRule1, mockRule2));
        assertEquals(15.0, amazon.calculate(), 0.001);
    }

    @Test
    void testAddToCart() {
        ShoppingCart mockCart = mock(ShoppingCart.class);
        List<PriceRule> rules = new ArrayList<>();
        Amazon amazon = new Amazon(mockCart, rules);
        Item item = new Item(null, "Test Item", 1, 10.0);

        amazon.addToCart(item);

        verify(mockCart).add(item);
    }

    @Test
    void testNullCart() {
        assertDoesNotThrow(() -> {
            Amazon amazon = new Amazon(null, new ArrayList<>());
            amazon.calculate();
        });
    }


}