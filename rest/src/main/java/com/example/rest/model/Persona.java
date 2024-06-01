package com.example.rest.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;


@Data
@MappedSuperclass
public class Persona {

    private Long id;
    private String nombre;
    private String genero;
    private String edad;
    private String identificacion;
    private String direccion;
    private String telefono;
}