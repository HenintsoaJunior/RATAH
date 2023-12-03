<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="output.Tableau"%>
<%@page import="java.util.List"%>
<%@page import="conn.GetConnection"%>
<%@page import="java.util.Map"%>
<%@page import="formulaire.Formulaire"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

 <jsp:include page="../HeaderFooter/Header.jsp" />
    <% 
        String tableName = "Menu"; // Remplacez avec le nom de votre table
        String idRestaurantStr = (String) session.getAttribute("idRestaurant");
        int idRestaurant = Integer.parseInt(idRestaurantStr);
        List<Map<String, Object>> tableau = Tableau.Tableau(tableName,idRestaurant);

        if ("Menu".equals(tableName) && !tableau.isEmpty()) {
            for (Map<String, Object> row : tableau) {
                String nomMenu = (String) row.get("NOM_MENU");
                String imageMenu = (String) row.get("IMAGE"); // Assurez-vous que le chemin de l'image est correct
                BigDecimal prixMenu = (BigDecimal) row.get("PRIX");

                %>
                <section class="page-section">
                    <div class="container">
                        <div class="product-item">
                            <div class="d-flex product-item-title">
                                <div class="d-flex me-auto bg-faded p-5 rounded">
                                    <h2 class="section-heading mb-0"><span class="section-heading-upper">Blended to Perfection</span><span class="section-heading-lower"><%= nomMenu %></span></h2>
                                </div>
                            </div>
                            <img class="img-fluid d-flex mx-auto product-item-img mb-3 mb-lg-0 rounded" src="../assets/img/<%= imageMenu %>">
                            <div class="bg-faded p-5 rounded">
                                <h2><%= prixMenu %> AR <a href="Commander.jsp">Commander</a></h2>
                            </div>
                        </div>
                    </div>
                </section>
            <% 
            }
        }
    %>

<jsp:include page="../HeaderFooter/Footer.jsp" />