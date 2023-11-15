package ressource;

import java.awt.Point;
public class RessourcesMonde {
    private Point terrainPoint; // Coordonnées du terrain
    private Point woodPoint;    // Coordonnées du bois
    private Point goldPoint;    // Coordonnées de l'or
    
   
    public double getTerrainResourceRate() {
        // Ajoutez ici le taux de collecte pour le terrain
        return 0.01; // Exemple : 10 AR/s
    }

    public double getWoodResourceRate() {
        // Ajoutez ici le taux de collecte pour le bois
        return 0.015; // Exemple : 15 AR/s
    }

    public double getGoldResourceRate() {
        // Ajoutez ici le taux de collecte pour l'or
        return 0.05; // Exemple : 50 AR/s
    }

    public RessourcesMonde(Point terrainPoint, Point woodPoint, Point goldPoint) {
        super();
        this.terrainPoint = terrainPoint;
        this.woodPoint = woodPoint;
        this.goldPoint = goldPoint;
    }

    public Point getTerrainPoint() {
        return terrainPoint;
    }

    public void setTerrainPoint(Point terrainPoint) {
        this.terrainPoint = terrainPoint;
    }

    public Point getWoodPoint() {
        return woodPoint;
    }

    public void setWoodPoint(Point woodPoint) {
        this.woodPoint = woodPoint;
    }

    public Point getGoldPoint() {
        return goldPoint;
    }

    public void setGoldPoint(Point goldPoint) {
        this.goldPoint = goldPoint;
    }
}
