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
    public Polygon getBoundingTriangle() {
        if (circonference == null || circonference.size() < 3) {
            return null;
        }

        // Creation d'un triangle base sur les trois premiers points de la liste
        Polygon triangle = new Polygon();
        triangle.addPoint(circonference.get(0).x, circonference.get(0).y);
        triangle.addPoint(circonference.get(1).x, circonference.get(1).y);
        triangle.addPoint(circonference.get(2).x, circonference.get(2).y);

        return triangle;
    }
    public Polygon getBoundingHexagon() {
        if (circonference == null || circonference.size() < 6) {
            return null;
        }

        // Creation d'un hexagone base sur les six premiers points de la liste
        Polygon hexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            hexagon.addPoint(circonference.get(i).x, circonference.get(i).y);
        }

        return hexagon;
    }
}