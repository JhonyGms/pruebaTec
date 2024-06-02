package com.example.rest.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.rest.exception.CuentaNoEncontradaException;
import com.example.rest.exception.SaldoInsuficienteException;
import com.example.rest.model.Movimiento;
import com.example.rest.service.MovimientoService;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class MovimientoControllerTest {


    // getAllMovimientos returns a list of all movimientos
    @Test
    public void test_getAllMovimientos_returns_list_of_all_movimientos() {
        // Given
        MovimientoService movimientoService = mock(MovimientoService.class);
        MovimientoController movimientoController = new MovimientoController();
        ReflectionTestUtils.setField(movimientoController, "movimientoService", movimientoService);
        List<Movimiento> expectedMovimientos = Arrays.asList(new Movimiento(), new Movimiento());
        when(movimientoService.findAll()).thenReturn(expectedMovimientos);

        // When
        List<Movimiento> actualMovimientos = movimientoController.getAllMovimientos();

        // Then
        assertEquals(expectedMovimientos, actualMovimientos);
    }

    // getMovimientoById returns a movimiento when it exists
    @Test
    public void test_getMovimientoById_returns_movimiento_when_exists() {
        // Given
        MovimientoService movimientoService = mock(MovimientoService.class);
        MovimientoController movimientoController = new MovimientoController();
        ReflectionTestUtils.setField(movimientoController, "movimientoService", movimientoService);
        Movimiento expectedMovimiento = new Movimiento();
        when(movimientoService.findById(1L)).thenReturn(Optional.of(expectedMovimiento));

        // When
        ResponseEntity<Movimiento> response = movimientoController.getMovimientoById(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedMovimiento, response.getBody());
    }

    // createMovimiento saves and returns a new movimiento
    @Test
    public void test_createMovimiento_saves_and_returns_new_movimiento() {
        // Given
        MovimientoService movimientoService = mock(MovimientoService.class);
        MovimientoController movimientoController = new MovimientoController();
        ReflectionTestUtils.setField(movimientoController, "movimientoService", movimientoService);
        Movimiento newMovimiento = new Movimiento();
        when(movimientoService.save(any(Movimiento.class))).thenReturn(newMovimiento);

        // When
        Movimiento createdMovimiento = movimientoController.createMovimiento(newMovimiento);

        // Then
        assertEquals(newMovimiento, createdMovimiento);
    }

    // updateMovimiento updates and returns an existing movimiento
    @Test
    public void test_updateMovimiento_updates_and_returns_existing_movimiento() {
        // Given
        MovimientoService movimientoService = mock(MovimientoService.class);
        MovimientoController movimientoController = new MovimientoController();
        ReflectionTestUtils.setField(movimientoController, "movimientoService", movimientoService);
        Movimiento existingMovimiento = new Movimiento();
        existingMovimiento.setId(1L);
        when(movimientoService.findById(1L)).thenReturn(Optional.of(existingMovimiento));
        when(movimientoService.save(any(Movimiento.class))).thenReturn(existingMovimiento);

        // When
        ResponseEntity<Movimiento> response = movimientoController.updateMovimiento(1L, existingMovimiento);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingMovimiento, response.getBody());
    }

    // deleteMovimiento removes a movimiento by id
    @Test
    public void test_deleteMovimiento_removes_movimiento_by_id() {
        // Given
        MovimientoService movimientoService = mock(MovimientoService.class);
        MovimientoController movimientoController = new MovimientoController();
        ReflectionTestUtils.setField(movimientoController, "movimientoService", movimientoService);

        // When
        ResponseEntity<Void> response = movimientoController.deleteMovimiento(1L);

        // Then
        verify(movimientoService).deleteById(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    // getMovimientoById returns 404 when movimiento does not exist
    @Test
    public void test_getMovimientoById_returns_404_when_movimiento_does_not_exist() {
        // Given
        MovimientoService movimientoService = mock(MovimientoService.class);
        MovimientoController movimientoController = new MovimientoController();
        ReflectionTestUtils.setField(movimientoController, "movimientoService", movimientoService);
        when(movimientoService.findById(1L)).thenReturn(Optional.empty());

        // When
        ResponseEntity<Movimiento> response = movimientoController.getMovimientoById(1L);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // updateMovimiento returns 404 when movimiento to update does not exist
    @Test
    public void test_updateMovimiento_returns_404_when_movimiento_to_update_does_not_exist() {
        // Given
        MovimientoService movimientoService = mock(MovimientoService.class);
        MovimientoController movimientoController = new MovimientoController();
        ReflectionTestUtils.setField(movimientoController, "movimientoService", movimientoService);
        when(movimientoService.findById(1L)).thenReturn(Optional.empty());

        // When
        ResponseEntity<Movimiento> response = movimientoController.updateMovimiento(1L, new Movimiento());

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // registrarMovimiento throws exception when cuenta does not exist
    @Test(expected = CuentaNoEncontradaException.class)
    public void test_registrarMovimiento_throws_exception_when_cuenta_does_not_exist() {
        // Given
        MovimientoService movimientoService = mock(MovimientoService.class);
        MovimientoController movimientoController = new MovimientoController();
        ReflectionTestUtils.setField(movimientoController, "movimientoService", movimientoService);

        doThrow(new CuentaNoEncontradaException("Cuenta no encontrada")).when(movimientoService).registrarMovimiento(anyLong(), anyDouble(), anyString());

        // When & Then
        movimientoController.registrarMovimiento(1L, 100.0, "CREDITO");
    }

    // registrarMovimiento throws exception when saldo is insufficient for DEBITO
    @Test(expected = SaldoInsuficienteException.class)
    public void test_registrarMovimiento_throws_exception_when_saldo_is_insufficient_for_DEBITO() {
        // Given
        MovimientoService movimientoService = mock(MovimientoService.class);
        MovimientoController movimientoController = new MovimientoController();
        ReflectionTestUtils.setField(movimientoController, "movimientoService", movimientoService);

        doThrow(new SaldoInsuficienteException("Saldo no disponible")).when(movimientoService).registrarMovimiento(anyLong(), anyDouble(), eq("DEBITO"));

        // When & Then
        movimientoController.registrarMovimiento(1L, -100.0, "DEBITO");
    }

}