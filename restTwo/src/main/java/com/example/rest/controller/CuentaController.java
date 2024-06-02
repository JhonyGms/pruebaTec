package com.example.rest.controller;

import com.example.rest.model.Cuenta;
import com.example.rest.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {
    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public List<Cuenta> getAllCuentas() {
        return cuentaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> getCuentaById(@PathVariable Long id) {
        Optional<Cuenta> cuenta = cuentaService.findById(id);
        return cuenta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cuenta createCuenta(@RequestBody Cuenta cuenta) {
        return cuentaService.save(cuenta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> updateCuenta(@PathVariable Long id, @RequestBody Cuenta cuentaDetails) {
        Optional<Cuenta> optionalCuenta = cuentaService.findById(id);
        if (optionalCuenta.isPresent()) {
            Cuenta cuenta = optionalCuenta.get();
            cuenta.setNumeroCuenta((cuentaDetails.getNumeroCuenta() != null) ? cuentaDetails.getNumeroCuenta() : cuenta.getNumeroCuenta());
            cuenta.setTipoCuenta((cuentaDetails.getTipoCuenta() != null) ? cuentaDetails.getTipoCuenta() : cuenta.getTipoCuenta());
            cuenta.setEstado((cuentaDetails.getEstado() != null) ? cuentaDetails.getEstado() : cuenta.getEstado());
            cuenta.setSaldo((cuentaDetails.getSaldo() != null) ? cuentaDetails.getSaldo() : cuenta.getSaldo());
            return ResponseEntity.ok(cuentaService.save(cuenta));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuenta(@PathVariable Long id) {
        cuentaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
