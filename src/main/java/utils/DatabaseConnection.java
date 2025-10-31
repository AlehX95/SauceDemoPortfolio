package utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseConnection {

    private static final Logger logger = LogManager.getLogger(DatabaseConnection.class);
    private static Connection connection;

    private DatabaseConnection() {
        
    }

public static Connection getConnection() {
    if (connection != null) {
        return connection; // Already connected
    }

    Properties props = new Properties();

    try (InputStream input = DatabaseConnection.class
            .getClassLoader()
            .getResourceAsStream("config/config.properties")) {

        if (input != null) {
            props.load(input);
            logger.info("✅ Loaded config.properties from classpath");
        } else {
            // Fallback: look in local path
            String path = System.getProperty("user.dir") + "/src/test/resources/config/config.properties";
            File file = new File(path);
            logger.warn("⚠️ config.properties not found in classpath. Trying local path: {}", path);

            if (file.exists()) {
                try (InputStream fileInput = new FileInputStream(file)) {
                    props.load(fileInput);
                    logger.info("✅ Loaded config.properties from local path: {}", file.getAbsolutePath());
                }
            } else {
                logger.error("❌ config.properties not found in classpath or local path.");
                return null;
            }
        }

        // Extract database values from properties
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        // Validate required properties
        if (url == null || user == null || password == null) {
            logger.error("❌ Missing required database configuration in config.properties.");
            return null;
        }

        // Try to connect to database
        connection = DriverManager.getConnection(url, user, password);
        logger.info("✅ Successfully connected to database: {}", url);

    } catch (IOException e) {
        logger.error("❌ Error loading config.properties: ", e);
    } catch (SQLException e) {
        logger.error("❌ SQL connection error: ", e);
    } catch (Exception e) {
        logger.error("❌ Unexpected error while connecting to database: ", e);
    }

    return connection;
}


    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                logger.info(" Connection closed correctly");
            }
        } catch (SQLException e) {
            logger.error("❌ Error trying to close the connection: ", e);
        }
    }
}
