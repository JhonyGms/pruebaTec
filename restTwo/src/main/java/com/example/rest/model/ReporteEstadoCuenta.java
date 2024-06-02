package com.example.rest.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReporteEstadoCuenta {

    private List<CuentaReporte> cuentas;

    public ReporteEstadoCuenta() {
        this.cuentas = new ArrayList<>();
    }

    public void addCuentaReporte(CuentaReporte cuentaReporte) {
        this.cuentas.add(cuentaReporte);
    }
}
