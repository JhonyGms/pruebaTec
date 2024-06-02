package com.example.rest.service;

import com.example.rest.exception.CuentaNoEncontradaException;
import com.example.rest.exception.SaldoInsuficienteException;
import com.example.rest.model.Cuenta;
import com.example.rest.model.Movimiento;
import com.example.rest.repository.CuentaRepository;
import com.example.rest.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MovimientoService {
    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    public List<Movimiento> findAll() {
        return movimientoRepository.findAll();
    }

    public Optional<Movimiento> findById(Long id) {
        return movimientoRepository.findById(id);
    }

    public Movimiento save(Movimiento movimiento) {
        return movimientoRepository.save(movimiento);
    }

    public void deleteById(Long id) {
        movimientoRepository.deleteById(id);
    }

    @Transactional
    public Movimiento registrarMovimiento(Long cuentaId, Double monto, String tipo) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId).orElseThrow(() -> new CuentaNoEncontradaException("Cuenta no encontrada"));

        if (tipo.equals("DEBITO") && cuenta.getSaldo() + monto < 0) {
            throw new SaldoInsuficienteException("Saldo no disponible");
        }
            cuenta.setSaldo(cuenta.getSaldo() + monto);
            cuentaRepository.save(cuenta);

            Movimiento movimiento = new Movimiento();
            movimiento.setCuenta(cuenta);
            movimiento.setTipoMovimiento(tipo);
            movimiento.setValor(monto);
            movimiento.setSaldo(cuenta.getSaldo() + monto);

            return movimientoRepository.save(movimiento);


    }

}