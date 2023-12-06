CREATE OR REPLACE VIEW VueListeCommandesRestaurants AS
SELECT r.restaurant_id, r.nom AS nom_restaurant, c.nom_client, cmd.commande_id, m.nom_menu, cm.quantite, cmd.date_commande, f.date_facture
FROM Restaurant r
JOIN Menu m ON r.restaurant_id = m.restaurant_id
JOIN Commande_Menu cm ON m.menu_id = cm.menu_id
JOIN Commande cmd ON cm.commande_id = cmd.commande_id
JOIN Client c ON cmd.client_id = c.client_id
JOIN Facture f ON cmd.commande_id = f.commande_id;


CREATE OR REPLACE VIEW VueDetailFacturesClient AS
SELECT 
    c.nom_client,
    i.nom_intermediaire,
    r.restaurant_id,
	f.date_facture,
    LISTAGG(m.nom_menu || ' (Quantité: ' || cm.quantite || ')', ', ') 
    WITHIN GROUP (ORDER BY m.nom_menu) AS menus_commandes,
    SUM(m.prix * cm.quantite) AS montant_total
FROM 
    Facture f
JOIN 
    Commande cmd ON f.commande_id = cmd.commande_id
JOIN 
    Commande_Menu cm ON cmd.commande_id = cm.commande_id
JOIN 
    Menu m ON cm.menu_id = m.menu_id
JOIN 
    Client c ON cmd.client_id = c.client_id
JOIN 
    Restaurant r ON m.restaurant_id = r.restaurant_id
JOIN 
    Intermediaire i ON f.intermediaire_id = i.intermediaire_id
	
GROUP BY 
    c.nom_client, r.restaurant_id, i.nom_intermediaire,f.date_facture;



CREATE OR REPLACE VIEW VueMontantTotalCommandes AS
SELECT r.nom AS nom_restaurant,
       c.nom_client,
       i.nom_intermediaire,  
		f.date_facture,	   
       SUM(cm.quantite * m.prix) AS montant_total
FROM Client c
JOIN Commande cmd ON c.client_id = cmd.client_id
JOIN Commande_Menu cm ON cmd.commande_id = cm.commande_id
JOIN Menu m ON cm.menu_id = m.menu_id
JOIN Restaurant r ON r.restaurant_id = m.restaurant_id
LEFT JOIN Facture f ON cmd.commande_id = f.commande_id
LEFT JOIN Intermediaire i ON f.intermediaire_id = i.intermediaire_id
GROUP BY r.nom, c.nom_client, i.nom_intermediaire,f.date_facture;



CREATE OR REPLACE VIEW VueMontantTotalIntermediaires AS
SELECT r.nom AS nom_restaurant,r.restaurant_id, i.nom_intermediaire,f.date_facture, SUM(cm.quantite * m.prix_intermediaire) AS montant_obtenu
FROM Intermediaire i
JOIN Facture f ON i.intermediaire_id = f.intermediaire_id
JOIN Commande cmd ON f.commande_id = cmd.commande_id
JOIN Commande_Menu cm ON cmd.commande_id = cm.commande_id
JOIN Menu m ON cm.menu_id = m.menu_id
JOIN Restaurant r ON r.restaurant_id = m.restaurant_id
GROUP BY r.nom, i.nom_intermediaire,r.restaurant_id,f.date_facture;


CREATE OR REPLACE VIEW VueMontantIntRestaurant AS
SELECT 
    i.nom_intermediaire,
    r.restaurant_id,
	f.date_facture,
    SUM(cm.quantite * m.prix_intermediaire) AS montant_obtenu,
    r.nom AS nom_restaurant,
    SUM(cm.quantite) AS total_quantite,
    SUM((cm.quantite * m.prix) - (cm.quantite * m.prix_intermediaire)) AS montant_donne_resto
FROM 
    Intermediaire i
JOIN 
    Facture f ON i.intermediaire_id = f.intermediaire_id
