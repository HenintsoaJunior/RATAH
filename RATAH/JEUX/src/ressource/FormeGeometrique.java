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

    public void resizeSquares() {
        int newWidth =50;
        int newHeight = 50;

        if (circonference == null || circonference.size() % 4 != 0) {
            // Vérification si la liste de points représente des carrés (nombre de points multiple de 4)
            return;
        }

        for (int i = 0; i < circonference.size(); i += 4) {
            Point p1 = circonference.get(i);
            Point p2 = circonference.get(i + 1);
            Point p3 = circonference.get(i + 2);
            Point p4 = circonference.get(i + 3);

            int minX = Math.min(Math.min(p1.x, p2.x), Math.min(p3.x, p4.x));
            int minY = Math.min(Math.min(p1.y, p2.y), Math.min(p3.y, p4.y));

            p1.setLocation(minX, minY);
            p2.setLocation(minX + newWidth, minY);
            p3.setLocation(minX + newWidth, minY + newHeight);
            p4.setLocation(minX, minY + newHeight);
        }
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