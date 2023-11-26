package formulaire;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import conn.GetConnection;
import req.Requete;

public class Formulaire {
    private Connection connection;
    private Map<String, String> customLabels = new HashMap<>();
    private Map<String, Map<Integer, String>> foreignKeyCache = new HashMap<>();


    public Formulaire(Connection connection) {
        this.connection = connection;
    }

    public Map<String, String> generateLabels(String tableName) {
        Map<String, String> labelData = new HashMap<>();
        try {
            ResultSetMetaData metaData = getTableMetaData(tableName);
            labelData = generateLabelsFromMetaData(metaData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return labelData;
    }

    private Map<String, String> generateLabelsFromMetaData(ResultSetMetaData metaData) throws SQLException {
        Map<String, String> labelData = new HashMap<>();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            String label = customLabels.get(columnName);
            if (label == null) {
                label = columnName; // Utilisez le nom de colonne par défaut si aucun libellé personnalisé n'est spécifié
            }
            labelData.put(columnName, label);
        }
        return labelData;
    }

    private ResultSetMetaData getTableMetaData(String tableName) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE 1 = 0"; // We don't want to retrieve any data, just table structure
        PreparedStatement preparedStatement = GetConnection.getConnection(tableName).prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.getMetaData();
    }

    public Map<String, String> generateForm(String tableName) {
        Map<String, String> formData = new LinkedHashMap<>();
        try {
            ResultSetMetaData metaData = getTableMetaData(tableName);
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                String columnType = metaData.getColumnTypeName(i);
                String userInput = "";

                if (isForeignKey(tableName, columnName)) {
                    userInput = generateForeignKeyComboBox(tableName, columnName);
                } else {
                    userInput = generateUserInput(tableName, columnName, columnType);
                }
                
                formData.put(columnName, userInput);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return formData;
    }

    private boolean isForeignKey(String tableName, String columnName) throws SQLException {
        boolean isForeignKey = false;
        try {
            Connection connection = GetConnection.getConnection(tableName);
            
            // Query to check if the column is a foreign key
            String query = "SELECT COUNT(*) FROM USER_CONS_COLUMNS " +
                           "WHERE TABLE_NAME = ? " +
                           "AND COLUMN_NAME = ? " +
                           "AND CONSTRAINT_NAME IN (SELECT CONSTRAINT_NAME FROM USER_CONSTRAINTS WHERE CONSTRAINT_TYPE = 'R')";
            
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tableName.toUpperCase());
            preparedStatement.setString(2, columnName.toUpperCase());
            
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    isForeignKey = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isForeignKey;
    }
    

    private String generateForeignKeyComboBox(String tableName, String columnName) {
        StringBuilder optionsHTML = new StringBuilder("<select name='" + columnName + "' class=\"form-select\" id=\"floatingSelect\" >");
        try {
            if (!foreignKeyCache.containsKey(columnName)) {
                Map<Integer, String> values = fetchForeignKeyValues(tableName, columnName);
                if (values != null) {
                    foreignKeyCache.put(columnName, values);
                }
            }

            Map<Integer, String> cachedValues = foreignKeyCache.get(columnName);
            if (cachedValues != null) {
                for (Map.Entry<Integer, String> entry : cachedValues.entrySet()) {
                    int id = entry.getKey();
                    System.out.println(id);
                    String displayValue = entry.getValue();
                    optionsHTML.append("<option value='").append(id).append("'>").append(displayValue).append("</option>");
                }
            }

            optionsHTML.append("</select>");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionsHTML.toString();
    }

    
    private Map<Integer, String> fetchForeignKeyValues(String tableName, String columnName) throws SQLException {
        Map<Integer, String> values = new HashMap<>();
        Connection connection = null;
        try {
            connection = GetConnection.getConnection(tableName);
            String query = getQueryForColumnName(columnName);
            if (query.isEmpty()) {
                return null;
            }

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(columnName.toLowerCase());
                String displayValue = getDisplayValue(columnName, resultSet);
                values.put(id, displayValue);
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return values.isEmpty() ? null : values;
    }
  
    private String getQueryForColumnName(String columnName) {
        if (columnName.equals("CLIENT_ID")) {
            return Requete.getClients();
        } else if (columnName.equals("CHAMBRE_ID")) {
            return Requete.getChambre();
        }
        return "";
    }

    private String getDisplayValue(String columnName, ResultSet resultSet) throws SQLException {
        if (columnName.equals("CLIENT_ID")) {
            return resultSet.getString("nom") + " " + resultSet.getString("prenom");
        } else if (columnName.equals("CHAMBRE_ID")) {
            return resultSet.getString("type");
        }
        return "";
    }

    private String generateUserInput(String tableName, String columnName, String columnType) {
        String inputType = mapColumnTypeToInputType(tableName, columnType, columnName);
        return "<input type=\"" + inputType + "\" name=\"" + columnName + "\" class=\"form-control\">";
    }

    private String mapColumnTypeToInputType(String tableName, String columnType, String columnName) {
        if (columnType.equalsIgnoreCase("VARCHAR") || columnType.equalsIgnoreCase("CHAR")) {
            return "text";
        } else if (columnType.equalsIgnoreCase("INT") || columnType.equalsIgnoreCase("INTEGER") || columnType.equalsIgnoreCase("serial")) {
            return "number";
        } else if (columnType.equalsIgnoreCase("DATE")) {
            return "date";
        } else {
            return "text";
        }
    }
}
