package SistemaClinica.teste;

import SistemaClinica.service.Clinica;
import SistemaClinica.domain.Medico;
import SistemaClinica.domain.Paciente;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ClinicalTeste {
    static void main(String[] args) {
        Clinica clinica = new Clinica();
        Scanner sc =new Scanner(System.in);
        int opcao = -1;
        while (opcao != 0) {
            try {
                System.out.println("\n=============== MENU CLINICA ==================");
                System.out.println("1 - Cadastrar Paciente");
                System.out.println("2 - Cadastrar Medico");
                System.out.println("3 - Agendar Consulta");
                System.out.println("4 - Cancelar Consulta");
                System.out.println("5 - Verificar agenda medico");
                System.out.println("6 - Verificar agenda de pacientes");
                System.out.println("0 - Sair");
                System.out.print("Escolha uma opcao: ");

                opcao = Integer.parseInt(sc.nextLine());


                switch (opcao) {
                    case 1:
                        System.out.print("Nome: ");
                        String nomePaciente = sc.nextLine();

                        System.out.print("CPF: ");
                        String cpfPaciente = sc.nextLine();

                        System.out.print("Idade: ");
                        int idade = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Telefone: ");
                        String telefone = sc.nextLine();

                        Paciente paciente = new Paciente(nomePaciente, cpfPaciente, idade, telefone);
                        clinica.CadastrarPaciente(paciente);
                        break;

                    case 2:
                        System.out.print("Nome do medico: ");
                        String nomeMedico = sc.nextLine();

                        System.out.print("CRM: ");
                        String crm = sc.nextLine();

                        System.out.print("Especialidade: ");
                        String especialidade = sc.nextLine();

                        Medico medico = new Medico(nomeMedico, crm, especialidade);
                        clinica.cadastrarMedico(medico);
                        break;

                    case 3:
                        System.out.print("CPF do paciente: ");
                        String cpfAgendamento = sc.nextLine();

                        System.out.print("CRM do medico: ");
                        String crmAgendamento = sc.nextLine();

                        System.out.print("Ano: ");
                        int ano = sc.nextInt();

                        System.out.print("Mes: ");
                        int mes = sc.nextInt();

                        System.out.print("Dia: ");
                        int dia = sc.nextInt();

                        System.out.print("Hora: ");
                        int hora = sc.nextInt();

                        System.out.print("Minuto: ");
                        int minuto = sc.nextInt();
                        sc.nextLine();

                        LocalDateTime horario = LocalDateTime.of(ano, mes, dia, hora, minuto);

                        clinica.agendarConsulta(cpfAgendamento, crmAgendamento, horario);
                        break;

                    case 4:
                        System.out.print("CPF do paciente: ");
                        String cpfCancelar = sc.nextLine();

                        System.out.print("Ano: ");
                        int anoC = sc.nextInt();

                        System.out.print("Mes: ");
                        int mesC = sc.nextInt();

                        System.out.print("Dia: ");
                        int diaC = sc.nextInt();

                        System.out.print("Hora: ");
                        int horaC = sc.nextInt();

                        System.out.print("Minuto: ");
                        int minutoC = sc.nextInt();
                        sc.nextLine();

                        LocalDateTime horarioCancelar = LocalDateTime.of(anoC, mesC, diaC, horaC, minutoC);

                        clinica.cancelarConsulta(cpfCancelar, horarioCancelar);
                        break;

                    case 5:
                        System.out.print("CRM do medico: ");
                        String crmAgenda = sc.nextLine();

                        clinica.ConsultarAgendaMedico(crmAgenda);
                        break;

                    case 6:
                        System.out.print("CPF do paciente: ");
                        String cpfAgenda = sc.nextLine();

                        clinica.verificarAgendaPacientes(cpfAgenda);
                        break;

                    case 0:
                        System.out.println("Encerrando sistema...");
                        break;

                    default:
                        System.out.println("Opcao invalida. Digite um numero entre 0 e 6.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida. Digite apenas numeros quando solicitado.");
                sc.nextLine();

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    public static void MENU(){
        System.out.println("===============MENU CLINICA==================");
        System.out.println("1- Cadastrar Paciente ");
        System.out.println("2- Cadastrar Medico");
        System.out.println("3- Agenda Consulta");
        System.out.println("4- Cancelar Consulta ");
        System.out.println("5- Verificar agenda medico");
        System.out.println("6- Verificar agenda de pacientes");
        System.out.println();
    }

}
