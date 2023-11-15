package dessin;


import javax.swing.*;

import centrale.Centrale;
import game.Entite;
import ressource.Champ;
import run.Run;

import java.awt.event.*;
import java.awt.Point;
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
        this.setBackground(Color.DARK_GRAY);
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

        for (Champ champ : plan.getChamps()) {
            champ.show(g);
        }
        for (Centrale caserne : plan.getCasernes()) {
            caserne.show(g);
        }
        for (Entite personne : plan.getPersonnes()) {
            personne.show(g);
        }
        
        g.drawString("Point : " + plan.getClans().get(0).getResource(), 1200, 150);
        g.drawString("Point : " + plan.getClans().get(1).getResource(), 10, 150);

        this.repaint();
    }
}
