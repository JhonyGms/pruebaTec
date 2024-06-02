package com.example.rest.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Cliente extends Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long clienteid;

    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false)
    private String estado;

    private LocalDateTime fecha;


    @OneToMany(mappedBy = "cliente")
    private List<Cuenta> cuentas;
}
