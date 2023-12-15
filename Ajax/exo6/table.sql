CREATE TABLE Categories(
    idCategorie SERIAL PRIMARY KEY,
    nom VARCHAR(10)
);

CREATE TABLE Produits(
    idProduit SERIAL PRIMARY KEY,
    idCategorie INT,
    nom VARCHAR(10),
    FOREIGN KEY (idCategorie) REFERENCES Categories(idCategorie)
);

CREATE TABLE Vente (
    idVente SERIAL PRIMARY KEY,
    idProduit INT,
    idCategorie INT,
    quantite INT CHECK (quantite > 0),
    prix_unitaire DECIMAL(10,2) CHECK (prix_unitaire > 0),
    date_vente DATE,
    FOREIGN KEY (idProduit) REFERENCES Produits(idProduit),
    FOREIGN KEY (idCategorie) REFERENCES Categories(idCategorie)
);


CREATE OR REPLACE VIEW VenteComplet AS SELECT c.idCategorie,v.idvente,c.nom AS Categorie, p.nom AS NomProduit,v.quantite, v.prix_unitaire, v.date_vente
FROM Categories c
JOIN Produits p ON c.idCategorie = p.idCategorie
JOIN Vente v ON p.idProduit = v.idProduit;

-- Insérer dans la table Categories
INSERT INTO Categories (nom) VALUES ('Catégorie1');
INSERT INTO Categories (nom) VALUES ('Catégorie2');
INSERT INTO Categories (nom) VALUES ('Catégorie3');
INSERT INTO Categories (nom) VALUES ('Catégorie4');
INSERT INTO Categories (nom) VALUES ('Catégorie5');

-- Insérer dans la table Produits
INSERT INTO Produits (idCategorie, nom) VALUES (1, 'Produit1');
INSERT INTO Produits (idCategorie, nom) VALUES (2, 'Produit2');
INSERT INTO Produits (idCategorie, nom) VALUES (3, 'Produit3');
INSERT INTO Produits (idCategorie, nom) VALUES (4, 'Produit4');
INSERT INTO Produits (idCategorie, nom) VALUES (5, 'Produit5');

-- Insérer dans la table Vente
INSERT INTO Vente (idProduit, idCategorie, quantite, prix_unitaire, date_vente) VALUES (1, 1, 10, 100.00, '2023-12-12');
INSERT INTO Vente (idProduit, idCategorie, quantite, prix_unitaire, date_vente) VALUES (2, 2, 20, 200.00, '2023-12-13');
INSERT INTO Vente (idProduit, idCategorie, quantite, prix_unitaire, date_vente) VALUES (3, 3, 30, 300.00, '2023-12-14');
INSERT INTO Vente (idProduit, idCategorie, quantite, prix_unitaire, date_vente) VALUES (4, 4, 40, 400.00, '2023-12-15');
INSERT INTO Vente (idProduit, idCategorie, quantite, prix_unitaire, date_vente) VALUES (5, 5, 50, 500.00, '2023-12-16');
