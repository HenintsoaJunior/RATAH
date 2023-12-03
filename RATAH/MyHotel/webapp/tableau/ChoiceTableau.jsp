<%@page import="conn.ConnOracle"%>
<%@page import="conn.GetConnection"%>
<%@page import="java.util.Map"%>
<%@page import="formulaire.Formulaire"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

 <jsp:include page="../HeaderFooter/Header.jsp" />
 
 <%
    String idRestaurant = request.getParameter("id_restaurant");
 
 	out.println(idRestaurant);
	 HttpSession sessions = request.getSession(true);
	 sessions.setAttribute("idRestaurant", idRestaurant);
	 
%>
<section class="page-section clearfix">
        <div class="container">
            <div class="intro"><img class="img-fluid intro-img mb-3 mb-lg-0 rounded" src="../assets/img/intro.jpg">
                <div class="text-center intro-text p-5 rounded bg-faded">
                    <h2 class="section-heading mb-4"><span class="section-heading-upper">Tableau</span><span class="section-heading-lower"></span></h2>
                  		<div class="input-group"></div>
                  		
					        <p class="mb-3"></p>
					        <div class="mx-auto intro-button">
								<a href="Tableau.jsp?tableNames=Commandes" class="btn btn-primary d-inline-block mx-auto btn-xl">Commandes</a>
								
								<a href="Tableau.jsp?tableNames=VueBeneficeRestaurants" class="btn btn-primary d-inline-block mx-auto btn-xl">Statistique</a>
							
					        </div>
					               
      					</div>
    				</div>
  				</div>
</section>

<jsp:include page="../HeaderFooter/Footer.jsp" />