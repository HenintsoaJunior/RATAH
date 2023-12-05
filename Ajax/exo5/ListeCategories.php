<?php
include 'connexion.php';
include 'Fonction.php';

$conn = conn();

if ($_SERVER['REQUEST_METHOD'] === 'GET') {
    $vente = chargerProduitCategorie($conn);
    echo json_encode($vente);
}



?>
