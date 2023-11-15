package ressource;

import java.awt.Polygon;
import java.util.List;
import javax.swing.JLabel;

import dessin.GeometryUtils;
import game.Chercheur;
import game.Civilisation;
import game.Entite;
import game.Population;

public class GestionRessources {

    private double ressourceAcollecter;

    public GestionRessources() {
    }

    public void collecterRessourcesSurBois(Entite entite, RessourcesMonde ressourcesMonde, List<Entite> entites) {
        if (GeometryUtils.estDansTerrain(entite, entites)) {
            ressourceAcollecter += ressourcesMonde.getWoodResourceRate() * compterEntitesCollectant(entites, entite, ressourcesMonde);
        }
    }

    public void collecterRessourcesSurOr(Entite entite, RessourcesMonde ressourcesMonde, List<Entite> entites) {
        if (GeometryUtils.estDansBois(entite, entites)) {
            ressourceAcollecter += ressourcesMonde.getGoldResourceRate() * compterEntitesCollectant(entites, entite, ressourcesMonde);
        }
    }

    public void collecterRessourcesSurTerrain(Entite entite, RessourcesMonde ressourcesMonde, List<Entite> entites) {
        if (GeometryUtils.estDansOr(entite, entites)) {
            ressourceAcollecter += ressourcesMonde.getTerrainResourceRate() * compterEntitesCollectant(entites, entite, ressourcesMonde);
        }
    }

    public int compterEntitesCollectant(List<Entite> entites, Entite entite, RessourcesMonde ressourcesMonde) {
        int nbEntitesCollectant = 1;  // Initialisation avec 1, car l'entité actuelle collecte

        for (Entite autreEntite : entites) {
            if (autreEntite != entite) {
                if (GeometryUtils.estDansBois(autreEntite, entites)) {
                    nbEntitesCollectant++;
                    System.out.println(nbEntitesCollectant);
                }

                if (GeometryUtils.estDansOr(autreEntite, entites)) {
                    nbEntitesCollectant++;
                }

                if (GeometryUtils.estDansTerrain(autreEntite, entites)) {
                    nbEntitesCollectant++;
                }
            }
        }

        return nbEntitesCollectant;
    }

    public void collecterRessourcesParEntite(Entite entite, RessourcesMonde ressourcesMonde, List<Entite> entites, JLabel labelRessource) {
        if (entite != null && ressourcesMonde != null && entites != null) {
            Civilisation civilisation = entite.getCivilisation();
            int nbEntitesCollectant = compterEntitesCollectant(entites, entite, ressourcesMonde);

            if (entite instanceof Population || entite instanceof Chercheur) {
                collecterRessourcesSurBois(entite, ressourcesMonde, entites);
                collecterRessourcesSurOr(entite, ressourcesMonde, entites);
                collecterRessourcesSurTerrain(entite, ressourcesMonde, entites);
            }

            double totalRessources = ressourceAcollecter;
            civilisation.setResources(totalRessources);
            labelRessource.setText("Ressource : " + totalRessources);
            System.out.println(entite.getClass().getSimpleName() + " a collecté " + ressourceAcollecter +
                    " ressources. Nouveau total : " + totalRessources);

            try {
                // Pause de 1 seconde
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
