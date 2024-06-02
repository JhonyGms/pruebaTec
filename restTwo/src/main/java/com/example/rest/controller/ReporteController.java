package com.example.rest.controller;


import com.example.rest.model.CuentaReporte;
import com.example.rest.model.ReporteEstadoCuenta;
import com.example.rest.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;
    @PostMapping
    public List<CuentaReporte> generarReporte(
            @RequestParam Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate  fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate  fechaFin) {

        return reporteService.generarReporte(clienteId, fechaInicio, fechaFin).getCuentas();

    }
}