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
        for (int i = 0; i < plan.getPlan().getPersonnes().size(); i++) {
            Entite draggedPersonne = plan.getPlan().getPersonnes().get(i);
            if (draggedPersonne.getDragging()) {
                draggedPersonne.updateCoord(e);
                return;   
            }
        }
    }
}
