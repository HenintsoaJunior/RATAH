package dessin;

import javax.imageio.ImageIO;
import javax.swing.*;

import centrale.Centrale;
import game.Entite;
import game.Hopital;
import ressource.Champ;
import run.Run;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class DessinPlan extends JPanel implements Serializable {

    Plan plan;
    int refreshInterval = 1000;
    Timer refreshTimer;
    private Image backgroundImage;

    public DessinPlan(Plan p) {
        this.plan = p;

        Run timer = new Run(p.getPersonnes(), p.getChamps(), p.getHopital());
        timer.setCoolDown(3);

        Thread timerThread = new Thread(timer);
        timerThread.start();

        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\HENINTSOA\\Documents\\github\\RATAH\\JEUX\\src\\image\\fond.jpg")); // Charger l'image ici
        } catch (IOException e) {
            e.printStackTrace();
        }

        refreshTimer = new Timer(refreshInterval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        refreshTimer.start();
    }

    public Plan getPlan() {
        return plan;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        for (Champ champ : plan.getChamps()) {
            champ.show(g);
        }
        for (Hopital hopital : plan.getHopital()) {
            hopital.show(g);
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
