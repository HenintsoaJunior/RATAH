<?php

function Insert($conn,$date, $produit, $quantite)
{
    try {
        $stmt = $conn->prepare("INSERT INTO vente (date, produit,quantite) VALUES (:dates,:produit,:quantite)");
        $stmt->bindParam(':dates', $date, PDO::PARAM_STR);
        $stmt->bindParam(':produit', $produit, PDO::PARAM_STR);
        $stmt->bindParam(':quantite', $quantite, PDO::PARAM_INT);
        $stmt->execute();
        $result = $stmt->fetch(PDO::FETCH_ASSOC);

    } catch (PDOException $e) {
        echo json_encode(array('success' => false, 'error' => $e->getMessage()));
    }
}
function chargerVente($conn)
{
    try {
        $stmt = $conn->query("SELECT * FROM vente");
        $vente = $stmt->fetchAll(PDO::FETCH_ASSOC);
        return $vente;
    } catch (PDOException $e) {
        return array('error' => 'Erreur lors du chargement des publications : ' . $e->getMessage());
    }
}

?>