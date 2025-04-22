package com.tiendaOctavius.domain;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Mantenimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String correo;
    private LocalDateTime fecha;
    private String descripcion;

    // Constructor por defecto
    public Mantenimiento() {}

    // Constructor con parámetros
    public Mantenimiento(String nombre, String correo, LocalDateTime fecha, String descripcion) {
        this.nombre = nombre;
        this.correo = correo;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
