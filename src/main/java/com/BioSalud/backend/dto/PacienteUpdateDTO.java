package com.BioSalud.backend.dto;

import com.BioSalud.backend.model.Paciente;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PacienteUpdateDTO {
    private String nombres;
    private String apellidos;
    private Paciente.Sexo sexo;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String email;
    private Boolean alergias;
}
