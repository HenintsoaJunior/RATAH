package dessin;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import collision.Collision;
import game.Chercheur;
import game.Civilisation;
import game.Entite;
import game.Militaire;
import game.Population;
import listener.EntityDragHandler;
import ressource.GestionRessources;
import ressource.RessourcesMonde;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

public class PanneauRessources extends JPanel {
    private RessourcesMonde ressourcesMonde;
    private List<Population> populations;
    private List<Militaire> militaires;
    private List<Chercheur> chercheurs;
    private EntityDragHandler entityDragHandler;
    Polygon terrainPolygon=new Polygon();
    Dessin dessiner;
    Collision collision;
    GestionRessources ressource;
    JLabel labelRessource=new JLabel();
    double ressourceAcollecter = 0;
    GeometryUtils geo;

   
    
    public PanneauRessources(RessourcesMonde ressourcesMonde) {
        populations = new ArrayList<>();
        militaires = new ArrayList<>();
        chercheurs = new ArrayList<>();
        this.ressourcesMonde = ressourcesMonde;
        setPreferredSize(new Dimension(800, 600)); // Définir la dimension souhaitée ici
        this.entityDragHandler = new EntityDragHandler(this);

        List<Point[]> terrainList = new ArrayList<>();
        List<Point[]> boisList = new ArrayList<>();
        List<Point[]> orList = new ArrayList<>();

        Point[] terrainPoints1 = GeometryUtils.createPoints(100, 200, 150, 100, 250, 100, 300, 200, 225, 300);
        Point[] terrainPoints2 = GeometryUtils.createPoints(120, 220, 170, 120, 270, 120, 320, 220, 245, 320);
        Point[] boisPoints = GeometryUtils.createPoints(100, 200, 150, 100, 250, 100, 300, 200, 225, 300);
        Point[] orPoints = GeometryUtils.createPoints(200, 150, 250, 50, 300, 150);

        GeometryUtils.deplacerDroite(terrainPoints1, 900);
        
        GeometryUtils.reduireTaille(terrainPoints1, 0.5);
        GeometryUtils.reduireTaille(terrainPoints2, 0.4);
        
        GeometryUtils.deplacerBas(terrainPoints2, 200);
        GeometryUtils.deplacerDroite(terrainPoints2, 200);
        
        GeometryUtils.reduireTaille(boisPoints, 0.5);
        
        GeometryUtils.deplacerBas(orPoints, 400);

        terrainList.add(terrainPoints1);
        terrainList.add(terrainPoints2);
        boisList.add(boisPoints);
        orList.add(orPoints);

        geo = new GeometryUtils(terrainList, boisList, orList);
        dessiner = new Dessin(ressourcesMonde, terrainList, boisList, orList);
        collision = new Collision();
        ressource = new GestionRessources();
    }

    
    public void setEntitiesAndLabels(JPanel beigePanel, List<Civilisation> civilisations) {
        for (Civilisation civilisation : civilisations) {
            // Ajoutez les labels de la civilisation directement sur le beigePanel
            JLabel labelNom = new JLabel("Nom: " + civilisation.getNom());
            JLabel labelPopulations = new JLabel("Populations: " + civilisation.getNbPopulation());
            JLabel labelMilitaires = new JLabel("Militaires: " + civilisation.getNbmilitary());
            JLabel labelChercheurs = new JLabel("Chercheurs: " + civilisation.getNbresearchers());
            labelRessource = new JLabel("Ressource : " + civilisation.getResources());

            beigePanel.add(labelNom);
            beigePanel.add(labelPopulations);
            beigePanel.add(labelMilitaires);
            beigePanel.add(labelChercheurs);
            beigePanel.add(labelRessource);
            beigePanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        }
    }
    
    public void resetZoneStatus() {
        for (Population population : populations) {
            if (population != null) {
                population.setInTerrainZone(false);
                population.setInWoodZone(false);
                population.setInGoldZone(false);
            }
        }
        for (Militaire militaire : militaires) {
            if (militaire != null) {
                militaire.setInTerrainZone(false);
                militaire.setInWoodZone(false);
                militaire.setInGoldZone(false);
            }
        }
        for (Chercheur chercheur : chercheurs) {
            if (chercheur != null) {
                chercheur.setInTerrainZone(false);
                chercheur.setInWoodZone(false);
                chercheur.setInGoldZone(false);
            }
        }
    }
    
    private void dessiner(Graphics g, List<Entite> entites, String nomEntite, Polygon terrainPolygon, Point bois, Point or, int carreSize, int cercleSize) {
        for (Entite entite : entites) {
            dessiner.dessinerEntite(entite, g);
            collision.detecterCollisions(entites);
            verifierPosition(entite,entites,ressourcesMonde);
           
        }
    }


    private void verifierPosition(Entite entite, List<Entite> entites, RessourcesMonde ressourcesMonde) {
        // Créer un nouveau thread
        Thread verifierPositionThread = new Thread(() -> {
            GeometryUtils.verifierZoneTerrain(entites);
            GeometryUtils.verifierZoneBois(entites);
            GeometryUtils.verifierZoneOr(entites);
            collecterRessourcesSiDansZone(entite, ressourcesMonde, entites);

            try {
                // Pause de 1 seconde
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Démarrer le thread
        verifierPositionThread.start();
    }

    
    private void collecterRessourcesSiDansZone(Entite entite, RessourcesMonde ressourcesMonde, List<Entite> entites) {
        if (entite.isInTerrainZone() || entite.isInWoodZone() || entite.isInGoldZone()) {
            ressource.collecterRessourcesParEntite(entite, ressourcesMonde, entites,labelRessource);
        }
    }

    public void addMouseListenerToPopulations() {
        for (Population population : populations) {
            if (population != null) {
                entityDragHandler.addMouseListenerToEntity(population);
                entityDragHandler.addMouseMotionListenerToEntity(population);
            }
        }
        for (Militaire militaire : militaires) {
            if (militaire != null) {
                entityDragHandler.addMouseListenerToEntity(militaire);
                entityDragHandler.addMouseMotionListenerToEntity(militaire);
            }
        }
        for (Chercheur chercheur : chercheurs) {
            if (chercheur != null) {
                entityDragHandler.addMouseListenerToEntity(chercheur);
                entityDragHandler.addMouseMotionListenerToEntity(chercheur);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Dessine le terrain
        dessiner.dessinerTerrain(g);

        // Dessine le carré (bois)
        dessiner.dessinerBois(g);

        // Dessine le cercle (or)
        dessiner.dessinerOr(g);

        List<Entite> entites = new ArrayList<>();
        entites.addAll(populations);
        entites.addAll(militaires);
        entites.addAll(chercheurs);

        dessiner(g, entites, "Entite", terrainPolygon, ressourcesMonde.getWoodPoint(), ressourcesMonde.getGoldPoint(), 50, 50);

    }


    public void setEntites(List<Population> populations, List<Militaire> militaires, List<Chercheur> chercheurs) {
        this.populations = populations;
        this.militaires = militaires;
        this.chercheurs = chercheurs;
        addMouseListenerToPopulations();
        repaint();
    }

}