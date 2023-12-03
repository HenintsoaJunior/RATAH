CREATE TABLE Clients (
    client_id NUMBER PRIMARY KEY,
    nom VARCHAR2(50),
    telephone VARCHAR2(20)
);

CREATE TABLE Intermediaires (
    intermediaire_id NUMBER PRIMARY KEY,
    nom VARCHAR2(50),
    prenom VARCHAR2(50),
    forfait NUMBER(10, 2),
    recette_min NUMBER(10, 2)
);

CREATE TABLE Menus (
    menu_id NUMBER PRIMARY KEY,
    nom_menu VARCHAR2(100),
    description VARCHAR2(4000),
    prix NUMBER(10, 2),
    prix_intermediaire NUMBER(10, 2),
    prix_revient NUMBER(10, 2)
);

CREATE TABLE Commandes (
    commande_id NUMBER PRIMARY KEY,
    client_id NUMBER,
    date_commande TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_commandes_clients FOREIGN KEY (client_id) REFERENCES Clients(client_id)
);

CREATE TABLE CommandesMenus (
    commande_id NUMBER,
    menu_id NUMBER,
    quantite NUMBER,
    CONSTRAINT fk_commandes_menus_commande FOREIGN KEY (commande_id) REFERENCES Commandes(commande_id),
    CONSTRAINT fk_commandes_menus_menu FOREIGN KEY (menu_id) REFERENCES Menus(menu_id),
    CONSTRAINT pk_commandes_menus PRIMARY KEY (commande_id, menu_id)
);


CREATE TABLE Factures (
    facture_id NUMBER PRIMARY KEY,
    commande_id NUMBER,
    menu_id NUMBER,
    client_id NUMBER,
    intermediaire_id NUMBER,
    date_facture TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_factures_commandes_menus FOREIGN KEY (commande_id, menu_id) REFERENCES CommandesMenus(commande_id, menu_id),
    CONSTRAINT fk_factures_client FOREIGN KEY (client_id) REFERENCES Clients(client_id),
    CONSTRAINT fk_factures_intermediaire FOREIGN KEY (intermediaire_id) REFERENCES Intermediaires(intermediaire_id)
);


INSERT INTO Clients (client_id, nom,telephone)
VALUES (1, 'Dupont','1234567890');

INSERT INTO Intermediaires (intermediaire_id, nom, prenom, forfait, recette_min)
VALUES (1, 'Smith', 'Alice', 500000, 1000000);

INSERT INTO Menus (menu_id, nom_menu, description, prix, prix_intermediaire, prix_revient)
VALUES (1, 'Menu classique', 'Description du menu classique', 200000, 180000, 100000);

INSERT INTO Menus (menu_id, nom_menu, description, prix, prix_intermediaire, prix_revient)
VALUES (2, 'Menu Gold', 'Description du menu Gold', 500000, 400000, 200000);

INSERT INTO Repas (repas_id, client_id, intermediaire_id, date_repas)
VALUES (1, 1, 1, CURRENT_TIMESTAMP);


INSERT INTO Repas (repas_id, client_id, intermediaire_id, date_repas)
VALUES (2, 1, 1, CURRENT_TIMESTAMP);


INSERT INTO RepasMenus (repas_id, menu_id)
VALUES (1, 1);

INSERT INTO RepasMenus (repas_id, menu_id)
VALUES (2, 2);

INSERT INTO Factures (facture_id, repas_id)
VALUES (1, 1);

INSERT INTO Factures (facture_id, repas_id)
VALUES (2, 2);

INSERT INTO Commandes (commande_id, client_id, date_commande)
VALUES (1, 1, CURRENT_TIMESTAMP);

INSERT INTO Commandes (commande_id, client_id, date_commande)
VALUES (2, 1, CURRENT_TIMESTAMP);

INSERT INTO CommandesMenus (commande_id, menu_id, quantite)
VALUES (1, 1, 2);

INSERT INTO CommandesMenus (commande_id, menu_id, quantite)
VALUES (2, 2, 3);

delete from factures;
delete from commandesmenus;
delete from commandes;
delete from menus;
delete from intermediaires;
delete from clients;
