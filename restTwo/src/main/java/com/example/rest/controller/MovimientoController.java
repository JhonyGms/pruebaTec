package com.example.rest.controller;

import com.example.rest.model.Movimiento;
import com.example.rest.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {
    @Autowired
    private MovimientoService movimientoService;

    @GetMapping
    public List<Movimiento> getAllMovimientos() {
        return movimientoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimiento> getMovimientoById(@PathVariable Long id) {
        Optional<Movimiento> movimiento = movimientoService.findById(id);
        return movimiento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Movimiento createMovimiento(@RequestBody Movimiento movimiento) {
        movimiento.setFecha(LocalDate.EPOCH.atStartOfDay());
        return movimientoService.save(movimiento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movimiento> updateMovimiento(@PathVariable Long id, @RequestBody Movimiento movimientoDetails) {
        Optional<Movimiento> optionalMovimiento = movimientoService.findById(id);
        if (optionalMovimiento.isPresent()) {
            Movimiento movimiento = optionalMovimiento.get();
            movimiento.setTipoMovimiento((movimientoDetails.getTipoMovimiento() != null) ? movimientoDetails.getTipoMovimiento() : movimiento.getTipoMovimiento());
            movimiento.setValor((movimientoDetails.getValor() != null) ? movimientoDetails.getValor() : movimiento.getValor());
            movimiento.setSaldo((movimientoDetails.getSaldo() != null) ? movimientoDetails.getSaldo() : movimiento.getSaldo());

            movimiento.setFecha(LocalDate.EPOCH.atStartOfDay());
            return ResponseEntity.ok(movimientoService.save(movimiento));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovimiento(@PathVariable Long id) {
        try {
            movimientoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // Manejo de la excepción (devolución de error, etc.)
            return null;
        }
    }

    @PostMapping("/{id}/movimientos")
    public Movimiento registrarMovimiento(@PathVariable Long id, @RequestParam Double monto, @RequestParam String tipo) {
        try {
            return movimientoService.registrarMovimiento(id, monto, tipo);
        } catch (Exception e) {
            // Manejo de la excepción (devolución de error, etc.)
            return null;
        }
    }
}