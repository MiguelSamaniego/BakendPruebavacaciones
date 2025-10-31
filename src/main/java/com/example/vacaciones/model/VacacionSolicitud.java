package com.example.vacaciones.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vacacion_solicitud")
public class VacacionSolicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long empleadoId;

    private String empleadoNombre;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private Integer diasSolicitados;

    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;

    @Column(length = 500)
    private String observacion;

    private Long aprobadoPor;

    private LocalDate fechaRegistro = LocalDate.now();

    public enum EstadoSolicitud {
        PENDIENTE, APROBADA, RECHAZADA
    }

    public Long getId() {
        return id;
    }

    public Long getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId = empleadoId;
    }

    public String getEmpleadoNombre() {
        return empleadoNombre;
    }

    public void setEmpleadoNombre(String empleadoNombre) {
        this.empleadoNombre = empleadoNombre;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getDiasSolicitados() {
        return diasSolicitados;
    }

    public void setDiasSolicitados(Integer diasSolicitados) {
        this.diasSolicitados = diasSolicitados;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Long getAprobadoPor() {
        return aprobadoPor;
    }

    public void setAprobadoPor(Long aprobadoPor) {
        this.aprobadoPor = aprobadoPor;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
