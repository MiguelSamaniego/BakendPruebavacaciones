package com.example.vacaciones.service;

import com.example.vacaciones.dto.AprobarSolicitudReq;
import com.example.vacaciones.dto.VacacionSolicitudReq;
import com.example.vacaciones.model.VacacionSolicitud;
import com.example.vacaciones.repo.VacacionSolicitudRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacacionService {

    private final VacacionSolicitudRepo repo;

    public VacacionService(VacacionSolicitudRepo repo) {
        this.repo = repo;
    }

    public VacacionSolicitud crear(VacacionSolicitudReq dto) {
        VacacionSolicitud v = new VacacionSolicitud();
        v.setEmpleadoId(dto.empleadoId);
        v.setEmpleadoNombre(dto.empleadoNombre);
        v.setFechaInicio(dto.fechaInicio);
        v.setFechaFin(dto.fechaFin);
        v.setDiasSolicitados(dto.diasSolicitados);
        return repo.save(v);
    }

    public List<VacacionSolicitud> listarPorEmpleado(Long empleadoId) {
        return repo.findByEmpleadoIdOrderByIdDesc(empleadoId);
    }

    public List<VacacionSolicitud> listarTodas() {
        return repo.findAll();
    }

    public VacacionSolicitud aprobarORechazar(Long id, AprobarSolicitudReq dto) {
        VacacionSolicitud v = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe solicitud " + id));

        if (dto.fechaInicio != null) v.setFechaInicio(dto.fechaInicio);
        if (dto.fechaFin != null) v.setFechaFin(dto.fechaFin);
        if (dto.diasSolicitados != null) v.setDiasSolicitados(dto.diasSolicitados);

        v.setObservacion(dto.observacion);
        v.setAprobadoPor(dto.aprobadoPor);

        if (dto.aprobar) {
            v.setEstado(VacacionSolicitud.EstadoSolicitud.APROBADA);
        } else {
            v.setEstado(VacacionSolicitud.EstadoSolicitud.RECHAZADA);
        }

        return repo.save(v);
    }
}
