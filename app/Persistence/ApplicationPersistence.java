package Persistence;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.*;
import play.db.DB;
import play.Logger;
/**
 * @author Sougata
 * Aug 25, 2015 
 */
public class ApplicationPersistence {
	private static final int CLIENT_CODE_STACK_INDEX;
	static {
		// Finds out the index of "this code" in the returned stack trace - funny but it differs in JDK 1.5 and 1.6
		int i = 0;
		for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
			i++;
			if (ste.getClassName().equals(ApplicationPersistence.class.getName())) {
				break;
			}
		}
		CLIENT_CODE_STACK_INDEX = i;
	}
	public static String methodName() {
		return Thread.currentThread().getStackTrace()[CLIENT_CODE_STACK_INDEX].getMethodName();
	}


	public static Connection getConnection(){
		Connection conn = DB.getConnection();
		return conn;
	}

	public static void connectionClose(Connection conn){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Users> users() {
		Logger.info("Inside " + methodName() + " method!");
		ArrayList<Users> users = new ArrayList<Users>();
		try {

			ResultSet rs;
			Connection conn = DB.getConnection();

			//PreparedStatement ps = conn.prepareStatement("select distinct name, u_name from pinfo order by name");
			PreparedStatement ps = conn.prepareStatement("select distinct username, user_id from registration order by username");
			rs = ps.executeQuery();
			while (rs.next() ) {
				users.add(new Users(rs.getString("username"), rs.getString("user_id")));
			}
			conn.close();
		} catch (Exception e) {
			System.err.println("Got an exception!"+ methodName());
		}

		return users;
	}


	public static String userName(String u_name, String columnName)
	{
		Logger.info("Inside " + methodName() + " method!");
		Connection conn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("select " +columnName+ " from registration where user_id = ?");
			ps.setInt(1, Integer.parseInt(u_name.trim()));
			rs = ps.executeQuery();
			while (rs.next()) {
				u_name = rs.getString(columnName);
			}
		}
		catch (Exception e) {
			System.err.println("Got an exception! " + methodName());
			e.printStackTrace();
		}

		return u_name;
	}


	public static ArrayList<Quora> quora_questions_answered(String u_name) {
		Logger.info("Inside " + methodName() + " method!");

		ArrayList<Quora> questions_answered = new ArrayList<Quora>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();

			ps = conn.prepareStatement("select question, answer from quora_answer where dbid = ?");
			ps.setString(1, u_name.trim());
			rs = ps.executeQuery();
			while (rs.next()) {
				questions_answered.add(new Quora(rs.getString("question"), rs.getString("answer")));
			}

			conn.close();
			ps.close();
			rs.close();

		} catch (Exception e) {
			System.err.println("Got an exception! " + methodName());
			e.printStackTrace();
		}
		return questions_answered;
	}

	public static ArrayList<Quora> quora_questions_asked(String u_name) {
		Logger.info("Inside " + methodName() + " method!");

		ArrayList<Quora> questions_asked = new ArrayList<Quora>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();

			ps = conn.prepareStatement("select question from quora_question where dbid = ?");
			ps.setString(1, u_name.trim());
			rs = ps.executeQuery();
			while (rs.next() ) {
				questions_asked.add(new Quora(rs.getString("question")));
			}

			conn.close();
			ps.close();
			rs.close();

		} catch (Exception e) {
			System.err.println("Got an exception! " + methodName());
			e.printStackTrace();
		}

		return questions_asked;
	}


	public static ArrayList<Goodreads> goodreads(String u_name) {
		Logger.info("Inside " + methodName() + " method!");

		ArrayList<Goodreads> goodReads = new ArrayList<Goodreads>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();

			ps = conn.prepareStatement("select rating,title,author,reviewtext from greads where dbid = ?");
			ps.setString(1, u_name.trim());

			rs = ps.executeQuery();
			while (rs.next() ) {
				goodReads.add(new Goodreads(rs.getString("rating"), rs.getString("title"), 
						rs.getString("author"), rs.getString("reviewtext")));
			}

			conn.close();
			ps.close();
			rs.close();

		} catch (Exception e) {
			System.err.println("Got an exception! " + methodName());
		}

		return goodReads;
	}


	public static ArrayList<Twitter> tweeetMentioned(String u_name) {
		Logger.info("Inside " + methodName() + " method!");

		ArrayList<Twitter> tweeetMentioned = new ArrayList<Twitter>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();

			ps = conn.prepareStatement("select tweet, type, puser, tweetid from twitter_other "
					+ "where dbid = ? and type = ?");
			ps.setString(1, u_name.trim());
			ps.setString(2, "Mentions");
			rs = ps.executeQuery();
			while (rs.next() ) {
				tweeetMentioned.add(new Twitter(rs.getString("tweet"),rs.getString("type"),
						rs.getString("puser"), rs.getString("tweetid")));
			}

			conn.close();
			ps.close();
			rs.close();

		} catch (Exception e) {
			System.err.println("Got an exception! " + methodName());
		}

		return tweeetMentioned;
	}

	public static ArrayList<Twitter> tweeetFavorites(String u_name) {
		Logger.info("Inside " + methodName() + " method!");

		ArrayList<Twitter> tweeetFavorites = new ArrayList<Twitter>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();

			conn = DB.getConnection();
			ps = conn.prepareStatement("select tweet, type, puser, tweetid from twitter_other "
					+ "where dbid = ? and type = ?");
			ps.setString(1, u_name.trim());
			ps.setString(2, "Favorites");
			rs = ps.executeQuery();
			while (rs.next() ) {
				tweeetFavorites.add(new Twitter(rs.getString("tweet"),rs.getString("type"),
						rs.getString("puser"), rs.getString("tweetid")));
			}

			conn.close();
			ps.close();
			rs.close();

		} catch (Exception e) {
			System.err.println("Got an exception! " + methodName());
		}

		return tweeetFavorites;
	}

	public static ArrayList<Twitter> tweets(String u_name) {
		Logger.info("Inside " + methodName() + " method!");

		ArrayList<Twitter> tweets = new ArrayList<Twitter>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();

			ps = conn.prepareStatement("select tweet from twitter where dbid = ?");
			ps.setString(1, u_name.trim());
			rs = ps.executeQuery();
			while (rs.next() ) {
				tweets.add(new Twitter(rs.getString("tweet")));
			}

			conn.close();
			ps.close();
			rs.close();

		} catch (Exception e) {
			System.err.println("Got an exception! " + methodName());
		}

		return tweets;
	}

}
