package listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.*;

import centrale.Centrale;
import dessin.DessinPlan;
import game.Entite;


public class Listener extends MouseAdapter{
    private DessinPlan plan;
    public Listener(DessinPlan p) { 
        this.plan=p;
    }
    
    public void mousePressed(MouseEvent e) {
        for (Entite personne : plan.getPlan().getPersonnes()) 
        { personne.isMousePressed(personne, e); }

        for (Centrale c : plan.getPlan().getCasernes()) {
            try 
            { c.isMousePressed(c, plan.getPlan().getPersonnes(),e); } 
            
            catch (Exception ex) 
            { JOptionPane.showMessageDialog(null, ex.getMessage(), "Alerte", JOptionPane.WARNING_MESSAGE); }
        }
        plan.repaint();
    }  
    
    public void mouseReleased(MouseEvent e) {
        for (Entite personne : plan.getPlan().getPersonnes()) 
        { personne.isMouseReleased(personne, e); }
    }
    
    public void mouseDragged(MouseEvent e) {
        boolean isPersonDragged = false;

        for (Entite draggedPersonne : plan.getPlan().getPersonnes()) {
            if (draggedPersonne.getDragging()) {
                draggedPersonne.updateCoord(e);
                isPersonDragged = true;
                break; // Sortir de la boucle dès qu'une personne est trouvée
            }
        }
        
        if (isPersonDragged) {
        	try 
            { Thread.sleep(0); } // Attendre une seconde
            catch (Exception es)  
            { es.printStackTrace();}
            //plan.repaint();
        }
    }
 }
