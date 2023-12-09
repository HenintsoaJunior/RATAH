<%@page import="conn.ConnOracle"%>
<%@page import="conn.GetConnection"%>
<%@page import="java.util.Map"%>
<%@page import="formulaire.Formulaire"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

 <jsp:include page="../HeaderFooter/Header.jsp" />
<section class="page-section clearfix">
        <div class="container">
            <div class="intro"><img class="img-fluid intro-img mb-3 mb-lg-0 rounded" src="../assets/img/intro.jpg">
                <div class="text-center intro-text p-5 rounded bg-faded">
                    <h2 class="section-heading mb-4"><span class="section-heading-upper">FORMULAIRE</span><span class="section-heading-lower"></span></h2>
                  		<div class="input-group"></div>
    	<form action="../insert/TraitementInsert.jsp" method="post">
        <%
       
            String tableName = "Commande_Menu";
        	Connection connection = GetConnection.getConnection(tableName);
        	Formulaire formGenerator = new Formulaire(connection);
            session.setAttribute("table", tableName);
            Map<String, String> formData = formGenerator.generateForm(tableName);
            Map<String, String> labelData = formGenerator.generateLabels(tableName);

            for (Map.Entry<String, String> entry : formData.entrySet()) {
        %>
        <div>
            <label for="<%= entry.getKey() %>"><%= labelData.get(entry.getKey()) %></label>
            <%= entry.getValue() %>
        </div>
        <%
            }
            ConnOracle.getConnectionOracle();
        
        %>
        <p class="mb-3"></p>
        <div class="mx-auto intro-button">
        	<input type="submit" value="Enregister" class="btn btn-primary d-inline-block mx-auto btn-xl">
        </div>
               
  		</form>
      </div>
    </div>
  </div>
</section>
  
<jsp:include page="../HeaderFooter/Footer.jsp" />