package game;


import ressource.Champ;

import java.util.HashMap;
import java.util.Map;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

public class Chercheur extends Entite{
    final double bonus=100;
    public Chercheur(){}
    public Chercheur(Point p,Civilisation cl){
        super(p,cl);
        this.setRole("chercheur");
    }  
    public void bost(Champ champ) {
        boolean test=false;
        double actualProd;
        boolean test1=false;
        for(Chercheur chercheur : champ.getClaimer()){
            if(this == chercheur){
                test1=true;
            }
        }
        if(test1 == false){
            champ.getClaimer().add(this);
            for (Map.Entry<String,Double> entry : champ.getClaming().entrySet()){
                    if(entry.getKey().equals(this.getCivilisation().getNameClan())){
                        actualProd=entry.getValue()+((bonus * champ.getProductivite()) / 100);
                        champ.getClaming().put(this.getCivilisation().getNameClan(),actualProd);
                        test = true;
                    }
            }
            if(test == false){
                champ.getClaming().put(this.getCivilisation().getNameClan(),(champ.getProductivite()+((bonus * champ.getProductivite()) / 100)));
            }  
        }      
    }
    public void removeSearcherBost(Champ champ){
        double actualProd;
        for (Map.Entry<String,Double> entry : champ.getClaming().entrySet()){
            if(entry.getKey().equals(this.getCivilisation().getNameClan()) 
            && entry.getValue() > (champ.getProductivite()+((bonus * champ.getProductivite()) / 100))){
                actualProd=entry.getValue()-((bonus * champ.getProductivite()) / 100);
                champ.getClaming().put(this.getCivilisation().getNameClan(),actualProd);
            }else{
                champ.getClaming().remove(this.getCivilisation().getNameClan());
            }
        }
    }
}