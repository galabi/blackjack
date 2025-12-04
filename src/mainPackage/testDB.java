package mainPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class testDB {

    public static void main(String[] args) {
    }

    public static Connection connect() {
        String url = "jdbc:sqlite:BlackJack.db"; // יוצר קובץ אם לא קיים
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.err.println("Error connecting to SQLite database:");
            e.printStackTrace();
            return null;
        }
    }

    public static void checkForTable() {
        try (Connection conn = connect()) {

            if (conn == null) {
                System.err.println("Connection returned null! Table not created.");
                return;
            }

            try (Statement stmt = conn.createStatement()) {

                stmt.execute("""
                    CREATE TABLE IF NOT EXISTS users (
                        id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,
                        user_name TEXT NOT NULL,
                        user_money INTEGER NOT NULL,
                        user_games_count INTEGER NOT NULL
                    );
                """);

                System.out.println("Table 'users' was created or already exists!");

            }
        } catch (SQLException e) {
            System.err.println("SQL error while creating table:");
            e.printStackTrace();
        }
    }
    public static void addUser(String name, int money, int gamesCount) {
        String sql = "INSERT INTO users (user_name, user_money, user_games_count) VALUES (?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setInt(2, money);
            stmt.setInt(3, gamesCount);

            stmt.executeUpdate();

            System.out.println("User added: " + name);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
