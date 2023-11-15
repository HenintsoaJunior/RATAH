package centrale;


import java.awt.event.*;
import java.awt.*;
import java.awt.Point;
import java.awt.Rectangle;
import game.Chercheur;
import game.Civilisation;
import game.Entite;
import game.Militaire;
import game.Population;
import java.util.List;

import java.io.*;
public class Centrale implements Serializable {
    Civilisation clan;
    String type;
    Rectangle caserneRect;
    final double militaire = 75.0 , chercheur = 100 , civil = 50;
    public Centrale(){}
    public Centrale(Rectangle r,String type,Civilisation c){
        this.caserneRect=r;
        this.type=type;
        this.clan=c;
    }
    public Civilisation getClan(){
        return clan;
    }
    public void setClan(Civilisation c){
        this.clan=c;
    }
    public void setType(String s){
        this.type=s;
    }
    public String getType(){
        return type;
    }
    public Rectangle getCaserneRect(){
        return caserneRect;
    }
    private boolean test(double cost) throws Exception{
        if(this.clan.getResource()>=cost){
            return true;
        }
        throw new Exception("No Enough Money");
    }
    public Militaire generatePolice(Point p)throws Exception{
        if(this.test(militaire)){
            this.clan.setResource((this.clan.getResource())-militaire);
            return new Militaire(p,this.clan);
        }
        return null;
    }
    public Population generateCivilNormale(Point p)throws Exception{
        if(this.test(civil)){
            this.clan.setResource((this.clan.getResource())-civil);
            return new Population(p,this.clan);
        }
        return null;
    }
    public Chercheur generateChercheur(Point p)throws Exception{
        if(this.test(chercheur)){
            this.clan.setResource((this.clan.getResource())-chercheur);
            return new Chercheur(p,this.clan);
        }
        return null;
    }
    public void isMousePressed(Centrale c,java.util.List<Entite> p,MouseEvent e)throws Exception{
        Point initPosition = new Point((int) (caserneRect.getX() -1), (int) (caserneRect.getY() -1));
        if (this.caserneRect.contains(e.getX(), e.getY())) {
            switch(c.type){
                case "police":
                    p.add(this.generatePolice(initPosition));
                    break;
                case "chercheur":
                    p.add(this.generateChercheur(initPosition));
                    break;
                case "civil":
                    p.add(this.generateCivilNormale(initPosition));
                    break;
            }
        }
    }
    public void show(Graphics g) {
        g.setColor(Color.CYAN);
        g.drawRect(this.getCaserneRect().x, this.getCaserneRect().y, this.getCaserneRect().width, this.getCaserneRect().height);

        g.setColor(Color.WHITE);
        g.drawString(this.getType(), this.getCaserneRect().x + 20, this.getCaserneRect().y + 50);
    }
}