package output;

import java.sql.PreparedStatement;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import conn.GetConnection;
import req.Requete;


public class Tableau {
	
	public static List<String[]> executeQuery(Connection connection, PreparedStatement preparedStatement) {
	    List<String[]> data = new ArrayList<>();

	    try {
	        ResultSet resultSet = preparedStatement.executeQuery();

	        int columnCount = resultSet.getMetaData().getColumnCount();
	        while (resultSet.next()) {
	            String[] rowData = new String[columnCount];
	            for (int i = 1; i <= columnCount; i++) {
	                rowData[i - 1] = resultSet.getString(i);
	            }
	            data.add(rowData);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return data;
	}
	
	public static List<Map<String, Object>> Tableau(String tableName) {
        return Tableau(tableName,0,0,0);
    }
	public static List<Map<String, Object>> Tableau(String tableName,int restorant_id) {
        return Tableau(tableName,restorant_id,0,0);
    }
	
	public static List<Map<String, Object>> Tableau(String tableName,int restorant_id,int mois,int annee) {
	    List<Map<String, Object>> data = new ArrayList<>();
	    Connection connection = null;
	    String selectQuery = null;

	    try {
	        connection = GetConnection.getConnection(tableName);

	        if (connection != null) {
	            selectQuery = generateQuery(tableName, restorant_id, mois, annee);
	            if (selectQuery != null) {
	                Statement statement = connection.createStatement();
	                ResultSet resultSet = statement.executeQuery(selectQuery);

	                // Utilisez la fonction executeQueryWithColumnNames pour récupérer les données.
	                data = executeQueryWithColumnNames(connection, resultSet);
	            } else {
	                System.out.println("La requête est invalide pour la table : " + tableName);
	            }
	        } else {
	            System.out.println("Aucune connexion n'a été établie pour la table : " + tableName);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Assurez-vous de fermer la connexion lorsque vous avez terminé
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    return data;
	}

	private static String generateQuery(String tableName,int restorant_id,int mois,int annee) {
		String  selectQuery;
	    
		if (tableName.equals("VueBeneficeRestaurants")) {
	        selectQuery = Requete.getStatistiqueByDate(restorant_id, mois, annee);
	    }else if (tableName.equals("Commandes")) {
	        selectQuery = Requete.getListeCommande(restorant_id);
	    }else if (tableName.equals("Menu")) {
	        selectQuery = Requete.getMenu(restorant_id);
	    }else if (tableName.equals("Facture")) {
	        selectQuery = Requete.getListeFacture(restorant_id);
	    }else{
	    	selectQuery = "SELECT * FROM " + tableName + "";	    
	    }
	    
	    return selectQuery;
	}

	
	public static List<Map<String, Object>> executeQueryWithColumnNames(Connection connection, ResultSet resultSet) {
	    List<Map<String, Object>> result = new ArrayList<>();

	    try {
	        ResultSetMetaData metaData = resultSet.getMetaData();
	        int columnCount = metaData.getColumnCount();

	        while (resultSet.next()) {
	            Map<String, Object> row = new HashMap<>();
	            for (int i = 1; i <= columnCount; i++) {
	                String columnName = metaData.getColumnName(i);
	                Object columnValue = resultSet.getObject(i);
	                row.put(columnName, columnValue);
	            }
	            result.add(row);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return result;
	}
}