<?php
include 'connexion.php';
include 'Formulaire.php';

$conn = conn();

if ($_SERVER['REQUEST_METHOD'] === 'GET') {
    $vente = chargerVente($conn);
    echo json_encode($vente);
}



?>
