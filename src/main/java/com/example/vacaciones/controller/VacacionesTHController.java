package com.example.vacaciones.controller;

import com.example.vacaciones.dto.AprobarSolicitudReq;
import com.example.vacaciones.model.VacacionSolicitud;
import com.example.vacaciones.service.VacacionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacaciones/th")
@CrossOrigin
public class VacacionesTHController {

    private final VacacionService service;

    public VacacionesTHController(VacacionService service) {
        this.service = service;
    }

    @GetMapping
    public List<VacacionSolicitud> listarTodas() {
        return service.listarTodas();
    }

    @PutMapping("/{id}/decision")
    public VacacionSolicitud aprobarORechazar(@PathVariable Long id,
                                              @RequestBody AprobarSolicitudReq dto) {
        return service.aprobarORechazar(id, dto);
    }
}
