package org.example.Barnes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {


    @Test
    @DisplayName("specification-based: Test Book constructor with valid inputs")
    void testBookConstructor_ValidInputs() {
        Book book = new Book("1234567890", 100, 10);
        assertEquals(100, book.getPrice());
        assertEquals(10, book.getQuantity());
    }

    @Test
    @DisplayName("specification-based: Test Book constructor with zero price and quantity")
    void testBookConstructor_ZeroValues() {
        Book book = new Book("1234567890", 0, 0);
        assertEquals(0, book.getPrice());
        assertEquals(0, book.getQuantity());
    }

    @Test
    @DisplayName("specification-based: Test Book equality with same ISBN")
    void testBookEquality_SameISBN() {
        Book book1 = new Book("1234567890", 100, 10);
        Book book2 = new Book("1234567890", 200, 5);
        assertEquals(book1, book2);
    }

    @Test
    @DisplayName("specification-based: Test Book inequality with different ISBNs")
    void testBookEquality_DifferentISBN() {
        Book book1 = new Book("1234567890", 100, 10);
        Book book2 = new Book("0987654321", 100, 10);
        assertNotEquals(book1, book2);
    }

    @Test
    @DisplayName("specification-based: Test hashCode consistency for the same ISBN")
    void testHashCodeConsistency() {
        Book book1 = new Book("1234567890", 100, 10);
        Book book2 = new Book("1234567890", 200, 5);
        assertEquals(book1.hashCode(), book2.hashCode());
    }


    @Test
    @DisplayName("specification-based: Test getPrice method")
    void testGetPrice() {
        Book book = new Book("1234567890", 150, 20);
        assertEquals(150, book.getPrice());
    }

    @Test
    @DisplayName("specification-based: Test getQuantity method")
    void testGetQuantity() {
        Book book = new Book("1234567890", 150, 20);
        assertEquals(20, book.getQuantity());
    }

    @Test
    @DisplayName("specification-based: Test equals method with null input")
    void testEquals_NullInput() {
        Book book = new Book("1234567890", 100, 10);
        assertNotEquals(book, null);
    }

    @Test
    @DisplayName("specification-based: Test equals method with same class")
    void testEquals_SameClass() {
        Book book = new Book("1234567890", 100, 10);
        assertEquals(book, book);
    }

    @Test
    @DisplayName("specification-based: Test equals method with different class input")
    void testEquals_DifferentClassInput() {
        Book book = new Book("1234567890", 100, 10);
        String otherObject = "NotABook";
        assertNotEquals(book, otherObject);
    }

}
