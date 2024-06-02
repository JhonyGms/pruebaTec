package com.example.rest.service;

import com.example.rest.model.Cuenta;
import com.example.rest.model.CuentaReporte;
import com.example.rest.model.Movimiento;
import com.example.rest.model.ReporteEstadoCuenta;
import com.example.rest.repository.CuentaRepository;
import com.example.rest.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReporteService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Transactional
    public ReporteEstadoCuenta generarReporte(Long clienteId, LocalDate  fechaInicio, LocalDate  fechaFin) {
        List<Cuenta> cuentas = cuentaRepository.findAll();
        ReporteEstadoCuenta reporte = new ReporteEstadoCuenta();

        for (Cuenta cuenta : cuentas) {
            List<Movimiento> movimientos = movimientoRepository.findAllByCuentaIdAndFechaBetween(cuenta.getId(), fechaInicio, fechaFin);
            CuentaReporte cuentaReporte = new CuentaReporte(cuenta, movimientos);
            reporte.addCuentaReporte(cuentaReporte);
        }

        return reporte;
    }
}
