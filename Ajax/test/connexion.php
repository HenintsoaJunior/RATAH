<?php
function conn()
{
    try {
        $conn = new PDO("pgsql:host=localhost;port=5432;dbname=vente", "postgres", "carasco20");
        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    } catch (PDOException $e) {
        echo "Connection failed: " . $e->getMessage();
    }
    return $conn;
}

?>
