 function submitForm() {
    // Afficher le loader
    var loader = document.getElementById("loader");
    if (loader) {
        loader.style.display = "block";
    } else {
        console.error("element loader non introuvable.");
    }

    var xhr; 
    try {  xhr = new ActiveXObject('Msxml2.XMLHTTP');   }
    catch (e) 
    {
        try {   xhr = new ActiveXObject('Microsoft.XMLHTTP'); }
        catch (e2) 
        {
           try {  xhr = new XMLHttpRequest();  }
           catch (e3) {  xhr = false;   }
         }
    }

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            
            if (loader) {
                loader.style.display = "none";
            } else {
                console.error("element loader non trouve.");
            }

            if (xhr.status === 200) {
                try {
                    var personnes = JSON.parse(xhr.responseText);
                    
                    var tableau = "<table border='1'><tr><th>ID</th><th>Nom</th><th>Prenom</th><th>Annee de naissance</th></tr>";

                    for (var i = 0; i < personnes.length; i++) {
                        tableau += "<tr><td>" + personnes[i].idPersonne + "</td><td>" + personnes[i].Nom + "</td><td>" + personnes[i].Prenom + "</td><td>" + personnes[i].AnneeNaissance + "</td></tr>";
                    }

                    tableau += "</table>";

                    // Ajouter le tableau à un élément existant dans le DOM
                    var tableauDiv = document.getElementById("tableauPersonnes");
                    if (tableauDiv) {
                        tableauDiv.innerHTML = tableau;
                    } else {
                        console.error("Element tableau non trouvé.");
                    }

                    // Afficher le nombre de personnes
                    var nombreDiv = document.getElementById("nombrePersonnes");
                    if (nombreDiv) {
                        nombreDiv.innerHTML = "Nombre de personnes : " + personnes.length;
                    } else {
                        console.error("Element nombre non trouve");
                    }
                } catch (e) {
                    console.error("La réponse n'est pas du JSON valide : ", e);
                }
            } else {
                console.error("Erreur lors de la requete : ", xhr.status);
            }
        }
    };

    // Votre code existant pour envoyer la requete AJAX
    xhr.open("GET", "json.php", true);
    xhr.send();
}