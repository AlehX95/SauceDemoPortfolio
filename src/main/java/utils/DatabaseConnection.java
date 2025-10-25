package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/saucedb";
    private static final String USER = "root"; 
    private static final String PASSWORD = "Pedromoreno1995";

    private DatabaseConnection() {
        // 🔒 Evita que se instancie esta clase
    }

    public static Connection getConnection() {
        try {
            // Cargar driver (opcional desde JDBC 4.0, pero lo mantenemos por compatibilidad)
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("❌ Error al conectar a la base de datos: " + e.getMessage());
            return null;
        }
    }
}


