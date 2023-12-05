function createXHR(method, url, data, callback) {
    const xhr = new XMLHttpRequest();
    xhr.open(method, url, true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                const response = JSON.parse(xhr.responseText);
                callback(response, null); // Appel de la fonction de traitement avec la réponse
            } else {
                callback(null, 'Erreur de connexion au serveur'); // Gestion de l'erreur
            }
        }
    };

    xhr.send(data);
}

function genererChaineRequete(donnees) {
    const params = Object.entries(donnees)
        .map(([key, value]) => `${encodeURIComponent(key)}=${encodeURIComponent(value)}`)
        .join('&');
    return params;
}

function envoyerFormulaireAuServeur(data) {
    function traiterReponse(response, erreur) {
        if (erreur) {
            document.getElementById('error').textContent = erreur;
        } else {
            console.log(response);
            if (response.success) {
                document.getElementById('success').textContent = 'Bien';
            } else {
                document.getElementById('error').textContent = 'Erreur';
            }
        }
    }

    createXHR('POST', 'Formulaire.php', data, traiterReponse);
}

function preparerDonneesFormulaire() {
    const date = document.getElementById('date').value;
    const categorie = document.getElementById('categorie').value;
    const produit = document.getElementById('produit').value;
    const prixUnitaire = document.getElementById('prixUnitaire').value;
    const quantite = document.getElementById('quantite').value;

    const formData = {
        date: date,
        categorie: categorie,
        produit: produit,
        prixUnitaire: prixUnitaire,
        quantite: quantite
    };

    const data = genererChaineRequete(formData);
    console.log(data);

    envoyerFormulaireAuServeur(data);
}


function soumissionFormulaire(event) {
    event.preventDefault();
    preparerDonneesFormulaire();
}

function chargerCategories() {
    function traiterReponse(response, erreur) {
        if (erreur) {
            alert(erreur);
        } else {
            remplirListeCategories(response);
            if (response.length > 0) {
                chargerProduitsParCategorie(response[0].idcategorie);
            }
        }
    }

    createXHR('GET', 'ListeCategories.php', null, traiterReponse);
}

function remplirListeCategories(categories) {
    const selectCategorie = document.getElementById('categorie');
    categories.forEach(category => {
        const option = document.createElement('option');
        option.value = category.idcategorie;
        option.textContent = category.nom;
        selectCategorie.appendChild(option);
    });
}

function chargerProduitsParCategorie(categoryId) {
    function traiterReponse(response, erreur) {
        if (erreur) {
            // Gérer les erreurs de connexion au serveur pour les produits
            console.error(erreur);
        } else {
            remplirListeProducts(response);
        }
    }

    createXHR('GET', `ListeProduitsParCategorie.php?idCategorie=${categoryId}`, null, traiterReponse);
}

function remplirListeProducts(products) {
    const selectProduit = document.getElementById('produit');
    selectProduit.innerHTML = '';

    products.forEach(product => {
        const option = document.createElement('option');
        option.value = product.idproduit;
        option.textContent = product.nom;
        selectProduit.appendChild(option);
    });
}

function chargerListeVente() {
    function traiterReponse(response, erreur) {
        if (erreur) {
            document.getElementById('error').textContent = 'Erreur de connexion au serveur';
        } else {
            displayListeVente(response);
        }
    }

    createXHR('GET', 'Liste.php', null, traiterReponse);
}

function displayListeVente(response) {
    const divListeVente = document.getElementById('listeVente');
    
    if (Array.isArray(response)) {
        let tableHTML = '<table border="1"><thead><tr><th>Categorie</th><th>NomProduit</th><th>quantite</th><th>prix_unitaire</th><th>date_vente</th><th>Supprimer</th></tr></thead><tbody>';

        response.forEach(item => {
            tableHTML += `<tr><td>${item.categorie}</td><td>${item.nomproduit}</td><td>${item.quantite}</td><td>${item.prix_unitaire}</td><td>${item.date_vente}</td><td><button onclick="supprimerVente(${item.idvente})">Supprimer</button></td></tr>`;
        });

        tableHTML += '</tbody></table>';
        divListeVente.innerHTML = tableHTML;
    } else {
        document.getElementById('error').textContent = 'Réponse invalide';
    }
}


function modifierItem(idvente) {
    console.log("modifier");
}

function supprimerVente(idvente) {
    function traiterReponse(response, erreur) { 
        if (erreur) {
            console.error('Erreur lors de la suppression');
            // Gérer les erreurs liées à la suppression de l'élément
        } else {
            refreshList(); // Recharge la liste des ventes après la suppression
            console.log("Supprimer " + idvente);
        }
    }

    createXHR('GET', `SupprimerVente.php?idvente=${idvente}`, null, traiterReponse);
}

// Chargement des produits lors du changement de catégorie
document.getElementById('categorie').addEventListener('change', function(event) {
    const selectedCategoryId = event.target.value;
    if (selectedCategoryId) {
        chargerProduitsParCategorie(selectedCategoryId);
    } else {
        console.log("Aucune catégorie sélectionnée.");
    }
});

// Chargement des catégories au démarrage
chargerCategories();

// Fonction de rafraîchissement de la liste des ventes
function refreshList() {
    chargerListeVente();
}

// Rafraîchissement de la liste des ventes au démarrage
refreshList();

// Soumission du formulaire
document.getElementById('FormulaireForm').addEventListener('submit', soumissionFormulaire);


// Actualisation de la liste des ventes à intervalle régulier
setInterval(function() {
    refreshList();
}, 1000); // Actualise toutes les 60 secondes (60000 millisecondes)
