package schoolLibraryProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Replace these variables with your FreeSQLDatabase details
    private static final String DB_NAME = "sql7744870";
    private static final String DB_USER = "sql7744870";
    private static final String DB_PASSWORD = "AWLDfNqSnc";
    private static final String DB_HOST = "sql7.freesqldatabase.com";
    private static final String DB_PORT = "3306"; // Check this on FreeSQLDatabase

    // JDBC URL for connection
    private static final String URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Open a connection
            connection = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to the database successfully.");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
        return connection;
    }
}
