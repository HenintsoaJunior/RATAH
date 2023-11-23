package main;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import centrale.Centrale;
import dessin.DessinPlan;
import dessin.Plan;
import fenetre.Fenetre;
import files.File;
import game.Civilisation;
import game.Hopital;
import listener.Listener;
import ressource.Champ;

public class Affichage {

	
    public static void main(String[] args) {
        List<Civilisation> clans = new ArrayList<>();
        Civilisation civ = new Civilisation("Clan 1", Color.RED);
        Civilisation civ2 = new Civilisation("Clan 2", Color.BLUE);
        clans.add(civ);
        clans.add(civ2);

        List<Champ> listeChamps = createChamps();
        List<Centrale> listeCaserne = createCasernes(clans);
        List<Hopital> listeHopital =createHospitals(clans);
        

        Plan plan = new Plan(listeChamps, listeCaserne,listeHopital, new ArrayList<>(), clans);
        	
	
	        if(File.load() != null){
	            Plan load = File.load();
	            plan = load;
	        }
	        DessinPlan planDrawer=new DessinPlan(plan);
	        Listener myMouseListener =new Listener(planDrawer);
	        Fenetre window =new Fenetre();
	        window.setPlanDrawer(planDrawer);
	        window.myMouseListeneradd(myMouseListener);
    }
    
    public static List<Champ> createChamps() {
        List<Champ> listeChamps = new ArrayList<>();

        // Offset pour déplacement de 400 pixels vers la gauche (-300 + 100) et de 100 pixels vers le haut
        int xOffset = -400;
        int yOffset = -100;

        // Champ 1
        List<Point> listePoint = new ArrayList<>();
        listePoint.add(new Point(500 + 120 + xOffset, 150 + 100 + yOffset)); // Déplacement de 100 pixels vers le bas
        listePoint.add(new Point(650 + 120 + xOffset, 150 + 100 + yOffset));
        listePoint.add(new Point(650 + 120 + xOffset, 250 + 100 + yOffset));
        listePoint.add(new Point(500 + 120 + xOffset, 250 + 100 + yOffset));
        Champ f = new Champ(listePoint, "gold");
        listeChamps.add(f);

        // Champ 2 (f2)
        List<Point> listePoint2 = new ArrayList<>();
        listePoint2.add(new Point(500 + 120 + xOffset, 50 + 100 + yOffset)); // Déplacement de 100 pixels vers le bas
        listePoint2.add(new Point(650 + 120 + xOffset, 50 + 100 + yOffset));
        listePoint2.add(new Point(650 + 120 + xOffset, 130 + 100 + yOffset));
        listePoint2.add(new Point(500 + 120 + xOffset, 130 + 100 + yOffset));
        Champ f2 = new Champ(listePoint2, "wood");
        listeChamps.add(f2);

        // Champ 3 (f3)
        List<Point> listePoint3 = new ArrayList<>();
        listePoint3.add(new Point(500 + 120 + xOffset, 300 + 100 + yOffset)); // Déplacement de 100 pixels vers le bas
        listePoint3.add(new Point(650 + 120 + xOffset, 300 + 100 + yOffset));
        listePoint3.add(new Point(650 + 120 + xOffset, 375 + 100 + yOffset));
        listePoint3.add(new Point(500 + 120 + xOffset, 375 + 100 + yOffset));
        Champ f3 = new Champ(listePoint3, "ground");
        listeChamps.add(f3);

        return listeChamps;
    }

    public static List<Centrale> createCasernes(List<Civilisation> clans) {
        List<Centrale> listeCaserne = new ArrayList<>();

        // Casernes pour clans.get(0)
        Centrale cas = new Centrale(new Rectangle(500, 150, 100, 100), "police", clans.get(0));
        listeCaserne.add(cas);

        Centrale cas1 = new Centrale(new Rectangle(500, 270, 100, 100), "civil", clans.get(0));
        listeCaserne.add(cas1);


        // Casernes pour clans.get(1)
        Centrale cas3 = new Centrale(new Rectangle(10, 150, 100, 100), "police", clans.get(1));
        listeCaserne.add(cas3);

        Centrale cas4 = new Centrale(new Rectangle(10, 270, 100, 100), "civil", clans.get(1));
        listeCaserne.add(cas4);

        return listeCaserne;
    }

    public static List<Hopital> createHospitals(List<Civilisation> clans) {
        List<Hopital> hospitalList = new ArrayList<>();

        // Hôpital 1
        Hopital hospital1 = new Hopital(new Rectangle(1250, 650, 100, 100), "soins", clans.get(0));
        
        hospitalList.add(hospital1);
        
        
        // Hôpital 2 (hospital2)
        Hopital hospital2 = new Hopital(new Rectangle(10, 650, 100, 100), "soins", clans.get(1));
        hospitalList.add(hospital2);


        return hospitalList;
    }

}