package run;


import java.lang.*;
import java.util.List;
import java.util.Vector;

import game.Chercheur;
import game.Entite;
import game.Militaire;
import game.Population;
import ressource.Champ;


public class Run implements Runnable {
    long currentTime;
    boolean running;

    int fightingTimeCounter;
    int timeCounter;
    int testCounter;

    int coolDown;
    List<Entite> listePersonne;
    List<Champ> listeChamps;

    public Run(List<Entite> listePersonne, List<Champ> listeChamps) {
        this.currentTime = 0;
        this.running = true;
        this.fightingTimeCounter = 1;
        this.timeCounter = 0;
        this.testCounter=2;
        this.listePersonne = listePersonne;
        this.listeChamps = listeChamps;
    }

    public void run() {
        while (running) {
            this.currentTime++;      // update the time each second
            this.timeCounter++;     // incremente the time counter 
            this.fightingTimeCounter++;
            this.testCounter++;
            this.farming();     // method to handle some event each second
            this.fight();
            this.test();
            this.flee();
            try 
            { Thread.sleep(500); } //1000 for 1 second
            catch (Exception e)  
            { e.printStackTrace();}
        }
    }

    public void farming() {
        // periodique event
        List<Entite> filtred=null;
        if (timeCounter == this.coolDown) {
                for (Champ champ : listeChamps) {
                    for(Entite p : listePersonne){
                        if(champ.getClaimer().contains(p) && !p.isInChamp(champ)){
                            ((Chercheur)p).removeSearcherBost(champ);
                        }
                    }
                    filtred=Champ.peoplesInChamp(champ,listePersonne);
                    for(Entite personne : filtred){
                        if(personne.getRole().equals("civil")){
                            ((Population)personne).work(champ);
                            System.out.println("is working");
                        }
                        else if(personne.getRole().equals("chercheur")){
                            ((Chercheur)personne).bost(champ);
                            System.out.println("bosted");
                        }
                    }                     
                }
            timeCounter = 0;       // reset the time counter
        }
    }

    public void fight(){
        System.out.println("fightingCounter = "+(fightingTimeCounter == this.coolDown));
        if(fightingTimeCounter == this.coolDown){
            for(Entite p : listePersonne){
                System.out.println("detecting police: "+p.getRole().equals("police"));
                if(p.getRole().equals("police")){
                    List<Entite> allperson =  p.getPeopleInFieldOfAttack(listePersonne);
                     System.out.println(allperson.size());
                    ((Militaire)p).kill(allperson);
                }
            }
            fightingTimeCounter = 2;
        }
    }

    public void test(){
        for(int i=0;i<listePersonne.size();i++){
            if(listePersonne.get(i).getVie() <=0 ){
                listePersonne.remove(i);
            }
        }
    }

    public void flee(){
        for(Entite p : listePersonne){
            for (Champ champ : listeChamps) {
                if(!(p.isInChamp(champ))){
                    if(p.getRole().equals("civil") || p.getRole().equals("chercheur")){
                        List<Entite> allperson =  p.getPeopleInFieldOfVision(listePersonne);
                        if(allperson.size() !=0){
                            p.setPosition(Entite.pointDeFuite(p.getPosition(),allperson));
                        }
                    }
                }
            }
        }
    }
    public void stop() 
    { running = false; }

    public void setCoolDown(int c)
    { this.coolDown = c; }
}