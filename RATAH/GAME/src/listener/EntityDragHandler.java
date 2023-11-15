package listener;

import dessin.PanneauRessources;
import game.Entite;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EntityDragHandler {

    private PanneauRessources panneauRessources;
    private Entite selectedEntity;

    public EntityDragHandler(PanneauRessources panneauRessources) {
        this.panneauRessources = panneauRessources;
    }

    public void addMouseListenerToEntity(final Entite entity) {
        panneauRessources.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point entityPoint = entity.getPoint();
                if (isMouseNearPoint(e.getPoint(), entityPoint)) {
                    selectedEntity = entity;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                selectedEntity = null;
                panneauRessources.resetZoneStatus();
            }
        });
    }

    public void addMouseMotionListenerToEntity(final Entite entity) {
        panneauRessources.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedEntity != null) {
                    int dx = e.getX() - selectedEntity.getPoint().x;
                    int dy = e.getY() - selectedEntity.getPoint().y;

                    // Vérifier les limites de la fenêtre (800x600)
                    int newX = Math.max(0, Math.min(selectedEntity.getPoint().x + dx, 800 - 20));
                    int newY = Math.max(0, Math.min(selectedEntity.getPoint().y + dy, 600 - 20));

                    selectedEntity.deplacer(newX, newY);
                    panneauRessources.repaint();
                }
            }
        });
    }

    private boolean isMouseNearPoint(Point mousePoint, Point entityPoint) {
        double distanceThreshold = 10; // Ajustez cette valeur selon vos besoins
        return mousePoint.distance(entityPoint) <= distanceThreshold;
    }
}
