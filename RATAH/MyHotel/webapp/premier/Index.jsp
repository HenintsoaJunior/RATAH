<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="output.Tableau"%>
<%@page import="java.util.List"%>
<%@page import="conn.GetConnection"%>
<%@page import="java.util.Map"%>
<%@page import="formulaire.Formulaire"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

 <jsp:include page="../HeaderFooter/Header1.jsp" />
    <% 
        String tableName = "Restaurant"; // Remplacez avec le nom de votre table
        List<Map<String, Object>> tableau = Tableau.Tableau(tableName);

        if ("Restaurant".equals(tableName) && !tableau.isEmpty()) {
            for (Map<String, Object> row : tableau) {
            	BigDecimal idResto = (BigDecimal) row.get("RESTAURANT_ID");
                String nomResto = (String) row.get("NOM");
                String imageResto = (String) row.get("IMAGE"); // Assurez-vous que le chemin de l'image est correct
                String Adresse = (String) row.get("ADRESSE");

                %>
                <section class="page-section">
                    <div class="container">
                        <div class="product-item">
                            <div class="d-flex product-item-title">
                                <div class="d-flex me-auto bg-faded p-5 rounded">
                                    <h2 class="section-heading mb-0"><span class="section-heading-upper">Blended to Perfection</span><span class="section-heading-lower"><%= nomResto %></span></h2>
                                </div>
                            </div>
                            <img class="img-fluid d-flex mx-auto product-item-img mb-3 mb-lg-0 rounded" src="../assets/img/<%= imageResto %>">
                            <div class="bg-faded p-5 rounded">
                                  <h2><a href="../tableau/ChoiceTableau.jsp?id_restaurant=<%= idResto %>">Visiter</a></h2>
                            </div>
                        </div>
                    </div>
                </section>
            <% 
            }
        }
    %>

<jsp:include page="../HeaderFooter/Footer.jsp" />