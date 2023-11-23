package run;


import java.awt.Point;
import java.lang.*;
import java.util.List;
import java.util.Vector;

import collision.Collision;
import game.Chercheur;
import game.Civilisation;
import game.Entite;
import game.Hopital;
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
    List<Hopital> listeHopital;
    
    
    public Run(List<Entite> listePersonne, List<Champ> listeChamps,List<Hopital> listeHopital) {
        this.currentTime = 0;
        this.running = true;
        this.fightingTimeCounter = 1;
        this.timeCounter = 0;
        this.testCounter=2;
        this.listePersonne = listePersonne;
        this.listeChamps = listeChamps;
        this.listeHopital=listeHopital;
    }
    
    public void run() {
        while (running) {
            this.currentTime++;      // update the time each second
            this.timeCounter++;     // incremente the time counter 
            this.fightingTimeCounter++;
            this.testCounter++;
            this.farming();     // method to handle some event each second
            this.fight();
            this.test(listeHopital,listePersonne);
            this.flee();
            
            try 
            { Thread.sleep(1000); } // Attendre une seconde
            catch (Exception e)  
            { e.printStackTrace();}
        }
    }

    public void farming() {
        if (timeCounter == this.coolDown) {
            for (Champ champ : listeChamps) {
                List<Entite> filtred = Champ.peoplesInChamp(champ, listePersonne);
                for (Entite personne : filtred) {
                    if (personne.getRole().equals("civil")) {
                        ((Population) personne).work(champ);
                        System.out.println("is working");
                    } else if (personne.getRole().equals("chercheur")) {
                        ((Chercheur) personne).bost(champ);
                        System.out.println("bosted");
                    }
                }
            }
            timeCounter = 0;
        }
    }

    public void fight(){
        //System.out.println("fightingCounter = "+(fightingTimeCounter == this.coolDown));
        if(fightingTimeCounter == this.coolDown){
            for(Entite p : listePersonne){
                //System.out.println("detecting police: "+p.getRole().equals("police"));
                if(p.getRole().equals("police")){
                    List<Entite> allperson =  Collision.getPeopleInFieldOfAttack(p, listePersonne);
                     System.out.println(allperson.size());
                    ((Militaire)p).kill(allperson);
                }
            }
            fightingTimeCounter = 2;
        }
    }

    public void test(List<Hopital> listeHopital, List<Entite> listePersonne) {
        for (int i = 0; i < listePersonne.size(); i++) {
            Entite entite = listePersonne.get(i);
            if (entite.getVie() <= 0) {
            	
                teleportToHospital(listeHopital, listePersonne, entite); // Téléporter l'entité à l'hôpital si nécessaire

                // Après la téléportation, vérifie si l'entité est encore présente dans la liste
                boolean stillInList = listePersonne.contains(entite);

                // Si l'entité n'est plus dans la liste après la téléportation, ajuste l'index
                if (!stillInList) {
                    i--; // Décrémentation pour compenser la suppression de l'entité de la liste
                }
            }
        }
    }



  
    public void teleportToHospital(List<Hopital> listeHopital, List<Entite> listePersonne, Entite entite) {
        if (entite.getVie() <= 0) {
            boolean teleporte = false; // Pour vérifier si l'entité a été téléportée
            boolean hopitalPlein = true; // Pour vérifier si l'hôpital est plein

            for (Hopital hopital : listeHopital) {
                if (hopital.getClan().equals(entite.getCivilisation()) && hopital.peutAccueillirEntite()) {
                    hopital.ajouterEntite(); // Augmente le compteur d'entités dans l'hôpital
                    entite.setPosition(new Point((int) hopital.getHopitalRect().getCenterX(), (int) hopital.getHopitalRect().getCenterY()));
                    entite.setVie(entite.getVieMax());
                    System.out.println("Entité téléportée vers l'hôpital !");
                    teleporte = true; // Met à jour le statut de téléportation
                    hopitalPlein = false; // Met à jour le statut de l'hôpital (il n'est plus plein)
                    break;
                }
            }

            if (!teleporte && hopitalPlein) {
                System.out.println("L'hôpital est plein, retrait des entités mordues...");

                // Suppression des entités mordues de la liste
                for (int i = listePersonne.size() - 1; i >= 0; i--) {
                    Entite e = listePersonne.get(i);
                    if (e.getVie() <= 0) {
                        listePersonne.remove(i);
                        System.out.println("Entité retirée de la liste: " + e);
                    }
                }
            }
        }
    }
    
    public void flee() {
        for (Entite p : listePersonne) {
            for (Champ champ : listeChamps) {
                decideFlee(p, champ);
            }
        }
    }
 
    private void handlePoliceFlee(Entite p) {
        List<Entite> allPerson = Collision.getPeopleInFieldOfVision(p, listePersonne);
        if (allPerson.size() != 0) {
            double totalAdversariesLife = 0;
            boolean shouldFlee = false;

            for (Entite adversary : allPerson) {
                totalAdversariesLife += adversary.getVie();
            }

            for (Entite adversary : allPerson) {
                if (adversary.getVie() > p.getVie() && totalAdversariesLife > p.getVie()) {
                    shouldFlee = true;
                    break;
                }
            }

            if (shouldFlee) {
                p.setPosition(Collision.pointDeFuite(p.getPosition(), allPerson));
            }
        }
    }
    private void handleCivilOrResearcherFlee(Entite p) {
        List<Entite> allPerson = Collision.getPeopleInFieldOfVision(p, listePersonne);
        if (allPerson.size() != 0) {
            p.setPosition(Collision.pointDeFuite(p.getPosition(), allPerson));
        }
    }
    

    private void decideFlee(Entite p, Champ champ) {
        if (!(Collision.isInChamp(p, champ))) {
            if (p.getRole().equals("police")) {
                handlePoliceFlee(p);
            } else if (p.getRole().equals("civil") || p.getRole().equals("chercheur")) {
                handleCivilOrResearcherFlee(p);
            }
        }
    }

    
    public void stop() 
    { running = false; }

    
    public void setCoolDown(int c)
    { this.coolDown = c; }

}