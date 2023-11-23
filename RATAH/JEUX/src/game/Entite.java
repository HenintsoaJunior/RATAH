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

import dessin.DessinPlan;
import dessin.Dessinateur;
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

    final int zoneAttackWidth = 60;
    final int zoneAttackHeight = 60;

    final int humanWidth = 10;
    final int humanHeight = 10;
    private boolean isAtHospital;
    private boolean Clignotant;
    
    String role;
    public Entite(){}
    public Entite(Point p,Civilisation cl){
        this.position=p;
        this.civilisation=cl;
        this.zone = new Ellipse2D.Double(p.getX() - (this.zoneHeight / 2), p.getY() - (this.zoneHeight / 2), this.zoneWidth, this.zoneHeight);
        this.zoneAttack = new Ellipse2D.Double(p.getX() - (this.zoneAttackHeight / 2), p.getY() - (this.zoneAttackHeight / 2), this.zoneAttackWidth, this.zoneAttackHeight);
    }
    
    
    public boolean isAtHospital() {
		return isAtHospital;
	}
    
	public void setAtHospital(boolean isAtHospital) {
		this.isAtHospital = isAtHospital;
	}
	
	public void setPosition(Point point) { 
	    this.position = point;
	    this.zone.setFrame(point.getX() - (this.zoneHeight / 2), point.getY() - (this.zoneHeight / 2), this.zoneWidth, this.zoneHeight); 
	    this.zoneAttack.setFrame(point.getX() - (this.zoneAttackHeight / 2), point.getY() - (this.zoneAttackHeight / 2), this.zoneAttackWidth, this.zoneAttackHeight);
	}

    public void updateCoord(MouseEvent e) {
    	
    	    try 
            { Thread.sleep(0); 
	            Point p = this.getPosition();
	            p.setLocation(e.getPoint());
	            this.setPosition(p);
	            this.zoneAttack.setFrame(p.getX() - (this.zoneAttackHeight / 2), p.getY() - (this.zoneAttackHeight / 2), this.zoneAttackWidth, this.zoneAttackHeight);
	        
            } 
            catch (Exception es)  
            { 
            	es.printStackTrace();
            
            }  
    }
     
    public boolean isMousePressed(Entite personne, MouseEvent e) {
        Point p = personne.getPosition();
        Ellipse2D zone = personne.getZone();
        if (zone.contains(e.getPoint())) {
            System.out.println("The person is pressed");
            personne.setDragging(true);
            return true; // Retourne true si la personne est pressée
        } else {
            System.out.println("Not pressed");
            return false; // Retourne false si la personne n'est pas pressée
        }
    }

    
    public void isMouseReleased(Entite personne, MouseEvent e) { 
        personne.setDragging(false); 
    }
    
    public void show(Graphics g) {
        Dessinateur.dessinerEntite(this, g);   
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
	public boolean isClignotant() {
		return Clignotant;
	}
	public void setClignotant(boolean clignotant) {
		Clignotant = clignotant;
	}
    

}