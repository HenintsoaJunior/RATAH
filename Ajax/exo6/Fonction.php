<?php

function ObtenirVente($conn,$idVente)
{
    try {
        $stmt = $conn->prepare("SELECT * FROM VenteComplet WHERE idVente=:idVente");
        $stmt->bindParam(':idVente', $idVente);
        $stmt->execute();
        $vente = $stmt->fetchAll(PDO::FETCH_ASSOC);
        return $vente;
    } catch (PDOException $e) {
        return array('error' => 'Erreur lors du chargement des publications : ' . $e->getMessage());
    }
}

function modifierVente($conn, $idVente, $idproduit,$idCategorie,$quantite,$prix_unitaire,$date) {
    try {
        $sql = "UPDATE vente SET idproduit = :idproduit, idcategorie = :idcategorie, quantite = :quantite, prix_unitaire = :prix_unitaire, date_vente = :date_vente WHERE idVente = :idVente";
        
        $stmt = $conn->prepare($sql);
        
        // Liaison des valeurs avec les paramètres de la requête
        $stmt->bindParam(':idproduit', $idproduit);
        $stmt->bindParam(':idcategorie', $idCategorie);
        $stmt->bindParam(':quantite', $quantite);
        $stmt->bindParam(':prix_unitaire', $prix_unitaire);
        $stmt->bindParam(':date_vente', $date);
        $stmt->bindParam(':idVente', $idVente);
          
        // Exécution de la requête
        $stmt->execute();
        
        return array('success' => 'Les données de la vente ont été mises à jour avec succès.');
    } catch (PDOException $e) {
        return array('error' => 'Erreur lors de la modification des données de la vente : ' . $e->getMessage());
    }
}

function supprimerVente($conn, $idVente) {
    try {
        $stmt = $conn->prepare("DELETE FROM vente WHERE idvente = :idVente");
        $stmt->bindParam(':idVente', $idVente);
        $stmt->execute();
        return array('success' => 'La vente a été supprimée avec succès.');
    } catch (PDOException $e) {
        return array('error' => 'Erreur lors de la suppression de la vente : ' . $e->getMessage());
    }
}

function Insert($conn,$idCategorie, $idproduit,$quantite,$prix_unitaire,$date)
{
    try {
        $stmt = $conn->prepare("INSERT INTO vente (idCategorie, idproduit,quantite,prix_unitaire,date_vente) VALUES (:idCategorie,:idproduit,:quantite,:prix_unitaire,:date_vente)");
        $stmt->bindParam(':idCategorie', $idCategorie, PDO::PARAM_STR);
        $stmt->bindParam(':idproduit', $idproduit, PDO::PARAM_STR);
        $stmt->bindParam(':quantite', $quantite, PDO::PARAM_INT);
        $stmt->bindParam(':prix_unitaire', $prix_unitaire, PDO::PARAM_INT);
        $stmt->bindParam(':date_vente', $date, PDO::PARAM_INT);
        $stmt->execute();
        $result = $stmt->fetch(PDO::FETCH_ASSOC);

    } catch (PDOException $e) {
        echo json_encode(array('success' => false, 'error' => $e->getMessage()));
    }
}

function chargerProduitCategorie($conn)
{
    try {
        $stmt = $conn->query("SELECT * FROM categories");
        $vente = $stmt->fetchAll(PDO::FETCH_ASSOC);
        return $vente;
    } catch (PDOException $e) {
        return array('error' => 'Erreur lors du chargement des publications : ' . $e->getMessage());
    }
}

function chargerProduitParCategorie($conn, $idCategorie)
{
    try {
        $stmt = $conn->prepare("SELECT * FROM produits WHERE idCategorie = :idCategorie");
        $stmt->bindParam(':idCategorie', $idCategorie);
        $stmt->execute();
        $produits = $stmt->fetchAll(PDO::FETCH_ASSOC);
        return $produits;
    } catch (PDOException $e) {
        return array('error' => 'Erreur lors du chargement des produits : ' . $e->getMessage());
    }
}


function chargerVente($conn)
{
    try {
        $stmt = $conn->query("SELECT * FROM VenteComplet");
        $vente = $stmt->fetchAll(PDO::FETCH_ASSOC);
        return $vente;
    } catch (PDOException $e) {
        return array('error' => 'Erreur lors du chargement des publications : ' . $e->getMessage());
    }
}

function chargerVenteComplet($conn,$idvente)
{
    try {
        $stmt = $conn->query("SELECT * FROM VenteComplet WHERE idvente=$idvente");
        $vente = $stmt->fetchAll(PDO::FETCH_ASSOC);
        return $vente;
    } catch (PDOException $e) {
        return array('error' => 'Erreur lors du chargement des publications : ' . $e->getMessage());
    }
}

?>