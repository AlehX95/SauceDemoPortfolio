package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseConnection {
	
	private static final Logger logger = LogManager.getLogger(DatabaseConnection.class);
    private static final String URL = "jdbc:mysql://localhost:3306/saucedb";
    private static final String USER = "root"; 
    private static final String PASSWORD = "Pedromoreno1995";

    private DatabaseConnection() {
        
    }

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
        	 logger.error("❌ Error connecting to the database: " + e.getMessage());
            return null;
        }
    }
}


