// Fonction pour envoyer les données au serveur
function sendFormDataToServer(data) {
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'Formulaire.php', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                const response = JSON.parse(xhr.responseText);
                console.log(response);
                if (response.success) {
                    document.getElementById('success').textContent = 'Good';
                } else {
                    document.getElementById('error').textContent = 'Erreur';
                }
            } else {
                document.getElementById('error').textContent = 'Erreur de connexion au serveur';
            }
        }
    };

    xhr.send(data);
}

function prepareFormData() {
    const date = document.getElementById('date').value;
    const categorie = document.getElementById('categorie').value;
    const produit = document.getElementById('produit').value;
    const prixUnitaire = document.getElementById('prixUnitaire').value;
    const quantite = document.getElementById('quantite').value;

    const data = `date=${encodeURIComponent(date)}&categorie=${encodeURIComponent(categorie)}&prixUnitaire=${encodeURIComponent(prixUnitaire)}&produit=${encodeURIComponent(produit)}&quantite=${encodeURIComponent(quantite)}`;
    console.log(data);

    sendFormDataToServer(data);
}

document.getElementById('FormulaireForm').addEventListener('submit', function(event) {
    event.preventDefault();
   
    prepareFormData();
});



function loadCategories() {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'ListeCategories.php', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                const categories = JSON.parse(xhr.responseText);
                // Appel de la fonction pour peupler le menu déroulant des catégories
                populateCategories(categories);
                // Chargement des produits de la première catégorie (s'il y en a)
                if(categories.length > 0) {
                    loadProductsByCategory(categories[0].idcategorie);
                }
            } else {
                alert('erreur');
            }
        }
    };

    xhr.send(null);
}

function populateCategories(categories) {
    const selectCategorie = document.getElementById('categorie');
    categories.forEach(category => {
        const option = document.createElement('option');
        option.value = category.idcategorie;
        option.textContent = category.nom;
        selectCategorie.appendChild(option);
    });
}


function loadProductsByCategory(categoryId) {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', `ListeProduitsParCategorie.php?idCategorie=${categoryId}`, true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                const products = JSON.parse(xhr.responseText);
                
                populateProducts(products);
            } else {
                // Gérer les erreurs de connexion au serveur pour les produits
            }
        }
    };

    xhr.send(null);
}

function populateProducts(products) {
    const selectProduit = document.getElementById('produit');
    selectProduit.innerHTML = '';

    products.forEach(product => {
        const option = document.createElement('option');
        option.value = product.idproduit;
        option.textContent = product.nom;
        selectProduit.appendChild(option);
    });
}

function loadListeVente() {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'Liste.php', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                const response = JSON.parse(xhr.responseText);
                // Appel de la fonction pour mettre à jour l'interface avec la liste des ventes
                displayListeVente(response);
            } else {
                document.getElementById('error').textContent = 'Erreur de connexion au serveur';
            }
        }
    };

    xhr.send(null);
}

function displayListeVente(response) {
    const divListeVente = document.getElementById('listeVente');
    
    if (Array.isArray(response)) {
        let tableHTML = '<table border="1"><thead><tr><th>Categorie</th><th>NomProduit</th><th>quantite</th><th>prix_unitaire</th><th>date_vente</th></tr></thead><tbody>';

        response.forEach(item => {
            tableHTML += `<tr><td>${item.categorie}</td><td>${item.nomproduit}</td><td>${item.quantite}</td><td>${item.prix_unitaire}</td><td>${item.date_vente}</td></tr>`;
        });

        tableHTML += '</tbody></table>';
        divListeVente.innerHTML = tableHTML;
    } else {
        document.getElementById('error').textContent = 'Réponse invalide';
    }
}

document.getElementById('categorie').addEventListener('change', function(event) {
    const selectedCategoryId = event.target.value;
    if (selectedCategoryId) {
        loadProductsByCategory(selectedCategoryId);
    } else {
        console.log("Aucune catégorie sélectionnée.");
    }
});

loadCategories();

function refreshList() {
    loadListeVente();
}


refreshList();

submitForm();

document.getElementById('reloadButton').addEventListener('click', function() {
    refreshList();
});
