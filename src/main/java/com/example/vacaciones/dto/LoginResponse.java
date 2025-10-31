package com.example.vacaciones.dto;

public class LoginResponse {
    public Long id;
    public String nombre;
    public String username;
    public String rol;
    public String token; // opcional, por ahora lo dejamos fijo o simulado

    public LoginResponse(Long id, String nombre, String username, String rol, String token) {
        this.id = id;
        this.nombre = nombre;
        this.username = username;
        this.rol = rol;
        this.token = token;
    }
}
