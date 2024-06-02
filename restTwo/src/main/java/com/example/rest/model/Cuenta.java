package com.example.rest.model;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Data
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numeroCuenta;

    @Column(nullable = false)
    private String tipoCuenta;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private Double saldo = 0.0;
}
