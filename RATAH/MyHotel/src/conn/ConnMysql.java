package conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnMysql {
    private static Connection connection;

    // Méthode pour établir la connexion à la base de données MySQL
    public static Connection getConnectionMySQL() {
        String url = "jdbc:mysql://localhost:3306/restaurant"; // Remplacez localhost, 3306 et votre_base_de_donnees par les informations de votre base de données MySQL
        String username = "root"; // Remplacez par votre nom d'utilisateur MySQL
        String password = ""; // Remplacez par votre mot de passe MySQL

        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver"); // Utilisez la classe de pilote MySQL
                connection = DriverManager.getConnection(url, username, password);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur de chargement du pilote MySQL JDBC: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données MySQL: " + e.getMessage());
        }
        return connection;
    }

    // Méthode pour préparer une déclaration SQLs
    public static PreparedStatement prepareStatements(String sql) throws SQLException {
        Connection conn = getConnectionMySQL();
        if (conn != null && !conn.isClosed()) {
            return conn.prepareStatement(sql);
        } else {
            throw new SQLException("La connexion à la base de données MySQL est invalide ou fermée.");
        }
    }

    // Méthode pour fermer la connexion
   
    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture de la connexion à la base de données MySQL: " + e.getMessage());
            } finally {
                connection = null; // Réinitialise la connexion après la fermeture
            }
        }
    }
    
}
