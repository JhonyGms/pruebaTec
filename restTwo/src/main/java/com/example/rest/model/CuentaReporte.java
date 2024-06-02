package com.example.rest.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class CuentaReporte {
    private Long cuentaId;
    private String numeroCuenta;
    private Double saldo;
    private List<MovimientoReporte> movimientos;

    public CuentaReporte(Cuenta cuenta, List<Movimiento> movimientos) {
        this.cuentaId = cuenta.getId();
        this.numeroCuenta = cuenta.getNumeroCuenta();
        this.saldo = cuenta.getSaldo();
        this.movimientos = new ArrayList<>();
        for (Movimiento movimiento : movimientos) {
            this.movimientos.add(new MovimientoReporte(movimiento));
        }
    }
}
