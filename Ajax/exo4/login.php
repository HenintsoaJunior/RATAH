<?php
include 'connexion.php';

$conn = conn();
if (isset($_POST['email'], $_POST['password'])) {
    $email = $_POST['email'];
    $password = $_POST['password'];
    login($conn, $email, $password);
} else {
    echo json_encode(array('success' => false));
}

function login($conn, $email, $password)
{
    try {
        $stmt = $conn->prepare("SELECT * FROM membre WHERE email = :email AND pwd = :password");
        $stmt->bindValue(':email', $email);
        $stmt->bindValue(':password', $password);
        $stmt->execute();

        $result = $stmt->fetch(PDO::FETCH_ASSOC);

        if ($result) {
            echo json_encode(array('success' => true));
        } else {
            echo json_encode(array('success' => false));
        }
    } catch (PDOException $e) {
        echo json_encode(array('success' => false, 'error' => $e->getMessage()));
    }
}
?>
