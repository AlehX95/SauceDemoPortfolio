package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserDataFetcher {
	
	private static final Logger logger = LogManager.getLogger(DatabaseConnection.class);


    public static String[] getUserCredentials(String username) {
        String[] credentials = new String[2];  // [0]=username, [1]=password

        String query;
        if (username == null || username.isEmpty()) {
            query = "SELECT username, password FROM users LIMIT 1";
        } else {
            query = "SELECT username, password FROM users WHERE username = ? LIMIT 1";
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            if (username != null && !username.isEmpty()) {
                stmt.setString(1, username);
            }

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                credentials[0] = rs.getString("username");
                credentials[1] = rs.getString("password");
                logger.info("User credentials fetched successfully from DB for user: {}", credentials[0]);
            } else {
                logger.warn("No user found in DB for username: {}", username);
            }

        } catch (Exception e) {
        	logger.error("Error fetching user credentials from DB: {}", e.getMessage(), e);
        }

        return credentials;
    }
}


