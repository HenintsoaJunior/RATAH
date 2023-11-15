package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ressource.Champ;

public class Entite implements Serializable{
    Point position;
    Ellipse2D zone;
    Ellipse2D zoneAttack;

    double damage=1.0;
    final double vieMax=10.0;
    double vie=10.0;
    Civilisation civilisation;
    boolean dragging = false;
    final int zoneWidth = 100;
    final int zoneHeight = 100;

    final int zoneAttackWidth = 50;
    final int zoneAttackHeight = 50;

    final int humanWidth = 10;
    final int humanHeight = 10;

    String role;
    public Entite(){}
    public Entite(Point p,Civilisation cl){
        this.position=p;
        this.civilisation=cl;
        this.zone = new Ellipse2D.Double(p.getX() - (this.zoneHeight / 2), p.getY() - (this.zoneHeight / 2), this.zoneWidth, this.zoneHeight);
        this.zoneAttack = new Ellipse2D.Double(p.getX() - (this.zoneAttackHeight / 2), p.getY() - (this.zoneAttackHeight / 2), this.zoneAttackWidth, this.zoneAttackHeight);
    }
    public int getZoneWidth(){
        return zoneWidth;
    }
    public int getZoneHeight(){
        return zoneHeight;
    }
    public double getDamage(){
        return damage;
    }
    public void setDragging(boolean b){
        this.dragging=b;
    }
    public boolean getDragging(){
        return dragging;
    }
    public Point getPosition(){
        return position;
    }
    public Ellipse2D getZone(){
        return zone;
    }
    public double getVie(){
        return vie;
    }
    public void setPosition(Point point) { 
        this.position = point;
        this.zone.setFrame(point.getX() - (this.zoneHeight / 2), point.getY() - (this.zoneHeight / 2), this.zoneWidth, this.zoneHeight); 
    }
    public void updateCoord(MouseEvent e) {
        Point p = this.getPosition();
        p.setLocation(e.getPoint());
        this.setPosition(p);
        this.zoneAttack.setFrame(p.getX() - (this.zoneAttackHeight / 2), p.getY() - (this.zoneAttackHeight / 2), this.zoneAttackWidth, this.zoneAttackHeight);
    }
        public List<Entite> getPeopleInFieldOfVision(List<Entite> allPeople) {
        List<Entite> peopleInFieldOfVision = new ArrayList<>();

        for (Entite otherPerson : allPeople) {
            if (!this.equals(otherPerson) && !this.getCivilisation().equals(otherPerson.getCivilisation()) && otherPerson.getRole().equals("police")) {
                System.out.println("in champ d Vision="+isInFieldOfVision(otherPerson.getPosition()));
                if (isInFieldOfVision(otherPerson.getPosition())) {
                    peopleInFieldOfVision.add(otherPerson);
                }
            }
        }

        return peopleInFieldOfVision;
    }
    public boolean isInFieldOfVision(Point otherPersonPosition) {
        return this.zone.contains(otherPersonPosition.getX(),otherPersonPosition.getY());  
    }
    public List<Entite> getPeopleInFieldOfAttack(List<Entite> allPeople) {
        
        List<Entite> peopleInFieldOfVision = new ArrayList<>();

        for (Entite otherPerson : allPeople) {
            if (!this.equals(otherPerson) && !this.getCivilisation().equals(otherPerson.getCivilisation())) {
                System.out.println("in champ d attack="+isInFieldOfAttack(otherPerson.getPosition()));
                if (isInFieldOfAttack(otherPerson.getPosition())) {
                    peopleInFieldOfVision.add(otherPerson);
                }
            }
        }

        return peopleInFieldOfVision;
    }
    public boolean isInFieldOfAttack(Point otherPersonPosition) {
        return this.zoneAttack.contains(otherPersonPosition.getX(),otherPersonPosition.getY());  
    }
    public boolean isInChamp(Champ champ) {
        return champ.getBoundingBox().contains(this.position);
    }
    public List<Entite> getAllEnemie(List<Entite> p){
        List<Entite> result=new ArrayList<Entite>();
        for(Entite person : p){
            if(!((person.getCivilisation().getNameClan()).equals(this.civilisation.getNameClan()))){
                result.add(person);
            }
        }
        return result;
    }
    public static Point pointDeFuite(Point personne, List<Entite> policiers) {
        // Calcule le barycentre des points des policiers avec le personne comme centre
        Point barycentre = barycentre(personne, policiers);

        // Calcule le vecteur de fuite en inversant la direction du vecteur personne -> barycentre
        int fuiteX = personne.x - barycentre.x;
        int fuiteY = personne.y - barycentre.y;

        // Applique un facteur de fuite (a ajuster selon vos besoins)
        double facteurFuite = 0.5;
        fuiteX = (int) (fuiteX * facteurFuite);
        fuiteY = (int) (fuiteY * facteurFuite);

        // Calcule le point de fuite en ajoutant le vecteur de fuite au point du personne
        int fuitePointX = personne.x + fuiteX;
        int fuitePointY = personne.y + fuiteY;

        return new Point(fuitePointX, fuitePointY);
    }
    public static Point barycentre(Point centre, List<Entite> points) {
        double sumX = 0;
        double sumY = 0;

        for (Entite point : points) {
            sumX += point.getPosition().getX();
            sumY += point.getPosition().getY();
        }

        double barycentreX = sumX / points.size();
        double barycentreY = sumY / points.size();

        return new Point((int) barycentreX, (int) barycentreY);
    }
    public void isMousePressed(Entite personne, MouseEvent e) {
        Point p = personne.getPosition();
        Ellipse2D zone = personne.getZone();
        if (zone.contains(e.getPoint())) {
            System.out.println("The person is pressed");
            personne.setDragging(true);
            return;
        } else {
            System.out.println("Not pressed");
        }
    }
    public void isMouseReleased(Entite personne, MouseEvent e) { 
        personne.setDragging(false); 
    }
    public void barreDeVie(Graphics g){
        g.setColor(new Color(255, 150, 150));
        g.fillRect((int)(this.position.getX())-(zoneWidth/2)/2, (int)(this.position.getY()-20), (int)(vie)*5, humanHeight/2);

        g.setColor(Color.RED);
        g.drawRect((int)(this.position.getX())-(zoneWidth/2)/2, (int)(this.position.getY()-20), (int)(vieMax)*5, humanHeight/2);
    } 
    public void show(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;   

        switch (this.role) {
            case "police":
                this.barreDeVie(g);
                g2d.setColor(Color.RED);
                g2d.draw(zoneAttack);

                g.drawString("P", (int) position.getX() - (this.humanWidth / 2), (int) position.getY() - (this.humanWidth / 2));
                break;
            case "chercheur":
            this.barreDeVie(g);
                g2d.setColor(Color.WHITE);
                g2d.draw(zone);

                g.drawString("CH", (int) position.getX() - (this.humanWidth / 2), (int) position.getY() - (this.humanWidth / 2));
                break;
            case "civil":
            this.barreDeVie(g);
                g2d.setColor(Color.WHITE);
                g2d.draw(zone);

                g.drawString("C", (int) position.getX() - (this.humanWidth / 2), (int) position.getY() - (this.humanWidth / 2));
                break;
        }
    }

    public Ellipse2D getZoneAttack() {
		return zoneAttack;
	}
	public void setZoneAttack(Ellipse2D zoneAttack) {
		this.zoneAttack = zoneAttack;
	}
	public Civilisation getCivilisation() {
		return civilisation;
	}
	public void setCivilisation(Civilisation civilisation) {
		this.civilisation = civilisation;
	}
	public double getVieMax() {
		return vieMax;
	}
	public int getZoneAttackWidth() {
		return zoneAttackWidth;
	}
	public int getZoneAttackHeight() {
		return zoneAttackHeight;
	}
	public int getHumanWidth() {
		return humanWidth;
	}
	public int getHumanHeight() {
		return humanHeight;
	}
	public void setDamage(double damage) {
		this.damage = damage;
	}
	public void setZone(Ellipse2D c){
        this.zone=c;
    }
    public void setVie(double v){
        this.vie=v;
    }
    
    public String getRole(){
        return role;
    }
    public void setRole(String r){
        this.role=r;
    }

}