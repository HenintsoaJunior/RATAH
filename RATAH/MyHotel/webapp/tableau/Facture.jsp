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
        String tableName = "Facture"; // Remplacez avec le nom de votre table
        String idRestaurantStr = (String) session.getAttribute("idRestaurant");
        int idRestaurant = Integer.parseInt(idRestaurantStr);
        List<Map<String, Object>> tableau = Tableau.Tableau(tableName,idRestaurant);

        if ("Facture".equals(tableName) && !tableau.isEmpty()) {
            for (Map<String, Object> row : tableau) {
                String nomClient = (String) row.get("NOM_CLIENT");
                String menusCommandes = (String) row.get("MENUS_COMMANDES"); // Assurez-vous que le chemin de l'image est correct
                BigDecimal Montant = (BigDecimal) row.get("MONTANT_TOTAL");

                %>
    <section class="page-section cta">
        <div class="container">
            <div class="row">
                <div class="col-xl-9 mx-auto">
                    <div class="text-center cta-inner rounded">
                        <h2 class="section-heading mb-5"><span class="section-heading-upper">Come On In</span><span class="section-heading-lower">FACTURE</span></h2>
                        <ul class="list-unstyled text-start mx-auto list-hours mb-5">
                            <li class="d-flex list-unstyled-item list-hours-item"><%= nomClient %><span class="ms-auto">  :  <%= menusCommandes %></span></li>
                        </ul>
                        <p class="address mb-5"><em><strong><%= Montant%> Ar</strong></em></p>
                       
                    </div>
                </div>
            </div>
        </div>
    </section>
    
      <% 
            }
        }
    %>

<jsp:include page="../HeaderFooter/Footer.jsp" />