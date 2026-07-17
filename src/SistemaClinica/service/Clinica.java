package SistemaClinica.service;

import SistemaClinica.domain.Consulta;
import SistemaClinica.domain.Medico;
import SistemaClinica.domain.Paciente;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Clinica {
    private ArrayList<Paciente> pacientes;
    private ArrayList<Medico> medicos;
    private ArrayList<Consulta> consultas;

    public Clinica() {
        this.pacientes = new ArrayList<>();
        this.medicos = new ArrayList<>();
        this.consultas = new ArrayList<>();
    }
    public void CadastrarPaciente(Paciente p){
        if (buscarPaciente(p.getCpf()) != null){
            System.out.println("Esse paciente ja foi cadastrado");
            return;
        }
        pacientes.add(p);
        ListaPaciente();
        System.out.println(" O paciente " + p.getNome() + " foi cadastrado com sucesso.");
    }
    public void cadastrarMedico(Medico m){
        if (buscarMedico(m.getCrm()) !=null){
            System.out.println("Esse medico ja foi cadastrado");
            return;
        }
        medicos.add(m);
        listaMedicos();
        System.out.println("O medico " + m.getNome() + " foi cadastrado com sucesso");
    }
    public void agendarConsulta(String cpf , String crm, LocalDateTime horario){
        Paciente p1 = buscarPaciente(cpf);
        Medico m2 = buscarMedico(crm);

        if (p1 == null){
            System.out.println("paciente nao existe");
            return;
        }
        if (m2 == null){
            System.out.println("medico nao encontrado");
            return;
        }
        if (horario.toLocalTime().isBefore(LocalTime.of(8,00)) ||
                horario.toLocalTime().isAfter(LocalTime.of(18,30))){
            System.out.println("A clinica nao esta em funcionamento.");
            return;
        }
        if (TempoConsulta(crm,  horario)){
            System.out.println("O medico esta em atendimento esse horario... ");
            return;
        }
        if (agendarConflitoPaciente(cpf,horario)){
            System.out.println("Paciente já tem consulta nesse horário.");
            return;
        }
       try {
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
    public boolean ConflitoConsulta(String crm, LocalDateTime horario){
        for (Consulta consulta: consultas){
            if (consulta.getMedico().getCrm().equalsIgnoreCase(crm) &&  consulta.getDia_horario().equals(horario)){
                return true;
            }
        }
        return false;
    }
    public boolean agendarConflitoPaciente(String cpf, LocalDateTime horario){
        for (Consulta c : consultas ){
            if (c.getPaciente().getCpf().equalsIgnoreCase(cpf) && c.getDia_horario().equals(horario)){
                return true;
            }
        }
        return false;
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
    public Medico buscarMedico(String crm){
        for (Medico m1 : medicos){
            if (m1.getCrm().equals(crm)){
                return m1;
            }
        }
        return null;
    }
    public Paciente buscarPaciente(String cpf){
        for (Paciente p1 : pacientes){
            if (p1.getCpf().equals(cpf))
                return p1;
        }
        return null;
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
            for (Paciente p1 : pacientes){
                pacientes1.write( p1.getNome() + ";" + p1.getCpf() +";" + p1.getIdade() + ";" + p1.getTelefone());
                pacientes1.newLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void listaMedicos(){
        try (FileWriter medicos_s = new FileWriter("ListaMedicos.txt"); BufferedWriter bw = new BufferedWriter(medicos_s)){
            for (Medico m1 : medicos){
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
