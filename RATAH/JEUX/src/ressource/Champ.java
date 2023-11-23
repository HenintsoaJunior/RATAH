package ressource;

import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;

import collision.Collision;
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
    private int width = 40; // Largeur par défaut à 40
    private int height = 40; // Hauteur par défaut à 40

    public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Map<String, Double> getClaiming() {
		return claiming;
	}
	public void setClaiming(Map<String, Double> claiming) {
		this.claiming = claiming;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public void setProductivite(double productivite) {
		this.productivite = productivite;
	}
	public void setClaimer(List<Chercheur> claimer) {
		this.claimer = claimer;
	}
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
            if (Collision.isInChamp(personne,c)) {      
            	personnesDansChamp.add(personne);
            
            }   
        }

        return personnesDansChamp;
    }
    
    
    public void show(Graphics g) {
        Rectangle boundingBox = this.getBoundingBox();

        if (this.type.equals("gold")) {
            try {
                Image image = ImageIO.read(new File("C:\\Users\\HENINTSOA\\Documents\\github\\RATAH\\JEUX\\src\\image\\or.png")); // Remplacez par votre chemin d'image pour "gold"
                g.drawImage(image, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (this.type.equals("wood")) {
            try {
                Image image = ImageIO.read(new File("C:\\Users\\HENINTSOA\\Documents\\github\\RATAH\\JEUX\\src\\image\\forets.png")); // Remplacez par votre chemin d'image pour "wood"
                g.drawImage(image, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (this.type.equals("ground")) {
            try {
                Image image = ImageIO.read(new File("C:\\Users\\HENINTSOA\\Documents\\github\\RATAH\\JEUX\\src\\image\\tany.png")); // Remplacez par votre chemin d'image pour "ground"
                g.drawImage(image, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }    
}
