
package dessin;


import javax.swing.*;

import centrale.Centrale;
import game.Entite;
import ressource.Champ;
import run.Run;

import java.awt.event.*;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;


import java.io.*;
public class DessinPlan extends JPanel implements Serializable{

    Plan plan;
    int refreshInterval = 1000; //to update the screen each one second
    Timer refreshTimer;

    public DessinPlan(Plan p) {
        this.plan=p;
        this.setBackground(Color.black);
        Run timer = new Run(p.getPersonnes(),p.getChamps());    // initiate the RunTime
        timer.setCoolDown(3);       // set the coolDown

        Thread timerThread = new Thread(timer);
        timerThread.start();    // start the thread

        // init the refresh timer
        refreshTimer = new Timer(refreshInterval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            { repaint(); }
        });
        refreshTimer.start(); 
    }
    
    public Plan getPlan(){
        return plan;
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);

        // Dessiner la bordure englobante pour toutes les formes
        Rectangle boundingBox = null; // Initialise le rectangle englobant

        for (Champ champ : plan.getChamps()) {
            Rectangle champBoundingBox = champ.getBoundingBox();

            // Mettre à jour le rectangle englobant pour inclure chaque forme
            if (champBoundingBox != null) {
                if (boundingBox == null) {
                    boundingBox = new Rectangle(champBoundingBox);
                } else {
                    boundingBox = boundingBox.union(champBoundingBox);
                }
            }

            // Dessiner chaque forme
            champ.show(g);
        }

     // Dessiner la bordure englobante une fois toutes les formes dessinées
        if (boundingBox != null) {
            int borderThickness = 50; // Définir l'épaisseur de la bordure
            g.drawRect(boundingBox.x - borderThickness, boundingBox.y - borderThickness,
                       boundingBox.width + 2 * borderThickness, boundingBox.height + 2 * borderThickness);
        }

        for (Centrale caserne : plan.getCasernes()) {
            caserne.show(g);
        }
        
        for (Entite personne : plan.getPersonnes()) {
            personne.show(g);
        }
        


        g.drawString("Argent : " + plan.getClans().get(0).getResource(), 1200, 150);
        g.drawString("Argent : " + plan.getClans().get(1).getResource(), 10, 150);

        this.repaint();
    }
}
