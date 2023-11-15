package game;

import ressource.RessourcesMonde;
import java.awt.Point;

public class Population extends Entite {
    private int population = 0;
    private RessourcesMonde monde;
    private int resources;

   
    
    public Population(int x, int y,Civilisation civilisation) {
        super(x, y,civilisation);
    }

    @Override
    public void deplacer(int nouvelleX, int nouvelleY) {
        // Mise à jour des coordonnées de l'entité
        setPoint(new Point(nouvelleX, nouvelleY));
    } 
    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

}
