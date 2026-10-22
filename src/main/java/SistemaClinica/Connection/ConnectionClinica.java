package SistemaClinica.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClinica {

    public static Connection Conexao() throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/clinica";
        String username ="root";
        String password = "88097";

        return DriverManager.getConnection(url, username, password);
    }


}
