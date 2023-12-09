package conn;

import java.sql.Connection;
import java.sql.SQLException;

public class GetConnection {

	public static Connection getConnection(String tableName) throws SQLException {
	        return ConnOracle.getConnectionOracle();
	}
}
