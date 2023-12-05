<?php
include 'connexion.php';
include 'Fonction.php';

$conn = conn();

if ($_SERVER['REQUEST_METHOD'] === 'GET' && isset($_GET['idvente'])) {
    $idvente = $_GET['idvente'];
    $vente = chargerVenteComplet($conn,$idvente);
    echo json_encode($vente);
}



?>
