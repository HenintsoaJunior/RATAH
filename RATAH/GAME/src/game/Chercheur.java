package game;

import java.awt.Point;

public class Chercheur extends Entite {
    private double vie = 5; // Points de vie initiaux
    private double tauxPerteVie = 0.001; // Taux de perte de vie par seconde

    // Constructeur
    public Chercheur(int x, int y,Civilisation civilisation) {
        super(x, y,civilisation); // Appelle le constructeur de la classe parente avec les coordonnées x et y
    }
    
    @Override
    public void deplacer(int nouvelleX, int nouvelleY) {
        super.deplacer(nouvelleX, nouvelleY);
    }

    public void subirAttaque(Chercheur chercheur) {
        double distance = getPoint().distance(chercheur.getPoint());
        if (distance <= 1) { // Distance <= 1 signifie un contact direct
            vie -= chercheur.getTauxPerteVie();
        }
    }

	public double getVie() {
		return vie;
	}

	public void setVie(double vie) {
		this.vie = vie;
	}

	public double getTauxPerteVie() {
		return tauxPerteVie;
	}

	public void setTauxPerteVie(double tauxPerteVie) {
		this.tauxPerteVie = tauxPerteVie;
	}

   
}
