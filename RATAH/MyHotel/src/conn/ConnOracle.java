package conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnOracle {
    
	public static Connection getConnectionOracle() {
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        
        String username = "restaurants";
        String password = "restaurants";
        Connection connection = null;

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur de chargement du pilote Oracle JDBC: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    
	public static PreparedStatement prepareStatements(String sql) throws SQLException {
        Connection connection = getConnectionOracle();
        if (connection != null && !connection.isClosed()) {
            return connection.prepareStatement(sql);
        } else {
            throw new SQLException("La connexion à la base de données Oracle est invalide ou fermée.");
        }
    }

    
	public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
