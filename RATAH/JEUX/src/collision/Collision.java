package collision;

import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import game.Entite;
import ressource.Champ;

public class Collision {
	
	
public static List<Entite> getPeopleInFieldOfAttack(Entite entite,List<Entite> allPeople) {
        
        List<Entite> peopleInFieldOfVision = new ArrayList<>();

        for (Entite otherPerson : allPeople) {
            if (!entite.equals(otherPerson) && !entite.getCivilisation().equals(otherPerson.getCivilisation())) {
                System.out.println("in champ d attack="+isInFieldOfAttack(entite,otherPerson.getPosition()));
                if (isInFieldOfAttack(entite,otherPerson.getPosition())) {
                    peopleInFieldOfVision.add(otherPerson);
                }
            }
        }

        return peopleInFieldOfVision;
    }
	
	public static List<Entite> getAllEnemie(Entite entite,List<Entite> p){
        List<Entite> result=new ArrayList<Entite>();
        for(Entite person : p){
            if(!((person.getCivilisation().getNameClan()).equals(entite.getCivilisation().getNameClan()))){
                result.add(person);
            }
        }
        return result;
    }
	public static List<Entite> getPeopleInFieldOfVision(Entite entity, List<Entite> allPeople) {
        List<Entite> peopleInFieldOfVision = new ArrayList<>();

        for (Entite otherPerson : allPeople) {
            if (!entity.equals(otherPerson)
                    && !entity.getCivilisation().equals(otherPerson.getCivilisation())
                    && otherPerson.getRole().equals("police")) {
                System.out.println("in champ d Vision=" + isInFieldOfVision(entity, otherPerson.getPosition()));
                if (isInFieldOfVision(entity, otherPerson.getPosition())) {
                    peopleInFieldOfVision.add(otherPerson);
                }
            }
        }

        return peopleInFieldOfVision;
    }
    public static boolean isInFieldOfVision(Entite entity, Point otherPersonPosition) {
        return entity.getZone().contains(otherPersonPosition.getX(), otherPersonPosition.getY());
    }

    public static boolean isInFieldOfAttack(Entite entity, Point otherPersonPosition) {
        return entity.getZoneAttack().contains(otherPersonPosition.getX(), otherPersonPosition.getY());
    }

    public static boolean isInChamp(Entite entity, Champ champ) {
        return champ.getBoundingBox().contains(entity.getPosition());
    }


    public static Point pointDeFuite(Point entityPosition, List<Entite> policiers) {
        double sumX = 0;
        double sumY = 0;

        for (Entite point : policiers) {
            sumX += point.getPosition().getX();
            sumY += point.getPosition().getY();
        }

        double barycentreX = sumX / policiers.size();
        double barycentreY = sumY / policiers.size();

        int fuiteX = entityPosition.x - (int) barycentreX;
        int fuiteY = entityPosition.y - (int) barycentreY;

        double facteurFuite = 0.5;
        fuiteX = (int) (fuiteX * facteurFuite);
        fuiteY = (int) (fuiteY * facteurFuite);

        int fuitePointX = entityPosition.x + fuiteX;
        int fuitePointY = entityPosition.y + fuiteY;

        return new Point(fuitePointX, fuitePointY);
    }

    public static Point barycentre(Point centre, List<Entite> points) {
        double sumX = 0;
        double sumY = 0;

        for (Entite point : points) {
            sumX += point.getPosition().getX();
            sumY += point.getPosition().getY();
        }

        double barycentreX = sumX / points.size();
        double barycentreY = sumY / points.size();

        return new Point((int) barycentreX, (int) barycentreY);
    }
}
