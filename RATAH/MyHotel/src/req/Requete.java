package req;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Requete {

	/*
	 *-------------------------------------Requete Oracle----------------------------------------------------------
	 */
	
	public static String getClients() {
        String query = "SELECT client_id, nom_client FROM Client";
        return query;
    }
	public static String getIntermediaire() {
        String query = "SELECT * FROM Intermediaire";
        return query;
    }
	public static String getMenu() {
        String query = "SELECT * FROM Menu";
        return query;
    }
	
	public static String getMenu(int id_restaurant) {
	    String query = "SELECT m.*FROM Menu m JOIN Restaurant r ON m.restaurant_id = r.restaurant_id WHERE m.restaurant_id = " + id_restaurant;
	    return query;
	}

	public static String getListeCommande(int restaurantId) {
	    String query = "SELECT * FROM VueListeCommandesRestaurants WHERE restaurant_id = " + restaurantId;
	    return query;
	}
	
	public static String getListeFacture(int restaurantId) {
	    String query = "SELECT * FROM VueDetailFacturesClient WHERE restaurant_id = " + restaurantId;
	    return query;
	}
	
	public static String getStatistique(int restaurantId) {
	    String query = "SELECT * FROM VueBeneficeRestaurants WHERE restaurant_id = " + restaurantId;
	    return query;
	}
	
	public static String getCommandes() {
        String query = "SELECT * FROM Commande";
        return query;
    }
	public static String getRestaurants() {
        String query = "SELECT * FROM Restaurant";
        return query;
    }
	
}