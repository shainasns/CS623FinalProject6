package ACIDTransaction;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class FinalProject {
	
	public static void main(String args[]) throws SQLException, IOException, 
	ClassNotFoundException {
		
		String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
		String username = "postgres";
		String password = "tanvi@123";
		
		
		Connection conn = DriverManager.getConnection(jdbcURL, username, password);
		System.out.println("connect");

		// Set atomicity
		conn.setAutoCommit(false);

		// Set isolation
		conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

		Statement stmt = null;
				
		try {
			stmt = conn.createStatement();
					
			// Execute updates
			stmt.executeUpdate("INSERT INTO depot VALUES ('d100', 'Chicago', 100)");
			stmt.executeUpdate("INSERT INTO stock VALUES ('p1', 'd100', 100)");
			} catch (SQLException e) {
			System.out.println(e);
					
			// Roll back if error occured to ensure atomicity
			conn.rollback();
			stmt.close();
			conn.close();
			return;
		}
		
		// Commit transaction and close connection
		conn.commit();
		stmt.close();
		conn.close();
		
	}

}
