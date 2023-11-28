function submitForm() {
    document.getElementById('FormulaireForm').addEventListener('submit', function(event) {
        event.preventDefault();
        const date = document.getElementById('date').value;
        const produit = document.getElementById('produit').value;
        const quantite = document.getElementById('quantite').value;

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

        const data = `date=${encodeURIComponent(date)}&produit=${encodeURIComponent(produit)}&quantite=${encodeURIComponent(quantite)}`;
        console.log(data);
        xhr.send(data);
    });
}



function loadListeVente() {
    const divListeVente = document.getElementById('listeVente');
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'Liste.php', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                const response = JSON.parse(xhr.responseText);

                if (Array.isArray(response)) {
                    let tableHTML = '<table border="1"><thead><tr><th>ID</th><th>Date</th><th>Produit</th><th>Quantite</th></tr></thead><tbody>';

                    response.forEach(item => {
                        tableHTML += `<tr><td>${item.id}</td><td>${item.date}</td><td>${item.produit}</td><td>${item.quantite}</td></tr>`;
                    });

                    tableHTML += '</tbody></table>';
                    divListeVente.innerHTML = tableHTML;
                } else {
                    document.getElementById('error').textContent = 'Réponse invalide';
                }
            } else {
                document.getElementById('error').textContent = 'Erreur de connexion au serveur';
            }
        }
    };

    xhr.send(null);
}


function refreshList() {
    loadListeVente();
}


refreshList();

submitForm();

document.getElementById('reloadButton').addEventListener('click', function() {
    refreshList();
});
