package com.BioSalud.backend.service;

import com.BioSalud.backend.dto.PacienteUpdateDTO;
import com.BioSalud.backend.model.Paciente;
import com.BioSalud.backend.repository.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PacienteService {
    private final PacienteRepository pacienteRepository;

    // Registrar nuevo paciente
    @Transactional
    public Paciente registrarNuevoPaciente(Paciente paciente) {
        pacienteRepository.findByDni(paciente.getDni()).ifPresent(p -> {
            throw new IllegalStateException("Ya existe un paciente con el DNI proporcionado.");
        });

        paciente.setEstado(Paciente.Estado.Activo);
        return pacienteRepository.save(paciente);
    }

    // Buscar pacientes activos
    public Page<Paciente> buscarPacientesActivos(String termino, String filtro, Pageable pageable) {
        return pacienteRepository.findByEstadoAndTermino(Paciente.Estado.Activo, termino, pageable);
    }

    // Buscar pacientes inactivos
    public Page<Paciente> buscarPacientesInactivos(Pageable pageable) {
        return pacienteRepository.findByEstado(Paciente.Estado.Inactivo, pageable);
    }

    // Cambiar estado (activar/inactivar)
    @Transactional
    public Paciente cambiarEstado(Integer id, Paciente.Estado nuevoEstado) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado con ID: " + id));
        paciente.setEstado(nuevoEstado);
        return pacienteRepository.save(paciente);
    }

    // Modificar datos de un paciente
    @Transactional
    public Paciente modificarPaciente(Integer id, PacienteUpdateDTO datos) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado con ID: " + id));

        paciente.setNombres(datos.getNombres());
        paciente.setApellidos(datos.getApellidos());
        paciente.setSexo(datos.getSexo());
        paciente.setFechaNacimiento(datos.getFechaNacimiento());
        paciente.setTelefono(datos.getTelefono());
        paciente.setEmail(datos.getEmail());
        paciente.setAlergias(datos.getAlergias());

        return pacienteRepository.save(paciente);
    }

    // Obtener un paciente para editar
    public PacienteUpdateDTO obtenerPacienteParaModificar(Integer id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado con ID: " + id));

        PacienteUpdateDTO dto = new PacienteUpdateDTO();
        dto.setNombres(paciente.getNombres());
        dto.setApellidos(paciente.getApellidos());
        dto.setSexo(paciente.getSexo());
        dto.setFechaNacimiento(paciente.getFechaNacimiento());
        dto.setTelefono(paciente.getTelefono());
        dto.setEmail(paciente.getEmail());
        dto.setAlergias(paciente.getAlergias());

        return dto;
    }

    // Listar todos los pacientes
    public Page<Paciente> listarTodosLosPacientes(Pageable pageable) {
        return pacienteRepository.findAll(pageable);
    }

    // Buscar pacientes por nombre
    public Page<Paciente> buscarPacientesPorNombre(String nombre, Pageable pageable) {
        return pacienteRepository.findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase(nombre, nombre,
                pageable);
    }

    public java.util.List<Paciente> buscarPacientesPorNombreSimple(String nombre) {
        return pacienteRepository
                .findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase(nombre, nombre, Pageable.unpaged())
                .getContent();
    }

    // Buscar paciente por ID
    public Paciente buscarPacientePorId(Integer id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado con ID: " + id));
    }
}