package ressource;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Polygon;
import java.io.*;
public class FormeGeometrique implements Serializable{
    List<Point> circonference;

    public FormeGeometrique() {
        this.circonference = new ArrayList<Point>();
    }
    public FormeGeometrique(List<Point> v){
        this.circonference=v;
    }
    public void setCirconference(List<Point> c){
        this.circonference=c;
    }
    public List <Point> getCirconference(){
        return circonference;
    }
  

    public double calculerCirconference(List<Point> circonference) {
        if (circonference == null || circonference.size() < 2) {
            return 0.0; // Gérer les cas où il n'y a pas assez de points pour former une circonférence
        }

        double circonferenceTotal = 0.0;

        for (int i = 1; i < circonference.size(); i++) {
            Point pointActuel = circonference.get(i);
            Point pointPrecedent = circonference.get(i - 1);

            // Calcul de la distance entre deux points consécutifs
            double distance = pointActuel.distance(pointPrecedent);

            // Ajout de la distance à la circonférence totale
            circonferenceTotal += distance;
        }

        // Ajout de la distance entre le dernier et le premier point pour fermer la circonférence
        Point premierPoint = circonference.get(0);
        Point dernierPoint = circonference.get(circonference.size() - 1);
        circonferenceTotal += dernierPoint.distance(premierPoint);

        return circonferenceTotal;
    }

    
    public Rectangle getBoundingBox() {
        if (circonference == null || circonference.isEmpty()) {
            return null;
        }

        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Point point : circonference) {
            minX = Math.min(minX, point.x);
            minY = Math.min(minY, point.y);
            maxX = Math.max(maxX, point.x);
            maxY = Math.max(maxY, point.y);
        }

        int width = maxX - minX;
        int height = maxY - minY;

        return new Rectangle(minX, minY, width, height);
    }
}