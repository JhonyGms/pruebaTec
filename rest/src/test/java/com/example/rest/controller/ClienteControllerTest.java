package com.example.rest.controller;

import com.example.rest.model.Cliente;
import com.example.rest.service.ClienteService;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ClienteControllerTest {


    // getAllCliente should return a list of all Cliente objects
    @Test
    public void test_get_all_cliente_returns_list_of_all_cliente_objects() {
        // Given
        ClienteService clienteService = mock(ClienteService.class);
        ClienteController clienteController = new ClienteController();
        ReflectionTestUtils.setField(clienteController, "clienteService", clienteService);
        List<Cliente> expectedClientes = Arrays.asList(new Cliente(), new Cliente());
        when(clienteService.findAll()).thenReturn(expectedClientes);

        // When
        List<Cliente> actualClientes = clienteController.getAllCliente();

        // Then
        assertEquals(expectedClientes, actualClientes);
    }

    // getClienteById should return a Cliente object when a valid ID is provided
    @Test
    public void test_get_cliente_by_id_returns_cliente_object_when_valid_id_provided() {
        // Given
        ClienteService clienteService = mock(ClienteService.class);
        ClienteController clienteController = new ClienteController();
        ReflectionTestUtils.setField(clienteController, "clienteService", clienteService);
        Cliente expectedCliente = new Cliente();
        when(clienteService.findById(1L)).thenReturn(Optional.of(expectedCliente));

        // When
        ResponseEntity<Cliente> response = clienteController.getClienteById(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCliente, response.getBody());
    }

    // createCliente should save and return a new Cliente object
    @Test
    public void test_create_cliente_saves_and_returns_new_cliente_object() {
        // Given
        ClienteService clienteService = mock(ClienteService.class);
        ClienteController clienteController = new ClienteController();
        ReflectionTestUtils.setField(clienteController, "clienteService", clienteService);
        Cliente newCliente = new Cliente();
        when(clienteService.save(any(Cliente.class))).thenReturn(newCliente);

        // When
        Cliente createdCliente = clienteController.createCliente(newCliente);

        // Then
        assertEquals(newCliente, createdCliente);
    }

    // updateCliente should update and return an existing Cliente object when a valid ID is provided
    @Test
    public void test_update_cliente_updates_and_returns_existing_cliente_object_when_valid_id_provided() {
        // Given
        ClienteService clienteService = mock(ClienteService.class);
        ClienteController clienteController = new ClienteController();
        ReflectionTestUtils.setField(clienteController, "clienteService", clienteService);
        Cliente existingCliente = new Cliente();
        existingCliente.setId(1L);
        when(clienteService.findById(1L)).thenReturn(Optional.of(existingCliente));
        when(clienteService.save(any(Cliente.class))).thenReturn(existingCliente);

        // When
        ResponseEntity<Cliente> response = clienteController.updateCliente(1L, existingCliente);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingCliente, response.getBody());
    }

    // deleteCliente should delete a Cliente object when a valid ID is provided
    @Test
    public void test_delete_cliente_deletes_cliente_object_when_valid_id_provided() {
        // Given
        ClienteService clienteService = mock(ClienteService.class);
        ClienteController clienteController = new ClienteController();
        ReflectionTestUtils.setField(clienteController, "clienteService", clienteService);

        // When
        ResponseEntity<Void> response = clienteController.deleteCliente(1L);

        // Then
        verify(clienteService).deleteById(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    // getClienteById should return 404 when an invalid ID is provided
    @Test
    public void test_get_cliente_by_id_returns_404_when_invalid_id_provided() {
        // Given
        ClienteService clienteService = mock(ClienteService.class);
        ClienteController clienteController = new ClienteController();
        ReflectionTestUtils.setField(clienteController, "clienteService", clienteService);
        when(clienteService.findById(1L)).thenReturn(Optional.empty());

        // When
        ResponseEntity<Cliente> response = clienteController.getClienteById(1L);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // updateCliente should return 404 when an invalid ID is provided
    @Test
    public void test_update_cliente_returns_404_when_invalid_id_provided() {
        // Given
        ClienteService clienteService = mock(ClienteService.class);
        ClienteController clienteController = new ClienteController();
        ReflectionTestUtils.setField(clienteController, "clienteService", clienteService);
        when(clienteService.findById(1L)).thenReturn(Optional.empty());

        // When
        ResponseEntity<Cliente> response = clienteController.updateCliente(1L, new Cliente());

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }







}