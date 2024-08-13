package mainPackage;

import java.sql.*;
import java.util.ArrayList;

import game.Members;


public class DBconnect {
	
	static String sql;
	static String url = "jdbc:mysql://localhost:3306/blackjack",username = "root",password = "0000";
	ResultSet rs;
	Statement st;
	Connection con = null;
	Main main;
	
	public int id,money,gamesPlayed;
	public String name; 
	public ArrayList<Members> members;
	
	public DBconnect(Main main) {
		this.main = main;
		try {
			con = DriverManager.getConnection(url,username,password);
			st = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void read() throws SQLException {
		members = new ArrayList<Members>();
		String sql = "select * from users";
		rs = st.executeQuery(sql);
		while(rs.next()) {
			id = Integer.valueOf(rs.getString(1));
			name = rs.getString(2);
			money = Integer.valueOf(rs.getString(3));
			gamesPlayed = Integer.valueOf(rs.getString(4));
			members.add(new Members(name,money,gamesPlayed,id,main));	
		}

	}
	
	public void write(int id,String name,int money,int games_played) throws SQLException{
		
		sql = "UPDATE users SET  user_name = ?, user_money = ?, user_games_played = ? WHERE id = ?;";
		
		PreparedStatement ps = con.prepareStatement(sql);  
		ps.setString(1,(name)); 
		ps.setString(2, Integer.toString(money)); 
        ps.setString(3, Integer.toString(games_played)); 
        ps.setString(4, Integer.toString(id));
        int i = ps.executeUpdate();
           if (i > 0) { 
               System.out.println("Product Updated"); 
           } else {
               System.out.println("Error Occured");
           }
	}
	
	public void deletePlayer(int id) throws SQLException{
		sql = "DELETE FROM users WHERE id = ?";
		
		PreparedStatement ps = con.prepareStatement(sql);  
        ps.setString(1, Integer.toString(id)); 
        int i = ps.executeUpdate();
           if (i > 0) { 
               System.out.println("Product Updated"); 
           } else {
               System.out.println("Error Occured");
           }
	}
	
	public void createPlayer(String name) throws SQLException {
		sql = "INSERT INTO users(user_name, user_money,user_games_played) VALUES (?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(sql);  
        ps.setString(1,(name)); 
        ps.setString(2, Integer.toString(500)); 
        ps.setString(3, Integer.toString(0)); 
        int i = ps.executeUpdate();
           if (i > 0) { 
               System.out.println("Product Updated"); 
           } else {
               System.out.println("Error Occured");
           }
	}
}
