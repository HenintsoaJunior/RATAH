window.onload = function() {
    chargerPublications();
};

function ajouterCommentaire(publicationId, commentaireTexte) {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'publication.php', true); 
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {                
                console.log('Commentaire ajouté avec succès !');
            } else {
                console.error('Erreur lors de l\'ajout du commentaire');
            }
        }
    };

    var data = 'id_publication=' + encodeURIComponent(publicationId) + '&commentaire=' + encodeURIComponent(commentaireTexte);
    console.log(data);
    xhr.send(data);
}

function publierMessage() {
    var message = document.getElementById('message').value;

    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'publication.php', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                document.getElementById('success').textContent = 'Message publié !';
            } else {
                document.getElementById('success').textContent = 'Erreur lors de la publication.';
            }
        }
    };

    var data = 'message=' + encodeURIComponent(message);
    xhr.send(data);
}

function chargerPublications() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'publication.php', true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var publications = JSON.parse(xhr.responseText);
                afficherPublications(publications);
            } else {
                console.error('Erreur lors du chargement des publications');
            }
        }
    };

    xhr.send();
}

function afficherPublication(pubData) {
    var divPublication = creerDivPublication(pubData);
    var divNouveauCommentaire = creerDivNouveauCommentaire();

    ajouterEvenementAjoutCommentaire(divNouveauCommentaire);

    divPublication.appendChild(divNouveauCommentaire);

    return divPublication;
}

function creerDivPublication(pubData) {
    var divPublication = document.createElement('div');
    divPublication.classList.add('publication');
    divPublication.setAttribute('data-id', pubData.id);

    var pPublication = document.createElement('p');
    pPublication.textContent = pubData.message;
    divPublication.appendChild(pPublication);

    var divCommentaires = document.createElement('div');
    divCommentaires.classList.add('commentaires');

    afficherCommentaires(pubData.commentaires, divCommentaires);

    divPublication.appendChild(divCommentaires);

    return divPublication;
}

function ajouterEvenementAjoutCommentaire(divNouveauCommentaire) {
    divNouveauCommentaire.querySelector('.ajouter-commentaire-btn')
        .addEventListener('click', function() {
            var commentaireTexte = this.previousElementSibling.value;
            var publicationId = this.closest('.publication').getAttribute('data-id');
            ajouterCommentaire(publicationId, commentaireTexte);
        });
}

function afficherCommentaires(commentairesData, divCommentaires) {
    if (commentairesData && Array.isArray(commentairesData)) {
        commentairesData.forEach(function(commentaire) {
            var divCommentaire = document.createElement('div');
            divCommentaire.classList.add('commentaire');
            var pCommentaire = document.createElement('p');
            pCommentaire.textContent = commentaire.commentaire;
            divCommentaire.appendChild(pCommentaire);
            divCommentaires.appendChild(divCommentaire);
        });
    }
}

function creerDivNouveauCommentaire() {
    var divNouveauCommentaire = document.createElement('div');
    divNouveauCommentaire.classList.add('nouveau-commentaire');
    var inputCommentaire = document.createElement('input');
    inputCommentaire.type = 'text';
    inputCommentaire.classList.add('commentaire-input');
    inputCommentaire.placeholder = 'Ajouter un commentaire...';
    var btnAjouterCommentaire = document.createElement('button');
    btnAjouterCommentaire.classList.add('ajouter-commentaire-btn');
    btnAjouterCommentaire.textContent = 'Ajouter';
    divNouveauCommentaire.appendChild(inputCommentaire);
    divNouveauCommentaire.appendChild(btnAjouterCommentaire);

    return divNouveauCommentaire;
}

function afficherPublications(data) {
    var publicationsList = document.getElementById('publicationsList');
    var commentairesList = document.getElementById('commentairesList');

    if (data && Array.isArray(data.publications)) {
        var publications = data.publications;

        publications.forEach(function(publication) {
            var divPublication = afficherPublication(publication);
            publicationsList.appendChild(divPublication);
        });
        
    } else if (data && Array.isArray(data.commentaire)) {
        var commentaires = data.commentaire;

        commentaires.forEach(function(commentaire) {
            var divCommentaire = afficherPublication(commentaire);
            commentairesList.appendChild(divCommentaire);
        }); 

    } else {
        console.error("Les données de publications ne sont pas au format attendu (non un tableau).");
    }
}






