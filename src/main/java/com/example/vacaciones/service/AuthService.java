package com.example.vacaciones.service;

import com.example.vacaciones.dto.LoginRequest;
import com.example.vacaciones.dto.LoginResponse;
import com.example.vacaciones.dto.RegisterRequest;
import com.example.vacaciones.model.Usuario;
import com.example.vacaciones.repo.UsuarioRepo;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepo usuarioRepo;

    public AuthService(UsuarioRepo usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    public LoginResponse login(LoginRequest req) {
        Usuario u = usuarioRepo.findByUsername(req.username)
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));
        if (!u.getPassword().equals(req.password)) {
            throw new RuntimeException("Credenciales inválidas");
        }
        return toLoginResponse(u);
    }

    public LoginResponse register(RegisterRequest req) {
        // 1. validar que no exista
        usuarioRepo.findByUsername(req.username).ifPresent(u -> {
            throw new RuntimeException("El usuario ya existe");
        });

        // 2. crear
        Usuario nuevo = new Usuario();
        nuevo.setUsername(req.username);
        nuevo.setPassword(req.password); // en real encriptar
        nuevo.setNombre(req.nombre);
        // si no envía rol, por defecto EMPLOYEE
        nuevo.setRol(req.rol != null ? req.rol : "EMPLOYEE");

        Usuario guardado = usuarioRepo.save(nuevo);

        // 3. devolverlo como si fuera login
        return toLoginResponse(guardado);
    }

    private LoginResponse toLoginResponse(Usuario u) {
        String fakeToken = "TOKEN-" + u.getUsername();
        return new LoginResponse(
                u.getId(),
                u.getNombre(),
                u.getUsername(),
                u.getRol(),
                fakeToken
        );
    }
}
