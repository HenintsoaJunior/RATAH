package ressource;

import java.util.*;
import java.util.List;

import game.Chercheur;
import game.Entite;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.Color;
import java.awt.*;
import java.io.*;
public class Champ extends FormeGeometrique implements Serializable {
    double productivite;
    String type;
    List<Chercheur> claimer = new ArrayList<Chercheur>();
    Map<String,Double> claiming =new HashMap<>();
    public Champ(){}
    public Champ(List<Point> v,String type){
        super(v);
        this.type=type;
    }
    public List<Chercheur> getClaimer(){
        return claimer;
    }
    public double getProductivite(){
        switch(type){
            case "gold":
                return 60.0;
            case "wood":
                return 20.0;
            case "ground":
                return  40.0;
        }
        return productivite;
    }
    
    public Map<String,Double> getClaming(){
        return claiming;
    }
    
    public static List<Entite> peoplesInChamp(Champ c, List<Entite> v) {
        List<Entite> personnesDansChamp = new ArrayList<Entite>();
        for (Entite personne : v) {
            if (personne.isInChamp(c)) {
                personnesDansChamp.add(personne);
            }
        }

        return personnesDansChamp;
    }
    
    public void show(Graphics g){
        Rectangle boundingBox = this.getBoundingBox();

        if(this.type.equals("gold")){
        	if (boundingBox != null) {
                int x = boundingBox.x;
                int y = boundingBox.y;
                int width = boundingBox.width;
                int height = boundingBox.height;

                // Utilisation des coins du rectangle pour former un pentagone approximatif
                int[] xPoints = {x + width / 2, x + width, x + (int) (width * 0.8), x + (int) (width * 0.2), x};
                int[] yPoints = {y, y + (int) (height * 0.3), y + height, y + height, y + (int) (height * 0.3)};

                Polygon pentagon = new Polygon(xPoints, yPoints, 5);

                g.setColor(Color.YELLOW);
                g.drawPolygon(pentagon);
        }
        	} else {
            if(this.type.equals("wood")){
                g.setColor(new Color(204, 102, 0));
            } else if(this.type.equals("ground")){
                g.setColor(Color.white);
            }
            g.drawRect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
            
        }
    }
}
