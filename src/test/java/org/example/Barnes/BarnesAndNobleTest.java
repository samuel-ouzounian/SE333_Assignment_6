package org.example.Barnes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BarnesAndNobleTest {

    private BookDatabase mockBookDatabase;
    private BuyBookProcess mockBuyBookProcess;
    private BarnesAndNoble barnesAndNoble;

    @BeforeEach
    void setUp() {
        mockBookDatabase = mock(BookDatabase.class);
        mockBuyBookProcess = mock(BuyBookProcess.class);
        barnesAndNoble = new BarnesAndNoble(mockBookDatabase, mockBuyBookProcess);
    }


    @Test
    @DisplayName("specification-based: Test getPriceForCart with valid order")
    void testGetPriceForCart_ValidOrder() {
        Book book1 = new Book("1234567890", 100, 10);
        Book book2 = new Book("0987654321", 200, 5);

        when(mockBookDatabase.findByISBN("1234567890")).thenReturn(book1);
        when(mockBookDatabase.findByISBN("0987654321")).thenReturn(book2);

        Map<String, Integer> order = new HashMap<>();
        order.put("1234567890", 3);
        order.put("0987654321", 2);

        PurchaseSummary purchaseSummary = barnesAndNoble.getPriceForCart(order);

        assertNotNull(purchaseSummary);
        assertEquals(700, purchaseSummary.getTotalPrice());
        assertTrue(purchaseSummary.getUnavailable().isEmpty());

        verify(mockBuyBookProcess).buyBook(book1, 3);
        verify(mockBuyBookProcess).buyBook(book2, 2);
    }

    @Test
    @DisplayName("specification-based: Test getPriceForCart with unavailable books")
    void testGetPriceForCart_UnavailableBooks() {
        Book book1 = new Book("1234567890", 100, 2); // Only 2 available
        when(mockBookDatabase.findByISBN("1234567890")).thenReturn(book1);

        Map<String, Integer> order = new HashMap<>();
        order.put("1234567890", 5); // Requesting 5 copies

        PurchaseSummary purchaseSummary = barnesAndNoble.getPriceForCart(order);

        assertNotNull(purchaseSummary);
        assertEquals(200, purchaseSummary.getTotalPrice());
        assertEquals(1, purchaseSummary.getUnavailable().size());
        assertEquals(3, purchaseSummary.getUnavailable().get(book1));

        verify(mockBuyBookProcess).buyBook(book1, 2);
    }

    @Test
    @DisplayName("specification-based: Test getPriceForCart with null order")
    void testGetPriceForCart_NullOrder() {
        PurchaseSummary purchaseSummary = barnesAndNoble.getPriceForCart(null);
        assertNull(purchaseSummary);
    }

    @Test
    @DisplayName("specification-based: Test getPriceForCart with empty order")
    void testGetPriceForCart_EmptyOrder() {
        Map<String, Integer> order = new HashMap<>();
        PurchaseSummary purchaseSummary = barnesAndNoble.getPriceForCart(order);

        assertNotNull(purchaseSummary);
        assertEquals(0, purchaseSummary.getTotalPrice());
        assertTrue(purchaseSummary.getUnavailable().isEmpty());

        verifyNoInteractions(mockBuyBookProcess);
    }



    @Test
    @DisplayName("specification-based: Test retrieveBook with sufficient stock")
    void testRetrieveBook_SufficientStock() throws Exception {
        Book book = new Book("1234567890", 100, 10);
        when(mockBookDatabase.findByISBN("1234567890")).thenReturn(book);

        PurchaseSummary purchaseSummary = new PurchaseSummary();

        var retrieveMethod = BarnesAndNoble.class.getDeclaredMethod(
                "retrieveBook", String.class, int.class, PurchaseSummary.class);
        retrieveMethod.setAccessible(true);

        retrieveMethod.invoke(barnesAndNoble, "1234567890", 5, purchaseSummary);

        assertEquals(500, purchaseSummary.getTotalPrice());
        assertTrue(purchaseSummary.getUnavailable().isEmpty());

        verify(mockBuyBookProcess).buyBook(book, 5);
    }

    @Test
    @DisplayName("specification-based: Test retrieveBook with insufficient stock")
    void testRetrieveBook_InsufficientStock() throws Exception {
        Book book = new Book("1234567890", 100, 3);
        when(mockBookDatabase.findByISBN("1234567890")).thenReturn(book);

        PurchaseSummary purchaseSummary = new PurchaseSummary();

        var retrieveMethod = BarnesAndNoble.class.getDeclaredMethod(
                "retrieveBook", String.class, int.class, PurchaseSummary.class);
        retrieveMethod.setAccessible(true);

        retrieveMethod.invoke(barnesAndNoble, "1234567890", 5, purchaseSummary);

        assertEquals(300, purchaseSummary.getTotalPrice()); // (3 * 100)
        assertEquals(1, purchaseSummary.getUnavailable().size());
        assertEquals(2, purchaseSummary.getUnavailable().get(book));

        verify(mockBuyBookProcess).buyBook(book, 3);
    }

    @Test
    @DisplayName("specification-based: Test retrieveBook for non-existent ISBN")
    void testRetrieveBook_NonExistentISBN() throws Exception {
        when(mockBookDatabase.findByISBN("0000000000")).thenReturn(null);

        PurchaseSummary purchaseSummary = new PurchaseSummary();

        var retrieveMethod = BarnesAndNoble.class.getDeclaredMethod(
                "retrieveBook", String.class, int.class, PurchaseSummary.class);

        retrieveMethod.setAccessible(true);

        Exception exception = assertThrows(InvocationTargetException.class, () -> {
            retrieveMethod.invoke(barnesAndNoble, "0000000000", 1, purchaseSummary);
        });

        assertTrue(exception.getCause() instanceof NullPointerException);
        assertEquals(0, purchaseSummary.getTotalPrice());
        assertTrue(purchaseSummary.getUnavailable().isEmpty());

        verify(mockBuyBookProcess, never()).buyBook(any(), anyInt());
    }

    @Test
    @DisplayName("specification-based: Test getPriceForCart with multiple books, some unavailable")
    void testGetPriceForCart_MultipleBooks_SomeUnavailable() {
        Book book1 = new Book("1111111111", 100, 5);
        Book book2 = new Book("2222222222", 200, 2);
        Book book3 = new Book("3333333333", 150, 10);

        when(mockBookDatabase.findByISBN("1111111111")).thenReturn(book1);
        when(mockBookDatabase.findByISBN("2222222222")).thenReturn(book2);
        when(mockBookDatabase.findByISBN("3333333333")).thenReturn(book3);

        Map<String, Integer> order = new HashMap<>();
        order.put("1111111111", 3);
        order.put("2222222222", 5);
        order.put("3333333333", 2);

        PurchaseSummary purchaseSummary = barnesAndNoble.getPriceForCart(order);

        assertNotNull(purchaseSummary);
        assertEquals(1000, purchaseSummary.getTotalPrice());
        assertEquals(1, purchaseSummary.getUnavailable().size());
        assertEquals(3, purchaseSummary.getUnavailable().get(book2));

        verify(mockBuyBookProcess).buyBook(book1, 3);
        verify(mockBuyBookProcess).buyBook(book2, 2);
        verify(mockBuyBookProcess).buyBook(book3, 2);
    }

    @Test
    @DisplayName("specification-based: Test getPriceForCart with zero quantity order")
    void testGetPriceForCart_ZeroQuantityOrder() {
        Book book = new Book("1234567890", 100, 10);
        when(mockBookDatabase.findByISBN("1234567890")).thenReturn(book);

        Map<String, Integer> order = new HashMap<>();
        order.put("1234567890", 0);

        PurchaseSummary purchaseSummary = barnesAndNoble.getPriceForCart(order);

        assertNotNull(purchaseSummary);
        assertEquals(0, purchaseSummary.getTotalPrice());
        assertTrue(purchaseSummary.getUnavailable().isEmpty());
    }
}
