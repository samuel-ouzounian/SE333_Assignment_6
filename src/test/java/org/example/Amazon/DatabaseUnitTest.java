package org.example.Amazon;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseUnitTest {

    private Database database;

    @BeforeEach
    void setUp() {
        database = new Database();
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Test
    void testGetConnection() throws SQLException {
        Connection connection = database.getConnection();
        assertNotNull(connection);
        assertFalse(connection.isClosed());
    }

    @Test
    void testResetDatabase() throws SQLException {
        Connection connection = database.getConnection();
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO shoppingcart (name, type, quantity, priceperunit) VALUES (?, ?, ?, ?)")) {
            ps.setString(1, "Test Item");
            ps.setString(2, "OTHER");
            ps.setInt(3, 1);
            ps.setDouble(4, 10.0);
            ps.executeUpdate();
        }

        database.resetDatabase();

        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM shoppingcart")) {
            ResultSet rs = ps.executeQuery();
            assertFalse(rs.next());
        }
    }

    @Test
    void testClose() throws SQLException {
        Connection connection = database.getConnection();
        database.close();
        assertTrue(connection.isClosed());

    }

    @Test
    void testWithSql() {
        Integer result = database.withSql(() -> 42);
        assertEquals(42, result);

    }

    @Test
    void testWithSqlException() {
        assertThrows(RuntimeException.class, () -> {
            database.withSql(() -> {
                throw new SQLException("Test Exception");
            });
        });
    }

    @Test
    void testDoubleInitialization() {
        assertDoesNotThrow(() -> {
            new Database();
        });
    }
}