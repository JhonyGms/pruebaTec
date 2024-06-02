package com.example.rest.repository;

import com.example.rest.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findAllByCuentaIdAndFechaBetween(Long cuentaId, LocalDate fechaInicio, LocalDate  fechaFin);
}