/*package main;

import java.awt.Point;

import game.Civilisation;
import game.Militaire;
import game.Population;
import ressource.RessourcesMonde;

public class JeuMilitaire {

    public static void main(String[] args) {
        // Création de deux civilisations avec des ressources initiales
        Civilisation civilisation1 = new Civilisation(1000);
        Civilisation civilisation2 = new Civilisation(1000);

        RessourcesMonde monde = new RessourcesMonde(new Point(0, 0), new Point(0, 0), new Point(0, 0));

        // Création de tableaux pour stocker les populations et les militaires
        Population[] populationsCivilisation1 = new Population[2];
        Militaire[] militairesCivilisation2 = new Militaire[2];

        // Création de deux populations dans la civilisation 1
        for (int i = 0; i < 2; i++) {
            civilisation1.createPopulation();
            populationsCivilisation1[i] = new Population(0, 0);
        }

        // Création de deux militaires dans la civilisation 2
        for (int i = 0; i < 2; i++) {
            civilisation2.createMilitary();
            militairesCivilisation2[i] = new Militaire(10, 0); // Créez des militaires avec des coordonnées appropriées
        }

        // Affichage des informations initiales des civilisations
        civilisation1.displayCivilizationInfo();
        civilisation2.displayCivilizationInfo();

         
    }
}
*/