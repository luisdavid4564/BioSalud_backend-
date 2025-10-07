package com.BioSalud.backend.controller;

import com.BioSalud.backend.dto.PacienteUpdateDTO;
import com.BioSalud.backend.model.Paciente;
import com.BioSalud.backend.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {
    private final PacienteService pacienteService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RECEPCIONISTA', 'MEDICO')")
    public ResponseEntity<Page<Paciente>> obtenerTodosLosPacientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(pacienteService.buscarPacientesActivos("", "nombre", pageable));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RECEPCIONISTA')")
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody Paciente paciente) {
        return ResponseEntity.ok(pacienteService.registrarNuevoPaciente(paciente));
    }

    @GetMapping("/activos")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RECEPCIONISTA', 'MEDICO')")
    public ResponseEntity<Page<Paciente>> buscarPacientesActivos(
            @RequestParam(defaultValue = "") String termino,
            @RequestParam(defaultValue = "nombre") String filtro,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(pacienteService.buscarPacientesActivos(termino, filtro, pageable));
    }

    @GetMapping("/inactivos")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RECEPCIONISTA')")
    public ResponseEntity<Page<Paciente>> buscarPacientesInactivos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(pacienteService.buscarPacientesInactivos(pageable));
    }

    @PutMapping("/{id}/inactivar")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RECEPCIONISTA')")
    public ResponseEntity<Paciente> inactivarPaciente(@PathVariable Integer id) {
        return ResponseEntity.ok(pacienteService.cambiarEstado(id, Paciente.Estado.Inactivo));
    }

    @PutMapping("/{id}/activar")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RECEPCIONISTA')")
    public ResponseEntity<Paciente> activarPaciente(@PathVariable Integer id) {
        return ResponseEntity.ok(pacienteService.cambiarEstado(id, Paciente.Estado.Activo));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RECEPCIONISTA')")
    public ResponseEntity<Paciente> modificarPaciente(@PathVariable Integer id, @RequestBody PacienteUpdateDTO datos) {
        return ResponseEntity.ok(pacienteService.modificarPaciente(id, datos));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'RECEPCIONISTA', 'MEDICO')")
    public ResponseEntity<PacienteUpdateDTO> obtenerPacientePorId(@PathVariable Integer id) {
        return ResponseEntity.ok(pacienteService.obtenerPacienteParaModificar(id));
    }
}
