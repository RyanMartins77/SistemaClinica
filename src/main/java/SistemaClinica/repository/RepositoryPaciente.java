package SistemaClinica.repository;

import SistemaClinica.Connection.ConnectionClinica;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
@Log4j2
public class RepositoryPaciente {

    public static void Adicionar(String nome, String cpf, String telefone)
            throws SQLException {

        String sql = "INSERT INTO paciente (nome, cpf, telefone) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionClinica.Conexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nome);
            ps.setString(2, cpf);
            ps.setString(3, telefone);

            int i = ps.executeUpdate();
            log.info("Paciente cadastrado com sucesso. Database effect rows '{}'", i);


        }
    }

    public static void delete(String cpf) throws SQLException {

        String sql = "DELETE FROM paciente WHERE cpf = ?";

        try (Connection conn = ConnectionClinica.Conexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cpf);

            ps.executeUpdate();

        }
    }
}
