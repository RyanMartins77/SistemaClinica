package SistemaClinica.service;

import SistemaClinica.repository.RepositoryMedico;

import java.sql.SQLException;

public class ServiceMedico {
    public static void save(String crm, String nome, String especialidade)throws SQLException{
        RepositoryMedico.Adicionar(crm,nome,especialidade);
    }
    public void delete(String crm)throws SQLException {
        RepositoryMedico.delete(crm);
    }
    public void atualizar(String crm, String novo_Nome)throws SQLException{
        RepositoryMedico.update(crm,novo_Nome);
    }

}
