<?php
include 'connexion.php';
include 'Fonction.php';

$conn = conn();

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    if (isset($_POST['idvente'])) {
        
        $idVente = $_POST['idvente'];
        $date = $_POST['date'];
        $categorie = $_POST['categorie'];
        $produit = $_POST['produit'];
        $prixUnitaire = $_POST['prixUnitaire'];
        $quantite = $_POST['quantite'];

        $nouvellesDonnees = array(
            'produit' => $produit,
            'categorie' => $categorie,
            'quantite' => $quantite,
            'prixUnitaire' => $prixUnitaire,
            'date' => $date
        );

        $resultat = modifierVente($conn, $idVente, $nouvellesDonnees);

        header('Content-Type: application/json');
        echo json_encode($resultat);
    } else {
        header("HTTP/1.1 400 Bad Request");
        echo json_encode(array('error' => 'Toutes les données nécessaires ne sont pas fournies.'));
    }
} else {
    header("HTTP/1.1 405 Method Not Allowed");
    echo json_encode(array('error' => 'Méthode non autorisée.'));
}
?>
