<?php

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

?>