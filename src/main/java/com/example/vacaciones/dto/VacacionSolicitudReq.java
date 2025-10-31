package com.example.vacaciones.dto;

import java.time.LocalDate;

public class VacacionSolicitudReq {
    public Long empleadoId;
    public String empleadoNombre;
    public LocalDate fechaInicio;
    public LocalDate fechaFin;
    public Integer diasSolicitados;
}
