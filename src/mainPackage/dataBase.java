package mainPackage;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;

import game.Members;


public class dataBase {
	
	public static Main main;
	
	public static ArrayList<Members> members = new ArrayList<Members>();
	
    public static Connection connect() {    	
    	String path = "";
        try {
            path = new File(dataBase.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String url = "jdbc:sqlite:" + path + "/BlackJack.db";
        try {
        	Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            System.err.println("Could not load SQLite JDBC Driver!");
            e.printStackTrace();
            return null;
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
            }
        } catch (SQLException e) {
            System.err.println("SQL error while creating table:");
            e.printStackTrace();
        }
    }
    
	public static void read() {
		String sql = "SELECT id, user_name, user_money, user_games_count FROM users";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
        	
        	members.clear();
        	
        	while (rs.next()) {
        		int id = rs.getInt("id");
        		String name = rs.getString("user_name");
        		int money = rs.getInt("user_money");
        		int gamesPlayed = rs.getInt("user_games_count");
        		
        		Members newMember = new Members(name, money, gamesPlayed, members.size(), id, main);
        		members.add(newMember);
        		
        		newMember.memberSelect();
        		
        		}
            
            updateLocation(); 

        } catch (SQLException e) {
        	System.out.println("Error occurred while reading users");
        	e.printStackTrace();
        }
    }

	
	public static void write(int id, String newName, int newMoney, int newGamesCount) {
        String sql = "UPDATE users SET user_name = ?, user_money = ?, user_games_count = ? WHERE id = ?";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	
            pstmt.setString(1, newName);
            pstmt.setInt(2, newMoney);
            pstmt.setInt(3, newGamesCount);
            pstmt.setInt(4, id);
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Error Occured while updating user");
        }
    }
	
	public static void updateMoney(int id,int newMoney) {
		String sql = "UPDATE users SET user_money = ? WHERE id = ?;";
		
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	
        	pstmt.setInt(1, newMoney); 
        	pstmt.setInt(2, id);
        	pstmt.executeUpdate();

		}catch (SQLException e) {
            System.out.println("Error Occured while updating money");
		}
	}
	
	public static void updateGamesPlayed(int id,int NewGamesPlayed) {
		String sql = "UPDATE users SET user_games_count = ? WHERE id = ?;";
				
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	
        	pstmt.setInt(1, NewGamesPlayed); 
        	pstmt.setInt(2, id);
        	pstmt.executeUpdate();

		}catch (SQLException e) {
            System.out.println("Error Occured while updating gamesplayed");
		}
	}
	
	public static void deletePlayer(int memberNumber){
		String sql = "DELETE FROM users WHERE id = ?;";
		
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	
        	pstmt.setInt(1, members.get(memberNumber).getId());
        	pstmt.executeUpdate();
        	
        	members.remove(memberNumber);
        	
	        for(int i = 0; i< members.size();i++) {
				members.get(i).setMemberNumber(i);
			}
			updateLocation();

		}catch (Exception e) {
            System.out.println("Error Occured while deleting member");
		}
        
	}
	
	public static void createPlayer(String name) {
		
        String sql = "INSERT INTO users (user_name, user_money, user_games_count) VALUES (?, ?, ?)";

        try (Connection conn = connect();
        		PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, name);
            stmt.setInt(2, 500);
            stmt.setInt(3, 0);

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            	if (generatedKeys.next()) {
            		members.add(new Members(name, 500, 0, members.size(), generatedKeys.getInt(1), main));
            		
            		members.get(members.size()-1).memberSelect();
            		}
            	}
            updateLocation();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static void updateLocation() {
		if (Main.homescreen == null) return;

		Main.homescreen.baseYMembers = 750 - (members.size()*30);
		for(Members i : members) {
			i.memberSelectUpdate();
		}
		Main.homescreen.login.setLocation(880, Main.homescreen.baseYMembers -40);
	}
	public static void setMainInstance(Main m){
		main = m;
	}
}
