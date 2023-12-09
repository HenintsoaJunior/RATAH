<%@page import="conn.ConnOracle"%>
<%@page import="insert.Insert"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="formulaire.Formulaire" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.io.IOException" %>

<%
    Connection connection = ConnOracle.getConnectionOracle();

	Object table =session.getAttribute("table");
	String tableName = table.toString();
    Map<String, String> formData = new HashMap<>();

    // Récupérez les noms des colonnes de la table "Sexe" à partir du formulaire
    Formulaire formGenerator = new Formulaire(connection);
    Map<String, String> columnNames = formGenerator.generateForm(tableName);

    // Parcourez les colonnes pour obtenir les valeurs à partir du formulaire
    for (Map.Entry<String, String> columnNameEntry : columnNames.entrySet()) {
        String columnName = columnNameEntry.getKey();
        String columnValue = request.getParameter(columnName); // Récupérez la valeur à partir du formulaire

        // Ajoutez la colonne et sa valeur au map formData
        formData.put(columnName, columnValue);
    }

    Insert insertHandler = new Insert(connection);

    // Appel de la méthode insertData pour insérer les données dans la base de données
    boolean insertionSuccess = insertHandler.insertData(tableName, formData);

   	ConnOracle.getConnectionOracle();

    if (insertionSuccess) {
        response.sendRedirect("confirmation.jsp");
    } else {
        response.sendRedirect("erreur.jsp");
    }
%>