JOIN 
    Commande cmd ON f.commande_id = cmd.commande_id
JOIN 
    Commande_Menu cm ON cmd.commande_id = cm.commande_id
JOIN 
    Menu m ON cm.menu_id = m.menu_id 
JOIN 
    Restaurant r ON r.restaurant_id = m.restaurant_id
GROUP BY 
    i.nom_intermediaire, r.restaurant_id, r.nom,f.date_facture
HAVING 
    SUM(cm.quantite * m.prix_intermediaire) >= MIN(i.recette_minimum) 
    AND SUM(cm.quantite * m.prix_intermediaire) <= MAX(i.forfait);
	
	

CREATE OR REPLACE VIEW VueChiffreAffairesRestaurants AS
SELECT 
    r.nom AS nom_restaurant,
    r.restaurant_id,
    f.date_facture, 
    SUM(cm.quantite * m.prix) AS chiffre_affaires
FROM 
    Restaurant r
JOIN 
    Menu m ON r.restaurant_id = m.restaurant_id
JOIN 
    Commande_Menu cm ON m.menu_id = cm.menu_id
JOIN 
    Commande cmd ON cm.commande_id = cmd.commande_id
JOIN 
    Facture f ON cmd.commande_id = f.commande_id
GROUP BY 
    r.nom, r.restaurant_id,f.date_facture;


CREATE OR REPLACE VIEW VuePrixRevientRestaurants AS
SELECT 
    r.nom AS nom_restaurant,
    r.restaurant_id,
    f.date_facture,
    SUM(cm.quantite * m.prix_revient) AS prix_revient
FROM 
    Restaurant r
JOIN 
    Menu m ON r.restaurant_id = m.restaurant_id
JOIN 
    Commande_Menu cm ON m.menu_id = cm.menu_id
JOIN 
    Commande cmd ON cm.commande_id = cmd.commande_id
JOIN 
    Facture f ON cmd.commande_id = f.commande_id
GROUP BY 
    r.nom, r.restaurant_id, f.date_facture;



CREATE OR REPLACE VIEW VuStatistiques AS
SELECT r.nom, r.restaurant_id, COALESCE(VCA.date_facture, VPR.date_facture, VIR.date_facture) AS date_stat,
       COALESCE(chiffre_affaires, 0) AS chiffre_affaires,
       COALESCE(prix_revient, 0) AS prix_revient,
       COALESCE(montant_donne_resto, 0) AS montant_intermediaire,
       COALESCE(chiffre_affaires, 0) - COALESCE(prix_revient, 0) - COALESCE(montant_donne_resto, 0) AS benefice
FROM Restaurant r
LEFT JOIN (
    SELECT restaurant_id, date_facture, SUM(chiffre_affaires) AS chiffre_affaires
    FROM VueChiffreAffairesRestaurants
    GROUP BY restaurant_id, date_facture
) VCA ON r.restaurant_id = VCA.restaurant_id
LEFT JOIN (
    SELECT restaurant_id, date_facture, SUM(prix_revient) AS prix_revient
    FROM VuePrixRevientRestaurants
    GROUP BY restaurant_id, date_facture
) VPR ON r.restaurant_id = VPR.restaurant_id AND VPR.date_facture = VCA.date_facture
LEFT JOIN (
    SELECT restaurant_id, date_facture, SUM(montant_donne_resto) AS montant_donne_resto
    FROM VueMontantIntRestaurant
    GROUP BY restaurant_id, date_facture
) VIR ON r.restaurant_id = VIR.restaurant_id AND VIR.date_facture = COALESCE(VCA.date_facture, VPR.date_facture);


SELECT *
FROM VuStatistiques
WHERE restaurant_id = 1
    AND EXTRACT(DAY FROM date_stat) = 5
    AND EXTRACT(MONTH FROM date_stat) = 12
    AND EXTRACT(YEAR FROM date_stat) = 2023;
	

