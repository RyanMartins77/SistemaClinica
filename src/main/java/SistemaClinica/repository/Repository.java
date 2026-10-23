package SistemaClinica.repository;

import SistemaClinica.Connection.ConnectionClinica;
import SistemaClinica.domain.Medico;
import SistemaClinica.domain.Paciente;
import SistemaClinica.exception.ChaveInexistente;
import SistemaClinica.exception.ChaveJaExistenteException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Repository<K,V> {
    private Map<K, V> listas = new HashMap<>();

    public void adicionar(K chave, V valor) throws ChaveJaExistenteException {
        if (listas.containsKey(chave)) {
            throw new ChaveJaExistenteException("chave ja registrada. ");
        }
        listas.put(chave, valor);

    }

    public void remove(K valor) throws ChaveInexistente {
        if (listas.remove(valor) == null) {
            throw new ChaveInexistente("Essa chave é inexistente");
        }
    }

    public Optional<Paciente> buscarPaciente(String cpf) throws SQLException {
        String sql = "SELECT nome, cpf, telefone FROM paciente WHERE cpf = ?";
        try (Connection conn = ConnectionClinica.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, cpf);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()){
                    Paciente p1 = new Paciente(
                            rs.getString("cpf"),
                            rs.getString("nome"),
                            rs.getString("telefone")
                    );
                    return Optional.of(p1);

                }
            }
        }
        return Optional.empty();
    }

    public Optional<Medico> buscarMedico(String crm) throws SQLException {
        String sql = "SELECT crm, nome, especialidade FROM medico WHERE cpf = ?";
        try (Connection conn = ConnectionClinica.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, crm);
           try (ResultSet rs = ps.executeQuery()){
               if (rs.next()) {
                   Medico p1 = new Medico(rs.getString("crm"), rs.getString("nome"), rs.getString("especialidade"));
                   return Optional.of(p1);
               }
           }

        }
        return Optional.empty();
    }

    public Collection<V> valores() {
        return listas.values();
    }

    public void listar() {
        for (Map.Entry<K, V> teste : listas.entrySet()) {
            System.out.println(teste.getKey() + " " + teste.getValue());
        }
    }

}
