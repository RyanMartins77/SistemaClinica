package SistemaClinica.repository;

import SistemaClinica.Connection.ConnectionClinica;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

@Log4j2
public class RepositoryMedico {
    public static void Adicionar(String crm, String nome, String especialidade)throws SQLException{
        String sql = "INSERT INTO clinica.medico (crm, nome, especialidade) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionClinica.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, crm );
            ps.setString(2,nome );
            ps.setString(3, especialidade);
            int i = ps.executeUpdate();
            log.info("inserindo dados. database row effect '{}' ", i);

        }
    }
    public static void delete(String crm)throws SQLException{
         String sql = "DELETE FROM clinica.medico WHERE crm = ?";;
        try (Connection conn = ConnectionClinica.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, crm );
            int i = ps.executeUpdate();
            log.info("removendo na linha '{}' ", i);
        }
    }

    public static void update(String crm, String novoNome) throws SQLException{
        String sql = "UPDATE clinica.medico SET nome = ? WHERE crm = ?";

        try (Connection conn = ConnectionClinica.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, novoNome);
            ps.setString(2, crm);

            int i = ps.executeUpdate();

            log.info("Médicos atualizados: {}", i);

        }
    }


}
