<%@page import="conn.ConnOracle"%>
<%@page import="conn.GetConnection"%>
<%@page import="java.util.Map"%>
<%@page import="formulaire.Formulaire"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

 <jsp:include page="../HeaderFooter/Header.jsp" />
 
 <%
 String tableName = request.getParameter("tableNames");
 HttpSession sessions = request.getSession(true);
 sessions.setAttribute("tableNames", tableName);
 %>
<section class="page-section clearfix">
        <div class="container">
            <div class="intro"><img class="img-fluid intro-img mb-3 mb-lg-0 rounded" src="../assets/img/intro.jpg">
                <div class="text-center intro-text p-5 rounded bg-faded">
                    <h2 class="section-heading mb-4"><span class="section-heading-upper"></span><span class="section-heading-lower"></span></h2>
                  		<br>
                  		<div class="input-group"></div>
                  		
					        <p class="mb-3"></p>
					        <div class="mx-auto intro-button">
								<form action="Tableau.jsp" method="get">
								    <label for="annee">Année :</label>
								    <input type="number" id="annee" name="annee" min="1900" max="2100" required>
									<br>
									<br>
								    <label for="mois">Mois :</label>
								    <select id="mois" name="mois" required>
								        <option value="">Sélectionner un mois</option>
								        <option value="1">Janvier</option>
								        <option value="2">Février</option>
								        <option value="3">Mars</option>
								        <option value="4">Avril</option>
								        <option value="5">Mai</option>
								        <option value="6">Juin</option>
								        <option value="7">Juillet</option>
								        <option value="8">Août</option>
								        <option value="9">Septembre</option>
								        <option value="10">Octobre</option>
								        <option value="11">Novembre</option>
								        <option value="12">Décembre</option>
								    </select>
									<br>
									<br>
								    <input type="submit" value="Valider" class="btn btn-primary d-inline-block mx-auto btn-xl">
								</form>
									
							
					        </div>
					               
      					</div>
    				</div>
  				</div>
</section>

<jsp:include page="../HeaderFooter/Footer.jsp" />