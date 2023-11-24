<?php
header("Content-Type: application/json");

$servername = "localhost"; 
$username = "root"; 
$password = ""; 
$dbname = "ajax"; 


$conn = mysqli_connect($servername, $username, $password, $dbname);

if (!$conn) {
    die("La connexion a échoué : " . mysqli_connect_error());
}

$sql = "SELECT idPersonne,Nom,Prenom, (YEAR(AnneeNaissance))AS AnneeNaissance FROM personne";
$result = mysqli_query($conn, $sql);


$personnes = array();

if (mysqli_num_rows($result) > 0) {

    while ($row = mysqli_fetch_assoc($result)) {
        $personnes[] = $row;
    }
}
sleep(3);
echo json_encode($personnes);
?>
