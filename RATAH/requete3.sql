CREATE OR REPLACE VIEW VueListeCommandesRestaurants AS
SELECT r.restaurant_id,r.nom AS nom_restaurant, c.nom_client, cmd.commande_id, m.nom_menu, cm.quantite, cmd.date_commande
FROM Restaurant r
JOIN Menu m ON r.restaurant_id = m.restaurant_id
JOIN Commande cmd ON m.menu_id = cmd.menu_id
JOIN Commande_Menu cm ON cmd.commande_id = cm.commande_id
JOIN Client c ON cmd.client_id = c.client_id;

CREATE OR REPLACE VIEW VueDetailFacturesClient AS
SELECT c.nom_client,
    m.restaurant_id,
    GROUP_CONCAT(CONCAT(m.nom_menu, ' (Quantité: ', cm.quantite, ')') ORDER BY m.nom_menu SEPARATOR ', ') AS menus_commandes,
    SUM(m.prix * cm.quantite) AS montant_total
FROM Facture f
JOIN Commande cmd ON f.commande_id = cmd.commande_id
JOIN Commande_Menu cm ON cmd.commande_id = cm.commande_id
JOIN Menu m ON cm.menu_id = m.menu_id
JOIN Client c ON cmd.client_id = c.client_id
GROUP BY c.nom_client, m.restaurant_id;



CREATE OR REPLACE VIEW VueMontantTotalCommandes AS
SELECT r.nom AS nom_restaurant,
       c.nom_client,
       i.nom_intermediaire,
       SUM(cm.quantite * m.prix) AS montant_total
FROM Client c
JOIN Commande cmd ON c.client_id = cmd.client_id
JOIN Commande_Menu cm ON cmd.commande_id = cm.commande_id
JOIN Menu m ON cm.menu_id = m.menu_id
JOIN Restaurant r ON r.restaurant_id = m.restaurant_id
LEFT JOIN Facture f ON cmd.commande_id = f.commande_id
LEFT JOIN Intermediaire i ON f.intermediaire_id = i.intermediaire_id
GROUP BY r.nom, c.nom_client, i.nom_intermediaire;



CREATE OR REPLACE VIEW VueMontantTotalIntermediaires AS
SELECT r.nom AS nom_restaurant,r.restaurant_id, i.nom_intermediaire, SUM(cm.quantite * m.prix_intermediaire) AS montant_obtenu
FROM Intermediaire i
LEFT JOIN Facture f ON i.intermediaire_id = f.intermediaire_id
LEFT JOIN Commande cmd ON f.commande_id = cmd.commande_id
LEFT JOIN Commande_Menu cm ON cmd.commande_id = cm.commande_id
LEFT JOIN Menu m ON cm.menu_id = m.menu_id
LEFT JOIN Restaurant r ON r.restaurant_id = m.restaurant_id
GROUP BY r.nom, i.nom_intermediaire,r.restaurant_id;


CREATE OR REPLACE VIEW VueMontantIntRestaurant AS
SELECT 
    i.nom_intermediaire,
    r.restaurant_id,
    SUM(cm.quantite * m.prix_intermediaire) AS montant_obtenu,
    r.nom AS nom_restaurant,
    SUM(cm.quantite) AS total_quantite,
    SUM((cm.quantite * m.prix) - (cm.quantite * m.prix_intermediaire)) AS montant_donne_resto
FROM 
    Intermediaire i
LEFT JOIN 
    Facture f ON i.intermediaire_id = f.intermediaire_id
LEFT JOIN 
    Commande cmd ON f.commande_id = cmd.commande_id
LEFT JOIN 
    Commande_Menu cm ON cmd.commande_id = cm.commande_id
LEFT JOIN 
    Menu m ON cm.menu_id = m.menu_id 
LEFT JOIN 
    Restaurant r ON r.restaurant_id = m.restaurant_id
GROUP BY 
    i.nom_intermediaire, r.restaurant_id, r.nom
HAVING 
    SUM(cm.quantite * m.prix_intermediaire) >= MIN(i.recette_minimum) 
    AND SUM(cm.quantite * m.prix_intermediaire) <= MAX(i.forfait);


CREATE OR REPLACE VIEW VueChiffreAffairesRestaurants AS
SELECT r.nom AS nom_restaurant,r.restaurant_id,
       SUM(cm.quantite * m.prix) AS chiffre_affaires
FROM Restaurant r
LEFT JOIN Menu m ON r.restaurant_id = m.restaurant_id
LEFT JOIN Commande_Menu cm ON m.menu_id = cm.menu_id
GROUP BY r.nom, r.restaurant_id;



CREATE OR REPLACE VIEW VuePrixRevientRestaurants AS
SELECT r.nom AS nom_restaurant,r.restaurant_id,
       SUM(cm.quantite * m.prix_revient) AS prix_revient
FROM Restaurant r
LEFT JOIN Menu m ON r.restaurant_id = m.restaurant_id
LEFT JOIN Commande_Menu cm ON m.menu_id = cm.menu_id
GROUP BY r.nom, r.restaurant_id;



CREATE OR REPLACE VIEW VueBeneficeRestaurants AS
SELECT c.nom, c.restaurant_id,
       COALESCE(SUM(VCA.chiffre_affaires), 0) AS chiffre_affaires,
       COALESCE(SUM(VPR.prix_revient), 0) AS prix_revient,
       COALESCE(SUM(VIR.montant_donne_resto), 0) AS montant_obtenu_intermediaire,
       COALESCE(SUM(VCA.chiffre_affaires), 0) - COALESCE(SUM(VPR.prix_revient), 0) - COALESCE(SUM(VIR.montant_donne_resto), 0) AS benefice
FROM Restaurant c
LEFT JOIN VueChiffreAffairesRestaurants VCA ON c.restaurant_id = VCA.restaurant_id
LEFT JOIN VuePrixRevientRestaurants VPR ON c.restaurant_id = VPR.restaurant_id
LEFT JOIN VueMontantIntRestaurant VIR ON c.restaurant_id = VIR.restaurant_id
GROUP BY c.nom, c.restaurant_id;
