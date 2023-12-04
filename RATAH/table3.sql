CREATE TABLE Restaurant (
    restaurant_id INT PRIMARY KEY,
    nom VARCHAR(100),
    adresse VARCHAR(200),
    Image VARCHAR(100)
);

CREATE TABLE Menu (
    menu_id INT PRIMARY KEY,
    restaurant_id INT,
    nom_menu VARCHAR(100),
    prix DECIMAL(10, 2),
    prix_intermediaire DECIMAL(10, 2),
    prix_revient DECIMAL(10, 2),
    Image VARCHAR(100),
    FOREIGN KEY (restaurant_id) REFERENCES Restaurant(restaurant_id)
);

CREATE TABLE Client (
    client_id INT PRIMARY KEY,
    nom_client VARCHAR(100)
);

CREATE TABLE Intermediaire (
    intermediaire_id INT PRIMARY KEY,
    nom_intermediaire VARCHAR(100),
    forfait DECIMAL(10, 2),
    recette_minimum DECIMAL(10, 2),
    statut VARCHAR(10)
);

CREATE TABLE Commande (
    commande_id INT PRIMARY KEY,
    restaurant_id INT,
    menu_id INT,
    client_id INT,
    date_commande DATE,
    FOREIGN KEY (restaurant_id) REFERENCES Restaurant(restaurant_id),
    FOREIGN KEY (menu_id) REFERENCES Menu(menu_id),
    FOREIGN KEY (client_id) REFERENCES Client(client_id)
);

CREATE TABLE Commande_Menu (
    commande_id INT,
    menu_id INT,
    quantite INT,
    PRIMARY KEY (commande_id, menu_id),
    FOREIGN KEY (commande_id) REFERENCES Commande(commande_id),
    FOREIGN KEY (menu_id) REFERENCES Menu(menu_id)
);

CREATE TABLE Facture (
    facture_id INT PRIMARY KEY,
    commande_id INT,
    date_facture DATE,
    intermediaire_id INT,
    FOREIGN KEY (commande_id) REFERENCES Commande(commande_id),
    FOREIGN KEY (intermediaire_id) REFERENCES Intermediaire(intermediaire_id)
);
