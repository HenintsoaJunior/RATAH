package dessin;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.Entite;

public class Dessinateur {
	
	
	private static void barreDeVie(Entite entite, Graphics g){
        g.setColor(new Color(255, 150, 150));
        g.fillRect((int)(entite.getPosition().getX()) - (entite.getZoneWidth() / 4), (int)(entite.getPosition().getY() - 20),
                (int)(entite.getVie()) * 5, entite.getHumanHeight() / 2);

        g.setColor(Color.RED);
        g.drawRect((int)(entite.getPosition().getX()) - (entite.getZoneWidth() / 4), (int)(entite.getPosition().getY() - 20),
                (int)(entite.getVieMax()) * 5, entite.getHumanHeight() / 2);
    }
	
    public static void dessinerEntite(Entite entite, Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        switch (entite.getRole()) {
            case "police":
                dessinerPolice(entite, g, g2d);
                break;
            case "chercheur":
                dessinerChercheur(entite, g, g2d);
                break;
            case "civil":
                dessinerCivil(entite, g, g2d);
                break;
        }
        barreDeVie(entite, g);
        dessinerImage(entite, g);
    }
    private static void dessinerImage(Entite entite, Graphics g) {
        try {
            Image image = ImageIO.read(new File("C:\\Users\\HENINTSOA\\Documents\\github\\RATAH\\JEUX\\src\\image\\br.png"));
            int x = (int) entite.getPosition().getX() - (entite.getHumanWidth() * 2); // Centrage horizontal
            int y = (int) entite.getPosition().getY() - (entite.getHumanHeight() * 2); // Centrage vertical
            g.drawImage(image, x, y, entite.getHumanWidth() * 4, entite.getHumanHeight() * 4, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void dessinerPolice(Entite entite, Graphics g, Graphics2D g2d) {
    	barreDeVie(entite,g);
        g2d.setColor(Color.RED);
        g2d.draw(entite.getZoneAttack());

        g.drawString("P", (int) entite.getPosition().getX() - (entite.getHumanWidth() / 2),
                (int) entite.getPosition().getY() - (entite.getHumanWidth() / 2));
    }

    private static void dessinerChercheur(Entite entite, Graphics g, Graphics2D g2d) {
    	barreDeVie(entite,g);
        g2d.setColor(Color.WHITE);
        g2d.draw(entite.getZone());

        g.drawString("CH", (int) entite.getPosition().getX() - (entite.getHumanWidth() / 2),
                (int) entite.getPosition().getY() - (entite.getHumanWidth() / 2));
    }

    private static void dessinerCivil(Entite entite, Graphics g, Graphics2D g2d) {
    	barreDeVie(entite,g);
        g2d.setColor(Color.WHITE);
        g2d.draw(entite.getZone());

        g.drawString("C", (int) entite.getPosition().getX() - (entite.getHumanWidth() / 2),
                (int) entite.getPosition().getY() - (entite.getHumanWidth() / 2));
    }
}
