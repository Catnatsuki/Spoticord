package catnatsuki.dicordify.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class createDb {
    private static final String DB_FOLDER = "db";
    private static final String DB_NAME = "picks.db";

    public static void main(String[] args) {
        // Create the db folder if it doesn't exist
        String url = "jdbc:sqlite:./db/picks.db";
        String createTableSQL = "CREATE TABLE IF NOT EXISTS additions ("
                + "userId TEXT PRIMARY KEY NOT NULL,"
                + "monthlyCounter INTEGER,"
                + "entryMonth INTEGER,"
                + "entryYear INTEGER"
                + ");";

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {

            // Execute the SQL command to create the table
            statement.execute(createTableSQL);

            System.out.println("Table 'additions' has been created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
