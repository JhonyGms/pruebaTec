package com.example.rest.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import com.example.rest.model.Cliente;
import com.example.rest.repository.ClienteRepository;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ClienteServiceTest {


    // findAll returns a list of all Cliente objects
    @Test
    public void test_findAll_returns_all_Cliente_objects() {
        // Given
        ClienteRepository clienteRepository = mock(ClienteRepository.class);
        ClienteService clienteService = new ClienteService();
        ReflectionTestUtils.setField(clienteService, "clienteRepository", clienteRepository);
        List<Cliente> expectedClientes = Arrays.asList(new Cliente(), new Cliente());
        when(clienteRepository.findAll()).thenReturn(expectedClientes);

        // When
        List<Cliente> actualClientes = clienteService.findAll();

        // Then
        assertEquals(expectedClientes, actualClientes);
    }

    // findById returns a Cliente object when a valid ID is provided
    @Test
    public void test_findById_returns_Cliente_object_for_valid_ID() {
        // Given
        ClienteRepository clienteRepository = mock(ClienteRepository.class);
        ClienteService clienteService = new ClienteService();
        ReflectionTestUtils.setField(clienteService, "clienteRepository", clienteRepository);
        Long validId = 1L;
        Cliente expectedCliente = new Cliente();
        when(clienteRepository.findById(validId)).thenReturn(Optional.of(expectedCliente));

        // When
        Optional<Cliente> actualCliente = clienteService.findById(validId);

        // Then
        assertTrue(actualCliente.isPresent());
        assertEquals(expectedCliente, actualCliente.get());
    }

    // save throws an exception when trying to save a Cliente with a duplicate clienteid
    @Test
    public void test_save_throws_exception_for_duplicate_clienteid() {
        // Given
        ClienteRepository clienteRepository = mock(ClienteRepository.class);
        ClienteService clienteService = new ClienteService();
        ReflectionTestUtils.setField(clienteService, "clienteRepository", clienteRepository);
        Cliente cliente = new Cliente();
        cliente.setClienteid(1L);
        when(clienteRepository.save(any(Cliente.class))).thenThrow(new DataIntegrityViolationException("Duplicate clienteid"));

        // When
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            clienteService.save(cliente);
        });

        // Then
        assertEquals("Duplicate clienteid", exception.getMessage());
    }

    // save throws an exception when trying to save a Cliente with null fields marked as non-nullable
    @Test
    public void test_save_throws_exception_for_null_non_nullable_fields() {
        // Given
        ClienteRepository clienteRepository = mock(ClienteRepository.class);
        ClienteService clienteService = new ClienteService();
        ReflectionTestUtils.setField(clienteService, "clienteRepository", clienteRepository);
        Cliente cliente = new Cliente();
        when(clienteRepository.save(any(Cliente.class))).thenThrow(new DataIntegrityViolationException("Null non-nullable fields"));

        // When
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            clienteService.save(cliente);
        });

        // Then
        assertEquals("Null non-nullable fields", exception.getMessage());
    }

}