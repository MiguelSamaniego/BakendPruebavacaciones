package com.example.vacaciones.config;

import com.example.vacaciones.model.Usuario;
import com.example.vacaciones.repo.UsuarioRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initUsers(UsuarioRepo usuarioRepo) {
        return args -> {
            if (usuarioRepo.count() == 0) {
                // empleado normal
                usuarioRepo.save(new Usuario(
                        "empleado1",
                        "Juan Pérez",
                        "1234",
                        "EMPLOYEE"
                ));

                // talento humano
                usuarioRepo.save(new Usuario(
                        "th1",
                        "María Talento",
                        "1234",
                        "TH"
                ));
            }
        };
    }
}
