package com.example.vacaciones.repo;

import com.example.vacaciones.model.VacacionSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacacionSolicitudRepo extends JpaRepository<VacacionSolicitud, Long> {

    List<VacacionSolicitud> findByEmpleadoIdOrderByIdDesc(Long empleadoId);
}
