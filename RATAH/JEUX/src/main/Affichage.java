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
import game.Entite;
import listener.Listener;
import ressource.Champ;

public class Affichage {
    public static void main(String[] args) {
        /// CLAN
        List<Civilisation> clans=new ArrayList<Civilisation>();
        Civilisation civ = new Civilisation("Clan 1",Color.RED);

        Civilisation civ2 = new Civilisation("Clan 2",Color.BLUE);

        clans.add(civ);
        clans.add(civ2);

        /// FIELD
        List<Point> listePoint = new ArrayList<>();
        List<Point> listePoint2 = new ArrayList<>();
        List<Point> listePoint3 = new ArrayList<>();


        List<Champ> listeChamps = new ArrayList<>();

        Champ f = new Champ(listePoint, "gold");
        Champ f2 = new Champ(listePoint2, "wood");
        Champ f3 = new Champ(listePoint3, "ground");


        listePoint.add(new Point(500, 150));
        listePoint.add(new Point(650, 150));
        listePoint.add(new Point(650, 250));
        listePoint.add(new Point(500, 250));

        listePoint2.add(new Point(500, 50));
        listePoint2.add(new Point(650, 50));
        listePoint2.add(new Point(650, 130));
        listePoint2.add(new Point(500, 130));

        listePoint3.add(new Point(500, 300));
        listePoint3.add(new Point(650, 300));
        listePoint3.add(new Point(650, 375));
        listePoint3.add(new Point(500, 375));

        listeChamps.add(f);
        listeChamps.add(f2);
        listeChamps.add(f3);


        /// CASERNE
        List<Centrale> listeCaserne = new ArrayList<>();

        Centrale cas = new Centrale(new Rectangle(1250, 200, 100, 100),"police",civ);
        Centrale cas1 = new Centrale(new Rectangle(1250, 320, 100, 100),"civil",civ);
        Centrale cas2 = new Centrale( new Rectangle(1250, 450, 100, 100),"chercheur",civ);

        Centrale cas3 = new Centrale(new Rectangle(10, 200, 100, 100),"police",civ2);
        Centrale cas4 = new Centrale(new Rectangle(10, 320, 100, 100),"civil",civ2);
        Centrale cas5 = new Centrale(new Rectangle(10, 450, 100, 100),"chercheur",civ2);

        listeCaserne.add(cas);
        listeCaserne.add(cas1);
        listeCaserne.add(cas2);
        listeCaserne.add(cas3);
        listeCaserne.add(cas4);
        listeCaserne.add(cas5);

        Plan plan=new Plan(listeChamps,listeCaserne,new ArrayList<Entite>(),clans);
        
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
}