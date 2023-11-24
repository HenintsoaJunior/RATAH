document.getElementById('InscriptionForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const nom=document.getElementById('nom').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
  
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'Inscription.php', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                const response = JSON.parse(xhr.responseText);
                console.log(response);
                if (response.success) {
                  document.getElementById('success').textContent = 'Good';
                  window.location.href = "login.html"; // Redirection vers publication.html après le login réussi
              } else {
                  document.getElementById('error').textContent = 'Erreur';
              }
              
            } else {
                document.getElementById('error').textContent = 'Erreur de connexion au serveur';
            }
        }
    };
  
    const data = `nom=${encodeURIComponent(nom)}&email=${encodeURIComponent(email)}&password=${encodeURIComponent(password)}`;
    console.log(data);
    xhr.send(data);
  });
  