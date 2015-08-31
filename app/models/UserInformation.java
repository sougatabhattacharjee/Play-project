/**
 * 
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import play.Logger;
import play.db.DB;

/**
 * @author Sougata
 * Aug 25, 2015 
 */
public class UserInformation {
	
	public int me;
	public int other;
	
	public String twitter_name;
	public String quora_name;
	public String quora_email;
	public String quora_password;
	public String goodreads_name;
	public String system_time;
	public String username;
	public int user_id;
	public static boolean flag = false;
	
	
	
	
	
	public UserInformation() {
		super();
	}





	public UserInformation(String twitter_name, String quora_name, String goodreads_name) {
		super();
		this.twitter_name = twitter_name;
		this.quora_name = quora_name;
		this.goodreads_name = goodreads_name;
	}





	public static boolean userNameSearch(String username){
		
		
		
		Logger.info("Inside userNameSearch() method!");
		String result = "";
		try {

			ResultSet rs;
			Connection conn = DB.getConnection();
			

			PreparedStatement ps = conn.prepareStatement("select username from registration where username = ?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			while (rs.next() ) {
				result = rs.getString("username");
			}
			conn.close();
			ps.close();
			rs.close();
		} catch (Exception e) {
			System.err.println("Got an exception!");
		}
		
		
		if(StringUtils.isNotBlank(result))
			flag = true;	//username exists
		
		
		return flag;
	}





	/**
	 * @return the twitter_name
	 */
	public String getTwitter_name() {
		return twitter_name;
	}





	/**
	 * @return the quora_name
	 */
	public String getQuora_name() {
		return quora_name;
	}





	/**
	 * @return the goodreads_name
	 */
	public String getGoodreads_name() {
		return goodreads_name;
	}
	
	
	
}
