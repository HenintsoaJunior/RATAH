<?php
include 'connexion.php';
include 'Fonction.php';

$conn = conn();

if ($_SERVER['REQUEST_METHOD'] === 'GET' && isset($_GET['idCategorie'])) {
    $idCategorie = $_GET['idCategorie'];
    $produits = chargerProduitParCategorie($conn, $idCategorie); // Utiliser l'ID de la catégorie envoyé depuis la requête
    echo json_encode($produits);
}
?>
