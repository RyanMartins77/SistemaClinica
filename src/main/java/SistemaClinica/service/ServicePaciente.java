package SistemaClinica.service;

import SistemaClinica.repository.RepositoryPaciente;


import java.sql.SQLException;

public class ServicePaciente {

    public static void save(String nome, String cpf , String telefone) throws SQLException {
        RepositoryPaciente.Adicionar(nome,cpf,telefone);
    }
    public static void delete(String cpf) throws SQLException {
        RepositoryPaciente.delete(cpf);
    }

}
