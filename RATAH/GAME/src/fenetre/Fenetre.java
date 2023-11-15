package fenetre;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.SwingUtilities;

import dessin.CentralPanel;
import dessin.PanneauRessources;
import game.Chercheur;
import game.Civilisation;
import game.Militaire;
import game.Population;
import ressource.GestionRessources;
import ressource.RessourcesMonde;

public class Fenetre {
    private JFrame frame;
    private JFrame centraleFrame;
    private PanneauRessources panneauRessources;
    private GestionRessources ressource;
    public Fenetre() {
        frame = new JFrame("Civilization Game");
        centraleFrame = new JFrame("Central");
        panneauRessources = new PanneauRessources(new RessourcesMonde(
                new Point(500, 200), // Coordonnées du terrain
                new Point(150, 200), // Coordonnées du bois
                new Point(400, 400) // Coordonnées de l'or
        ));
    }
   

    
    public void createAndShowGUI() {
        frame.setSize(800, 600);

        JPanel beigePanel = createPanelWithBeigeBackground();
        frame.add(beigePanel);

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setVisible(true);

        List<Civilisation> civilisations = createCivilisations();
        
        panneauRessources.setEntitiesAndLabels(beigePanel, civilisations);

    }
    
    public List<Civilisation> createCivilisations() {
        List<Civilisation> civilisations = new ArrayList<>();

        Civilisation civilisation1 = new Civilisation("Civilisation1");
        Civilisation civilisation2 = new Civilisation("Civilisation2");

        civilisations.add(civilisation1);
        civilisations.add(civilisation2);

        // Crée des listes pour stocker les populations, militaires et chercheurs
        List<Population> populations1 = new ArrayList<>();
        List<Militaire> militaires1 = new ArrayList<>();
        List<Chercheur> chercheurs1 = new ArrayList<>();

        //populations1.add(civilisation1.createPopulation(102, 90));
        populations1.add(civilisation1.createPopulation(600, 90));
        
        chercheurs1.add(civilisation1.createChercheur(459, 133));
        militaires1.add(civilisation1.createMilitaire(342, 566));

        militaires1.add(civilisation2.createMilitaire(600, 190));
        
        panneauRessources.setEntites(populations1, militaires1, chercheurs1);

        return civilisations;
    }

    
    private JPanel createPanelWithBeigeBackground() {
        JPanel panel = panneauRessources;
        panel.setBackground(new Color(245, 245, 220));
        return panel;
    }
    private JPanel createPanelWithBeigeBackground2(List<Civilisation> civilisations) {
        CentralPanel panel = new CentralPanel();
        panel.setBackground(new Color(245, 245, 220));

        // Définir la liste complète des civilisations sur l'instance réelle de CentralPanel
        panel.setCivilisations(civilisations);

        return panel;
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Fenetre gameGUI = new Fenetre();
            gameGUI.createAndShowGUI();
        });
    }
}
