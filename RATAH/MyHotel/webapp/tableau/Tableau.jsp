<%@page import="java.util.ArrayList"%>
<%@page import="output.Tableau"%>
<%@page import="java.util.List"%>
<%@page import="conn.GetConnection"%>
<%@page import="java.util.Map"%>
<%@page import="formulaire.Formulaire"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

 <jsp:include page="../HeaderFooter/Header.jsp" />
<section class="page-section clearfix">
        <div class="container">
            <div class="intro">
				<div class="text-center intro-text p-5 rounded bg-faded" style="width: 100%;">	
					<%
					    String tableName = (String) session.getAttribute("tableNames");
						String MoisStr = request.getParameter("mois");
						String AnneeStr = request.getParameter("annee");
					    String idRestaurantStr = (String) session.getAttribute("idRestaurant");
				        int idRestaurant = Integer.parseInt(idRestaurantStr);
				        int mois=Integer.parseInt(MoisStr);
				        int annee=Integer.parseInt(AnneeStr);
					    List<Map<String, Object>> tableau = Tableau.Tableau(tableName,idRestaurant,mois,annee);
					
					    // Obtenez les noms de colonnes à partir du premier élément du tableau s'il existe
					    List<String> attribut = new ArrayList<>();
					    if (!tableau.isEmpty()) {
					        Map<String, Object> firstRow = tableau.get(0);
					        attribut.addAll(firstRow.keySet());
					    }
					%>

					<h1>Tableau des Données</h1>

				    <table class="table table-dark table-striped">
				        <tr>
				            <% for (String columnName : attribut) { %>
				                <th><%= columnName %></th>
				            <% } %>
				        </tr>
				        
				        <% for (Map<String, Object> row : tableau) { %>
				            <tr>
				                <% for (String columnName : attribut) { %>
				                    <td><%= row.get(columnName) %></td>
				            
				                <% } %>
				                
				            </tr>
				        <% } %>
				           
				    </table>			    
      		</div>
    	</div>
  	</div>
</section>
  
<jsp:include page="../HeaderFooter/Footer.jsp" />