CREATE OR REPLACE VIEW ListeCommande AS
SELECT C.nom AS Nom_Client, CM.commande_id AS ID_Commande, M.nom_menu AS Nom_Menu, CM.quantite AS Quantite
FROM Clients C
JOIN Commandes CO ON C.client_id = CO.client_id
JOIN CommandesMenus CM ON CO.commande_id = CM.commande_id
JOIN Menus M ON CM.menu_id = M.menu_id
ORDER BY C.nom, CM.commande_id;



CREATE OR REPLACE VIEW VueTotalMontant AS
SELECT
    SUM(cm.quantite) AS quantite_repas,
    SUM(m.prix * cm.quantite) AS prix_total,
    MIN(f.date_facture) AS date_facture
FROM
    Factures f
JOIN
    Menus m ON f.menu_id = m.menu_id
JOIN
    CommandesMenus cm ON m.menu_id = cm.menu_id;
	
	
CREATE OR REPLACE VIEW VueIntermediaireRepas AS
SELECT
    i.intermediaire_id,
    SUM(cm.quantite) AS quantite_repas,
    SUM(m.prix_intermediaire * cm.quantite) AS prix_intermediaire_total
FROM
    CommandesMenus cm
JOIN
    Menus m ON cm.menu_id = m.menu_id
JOIN
    Factures f ON cm.commande_id = f.commande_id AND cm.menu_id = f.menu_id
JOIN
    Intermediaires i ON f.intermediaire_id = i.intermediaire_id
GROUP BY
    i.intermediaire_id;
	


CREATE OR REPLACE VIEW VueMontantIntermediaire AS
SELECT
    f.intermediaire_id,
    SUM((m.prix - m.prix_intermediaire) * cm.quantite) AS montant_a_donner,
    MIN(f.date_facture) AS date_facture
FROM
    Factures f
JOIN
    Menus m ON f.menu_id = m.menu_id
JOIN
    CommandesMenus cm ON m.menu_id = cm.menu_id
WHERE
    f.date_facture >= TRUNC(SYSDATE, 'MONTH') -- Commence le mois actuel
    AND f.date_facture < ADD_MONTHS(TRUNC(SYSDATE, 'MONTH'), 1) -- Termine le mois actuel
    AND f.intermediaire_id IS NOT NULL -- Exclure les factures avec un intermédiaire NULL
GROUP BY
    f.intermediaire_id
HAVING
    SUM((m.prix - m.prix_intermediaire) * cm.quantite) BETWEEN 500000 AND 10000000;	

CREATE OR REPLACE VIEW TotalMontantPourIntermediaire AS
SELECT
    COALESCE(SUM(montant_a_donner), 0) AS total_montant
FROM
    VueMontantIntermediaire;



CREATE OR REPLACE VIEW VueChiffreAffaires AS
SELECT 
    MIN(date_facture) AS date_facture,
    SUM(prix_total) AS chiffre_affaires_net
FROM 
    VueTotalMontant
WHERE 
    date_facture >= TRUNC(SYSDATE, 'MONTH')
    AND date_facture < ADD_MONTHS(TRUNC(SYSDATE, 'MONTH'), 1);

	
CREATE OR REPLACE VIEW VuePrixRevientTotal AS
SELECT 
    MIN(f.date_facture) AS date_facture,
    SUM(m.prix_revient * cm.quantite) AS prix_revient_total
FROM 
    Factures f
JOIN 
    Menus m ON f.menu_id = m.menu_id
JOIN 
    CommandesMenus cm ON cm.menu_id = m.menu_id
WHERE 
    f.date_facture >= TRUNC(SYSDATE, 'MONTH')
    AND f.date_facture < ADD_MONTHS(TRUNC(SYSDATE, 'MONTH'), 1);


CREATE OR REPLACE VIEW VueBenefice AS
SELECT 
    COALESCE((
        SELECT NVL(SUM(chiffre_affaires_net), 0)
        FROM VueChiffreAffaires
        WHERE date_facture >= TRUNC(SYSDATE, 'MONTH') 
            AND date_facture < ADD_MONTHS(TRUNC(SYSDATE, 'MONTH'), 1)
    ), 0) - 
    COALESCE((
        SELECT NVL(SUM(prix_revient_total), 0)
        FROM VuePrixRevientTotal
        WHERE date_facture >= TRUNC(SYSDATE, 'MONTH') 
            AND date_facture < ADD_MONTHS(TRUNC(SYSDATE, 'MONTH'), 1)
    ), 0) -
    COALESCE((
        SELECT NVL(SUM(montant_a_donner), 0)
        FROM VueMontantIntermediaire
        WHERE date_facture >= TRUNC(SYSDATE, 'MONTH') 
            AND date_facture < ADD_MONTHS(TRUNC(SYSDATE, 'MONTH'), 1)
    ), 0) AS beneficeDuMois,
    LAST_DAY(SYSDATE) AS FinDuMois
FROM DUAL;


CREATE OR REPLACE VIEW Statistique AS 
SELECT 
    COALESCE(vca.chiffre_affaires_net, 0) AS ChiffreAffairesNet,
    COALESCE(SUM(vmi.montant_a_donner), 0) AS MontantADonner,
    COALESCE(vprt.prix_revient_total, 0) AS PrixRevientTotal,
    COALESCE(vb.beneficeDuMois, 0) AS BeneficeDuMois,
    COALESCE(vb.FinDuMois, LAST_DAY(SYSDATE)) AS FinDuMois
FROM 
    VueChiffreAffaires vca
LEFT JOIN VueMontantIntermediaire vmi ON vca.date_facture = vmi.date_facture
LEFT JOIN VuePrixRevientTotal vprt ON vca.date_facture = vprt.date_facture
CROSS JOIN VueBenefice vb
GROUP BY
    vca.chiffre_affaires_net,
    vprt.prix_revient_total,
    vb.beneficeDuMois,
    vb.FinDuMois;

