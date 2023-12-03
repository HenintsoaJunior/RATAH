CREATE TABLE Restaurant (
    restaurant_id NUMBER PRIMARY KEY,
    nom VARCHAR2(100),
    adresse VARCHAR2(200)

);

CREATE TABLE Menu (
    menu_id NUMBER PRIMARY KEY,
    restaurant_id NUMBER,
    nom_menu VARCHAR2(100),
    prix NUMBER,
	prix_intermediaire NUMBER,
    prix_revient NUMBER,
    FOREIGN KEY (restaurant_id) REFERENCES Restaurant(restaurant_id)
);

CREATE TABLE Client (
    client_id NUMBER PRIMARY KEY,
    nom_client VARCHAR2(100)
);

CREATE TABLE Intermediaire (
    intermediaire_id NUMBER PRIMARY KEY,
    nom_intermediaire VARCHAR2(100),
    forfait NUMBER,
    recette_minimum NUMBER,
    statut VARCHAR2(10)
);

CREATE TABLE Commande (
    commande_id NUMBER PRIMARY KEY,
    restaurant_id NUMBER,
    menu_id NUMBER,
    client_id NUMBER,
    date_commande DATE,
    FOREIGN KEY (restaurant_id) REFERENCES Restaurant(restaurant_id),
    FOREIGN KEY (menu_id) REFERENCES Menu(menu_id),
    FOREIGN KEY (client_id) REFERENCES Client(client_id)
);


CREATE TABLE Commande_Menu (
    commande_id NUMBER,
    menu_id NUMBER,
    quantite NUMBER,
    PRIMARY KEY (commande_id, menu_id),
    FOREIGN KEY (commande_id) REFERENCES Commande(commande_id),
    FOREIGN KEY (menu_id) REFERENCES Menu(menu_id)
);


CREATE TABLE Facture (
    facture_id NUMBER PRIMARY KEY,
    commande_id NUMBER,
    date_facture DATE,
    intermediaire_id NUMBER,
    FOREIGN KEY (commande_id) REFERENCES Commande(commande_id),
    FOREIGN KEY (intermediaire_id) REFERENCES Intermediaire(intermediaire_id)
);

UPDATE Restaurant
SET image = 'resto1.jpg'
WHERE restaurant_id = 1;


delete from facture;
delete from commande_menu;
delete from commande;
delete from menu;
delete from intermediaire;
delete from client;
