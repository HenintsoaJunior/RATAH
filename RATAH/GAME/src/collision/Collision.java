package collision;

import java.awt.Point;
import java.awt.Polygon;
import java.util.List;
import javax.swing.JLabel;

import dessin.GeometryUtils;
import game.Chercheur;
import game.Civilisation;
import game.Entite;
import game.Militaire;
import game.Population;
import ressource.RessourcesMonde;

public class Collision {

    public Collision(/* Ajoutez les paramètres nécessaires */) {
        // Initialisez les attributs si nécessaire
    }
    
    public void detecterCollisions(List<Entite> entites) {
        for (Entite entite : entites) {
            if (entite instanceof Militaire) {
                for (Entite autreEntite : entites) {
                    // Vérifier si l'autre entité est une population ou un chercheur adverse
                    if ((autreEntite instanceof Population || autreEntite instanceof Chercheur) && !autreEntite.getCivilisation().equals(entite.getCivilisation())) {
                        // Vérifier s'il y a collision
                        if (collisionDetected(entite, autreEntite)) {
                            //System.out.println("Collision détectée entre Militaire et " + autreEntite.getClass().getSimpleName());
                        } else {
                            //System.out.println("Pas de collision entre Militaire et " + autreEntite.getClass().getSimpleName());
                        }
                    }
                }
            }
        }
    }

    public static boolean collisionDetected(Entite entite1, Entite entite2) {
    	if (distanceBetweenPoints(entite1.getPoint(), entite2.getPoint()) < entite1.getZone()+entite2.getZone()) {               
               	entite1.attaquerEntite(entite2);
               	entite2.attaquerEntite(entite1);
                return true;
            }
      
        return false;
    }

    public static double distanceBetweenPoints(Point p1, Point p2) {
        return Math.hypot(p2.x - p1.x, p2.y - p1.y);
    }

}
