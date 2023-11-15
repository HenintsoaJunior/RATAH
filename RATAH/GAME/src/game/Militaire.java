package game;

import java.awt.Point;
import java.awt.Rectangle;

public class Militaire extends Entite {

    public Militaire(int x, int y,Civilisation civilisation) {
        super(x, y,civilisation);
        setZone(40);
        setAttaque(1);
    }
    
    @Override
    public void deplacer(int nouvelleX, int nouvelleY) {
        super.deplacer(nouvelleX, nouvelleY);
    }

}
