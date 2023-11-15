package game;


import java.util.HashMap;
import java.util.Map;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.Vector;

import ressource.Champ;
public class Population extends Entite{ 
    public Population(){}
    public Population(Point p,Civilisation cl){
        super(p,cl);
        this.setRole("civil");
    }  
    public void work(Champ champ){
        boolean test =false;
        for (Map.Entry<String,Double> entry : champ.getClaming().entrySet()){
            if(entry.getKey().equals(this.getCivilisation().getNameClan())){
                this.civilisation.setResource(this.getCivilisation().getResource()+entry.getValue());
                test=true;
            }
        }if(test == false){
            this.civilisation.setResource(this.getCivilisation().getResource()+champ.getProductivite());
        }
    }
}