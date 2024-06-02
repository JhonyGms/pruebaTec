package com.example.rest.model;

import lombok.Data;

import java.time.LocalDate;
@Data
public class MovimientoReporte {
    private Long movimientoId;
    private Double monto;
    private String tipo;
    private LocalDate  fecha;

    public MovimientoReporte(Movimiento movimiento) {
        this.movimientoId = movimiento.getId();
        this.monto = movimiento.getValor();
        this.tipo = movimiento.getTipoMovimiento();
        this.fecha = movimiento.getFecha();
    }
}
