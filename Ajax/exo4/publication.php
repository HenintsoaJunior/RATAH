<?php
include 'connexion.php';

$conn = conn();

if ($_SERVER['REQUEST_METHOD'] === 'GET') {

    $publications = chargerPublications($conn);
    
    $commentaires = chargerCommentaire($conn); 

    $data = array(
        'publications' => $publications,
        'commentaires' => $commentaires
    );
    echo json_encode($data);
}
elseif ($_SERVER['REQUEST_METHOD'] === 'POST') {
        if (isset($_POST['message'])) {
           
            $message = $_POST['message'];
            publierMessage($conn, $message);
        } elseif (isset($_POST['id_publication']) && isset($_POST['commentaire'])) {
           
            $publicationId = $_POST['id_publication'];
            $commentaireTexte = $_POST['commentaire'];
            ajouterCommentaire($conn, $publicationId, $commentaireTexte);
        } else {
            echo json_encode(array('success' => false, 'error' => 'Requête invalide'));
        }
}

function publierMessage($conn, $message)
{
    try {
        $stmt = $conn->prepare("INSERT INTO publication (message) VALUES (:message)");
        $stmt->bindValue(':message', $message);
        $stmt->execute();

        echo json_encode(array('success' => true));
    } catch (PDOException $e) {
        echo json_encode(array('success' => false, 'error' => $e->getMessage()));
    }
}

function chargerPublications($conn)
{
    try {
        $stmt = $conn->query("SELECT * FROM publication ORDER BY id DESC");
        $publications = $stmt->fetchAll(PDO::FETCH_ASSOC);
        return $publications;
    } catch (PDOException $e) {
        return array('error' => 'Erreur lors du chargement des publications : ' . $e->getMessage());
    }
}

function ajouterCommentaire($conn, $publicationId, $commentaireTexte)
{
    try {
        $stmt = $conn->prepare("INSERT INTO commentaires (id_publication, commentaire) VALUES (:id_publication, :commentaire)");
        $stmt->bindValue(':id_publication', $publicationId);
        $stmt->bindValue(':commentaire', $commentaireTexte);
        $stmt->execute();

        echo json_encode(array('success' => true));
    } catch (PDOException $e) {
        echo json_encode(array('success' => false, 'error' => $e->getMessage()));
    }
}

function chargerCommentaire($conn)
{
    try {
        $stmt = $conn->query("SELECT * FROM commentaires");
        $commentaires = $stmt->fetchAll(PDO::FETCH_ASSOC);
        return $commentaires;
    } catch (PDOException $e) {
        return array('error' => 'Erreur lors du chargement des publications : ' . $e->getMessage());
    }
}


?>
