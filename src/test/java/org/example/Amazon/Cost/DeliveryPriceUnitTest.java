package org.example.Amazon.Cost;

import org.example.Amazon.Item;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DeliveryPriceUnitTest {
    @ParameterizedTest(name = "Cart with {0} items should cost {1}")
    @MethodSource("cartSizeAndDeliveryPrice")
    void testDeliveryPrice(int itemCount, double expectedPrice) {
        DeliveryPrice deliveryPrice = new DeliveryPrice();
        List<Item> cart = createCartWithItems(itemCount);
        assertEquals(expectedPrice, deliveryPrice.priceToAggregate(cart), 0.001);
    }

    private static Stream<Arguments> cartSizeAndDeliveryPrice() {
        return Stream.of(
                Arguments.of(0, 0.0),
                Arguments.of(1, 5.0),
                Arguments.of(3, 5.0),
                Arguments.of(4, 12.5),
                Arguments.of(10, 12.5),
                Arguments.of(11, 20.0),

                Arguments.of(2, 5.0),
                Arguments.of(7, 12.5)
        );
    }

    private List<Item> createCartWithItems(int numberOfItems) {
        if (numberOfItems < 0)
            return new ArrayList<>();
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < numberOfItems; i++) {
            items.add(new Item(ItemType.OTHER, "Item " + i, 1, 1.0));
        }
        return items;
    }
}