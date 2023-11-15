package dessin;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;
import java.util.List;

import game.Entite;

public class GeometryUtils {

	private static List<Point[]> terrainList;
    private static List<Point[]> boisList;
    private static List<Point[]> orList;

    @SuppressWarnings("static-access")
	public GeometryUtils(List<Point[]> terrainList, List<Point[]> boisList, List<Point[]> orList) {
        this.terrainList = terrainList;
        this.boisList = boisList;
        this.orList = orList;
    }

    public static boolean estDansTerrain(Entite entite, List<Entite> entities) {
        for (Point[] terrainPoints : terrainList) {
            Polygon terrainPolygon = new Polygon();
            for (Point terrainPoint : terrainPoints) {
                terrainPolygon.addPoint(terrainPoint.x, terrainPoint.y);
            }
            if (terrainPolygon.contains(entite.getPoint()) && entities.stream().anyMatch(e -> terrainPolygon.contains(e.getPoint()))) {
                return true;
            }
        }
        return false;
    }

    public static boolean estDansBois(Entite entite, List<Entite> entities) {
        for (Point[] boisPoints : boisList) {
            Polygon boisPolygon = new Polygon();
            for (Point point : boisPoints) {
                boisPolygon.addPoint(point.x, point.y);
            }
            if (boisPolygon.contains(entite.getPoint()) && entities.stream().anyMatch(e -> boisPolygon.contains(e.getPoint()))) {
                return true;
            }
        }
        return false;
    }

    public static boolean estDansOr(Entite entite, List<Entite> entities) {
        for (Point[] orPoints : orList) {
            Polygon orPolygon = new Polygon();
            for (Point point : orPoints) {
                orPolygon.addPoint(point.x, point.y);
            }
            if (orPolygon.contains(entite.getPoint()) && entities.stream().anyMatch(e -> orPolygon.contains(e.getPoint()))) {
                return true;
            }
        }
        return false;
    }
	public static Point[] createPoints(double... coordinates) {
	    int numPoints = coordinates.length / 2;
	    Point[] points = new Point[numPoints];

	    for (int i = 0; i < numPoints; i++) {
	        int xIndex = 2 * i;
	        int yIndex = 2 * i + 1;
	        points[i] = new Point((int) coordinates[xIndex], (int) coordinates[yIndex]);
	    }

	    return points;
	}


    public static void deplacerDroite(Point[] points, int deplacement) {
        for (Point point : points) {
            point.setLocation(point.getX() + deplacement, point.getY());
        }
    }

    public static void deplacerGauche(Point[] points, int deplacement) {
        for (Point point : points) {
            point.setLocation(point.getX() - deplacement, point.getY());
        }
    }

    public static void deplacerHaut(Point[] points, int deplacement) {
        for (Point point : points) {
            point.setLocation(point.getX(), point.getY() - deplacement);
        }
    }

    public static void deplacerBas(Point[] points, int deplacement) {
        for (Point point : points) {
            point.setLocation(point.getX(), point.getY() + deplacement);
        }
    }

    public static void reduireTaille(Point[] points, double facteurEchelle) {
        for (Point point : points) {
            point.setLocation(point.getX() * facteurEchelle, point.getY() * facteurEchelle);
        }
    }
   


    public static void verifierZoneTerrain(List<Entite> entites) {
        for (Entite entite : entites) {
            boolean estDansTerrain = estDansTerrain(entite, entites);
            if (estDansTerrain && !entite.isInTerrainZone()) {
                System.out.println("Le " + entite.getCivilisation() + " est dans la zone du terrain");
                entite.setInTerrainZone(true);
            } else if (!estDansTerrain && entite.isInTerrainZone()) {
                System.out.println("Le " + entite.getCivilisation() + " a quitté la zone du terrain");
                entite.setInTerrainZone(false);
            }
        }
    }

    public static void verifierZoneBois(List<Entite> entites) {
        for (Entite entite : entites) {
            boolean estDansBois = estDansBois(entite, entites);
            if (estDansBois && !entite.isInWoodZone()) {
                System.out.println("Le " + entite.getCivilisation() + " est dans la zone du bois");
                entite.setInWoodZone(true);
            } else if (!estDansBois && entite.isInWoodZone()) {
                System.out.println("Le " + entite.getCivilisation() + " a quitté la zone du bois");
                entite.setInWoodZone(false);
            }
        }
    }

    public static void verifierZoneOr(List<Entite> entites) {
        for (Entite entite : entites) {
            boolean estDansOr = estDansOr(entite, entites);
            if (estDansOr && !entite.isInGoldZone()) {
                System.out.println(entite.getCivilisation() + " est dans la zone de l'or");
                entite.setInGoldZone(true);
            } else if (!estDansOr && entite.isInGoldZone()) {
                System.out.println("Le " + entite.getCivilisation() + " a quitté la zone de l'or");
                entite.setInGoldZone(false);
            }
        }
    }

}
