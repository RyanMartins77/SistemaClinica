package SistemaClinica.service;

import SistemaClinica.domain.Consulta;
import SistemaClinica.domain.Medico;
import SistemaClinica.domain.Paciente;
import SistemaClinica.exception.ChaveJaExistenteException;
import SistemaClinica.repository.Repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Clinica {
    private Repository<String, Paciente> pacientes;
    private Repository<String, Medico> medicos;
    private List<Consulta> consultas;

    public Clinica() {
        this.pacientes = new Repository<>();
        this.medicos = new Repository<>();
        this.consultas = new ArrayList<>();
    }
    public void cadastrarPaciente(Paciente p){
        try{
            pacientes.adicionar(p.getCpf(), p);
            ListaPaciente();
        }catch (ChaveJaExistenteException e){
            System.out.println(e.getMessage());
        }
    }
    public void cadastrarMedico(Medico m){
        try{
            medicos.adicionar(m.getCrm(),m);
        }catch (ChaveJaExistenteException e ) {
            System.out.println(e.getMessage());
        }
    }
    public void agendarConsulta(String cpf , String crm, LocalDateTime horario){
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
         if (agendarConflitoPaciente(cpf,horario)){
            throw new IllegalArgumentException("O paciente ja esta em consulta nesse horario.");
        }

           Consulta c1 = new Consulta(p1,m2, horario);
           consultas.add(c1);
           listaConsultas();
           System.out.println("consulta agendada com sucesso");
       }catch (IllegalArgumentException e){
           System.out.println(e.getMessage());
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
    public void ListaConsulta(String cpf){
        boolean encontrado = false;
        for (Consulta c1 : consultas){
            if (c1.getPaciente().getCpf().equals(cpf)){
                System.out.println(c1);
                encontrado = true;
            }
        }
        if (!encontrado){
            System.out.println("consultas nao encontradas");
        }
    }
    public Optional<Paciente> buscarPaciente(String cpf ){
        return pacientes.buscar(cpf);
    }
    public Optional<Medico> buscarMedico(String crm){
        return medicos.buscar(crm);
    }
    public boolean ConflitoConsulta(String crm, LocalDateTime horario){
         return consultas.stream().anyMatch(c -> c.getDia_horario().equals(horario) && c.getMedico().equals(crm));
    }
    public boolean agendarConflitoPaciente(String cpf, LocalDateTime horario){
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
        for(Consulta c1 : consultas){
            if (c1.getMedico().getCrm().equalsIgnoreCase(crm)){
                System.out.println("nome: " + c1.getMedico().getNome() + "\n" + "horarios: " + c1.getDia_horario().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))+ "\npacientes:"   + c1.getPaciente().getNome());
            }
        }
    }
    public void verificarAgendaPacientes(String cpf){
        boolean encontrado = false;
        for (Consulta c2 : consultas){
            if (c2.getPaciente().getCpf().equalsIgnoreCase(cpf)){
                if (!encontrado){
                    System.out.println("consultas do paciente: ");
                }
                System.out.println(c2.getDia_horario().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "-" + c2.getMedico().getNome());
                encontrado = true;
            }
            if (!encontrado){
                System.out.println("nenhum paciente agendado com esse cpf");
            }
        }
    }
    public void ListaPaciente(){
        try (FileWriter pacientes_s = new FileWriter("ListaPacientes.txt"); BufferedWriter
        pacientes1 = new BufferedWriter(pacientes_s)){
            for (Paciente paciente: pacientes.valores()){
                pacientes1.write( paciente.getNome() + ";" + paciente.getCpf() +";" + paciente.getIdade() + ";" + paciente.getTelefone());
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
