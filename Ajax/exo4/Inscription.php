<?php
include 'connexion.php';

$conn = conn();
if (isset($_POST['email'], $_POST['password'],$_POST['nom'])) {
    $nom = $_POST['nom'];
    $email = $_POST['email'];
    $password = $_POST['password'];
    Inscription($conn,$nom, $email, $password);
    echo json_encode(array('success' => true));
    
}

function Inscription($conn,$nom, $email, $password)
{
    try {
        $stmt = $conn->prepare("INSERT INTO membre (nom, email,pwd) VALUES (:nom, :email,:password)");
        $stmt->bindValue('nom', $nom);
        $stmt->bindValue(':email', $email);
        $stmt->bindValue(':password', $password);
        $stmt->execute();

        $result = $stmt->fetch(PDO::FETCH_ASSOC);

    } catch (PDOException $e) {
        echo json_encode(array('success' => false, 'error' => $e->getMessage()));
    }
}
?>
