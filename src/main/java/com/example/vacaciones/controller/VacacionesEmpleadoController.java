package com.example.vacaciones.controller;

import com.example.vacaciones.dto.VacacionSolicitudReq;
import com.example.vacaciones.model.VacacionSolicitud;
import com.example.vacaciones.service.VacacionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacaciones/empleado")
@CrossOrigin
public class VacacionesEmpleadoController {

    private final VacacionService service;

    public VacacionesEmpleadoController(VacacionService service) {
        this.service = service;
    }

    @PostMapping
    public VacacionSolicitud crear(@RequestBody VacacionSolicitudReq dto) {
        return service.crear(dto);
    }

    @GetMapping("/{empleadoId}")
    public List<VacacionSolicitud> misSolicitudes(@PathVariable Long empleadoId) {
        return service.listarPorEmpleado(empleadoId);
    }
}
