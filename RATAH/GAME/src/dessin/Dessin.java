package dessin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.List;

import game.Chercheur;
import game.Entite;
import game.Militaire;
import game.Population;
import ressource.RessourcesMonde;

public class Dessin {
    private RessourcesMonde ressourcesMonde;  // Assurez-vous d'avoir la classe RessourcesMonde définie
    private List<Point[]> terrainList; 
    private List<Point[]> boisList;
    private List<Point[]> orList;

    public Dessin(RessourcesMonde ressourcesMonde, List<Point[]> terrainList, List<Point[]> boisList, List<Point[]> orList) {
        this.ressourcesMonde = ressourcesMonde;
        this.terrainList = terrainList;
        this.boisList = boisList;
        this.orList = orList;
    }
    public static void drawRedSquare(Graphics g, int xOffset, int yOffset, int squareSize) {
        g.setColor(Color.RED);
        g.fillRect(xOffset, yOffset, squareSize, squareSize);
    }

    public static void drawGreenSquare(Graphics g, int xOffset, int yOffset, int squareSize) {
        g.setColor(Color.GREEN);
        g.fillRect(xOffset + squareSize, yOffset, squareSize, squareSize);
    }

    public static void drawBlueSquare(Graphics g, int xOffset, int yOffset, int squareSize) {
        g.setColor(Color.BLUE);
        g.fillRect(xOffset + 2 * squareSize, yOffset, squareSize, squareSize);
    }

    public void dessinerOr(Graphics g) {
        Color color = Color.YELLOW; // Couleur de l'or
        g.setColor(color);

        for (Point[] orPoints : orList) {
            Polygon polygon = new Polygon();
            for (Point orPoint : orPoints) {
                polygon.addPoint(orPoint.x, orPoint.y);
            }
            g.fillPolygon(polygon);
        }
    }

    public void dessinerTerrain(Graphics g) {
        Color terrainColor = Color.BLUE; // Couleur du terrain
        g.setColor(terrainColor);

        for (Point[] terrainPoints : terrainList) {
            Polygon polygon = new Polygon();
            for (Point terrainPoint : terrainPoints) {
                polygon.addPoint(terrainPoint.x, terrainPoint.y);
            }
            g.fillPolygon(polygon);
        }
    }

    public void dessinerBois(Graphics g) {
        Color color = new Color(204, 102, 0);
        g.setColor(color);

        for (Point[] boisPoints : boisList) {
            Polygon polygon = new Polygon();
            for (Point boisPoint : boisPoints) {
                polygon.addPoint(boisPoint.x, boisPoint.y);
            }
            g.fillPolygon(polygon);
        }
    }

    public static void dessinerEntite(Entite entite, Graphics g) {
        Color entityColor = null;

        if (entite.getVie() <= 0) {
            entityColor = Color.BLACK;
        } else if (entite.getColor() != null) {
            entityColor = entite.getColor();
        } else {
            if (entite instanceof Population) {
                entityColor = Color.RED;
            } else if (entite instanceof Militaire) {
                // Dessiner une zone (cercle) autour des militaires
                int radius =  entite.getZone();
                g.setColor(Color.GREEN); // Couleur de la zone des militaires
                g.drawOval(entite.getPoint().x - radius, entite.getPoint().y - radius, 2 * radius, 2 * radius);
                
                entityColor = Color.GREEN; // Couleur des militaires
            } else if (entite instanceof Chercheur) {
                entityColor = new Color(153, 255, 255);
            }
        }

        g.setColor(entityColor);
        g.fillOval(entite.getPoint().x - 10, entite.getPoint().y - 10, 20, 20);
    }

    public RessourcesMonde getRessourcesMonde() {
        return ressourcesMonde;
    }

    public void setRessourcesMonde(RessourcesMonde ressourcesMonde) {
        this.ressourcesMonde = ressourcesMonde;
    }
    
    
}
