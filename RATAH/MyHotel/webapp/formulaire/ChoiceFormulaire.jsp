<%@page import="conn.ConnOracle"%>
<%@page import="conn.GetConnection"%>
<%@page import="java.util.Map"%>
<%@page import="formulaire.Formulaire"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

 <jsp:include page="../HeaderFooter/Header.jsp" />
<section class="page-section clearfix">
        <div class="container">
            <div class="intro">
                
       	<div class="mx-auto intro-button">
       		<a href="Formulaire.jsp?tableNames=Restaurant" class="btn btn-primary d-inline-block mx-auto btn-xl">Restaurant</a>		
			<a href="Formulaire.jsp?tableNames=Client" class="btn btn-primary d-inline-block mx-auto btn-xl">Clients</a>		
			<a href="Formulaire.jsp?tableNames=Intermediaire" class="btn btn-primary d-inline-block mx-auto btn-xl">Intermediaires</a>
			
			<a href="Formulaire.jsp?tableNames=Menu" class="btn btn-primary d-inline-block mx-auto btn-xl">Menus</a>
			<a href="Formulaire.jsp?tableNames=Commande" class="btn btn-primary d-inline-block mx-auto btn-xl">Commandes</a>
			<a href="Formulaire.jsp?tableNames=Commande_Menu" class="btn btn-primary d-inline-block mx-auto btn-xl">CommandesMenus</a>
			
			
			<a href="Formulaire.jsp?tableNames=Facture" class="btn btn-primary d-inline-block mx-auto btn-xl">Factures</a>
		
        </div>
               
      </div>
    </div>
</section>
  
<jsp:include page="../HeaderFooter/Footer.jsp" />