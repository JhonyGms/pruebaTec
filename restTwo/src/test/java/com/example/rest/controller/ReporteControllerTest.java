package com.example.rest.controller;

import com.example.rest.model.*;
import com.example.rest.service.ReporteService;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReporteControllerTest {



    // Generating a report with a clienteId that does not exist returns an empty list
    @Test
    public void test_generating_report_with_nonexistent_clienteid_returns_empty_list() {
        // Given
        ReporteService reporteService = mock(ReporteService.class);
        ReporteController reporteController = new ReporteController();
        ReflectionTestUtils.setField(reporteController, "reporteService", reporteService);
        Long clienteId = 999L;
        LocalDate fechaInicio = LocalDate.of(2023, 1, 1);
        LocalDate fechaFin = LocalDate.of(2023, 12, 31);
        when(reporteService.generarReporte(clienteId, fechaInicio, fechaFin)).thenReturn(new ReporteEstadoCuenta());

        // When
        List<CuentaReporte> actualReport = reporteController.generarReporte(clienteId, fechaInicio, fechaFin);

        // Then
        assertTrue(actualReport.isEmpty());
    }

    // Generating a report with fechaInicio after fechaFin returns an error or empty list
    @Test
    public void test_generating_report_with_fechaInicio_after_fechaFin_returns_error_or_empty_list() {
        // Given
        ReporteService reporteService = mock(ReporteService.class);
        ReporteController reporteController = new ReporteController();
        ReflectionTestUtils.setField(reporteController, "reporteService", reporteService);
        Long clienteId = 1L;
        LocalDate fechaInicio = LocalDate.of(2023, 12, 31);
        LocalDate fechaFin = LocalDate.of(2023, 1, 1);
        when(reporteService.generarReporte(clienteId, fechaInicio, fechaFin)).thenReturn(new ReporteEstadoCuenta());

        // When
        List<CuentaReporte> actualReport = reporteController.generarReporte(clienteId, fechaInicio, fechaFin);

        // Then
        assertTrue(actualReport.isEmpty());
    }

}