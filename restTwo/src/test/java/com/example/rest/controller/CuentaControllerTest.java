package com.example.rest.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import com.example.rest.model.Cuenta;
import com.example.rest.service.CuentaService;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class CuentaControllerTest {


    // Retrieve all cuentas successfully
    @Test
    public void test_retrieve_all_cuentas_successfully() {
        // Given
        CuentaService cuentaService = mock(CuentaService.class);
        CuentaController cuentaController = new CuentaController();
        ReflectionTestUtils.setField(cuentaController, "cuentaService", cuentaService);
        List<Cuenta> cuentas = Arrays.asList(new Cuenta(), new Cuenta());
        when(cuentaService.findAll()).thenReturn(cuentas);

        // When
        List<Cuenta> result = cuentaController.getAllCuentas();

        // Then
        assertEquals(2, result.size());
        verify(cuentaService, times(1)).findAll();
    }

    // Retrieve a cuenta by valid ID
    @Test
    public void test_retrieve_cuenta_by_valid_id() {
        // Given
        CuentaService cuentaService = mock(CuentaService.class);
        CuentaController cuentaController = new CuentaController();
        ReflectionTestUtils.setField(cuentaController, "cuentaService", cuentaService);
        Cuenta cuenta = new Cuenta();
        when(cuentaService.findById(1L)).thenReturn(Optional.of(cuenta));

        // When
        ResponseEntity<Cuenta> response = cuentaController.getCuentaById(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cuenta, response.getBody());
        verify(cuentaService, times(1)).findById(1L);
    }

    // Create a new cuenta with valid data
    @Test
    public void test_create_new_cuenta_with_valid_data() {
        // Given
        CuentaService cuentaService = mock(CuentaService.class);
        CuentaController cuentaController = new CuentaController();
        ReflectionTestUtils.setField(cuentaController, "cuentaService", cuentaService);
        Cuenta cuenta = new Cuenta();
        when(cuentaService.save(any(Cuenta.class))).thenReturn(cuenta);

        // When
        Cuenta result = cuentaController.createCuenta(cuenta);

        // Then
        assertEquals(cuenta, result);
        verify(cuentaService, times(1)).save(cuenta);
    }

    // Update an existing cuenta with valid data
    @Test
    public void test_update_existing_cuenta_with_valid_data() {
        // Given
        CuentaService cuentaService = mock(CuentaService.class);
        CuentaController cuentaController = new CuentaController();
        ReflectionTestUtils.setField(cuentaController, "cuentaService", cuentaService);
        Cuenta existingCuenta = new Cuenta();
        existingCuenta.setId(1L);
        when(cuentaService.findById(1L)).thenReturn(Optional.of(existingCuenta));
        when(cuentaService.save(any(Cuenta.class))).thenReturn(existingCuenta);

        // When
        ResponseEntity<Cuenta> response = cuentaController.updateCuenta(1L, existingCuenta);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingCuenta, response.getBody());
        verify(cuentaService, times(1)).findById(1L);
        verify(cuentaService, times(1)).save(existingCuenta);
    }

    // Delete a cuenta by valid ID
    @Test
    public void test_delete_cuenta_by_valid_id() {
        // Given
        CuentaService cuentaService = mock(CuentaService.class);
        CuentaController cuentaController = new CuentaController();
        ReflectionTestUtils.setField(cuentaController, "cuentaService", cuentaService);

        // When
        ResponseEntity<Void> response = cuentaController.deleteCuenta(1L);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(cuentaService, times(1)).deleteById(1L);
    }

    // Retrieve a cuenta by non-existent ID
    @Test
    public void test_retrieve_cuenta_by_non_existent_id() {
        // Given
        CuentaService cuentaService = mock(CuentaService.class);
        CuentaController cuentaController = new CuentaController();
        ReflectionTestUtils.setField(cuentaController, "cuentaService", cuentaService);
        when(cuentaService.findById(1L)).thenReturn(Optional.empty());

        // When
        ResponseEntity<Cuenta> response = cuentaController.getCuentaById(1L);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(cuentaService, times(1)).findById(1L);
    }

    // Update a cuenta with non-existent ID
    @Test
    public void test_update_cuenta_with_non_existent_id() {
        // Given
        CuentaService cuentaService = mock(CuentaService.class);
        CuentaController cuentaController = new CuentaController();
        ReflectionTestUtils.setField(cuentaController, "cuentaService", cuentaService);
        when(cuentaService.findById(1L)).thenReturn(Optional.empty());

        // When
        ResponseEntity<Cuenta> response = cuentaController.updateCuenta(1L, new Cuenta());

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(cuentaService, times(1)).findById(1L);
    }


}