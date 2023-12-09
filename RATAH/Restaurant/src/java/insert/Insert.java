package insert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import conn.GetConnection;

public class Insert {
    private Connection connection;

    public Insert(Connection connection) {
        this.connection = connection;
    }

    public boolean insertData(String tableName, Map<String, String> data) {
        try {
            String[] fieldNames = data.keySet().toArray(new String[0]);
            String[] fieldValues = data.values().toArray(new String[0]);

            PreparedStatement preparedStatement = prepareInsertStatement(tableName, fieldNames, fieldValues);

            if (preparedStatement == null) {
                return false;
            }

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String buildInsertQuery(String tableName, String[] fieldNames) {
        StringBuilder insertQuery = new StringBuilder();
        insertQuery.append("INSERT INTO ").append(tableName).append(" (");

        for (int i = 0; i < fieldNames.length; i++) {
            insertQuery.append(fieldNames[i]);
            if (i < fieldNames.length - 1) {
                insertQuery.append(", ");
            }
        }

        insertQuery.append(") VALUES (");

        for (int i = 0; i < fieldNames.length; i++) {
            if (i > 0) {
                insertQuery.append(", ");
            }
            if (isDateField(fieldNames[i])) {
                insertQuery.append("TO_DATE(?, 'YYYY-MM-DD')");
            } else {
                insertQuery.append("?");
            }
        }

        insertQuery.append(")");

        return insertQuery.toString();
    }

    private static PreparedStatement prepareInsertStatement(String tableName, String[] fieldNames, String[] fieldValues) throws SQLException {
        Connection connection = GetConnection.getConnection(tableName);

        if (connection == null) {
            System.out.println("Aucune connexion n'a été établie pour la table : " + tableName);
            return null;
        }

        String insertQuery = buildInsertQuery(tableName, fieldNames);
        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

        for (int i = 0; i < fieldValues.length; i++) {
            if (!setPreparedStatementValue(preparedStatement, fieldNames[i], fieldValues[i], i + 1)) {
                return null;
            }
        }

        return preparedStatement;
    }

    public static boolean setPreparedStatementValue(PreparedStatement preparedStatement, String fieldName, String fieldValue, int parameterIndex) {
        try {
            if (fieldName.equalsIgnoreCase("id")) {
            		int fieldValueInt = Integer.parseInt(fieldValue);
                preparedStatement.setInt(parameterIndex, fieldValueInt);
            } else {
                // Laisser les autres champs en tant que texte
                preparedStatement.setString(parameterIndex, fieldValue);
            }
            return true;
        } catch (NumberFormatException e) {
            System.err.println("Erreur de conversion du champ '" + fieldName + "' en entier.");
            return false;
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error
            return false;
        }
    }
    
    private static boolean isDateField(String fieldName) {
    	
        return fieldName.toLowerCase().contains("date");
    }
}
