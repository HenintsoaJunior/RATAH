package game;

import java.awt.*;
import java.util.List;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Hopital implements Serializable {
    Civilisation clan;
    String type;
    Rectangle hopitalRect;
    private int nombreEntites; // Compteur d'entités dans l'hôpital
    private final int capaciteMax = 3; // Capacité maximale de l'hôpital

    public Hopital() {}

    public Hopital(Rectangle r, String type, Civilisation c) {
        this.hopitalRect = r;
        this.type = type;
        this.clan = c;
    }

    // Méthodes getters et setters pour les attributs

    public boolean peutAccueillirEntite() {
        return nombreEntites < capaciteMax; // Vérifie si l'hôpital peut accueillir plus d'entités
    }

    public void ajouterEntite() {
        if (peutAccueillirEntite()) {
            nombreEntites++;
        }
    }

    public void retirerEntite() {
        if (nombreEntites > 0) {
            nombreEntites--;
        }
    }
    
    public Rectangle getHopitalRect() {
        return hopitalRect;
    }


    public Civilisation getClan() {
		return clan;
	}

	public void setClan(Civilisation clan) {
		this.clan = clan;
	}

	public void setType(String s) {
        this.type = s;
    }

    public String getType() {
        return type;
    }

    public int getNombreEntites() {
		return nombreEntites;
	}

	public void setNombreEntites(int nombreEntites) {
		this.nombreEntites = nombreEntites;
	}

	public int getCapaciteMax() {
		return capaciteMax;
	}

	public void show(Graphics g) {
        g.setColor(Color.blue);
        g.drawRect(this.getHopitalRect().x, this.getHopitalRect().y, this.getHopitalRect().width, this.getHopitalRect().height);
        
        try {
            Image image = ImageIO.read(new File("C:\\Users\\HENINTSOA\\Documents\\github\\RATAH\\JEUX\\src\\image\\hop.png")); // Remplacez par votre chemin d'image

            // Dimension de l'image redimensionnée
            int imageWidth = this.getHopitalRect().width;
            int imageHeight = this.getHopitalRect().height;

            int x = this.getHopitalRect().x;
            int y = this.getHopitalRect().y;

            g.drawImage(image, x, y, imageWidth, imageHeight, null); // Dessiner l'image redimensionnée
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
