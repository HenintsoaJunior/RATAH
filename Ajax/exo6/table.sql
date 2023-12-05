CREATE TABLE Categories(
    idCategorie SERIAL PRIMARY KEY,
    nom VARCHAR(10)
);

CREATE TABLE Produits(
    idProduit SERIAL PRIMARY KEY,
    idCategorie INT,
    nom VARCHAR(10),
    FOREIGN KEY (idCategorie) REFERENCES Categorie(idCategorie)
);

CREATE TABLE Vente (
    idVente SERIAL PRIMARY KEY,
    idProduit INT,
    idCategorie INT,
    quantite INT CHECK (quantite > 0),
    prix_unitaire DECIMAL(10,2) CHECK (prix_unitaire > 0),
    date_vente DATE,
    FOREIGN KEY (idProduit) REFERENCES Produit(idProduit),
    FOREIGN KEY (idCategorie) REFERENCES Categorie(idCategorie)
);


CREATE OR REPLACE VIEW VenteComplet AS SELECT v.idvente,c.nom AS Categorie, p.nom AS NomProduit,v.quantite, v.prix_unitaire, v.date_vente
FROM Categories c
JOIN Produits p ON c.idCategorie = p.idCategorie
JOIN Vente v ON p.idProduit = v.idProduit;

