package SistemaClinica.service;

import SistemaClinica.domain.Consulta;
import SistemaClinica.domain.Medico;
import SistemaClinica.domain.Paciente;
import SistemaClinica.exception.ChaveJaExistenteException;
import SistemaClinica.repository.Repository;
import SistemaClinica.repository.RepositoryConsulta;
import SistemaClinica.repository.RepositoryMedico;
import SistemaClinica.repository.RepositoryPaciente;
import lombok.extern.log4j.Log4j2;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
@Log4j2
public class Clinica {
    private Repository<String, Paciente> pacientes;
    private Repository<String, Medico> medicos;
    private List<Consulta> consultas;

    public Clinica() {
        this.pacientes = new Repository<>();
        this.medicos = new Repository<>();
        this.consultas = new ArrayList<>();
    }
    public void cadastrarPaciente(Paciente p) {
        try {
            RepositoryPaciente.Adicionar(
                    p.getNome(),
                    p.getCpf(),
                    p.getTelefone());
           log.info("Paciente cadastrado com sucesso!");
        } catch (SQLException e) {
            log.error("Erro ao cadastar usuario no Banco. ");
        }
    }

    public void cadastrarMedico(Medico m){
        try{
            RepositoryMedico.Adicionar(m.getCrm(),m.getNome(),m.getEspecialidade());
           log.info("Medico cadastrado com sucesso");
        }catch (SQLException e){
            log.info("erro ao cadastrar medico ao banco");
        }
    }
    public void agendarConsulta(String cpf , String crm, LocalDateTime horario) {
        try {
         Paciente p1 = buscarPaciente(cpf).orElseThrow(() -> new IllegalArgumentException("Paciente nao existe"));
         Medico m2 = buscarMedico(crm).orElseThrow(() -> new IllegalArgumentException("Medico nao existe"));

         if (horario.toLocalTime().isBefore(LocalTime.of(8,00)) ||
                horario.toLocalTime().isAfter(LocalTime.of(18,30))){
            throw new IllegalArgumentException("Clinica nao esta em horario de atendimento");
        }
         if (TempoConsulta(crm,  horario)){
            throw new IllegalArgumentException("O medico ja esta em consulta nesse horario");
        }
         if (conflitoPaciente(cpf,horario)){
            throw new IllegalArgumentException("O paciente ja esta em consulta nesse horario.");
        }

            RepositoryConsulta.Adicionar(crm,horario.toLocalDate(), horario.toLocalTime(), cpf);
            log.info("consulta agendada com sucesso");
       }catch (SQLException e){
            log.info("Erro ao consultar o banco de dados", e);
        }
        catch (IllegalArgumentException e){
          log.warn(e.getMessage());
       }

    }
    public void cancelarConsulta(String cpf, LocalDateTime data_hora){
        for (int i = 0; i < consultas.size(); i++) {
           Consulta c2 = consultas.get(i);
           if (c2.getPaciente().getCpf().equals(cpf) && c2.getDia_horario().equals(data_hora)){
               consultas.remove(i);
               return;
           }
        }
    }
    public void listarConsulta(String cpf){
         consultas.stream().filter(c -> c.getPaciente().getCpf().equals(cpf)).forEach(c ->log.info(c));
    }
    public Optional<Paciente> buscarPaciente(String cpf ) throws SQLException {
        return pacientes.buscarPaciente(cpf);
    }
    public Optional<Medico> buscarMedico(String crm) throws SQLException {
        return medicos.buscarMedico(crm);
    }
    public boolean conflitoConsulta(String crm, LocalDateTime horario){
         return consultas.stream().anyMatch(c -> c.getDia_horario().equals(horario) && c.getMedico().equals(crm));
    }
    public boolean conflitoPaciente(String cpf, LocalDateTime horario){
        return consultas.stream().anyMatch(c-> c.getPaciente().getCpf().equals(cpf) && c.getDia_horario().equals(horario));
    }
    public boolean TempoConsulta(String crm, LocalDateTime horarioNovo) {
        LocalDateTime inicioNovo = horarioNovo;
        LocalDateTime fimNovo = horarioNovo.plusMinutes(30);

        for (Consulta c1 : consultas) {
            if (c1.getMedico().getCrm().equalsIgnoreCase(crm)) {

                LocalDateTime inicioExistente = c1.getDia_horario();
                LocalDateTime fimExistente = inicioExistente.plusMinutes(30);

                if (inicioNovo.isBefore(fimExistente) &&
                        fimNovo.isAfter(inicioExistente)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void ConsultarAgendaMedico(String crm){
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        consultas.stream().filter(c-> c.getMedico().getCrm().equalsIgnoreCase(crm)).forEach(c ->log.info( "nome: " + c.getMedico().getNome()
                + "\nhorarios: " + c.getDia_horario().format(formatter)
                + "\npacientes: " + c.getPaciente().getNome()));
    }
    public void verificarAgendaPacientes(String cpf){
        boolean encontrado = false;
        for (Consulta c2 : consultas){
            if (c2.getPaciente().getCpf().equalsIgnoreCase(cpf)){
                if (!encontrado){
                   log.info("consultas do paciente: ");
                }
               log.info(c2.getDia_horario().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "-" + c2.getMedico().getNome());
                encontrado = true;
            }
            if (!encontrado){
               log.info("nenhum paciente agendado com esse cpf");
            }
        }
    }
    public void ListaPaciente(){
        try (FileWriter pacientes_s = new FileWriter("ListaPacientes.txt"); BufferedWriter
        pacientes1 = new BufferedWriter(pacientes_s)){
            for (Paciente paciente: pacientes.valores()){
                pacientes1.write( paciente.getNome() + ";" + paciente.getCpf() +";" +  ";" + paciente.getTelefone());
                pacientes1.newLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void listaMedicos(){
        try (FileWriter medicos_s = new FileWriter("ListaMedicos.txt"); BufferedWriter bw = new BufferedWriter(medicos_s)){
            for (Medico m1 : medicos.valores()){
                bw.write( m1.getNome() + ";" + m1.getCrm() + ";" + m1.getEspecialidade());
                bw.newLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void listaConsultas(){
        try(FileWriter consultas_s = new FileWriter("ListaConsultas.txt"); BufferedWriter bw = new BufferedWriter(consultas_s)){
            for (Consulta c1 : consultas){
               bw.write( c1.getPaciente().getCpf() + ";" + c1.getMedico().getNome() + ";" + c1.getDia_horario());
               bw.newLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
