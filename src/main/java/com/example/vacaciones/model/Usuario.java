package com.example.vacaciones.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // por ejemplo: "mperez"
    @Column(unique = true, nullable = false)
    private String username;

    // nombre completo: "Miguel Samaniego"
    private String nombre;

    // OJO: en real va encriptado, aquí lo dejamos plano para que sea fácil probar
    private String password;

    // "EMPLOYEE" o "TH"
    private String rol;

    public Usuario() {
    }

    public Usuario(String username, String nombre, String password, String rol) {
        this.username = username;
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
