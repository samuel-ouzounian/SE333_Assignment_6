package org.example.Amazon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ShoppingCartAdaptorUnitTest {

    @Mock
    private Database mockDatabase;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    private ShoppingCartAdaptor shoppingCartAdaptor;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(mockDatabase.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        shoppingCartAdaptor = new ShoppingCartAdaptor(mockDatabase);
    }

    @Test
    void testNumberOfItems_EmptyResultSet() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);
        when(mockDatabase.withSql(any())).thenAnswer(invocation -> {
            Database.SqlSupplier<?> supplier = invocation.getArgument(0);
            return supplier.doSql();
        });

        int result = shoppingCartAdaptor.numberOfItems();

        assertEquals(0, result);
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet).next();
    }

    @Test
    void testNumberOfItems_NonEmptyResultSet() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("count")).thenReturn(5);
        when(mockDatabase.withSql(any())).thenAnswer(invocation -> {
            Database.SqlSupplier<?> supplier = invocation.getArgument(0);
            return supplier.doSql();
        });

        int result = shoppingCartAdaptor.numberOfItems();

        assertEquals(5, result);
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet).next();
        verify(mockResultSet).getInt("count");
    }
}
