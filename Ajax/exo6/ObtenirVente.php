<?php
include 'connexion.php';
include 'Fonction.php';

$conn = conn();

if ($_SERVER['REQUEST_METHOD'] === 'GET') {
    $idVente = $_GET['idvente'];
    $vente = ObtenirVente($conn,$idVente);
    echo json_encode($vente);
}
?>
