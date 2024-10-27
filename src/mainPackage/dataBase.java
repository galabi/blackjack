package mainPackage;

import java.sql.*;
import java.util.ArrayList;

import game.Members;


public class dataBase {
	
	static String sql;
	static String url = "jdbc:mysql://localhost:3306/blackjack",username = "root",password = "0000";
	ResultSet rs;
	Statement st;
	Connection con = null;
	Main main;
	
	public int id,money,gamesPlayed;
	public String name; 
	public ArrayList<Members> members;
	
	public dataBase(Main main) {
		this.main = main;
		try {
			con = DriverManager.getConnection(url,username,password);
			st = con.createStatement();
		} catch (SQLException e) {
			System.out.println("Data Base is not connected");		}
		members = new ArrayList<Members>();
	}
	
	public void read() {
		try {

			String sql = "select * from users";
			rs = st.executeQuery(sql);
			while(rs.next()) {
				id = Integer.valueOf(rs.getString(1));
				name = rs.getString(2);
				money = Integer.valueOf(rs.getString(3));
				gamesPlayed = Integer.valueOf(rs.getString(4));
				members.add(new Members(name,money,gamesPlayed,members.size(),id,main));
				Main.homescreen.baseYMembers = 700 - (members.size()*30);
				members.get(members.size()-1).memberSelect();
			}
			updateLocation();
		}catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	public void write(int id,String name,int money,int games_played){
		sql = "UPDATE users SET  user_name = ?, user_money = ?, user_games_played = ? WHERE id = ?;";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);  
			ps.setString(1,(name)); 
			ps.setString(2, Integer.toString(money)); 
	        ps.setString(3, Integer.toString(games_played)); 
	        ps.setString(4, Integer.toString(id));
	        int i = ps.executeUpdate();
	           if (i > 0) { 
	        	   
	           } else {
	               System.out.println("Error Occured while updating data");
	           }
		}catch (Exception e) {
			// TODO: handle exception
		}
		updateLocation();
	}
	public void updateMoney(int id,int money) {
		sql = "UPDATE users SET user_money = ? WHERE id = ?;";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);  
			ps.setString(1, Integer.toString(money)); 
	        ps.setString(2, Integer.toString(id));
	        int i = ps.executeUpdate();
	           if (i > 0) { 

	           } else {
	               System.out.println("Error Occured while updating money");
	           }
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void updateGamesPlayed(int id,int games_played) {
		sql = "UPDATE users SET user_games_played = ? WHERE id = ?;";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);  
	        ps.setString(1, Integer.toString(games_played)); 
	        ps.setString(2, Integer.toString(id));
	        int i = ps.executeUpdate();
	           if (i > 0) { 
	           
	           } else {
	               System.out.println("Error Occured while updating games played");
	           }
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void deletePlayer(int member_number){
		sql = "DELETE FROM users WHERE id = ?;";
		try {
			PreparedStatement ps = con.prepareStatement(sql);  
	        ps.setString(1, Integer.toString(members.get(member_number).getId())); 
	        ps.executeUpdate();
	        
			members.remove(member_number);

	        for(int i = 0; i< members.size();i++) {
				members.get(i).setMemberNumber(i);
			}
			updateLocation();
			
		}catch (Exception e) {
			e.printStackTrace();		
			}
	}
	
	public void createPlayer(String name) {
		sql = "INSERT INTO users(user_name, user_money,user_games_played) VALUES (?, ?, ?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);  
	        ps.setString(1,(name)); 
	        ps.setString(2, Integer.toString(500)); 
	        ps.setString(3, Integer.toString(0)); 
	      
	        int i = ps.executeUpdate();
	           if (i > 0) { 

	           } else {
	               System.out.println("Error Occured while create Player");
	           }

	           try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
	               if (generatedKeys.next()) {
	                  members.add(new Members(name, 700, 0, members.size(), generatedKeys.getInt(1), main));
	  				Main.homescreen.baseYMembers = 700 - (members.size()*30);
					members.get(members.size()-1).memberSelect();
	               }
	           }
	           updateLocation();
		}catch (Exception e){

		}
	}
	
	public void updateLocation() {
		
		Main.homescreen.baseYMembers = 750 - (members.size()*30);
		for(Members i : members) {
			i.memberSelectUpdate();
		}
		Main.homescreen.login.setLocation(780, Main.homescreen.baseYMembers -30);
	}
}
