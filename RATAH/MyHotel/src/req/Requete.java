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
        String query = "SELECT client_id, nom, prenom FROM Clients";
        return query;
    }
	public static String getChambre() {
        String query = "SELECT chambre_id, type FROM Chambres";
        return query;
    }
	
}

