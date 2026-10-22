package SistemaClinica.service;

import SistemaClinica.repository.RepositoryConsulta;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class ServiceConsulta {

    public static void save(int id_consulta, String crm, LocalDate data_consulta, LocalTime horario, String Status, String cpf_pac)throws SQLException{
        RepositoryConsulta.Adicionar(id_consulta, crm,data_consulta,horario, Status, cpf_pac);
    }
    public static void delete(int id_consulta)throws SQLException {
        RepositoryConsulta.delete(id_consulta);
    }
    public static void update(int id_consulta,
                              LocalDate novaData,
                              LocalTime novoHorario)throws SQLException{
        RepositoryConsulta.update(id_consulta,novaData,novoHorario);
    }
}
