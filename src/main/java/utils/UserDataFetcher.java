package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDataFetcher {

    /**
     * Obtiene las credenciales de un usuario según su nombre.
     * Si no se especifica nombre, devuelve el primer registro encontrado.
     */
    public static String[] getUserCredentials(String username) {
        String[] credentials = new String[2];

        String query = (username == null || username.isEmpty())
                ? "SELECT username, password FROM users LIMIT 1"
                : "SELECT username, password FROM users WHERE username = ? LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            if (username != null && !username.isEmpty()) {
                stmt.setString(1, username);
            }

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                credentials[0] = rs.getString("username");
                credentials[1] = rs.getString("password");
                System.out.println("✅ Credenciales obtenidas de la DB para: " + credentials[0]);
            } else {
                System.out.println("⚠️ No se encontró el usuario: " + username);
            }

        } catch (Exception e) {
            System.out.println("❌ Error al obtener credenciales desde la base de datos: " + e.getMessage());
        }

        return credentials;
    }
}


