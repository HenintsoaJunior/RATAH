package game;


import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.List;

import collision.Collision;

public class Militaire extends Entite{
    public Militaire(){}
    public Militaire(Point p,Civilisation cl){
        super(p,cl);
        this.setRole("police");
    }  
    public void kill(List<Entite> personnes){
        List<Entite> enemies = Collision.getAllEnemie(this, personnes);
        for(int i=0;i<enemies.size();i++){
            enemies.get(i).setVie((enemies.get(i)).getVie()-(this.getDamage()/enemies.size()));
        }
    }
}