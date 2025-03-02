package org.example.Barnes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseSummaryTest {

    private PurchaseSummary purchaseSummary;
    private Book testBook;

    @BeforeEach
    void setUp() {
        purchaseSummary = new PurchaseSummary();
        testBook = new Book("1234567890", 100, 10);
    }

    @Test
    @DisplayName("specification-based: Test initial state of PurchaseSummary")
    void testInitialState() {
        assertEquals(0, purchaseSummary.getTotalPrice());
        assertTrue(purchaseSummary.getUnavailable().isEmpty());
    }

    @Test
    @DisplayName("specification-based: Test adding unavailable book")
    void testAddUnavailable() {
        purchaseSummary.addUnavailable(testBook, 5);
        Map<Book, Integer> unavailable = purchaseSummary.getUnavailable();
        assertEquals(1, unavailable.size());
        assertTrue(unavailable.containsKey(testBook));
        assertEquals(5, unavailable.get(testBook));
    }

    @Test
    @DisplayName("specification-based: Test adding multiple unavailable books")
    void testAddMultipleUnavailable() {
        Book book1 = new Book("1111111111", 50, 5);
        Book book2 = new Book("2222222222", 75, 8);

        purchaseSummary.addUnavailable(book1, 2);
        purchaseSummary.addUnavailable(book2, 3);

        Map<Book, Integer> unavailable = purchaseSummary.getUnavailable();
        assertEquals(2, unavailable.size());
        assertEquals(2, unavailable.get(book1));
        assertEquals(3, unavailable.get(book2));
    }

    @Test
    @DisplayName("specification-based: Test adding to total price")
    void testAddToTotalPrice() {
        purchaseSummary.addToTotalPrice(100);
        assertEquals(100, purchaseSummary.getTotalPrice());

        purchaseSummary.addToTotalPrice(50);
        assertEquals(150, purchaseSummary.getTotalPrice());
    }

    @Test
    @DisplayName("specification-based: Test adding negative value to total price")
    void testAddNegativeToTotalPrice() {
        purchaseSummary.addToTotalPrice(100);
        purchaseSummary.addToTotalPrice(-50);
        assertEquals(50, purchaseSummary.getTotalPrice());
    }

    @Test
    @DisplayName("specification-based: Test unmodifiable unavailable map")
    void testUnmodifiableUnavailableMap() {
        purchaseSummary.addUnavailable(testBook, 5);
        Map<Book, Integer> unavailable = purchaseSummary.getUnavailable();
        assertThrows(UnsupportedOperationException.class, () -> unavailable.put(new Book("9876543210", 200, 20), 3));
    }

    @Test
    @DisplayName("specification-based: Test adding zero quantity unavailable book")
    void testAddZeroQuantityUnavailable() {
        purchaseSummary.addUnavailable(testBook, 0);
        Map<Book, Integer> unavailable = purchaseSummary.getUnavailable();
        assertTrue(unavailable.containsKey(testBook));
        assertEquals(0, unavailable.get(testBook));
    }

    @Test
    @DisplayName("specification-based: Test adding same book multiple times as unavailable")
    void testAddSameBookMultipleTimesAsUnavailable() {
        purchaseSummary.addUnavailable(testBook, 3);
        purchaseSummary.addUnavailable(testBook, 2);
        Map<Book, Integer> unavailable = purchaseSummary.getUnavailable();
        assertEquals(1, unavailable.size());
        assertEquals(2, unavailable.get(testBook));
    }
}
