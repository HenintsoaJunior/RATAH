package game;

import java.awt.*;

public abstract class Entite {
	private Color color;
    private Point point;
    private Civilisation civilisation;
    private boolean inTerrainZone = false;
    private boolean inWoodZone = false;
    private boolean inGoldZone = false;
    private int zone;
    private double vie;
    private double attaque;
    private boolean deplacee;
    private boolean collisionDetected;
    
    
    
    public Entite(int x, int y, Civilisation civilisation) {
        this.point = new Point(x, y);
        this.civilisation = civilisation;
        setVie(10);
    }
    
    
    
    public boolean isCollisionDetected() {
		return collisionDetected;
	}



	public void setCollisionDetected(boolean collisionDetected) {
		this.collisionDetected = collisionDetected;
	}



	public boolean isDeplacee() {
		return deplacee;
	}


	public void setDeplacee(boolean deplacee) {
		this.deplacee = deplacee;
	}


	public void attaquerEntite(Entite entite) {
        	entite.setVie(entite.getVie() - this.getAttaque());
        	 System.out.println("Vie de "+entite.getClass().getSimpleName()+" diminuée à " + entite.getVie());
        
    }
    
	public double getAttaque() {
		return attaque;
	}


	public void setAttaque(double attaque) {
		this.attaque = attaque;
	}


	public double getVie() {
		return vie;
	}
	public void setVie(double vie) {
		this.vie = vie;
	}
	public int getZone() {
		return zone;
	}
	public void setZone(int zone) {
		this.zone = zone;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public void setCivilisation(Civilisation civilisation) {
		this.civilisation = civilisation;
	}
	public boolean isInTerrainZone() {
		return inTerrainZone;
	}
	public void setInTerrainZone(boolean inTerrainZone) {
		this.inTerrainZone = inTerrainZone;
	}
	public boolean isInWoodZone() {
		return inWoodZone;
	}
	public void setInWoodZone(boolean inWoodZone) {
		this.inWoodZone = inWoodZone;
	}
	public boolean isInGoldZone() {
		return inGoldZone;
	}
	public void setInGoldZone(boolean inGoldZone) {
		this.inGoldZone = inGoldZone;
	}
	
    public Civilisation getCivilisation() {
        return civilisation;
    }
    
    public void deplacer(int nouvelleX, int nouvelleY) {
        setPoint(new Point(nouvelleX, nouvelleY));
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
	
}
