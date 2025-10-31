package com.example.vacaciones.dto;

import java.time.LocalDate;

public class AprobarSolicitudReq {
    public LocalDate fechaInicio;
    public LocalDate fechaFin;
    public Integer diasSolicitados;
    public String observacion;
    public Long aprobadoPor;
    public boolean aprobar;
}
