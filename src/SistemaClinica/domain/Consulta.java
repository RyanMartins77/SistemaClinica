package SistemaClinica.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Consulta {
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime dia_horario;

    public Consulta(Paciente paciente, Medico medico, LocalDateTime dia_horario) {
        this.paciente = paciente;
        this.medico = medico;
        if (!dia_horario.isAfter(LocalDateTime.now())){
            throw new IllegalArgumentException("Dia Invalido para consulta.");
        }
        this.dia_horario = dia_horario;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public LocalDateTime getDia_horario() {
        return dia_horario;
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "paciente=" + paciente.getNome() +
                ", medico=" + medico.getNome() +
                ", dia_horario=" + dia_horario.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                '}';
    }
}
