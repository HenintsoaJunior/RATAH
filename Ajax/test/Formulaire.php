<?php
include 'connexion.php';
include 'Formulaire.php';

$conn = conn();
if (isset($_POST['date'], $_POST['produit'],$_POST['quantite'])) {
    $date = $_POST['date'];
    $produit = $_POST['produit'];
    $quantite = $_POST['quantite'];
    Insert($conn,$date, $produit, $quantite);
    echo json_encode(array('success' => true));
    
}
else{
    echo json_encode(array('success' => false));
}

?>
