package SistemaClinica.repository;

import SistemaClinica.Connection.ConnectionClinica;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

@Log4j2
public class RepositoryConsulta {
    public static void Adicionar(int id_consulta, String crm, LocalDate data_consulta, LocalTime horario, String Status, String cpf_pac)throws SQLException{
        String sql = "INSERT INTO `clinica`.`consulta` (`id_consulta`, `crm`, `data_consulta`," +
                " `horario`, `status`, `cpf_paciente`) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionClinica.Conexao();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id_consulta);
            ps.setString(2,crm );
            ps.setObject(3, data_consulta);
            ps.setObject(4, horario);
            ps.setString(5,Status);
            ps.setString(6,cpf_pac);
            int i = ps.executeUpdate();
                log.info("inserindo dados. database row effect '{}' ", i);

        }
    }
    public static void delete(int id_consulta)throws SQLException {
        String sql = "DELETE FROM clinica.consulta WHERE id_consulta = ?";

        try (Connection conn = ConnectionClinica.Conexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id_consulta);

            int i = ps.executeUpdate();
            if (log.isInfoEnabled()) {
                log.info("Consultas deletadas: {}", i);
            }

        }
    }
    public static void update(
            int id_consulta,
            LocalDate novaData,
            LocalTime novoHorario) throws SQLException{

        String sql = "UPDATE clinica.consulta " +
                "SET data_consulta = ?, horario = ? " +
                "WHERE id_consulta = ?";

        try (Connection conn = ConnectionClinica.Conexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, novaData);
            ps.setObject(2, novoHorario);
            ps.setInt(3, id_consulta);

            int i = ps.executeUpdate();
            if (log.isInfoEnabled()) {
                log.info("Consultas atualizadas: {}", i);
            }

        }
    }
}
