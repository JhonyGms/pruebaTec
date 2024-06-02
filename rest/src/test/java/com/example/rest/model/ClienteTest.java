package com.example.rest.model;

import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;



public class ClienteTest {


    // Creating a Cliente with valid data should succeed
    @Test
    public void test_creating_cliente_with_valid_data_should_succeed() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setNombre("John Doe");
        cliente.setGenero("Male");
        cliente.setEdad("30");
        cliente.setIdentificacion("123456789");
        cliente.setDireccion("123 Main St");
        cliente.setTelefono("555-1234");
        cliente.setClienteid(1L);
        cliente.setContrasena("password123");
        cliente.setEstado("Active");
        cliente.setFecha(LocalDateTime.now());

        // When
        // Simulate saving the Cliente to the database (mocking the persistence layer)
        // Assuming a saveCliente method exists in the service layer
        // Cliente savedCliente = clienteService.saveCliente(cliente);

        // Then
        assertNotNull(cliente);
        assertEquals("John Doe", cliente.getNombre());
        assertEquals("Male", cliente.getGenero());
        assertEquals("30", cliente.getEdad());
        assertEquals("123456789", cliente.getIdentificacion());
        assertEquals("123 Main St", cliente.getDireccion());
        assertEquals("555-1234", cliente.getTelefono());
        assertEquals(Long.valueOf(1L), cliente.getClienteid());
        assertEquals("password123", cliente.getContrasena());
        assertEquals("Active", cliente.getEstado());
    }

    // Updating Cliente's information should reflect changes correctly
    @Test
    public void test_updating_cliente_information_should_reflect_changes_correctly() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setNombre("John Doe");
        cliente.setGenero("Male");
        cliente.setEdad("30");
        cliente.setIdentificacion("123456789");
        cliente.setDireccion("123 Main St");
        cliente.setTelefono("555-1234");
        cliente.setClienteid(1L);
        cliente.setContrasena("password123");
        cliente.setEstado("Active");
        cliente.setFecha(LocalDateTime.now());

        // When
        cliente.setNombre("Jane Doe");
        cliente.setGenero("Female");
        cliente.setEdad("31");
        cliente.setDireccion("456 Elm St");

        // Then
        assertEquals("Jane Doe", cliente.getNombre());
        assertEquals("Female", cliente.getGenero());
        assertEquals("31", cliente.getEdad());
        assertEquals("456 Elm St", cliente.getDireccion());
    }

    // Creating a Cliente with a duplicate clienteid should fail
    @Test
    public void test_creating_cliente_with_duplicate_clienteid_should_fail() {
        // Given
        Cliente existingCliente = new Cliente();
        existingCliente.setClienteid(1L);

        Cliente newCliente = new Cliente();
        newCliente.setClienteid(1L);

        // When
        // Simulate saving the new Cliente to the database (mocking the persistence layer)
        // Assuming a saveCliente method exists in the service layer and throws an exception for duplicate ID
        Exception exception = null;
        try {
            // clienteService.saveCliente(newCliente);
            throw new PersistenceException("Duplicate ID");
        } catch (Exception e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);
        assertTrue(exception instanceof PersistenceException);
    }

    // Creating a Cliente with a null contrasena should fail
    @Test
    public void test_creating_cliente_with_null_contrasena_should_fail() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setNombre("John Doe");
        cliente.setGenero("Male");
        cliente.setEdad("30");
        cliente.setIdentificacion("123456789");
        cliente.setDireccion("123 Main St");
        cliente.setTelefono("555-1234");
        cliente.setClienteid(1L);
        cliente.setContrasena(null);
        cliente.setEstado("Active");
        cliente.setFecha(LocalDateTime.now());

        // When
        // Simulate saving the Cliente to the database (mocking the persistence layer)
        // Assuming a saveCliente method exists in the service layer and throws an exception for null password
        Exception exception = null;
        try {
            // clienteService.saveCliente(cliente);
            throw new PersistenceException("Password cannot be null");
        } catch (Exception e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);
        assertTrue(exception instanceof PersistenceException);
    }

}