package Persistence;
import java.sql.Array;
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
			ps.close();
			rs.close();
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


	public static UserInformation userNameAll(String u_name)
	{
		Logger.info("Inside " + methodName() + " method!");
		Connection conn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserInformation ui = null;
		try {
			ps = conn.prepareStatement("select twitter_name,quora_name,goodreads_name from registration where user_id = ?");
			ps.setInt(1, Integer.parseInt(u_name.trim()));
			rs = ps.executeQuery();
			while (rs.next()) {
				ui = new UserInformation(rs.getString("twitter_name"),
						rs.getString("quora_name"),rs.getString("goodreads_name"));
			}
		}
		catch (Exception e) {
			System.err.println("Got an exception! " + methodName());
			e.printStackTrace();
		}

		return ui;
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

	public static ArrayList<Quora> quora_blog(String u_name) {
		Logger.info("Inside " + methodName() + " method!");

		ArrayList<Quora> quora_blog = new ArrayList<Quora>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();

			ps = conn.prepareStatement("select blog_name,link from quora_blog where dbid = ?");
			ps.setString(1, u_name.trim());
			rs = ps.executeQuery();
			while (rs.next() ) {
				Quora qb = new Quora();
				qb.setBlog_name(rs.getString("blog_name"));
				qb.setBlog_link(rs.getString("link"));
				quora_blog.add(qb);
			}

			conn.close();
			ps.close();
			rs.close();

		} catch (Exception e) {
			System.err.println("Got an exception! " + methodName());
			e.printStackTrace();
		}

		return quora_blog;
	}

	public static ArrayList<Quora> quora_post(String u_name) {
		Logger.info("Inside " + methodName() + " method!");

		ArrayList<Quora> quora_post = new ArrayList<Quora>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();

			ps = conn.prepareStatement("select post_heading,link from quora_post where dbid = ?");
			ps.setString(1, u_name.trim());
			rs = ps.executeQuery();
			while (rs.next() ) {
				Quora qb = new Quora();
				qb.setPost_heading(rs.getString("post_heading"));
				qb.setPost_link(rs.getString("link"));
				quora_post.add(qb);
			}

			conn.close();
			ps.close();
			rs.close();

		} catch (Exception e) {
			System.err.println("Got an exception! " + methodName());
			e.printStackTrace();
		}

		return quora_post;
	}


	public static ArrayList<userInfo> quoraBio(String u_name) {
		Logger.info("Inside " + methodName() + " method!");

		ArrayList<userInfo> quoraBio = new ArrayList<userInfo>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();

			ps = conn.prepareStatement("select name, u_name, image_link, location, biography, description, interests, employment, education"
					+ " from pinfo where u_name = ?");
			ps.setString(1, u_name.trim());

			rs = ps.executeQuery();
			while (rs.next()) {
				quoraBio.add(new userInfo(rs.getString("name"), rs.getString("u_name"), rs.getString("image_link"),
						rs.getArray("location")==null?new String[0]:(String[])rs.getArray("location").getArray(), 
								rs.getString("biography"), rs.getString("description")
								, rs.getArray("interests")==null?new String[0]:(String[])rs.getArray("interests").getArray(), 
										rs.getArray("employment")==null?new String[0]:(String[])rs.getArray("employment").getArray(), 
												rs.getArray("education")==null?new String[0]:(String[])rs.getArray("education").getArray()
						));
			}

			conn.close();
			ps.close();
			rs.close();

		} catch (Exception e) {
			System.err.println("Got an exception! " + methodName());
			e.printStackTrace();
		}

		return quoraBio;
	}



	public static ArrayList<Quora> quora_questions_answered(String u_name, String searchText) {
		Logger.info("Inside " + methodName() + " method!");

		ArrayList<Quora> questions_answered = new ArrayList<Quora>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();

			ps = conn.prepareStatement("select question, answer from quora_answer where dbid = ? "
					+ "and (question ilike ? or answer ilike ?) ");
			ps.setString(1, u_name.trim());
			ps.setString(2, "%"+searchText.trim()+"%");
			ps.setString(3, "%"+searchText.trim()+"%");
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

	public static ArrayList<Quora> quora_questions_asked(String u_name, String searchText) {
		Logger.info("Inside " + methodName() + searchText + u_name + " method!");

		ArrayList<Quora> questions_asked = new ArrayList<Quora>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();

			ps = conn.prepareStatement("select question from quora_question where dbid = ? and question ilike ?");
			ps.setString(1, u_name.trim());
			ps.setString(2, "%"+searchText.trim()+"%");
			rs = ps.executeQuery();
			while (rs.next() ) {
				questions_asked.add(new Quora(rs.getString("question")));
			}

			Logger.info("questions_asked>>> " +questions_asked.size());

			conn.close();
			ps.close();
			rs.close();

		} catch (Exception e) {
			System.err.println("Got an exception! " + methodName());
			e.printStackTrace();
		}

		return questions_asked;
	}

	public static ArrayList<Quora> quora_blog(String u_name, String searchText) {
		Logger.info("Inside " + methodName() + " method!");

		ArrayList<Quora> quora_blog = new ArrayList<Quora>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();

			ps = conn.prepareStatement("select blog_name,link from quora_blog where dbid = ? and blog_name ilike ?");
			ps.setString(1, u_name.trim());
			ps.setString(2, "%"+searchText.trim()+"%");
			rs = ps.executeQuery();
			while (rs.next() ) {
				Quora qb = new Quora();
				qb.setBlog_name(rs.getString("blog_name"));
				qb.setBlog_link(rs.getString("link"));
				quora_blog.add(qb);
			}

			conn.close();
			ps.close();
			rs.close();

		} catch (Exception e) {
			System.err.println("Got an exception! " + methodName());
			e.printStackTrace();
		}

		return quora_blog;
	}

	public static ArrayList<Quora> quora_post(String u_name, String searchText) {
		Logger.info("Inside " + methodName() + " method!");

		ArrayList<Quora> quora_post = new ArrayList<Quora>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();

			ps = conn.prepareStatement("select post_heading,link from quora_post where dbid = ? and post_heading ilike ?");
			ps.setString(1, u_name.trim());
			ps.setString(2, "%"+searchText.trim()+"%");
			rs = ps.executeQuery();
			while (rs.next() ) {
				Quora qb = new Quora();
				qb.setPost_heading(rs.getString("post_heading"));
				qb.setPost_link(rs.getString("link"));
				quora_post.add(qb);
			}

			conn.close();
			ps.close();
			rs.close();

		} catch (Exception e) {
			System.err.println("Got an exception! " + methodName());
			e.printStackTrace();
		}

		return quora_post;
	}


	public static ArrayList<userInfo> quoraBio(String u_name, String searchText) {
		Logger.info("Inside " + methodName() + " method!");

		ArrayList<userInfo> quoraBio = new ArrayList<userInfo>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();

			ps = conn.prepareStatement("select name, u_name, image_link, location, biography, description, interests, employment, education from pinfo where u_name in "
					+" (select u_name from (select u_name,Array_To_String(location, ',') as tempo from pinfo where u_name = ?) as sear where tempo ilike ?) "
					+" union "
					+" select name, u_name, image_link, location, biography, description, interests, employment, education from pinfo where u_name in "
					+" (select u_name from (select u_name,Array_To_String(interests, ',') as tempo from pinfo where u_name = ?) as sear where tempo ilike ?) "
					+" union "
					+" select name, u_name, image_link, location, biography, description, interests, employment, education from pinfo where u_name in "
					+" (select u_name from (select u_name,Array_To_String(employment, ',') as tempo from pinfo where u_name = ?) as sear where tempo ilike ?) "
					+" union "
					+" select name, u_name, image_link, location, biography, description, interests, employment, education from pinfo where u_name in "
					+" (select u_name from (select u_name,Array_To_String(education, ',') as tempo from pinfo where u_name = ?) as sear where tempo ilike ?) "
					+" or biography ilike ? "
					+" or description ilike ? "
					+" and u_name = ?");

			ps.setString(1, u_name.trim());
			ps.setString(2, "%"+searchText.trim()+"%");
			ps.setString(3, u_name.trim());
			ps.setString(4, "%"+searchText.trim()+"%");
			ps.setString(5, u_name.trim());
			ps.setString(6, "%"+searchText.trim()+"%");
			ps.setString(7, u_name.trim());
			ps.setString(8, "%"+searchText.trim()+"%");
			ps.setString(9, "%"+searchText.trim()+"%");
			ps.setString(10, "%"+searchText.trim()+"%");
			ps.setString(11, u_name.trim());

			rs = ps.executeQuery();
			while (rs.next()) {
				quoraBio.add(new userInfo(rs.getString("name"), rs.getString("u_name"), rs.getString("image_link"),
						rs.getArray("location")==null?new String[0]:(String[])rs.getArray("location").getArray(), 
								rs.getString("biography"), rs.getString("description")
								, rs.getArray("interests")==null?new String[0]:(String[])rs.getArray("interests").getArray(), 
										rs.getArray("employment")==null?new String[0]:(String[])rs.getArray("employment").getArray(), 
												rs.getArray("education")==null?new String[0]:(String[])rs.getArray("education").getArray()
						));
			}

			conn.close();
			ps.close();
			rs.close();

		} catch (Exception e) {
			System.err.println("Got an exception! " + methodName());
			e.printStackTrace();
		}

		return quoraBio;
	}

	public static ArrayList<Goodreads> goodreads(String u_name) {
		Logger.info("Inside " + methodName() + " method!");

		ArrayList<Goodreads> goodReads = new ArrayList<Goodreads>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();

			ps = conn.prepareStatement("select rating,title,author,reviewtext,genre,shelf,isbn,isbn13,dbdate from greads where dbid = ? order by dbdate desc");
			ps.setString(1, u_name.trim());

			rs = ps.executeQuery();
			while (rs.next() ) {
				goodReads.add(new Goodreads(rs.getString("rating"), rs.getString("title"), 
						rs.getString("author"), rs.getString("reviewtext")
						, rs.getString("genre"), rs.getString("shelf"), rs.getString("isbn"), rs.getString("isbn13"), rs.getString("dbdate")));
			}

			conn.close();
			ps.close();
			rs.close();

		} catch (Exception e) {
			System.err.println("Got an exception! " + methodName());
		}

		return goodReads;
	}

	public static ArrayList<Goodreads> goodreadsreview(String u_name) {
		Logger.info("Inside " + methodName() + " method!");

		ArrayList<Goodreads> goodreadsreview = new ArrayList<Goodreads>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();

			ps = conn.prepareStatement("select title,reviewtext from greads where dbid = ? and reviewtext is not null");
			ps.setString(1, u_name.trim());

			rs = ps.executeQuery();
			while (rs.next() ) {
				goodreadsreview.add(new Goodreads(rs.getString("title"), rs.getString("reviewtext").trim()));
			}

			conn.close();
			ps.close();
			rs.close();

		} catch (Exception e) {
			System.err.println("Got an exception! " + methodName());
		}

		return goodreadsreview;
	}


	public static ArrayList<userInfo> goodreadsBio(String u_name) {
		Logger.info("Inside " + methodName() + " method!");

		ArrayList<userInfo> goodreadsBio = new ArrayList<userInfo>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();

			ps = conn.prepareStatement("select name, u_name, image_link, gender, age, location, website, interests,"
					+ " fav_books, fav_authors, bookshelves, description from pinfo where u_name = ?");
			ps.setString(1, u_name.trim());

			rs = ps.executeQuery();
			while (rs.next() ) {
				goodreadsBio.add(new userInfo(rs.getString("name"), rs.getString("u_name"), rs.getString("image_link"),
						rs.getString("gender"), rs.getInt("age"), 
						rs.getArray("location")==null?new String[0]:(String[])rs.getArray("location").getArray(), 
								rs.getString("website"), 
								rs.getArray("interests")==null?new String[0]:(String[])rs.getArray("interests").getArray(), 
										rs.getString("fav_books"), 
										rs.getArray("fav_authors")==null?new String[0]:(String[])rs.getArray("fav_authors").getArray(),
												rs.getArray("bookshelves")==null?new String[0]:(String[])rs.getArray("bookshelves").getArray(),
														rs.getString("description")));

			}

			conn.close();
			ps.close();
			rs.close();

		} catch (Exception e) {
			System.err.println("Got an exception! " + methodName());
			e.printStackTrace();
		}

		return goodreadsBio;
	}
	
	
	public static ArrayList<Grouplist> GroupList(String u_name, String api_type) {
		Logger.info("Inside " + methodName() + " method!");

		ArrayList<Grouplist> goodreadsGroup = new ArrayList<Grouplist>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql="";
		
		try {
			conn = DB.getConnection();
			
			if(api_type.equalsIgnoreCase("Twitter"))
				sql = "select group_name, dbid, api_type, groupid from group_list where api_type = 'Twitter' and dbid = ? ";
			if(api_type.equalsIgnoreCase("Goodreads"))
				sql= "select group_name, dbid, api_type, groupid from group_list where api_type = 'Goodreads' and dbid = ? ";

			ps = conn.prepareStatement(sql);
			ps.setString(1, u_name.trim());

			rs = ps.executeQuery();
			while (rs.next() ) {
				goodreadsGroup.add(new Grouplist(rs.getString("group_name"), rs.getString("dbid"), rs.getString("api_type"),
						rs.getString("groupid")));
			}

			conn.close();
			ps.close();
			rs.close();

		} catch (Exception e) {
			System.err.println("Got an exception! " + methodName());
			e.printStackTrace();
		}

		return goodreadsGroup;
	}
	
	public static ArrayList<Grouplist> GroupListSearch(String u_name, String api_type, String searchText) {
		Logger.info("Inside " + methodName() + " method!"+api_type+searchText+u_name);

		ArrayList<Grouplist> goodreadsGroup = new ArrayList<Grouplist>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql="";
		
		try {
			conn = DB.getConnection();
			
			if(api_type.equalsIgnoreCase("Twitter"))
				sql = "select group_name, dbid, api_type, groupid from group_list where api_type = 'Twitter' "
						+ " and dbid = ? and group_name ilike ? ";
			if(api_type.equalsIgnoreCase("Goodreads"))
				sql= "select group_name, dbid, api_type, groupid from group_list where api_type = 'Goodreads' "
						+ " and dbid = ? and group_name ilike ? ";

			ps = conn.prepareStatement(sql);
			ps.setString(1, u_name.trim());
			ps.setString(2, "%"+searchText.trim()+"%");
			rs = ps.executeQuery();
			while (rs.next() ) {
				goodreadsGroup.add(new Grouplist(rs.getString("group_name"), rs.getString("dbid"), rs.getString("api_type"),
						rs.getString("groupid")));
			}
			
			Logger.info(goodreadsGroup.size()+">>>>>>>>>>>");

			conn.close();
			ps.close();
			rs.close();

		} catch (Exception e) {
			System.err.println("Got an exception! " + methodName());
			e.printStackTrace();
		}

		return goodreadsGroup;
	}
	
	
	

	public static ArrayList<Goodreads> goodreads(String u_name, String searchText) {
		Logger.info("Inside " + methodName() + " method!!");

		ArrayList<Goodreads> goodReads = new ArrayList<Goodreads>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();

			ps = conn.prepareStatement("select rating,title,author,reviewtext,genre,shelf,isbn,isbn13,dbdate from greads where reviewid in " 
					+"	(select reviewid from (select reviewid,Array_To_String(shelf, ',') "
					+"			as tempo from greads where dbid = ?) as sear where tempo ilike ?) union "
					+" select rating,title,author,reviewtext,genre,shelf,isbn,isbn13,dbdate from greads where reviewid in " 
					+" (select reviewid from ( select reviewid,Array_To_String(author, ',') "
					+"	as tempo from greads where dbid = ?) as sear where tempo ilike ?) union "
					+"	select rating,title,author,reviewtext,genre,shelf,isbn,isbn13,dbdate from greads where reviewid in  "  
					+"	(select reviewid from( select reviewid,Array_To_String(genre, ',') "
					+"		as tempo from greads where dbid = ?) as sear where tempo ilike ?)  order by dbdate desc");

			ps.setString(1, u_name.trim());
			ps.setString(2, "%"+searchText.trim()+"%");
			ps.setString(3, u_name.trim());
			ps.setString(4, "%"+searchText.trim()+"%");
			ps.setString(5, u_name.trim());
			ps.setString(6, "%"+searchText.trim()+"%");

			rs = ps.executeQuery();
			while (rs.next() ) {
				goodReads.add(new Goodreads(rs.getString("rating"), rs.getString("title"), 
						rs.getString("author"), rs.getString("reviewtext")
						, rs.getString("genre"), rs.getString("shelf"), rs.getString("isbn"), rs.getString("isbn13"), rs.getString("dbdate")));
			}

			Logger.info("Inside>>>>>> " + goodReads.size() + " method!");

			conn.close();
			ps.close();
			rs.close();

		} catch (Exception e) {
			System.err.println("Got an exception! " + methodName());
			e.printStackTrace();
		}

		return goodReads;
	}

	public static ArrayList<Goodreads> goodreadsreview(String u_name, String searchText) {
		Logger.info("Inside " + methodName() + " method!");

		ArrayList<Goodreads> goodreadsreview = new ArrayList<Goodreads>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();

			ps = conn.prepareStatement("select title,reviewtext from greads where dbid = ? and reviewtext is not null "
					+ " and reviewtext ilike ?");

			ps.setString(1, u_name.trim());
			ps.setString(2, "%"+searchText.trim()+"%");

			rs = ps.executeQuery();
			while (rs.next() ) {
				goodreadsreview.add(new Goodreads(rs.getString("title"), rs.getString("reviewtext").trim()));
			}

			conn.close();
			ps.close();
			rs.close();

		} catch (Exception e) {
			System.err.println("Got an exception! " + methodName());
		}

		return goodreadsreview;
	}


	public static ArrayList<userInfo> goodreadsBio(String u_name, String searchText) {
		Logger.info("Inside " + methodName() + " method!");

		ArrayList<userInfo> goodreadsBio = new ArrayList<userInfo>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();

			ps = conn.prepareStatement("select name, u_name, image_link, gender, age, location, website, interests,  fav_books, fav_authors, bookshelves, description from pinfo " 
					+" where u_name in  "
					+" (select u_name from (select u_name,Array_To_String(location, ',') as tempo from pinfo where u_name = ?) as sear where tempo ilike ?) "
					+" union  "
					+" select name, u_name, image_link, gender, age, location, website, interests,  fav_books, fav_authors, bookshelves, description from pinfo " 
					+" where u_name in  "
					+" (select u_name from (select u_name,Array_To_String(interests, ',') as tempo from pinfo where u_name = ?) as sear where tempo ilike ?) "
					+" union  "
					+" select name, u_name, image_link, gender, age, location, website, interests,  fav_books, fav_authors, bookshelves, description from pinfo "  
					+" where u_name in  "
					+" (select u_name from (select u_name,Array_To_String(fav_authors, ',') as tempo from pinfo where u_name = ?) as sear where tempo ilike ?) "
					+" union  "
					+" select name, u_name, image_link, gender, age, location, website, interests,  fav_books, fav_authors, bookshelves, description from pinfo " 
					+" where u_name in  "
					+" (select u_name from (select u_name,Array_To_String(bookshelves, ',') as tempo from pinfo where u_name = ?) as sear where tempo ilike ?) "
					+" or website ilike ? "
					+" or fav_books ilike ? "
					+" or description ilike ? and u_name = ?");


			ps.setString(1, u_name.trim());
			ps.setString(2, "%"+searchText.trim()+"%");
			ps.setString(3, u_name.trim());
			ps.setString(4, "%"+searchText.trim()+"%");
			ps.setString(5, u_name.trim());
			ps.setString(6, "%"+searchText.trim()+"%");
			ps.setString(7, u_name.trim());
			ps.setString(8, "%"+searchText.trim()+"%");
			ps.setString(9, "%"+searchText.trim()+"%");
			ps.setString(10, "%"+searchText.trim()+"%");
			ps.setString(11, "%"+searchText.trim()+"%");
			ps.setString(12, u_name.trim());

			rs = ps.executeQuery();
			while (rs.next() ) {
				goodreadsBio.add(new userInfo(rs.getString("name"), rs.getString("u_name"), rs.getString("image_link"),
						rs.getString("gender"), rs.getInt("age"), 
						rs.getArray("location")==null?new String[0]:(String[])rs.getArray("location").getArray(), 
								rs.getString("website"), 
								rs.getArray("interests")==null?new String[0]:(String[])rs.getArray("interests").getArray(), 
										rs.getString("fav_books"), 
										rs.getArray("fav_authors")==null?new String[0]:(String[])rs.getArray("fav_authors").getArray(),
												rs.getArray("bookshelves")==null?new String[0]:(String[])rs.getArray("bookshelves").getArray(),
														rs.getString("description")));

			}

			conn.close();
			ps.close();
			rs.close();

		} catch (Exception e) {
			System.err.println("Got an exception! " + methodName());
			e.printStackTrace();
		}

		return goodreadsBio;
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
					+ "where dbid = ? and type = ?");	//tweet,hashtag[],usertag[],media[]
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
					+ "where dbid = ? and type = ?");	//tweet,hashtag[],usertag[],media[]
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
			ps.setString(1, u_name.trim());	//tweet,hashtag[],usertag[],media[]
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

	public static ArrayList<userInfo> twitterBio(String u_name) {
		Logger.info("Inside " + methodName() + " method!");

		ArrayList<userInfo> twitterBio = new ArrayList<userInfo>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();

			ps = conn.prepareStatement("select name, u_name, image_link, location, biography, description, interests, employment, education"
					+ " from pinfo where u_name = ?");
			ps.setString(1, u_name.trim());

			rs = ps.executeQuery();
			while (rs.next()) {

				twitterBio.add(new userInfo(rs.getString("name"), rs.getString("u_name"), rs.getString("image_link"),
						rs.getArray("location")==null?new String[0]:(String[])rs.getArray("location").getArray(), 
								rs.getString("biography"), rs.getString("description")
								, rs.getArray("interests")==null?new String[0]:(String[])rs.getArray("interests").getArray(), 
										rs.getArray("employment")==null?new String[0]:(String[])rs.getArray("employment").getArray(), 
												rs.getArray("education")==null?new String[0]:(String[])rs.getArray("education").getArray()
						));
			}

			conn.close();
			ps.close();
			rs.close();

		} catch (Exception e) {
			System.err.println("Got an exception! " + methodName());
			e.printStackTrace();
		}

		return twitterBio;
	}


	public static ArrayList<Twitter> tweeetMentioned(String u_name, String searchText) {
		Logger.info("Inside " + methodName() + " method!");

		ArrayList<Twitter> tweeetMentioned = new ArrayList<Twitter>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();

			ps = conn.prepareStatement("select tweet, type, puser, tweetid from twitter_other where tweetid in " 
					+" (select tweetid from (select tweetid,Array_To_String(hashtags, ',') as tempo from twitter_other where dbid = ? and type = ?) as sear where tempo ilike ?) "
					+" union "
					+" select tweet, type, puser, tweetid from twitter_other where tweetid in " 
					+" (select tweetid from (select tweetid,Array_To_String(usertag, ',') as tempo from twitter_other where dbid = ? and type = ?) as sear where tempo ilike ?) "
					+" union "
					+" select tweet, type, puser, tweetid from twitter_other where tweetid in " 
					+" (select tweetid from (select tweetid,Array_To_String(media, ',') as tempo from twitter_other where dbid = ? and type = ?) as sear where tempo ilike ?) "
					+" or tweet ilike ? "
					+" and dbid = ? and type = ?");

			ps.setString(1, u_name.trim());
			ps.setString(2, "Mentions");
			ps.setString(3, "%"+searchText.trim()+"%");
			ps.setString(4, u_name.trim());
			ps.setString(5, "Mentions");
			ps.setString(6, "%"+searchText.trim()+"%");
			ps.setString(7, u_name.trim());
			ps.setString(8, "Mentions");
			ps.setString(9, "%"+searchText.trim()+"%");
			ps.setString(10, "%"+searchText.trim()+"%");
			ps.setString(11, u_name.trim());
			ps.setString(12, "Mentions");


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

	public static ArrayList<Twitter> tweeetFavorites(String u_name, String searchText) {
		Logger.info("Inside " + methodName() + " method!");

		ArrayList<Twitter> tweeetFavorites = new ArrayList<Twitter>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();

			ps = conn.prepareStatement("select tweet, type, puser, tweetid from twitter_other where tweetid in " 
					+" (select tweetid from (select tweetid,Array_To_String(hashtags, ',') as tempo from twitter_other where dbid = ? and type = ?) as sear where tempo ilike ?) "
					+" union "
					+" select tweet, type, puser, tweetid from twitter_other where tweetid in " 
					+" (select tweetid from (select tweetid,Array_To_String(usertag, ',') as tempo from twitter_other where dbid = ? and type = ?) as sear where tempo ilike ?) "
					+" union "
					+" select tweet, type, puser, tweetid from twitter_other where tweetid in " 
					+" (select tweetid from (select tweetid,Array_To_String(media, ',') as tempo from twitter_other where dbid = ? and type = ?) as sear where tempo ilike ?) "
					+" or tweet ilike ? "
					+" and dbid = ? and type = ?");

			ps.setString(1, u_name.trim());
			ps.setString(2, "Favorites");
			ps.setString(3, "%"+searchText.trim()+"%");
			ps.setString(4, u_name.trim());
			ps.setString(5, "Favorites");
			ps.setString(6, "%"+searchText.trim()+"%");
			ps.setString(7, u_name.trim());
			ps.setString(8, "Favorites");
			ps.setString(9, "%"+searchText.trim()+"%");
			ps.setString(10, "%"+searchText.trim()+"%");
			ps.setString(11, u_name.trim());
			ps.setString(12, "Favorites");





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

	public static ArrayList<Twitter> tweets(String u_name, String searchText) {
		Logger.info("Inside " + methodName() + " method!");

		ArrayList<Twitter> tweets = new ArrayList<Twitter>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();

			ps = conn.prepareStatement("select tweet from twitter where tweetid in " 
					+" (select tweetid from (select tweetid,Array_To_String(hashtags, ',') as tempo from twitter where dbid = ?) as sear where tempo ilike ?) "
					+" union "
					+" select tweet from twitter where tweetid in " 
					+" (select tweetid from (select tweetid,Array_To_String(usertag, ',') as tempo from twitter where dbid = ?) as sear where tempo ilike ?) "
					+" union "
					+" select tweet from twitter where tweetid in " 
					+" (select tweetid from (select tweetid,Array_To_String(media, ',') as tempo from twitter where dbid = ?) as sear where tempo ilike ?) "
					+" or tweet ilike ? "
					+" and dbid = ?");

			ps.setString(1, u_name.trim());	
			ps.setString(2, "%"+searchText.trim()+"%");
			ps.setString(3, u_name.trim());	
			ps.setString(4, "%"+searchText.trim()+"%");
			ps.setString(5, u_name.trim());	
			ps.setString(6, "%"+searchText.trim()+"%");
			ps.setString(7, "%"+searchText.trim()+"%");
			ps.setString(8, u_name.trim());	



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

	public static ArrayList<userInfo> twitterBio(String u_name, String searchText) {
		Logger.info("Inside " + methodName() + " method!");

		ArrayList<userInfo> twitterBio = new ArrayList<userInfo>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();


			ps = conn.prepareStatement("select name, u_name, image_link, location, biography, description, interests, employment, education from pinfo where u_name in "
					+" (select u_name from (select u_name,Array_To_String(location, ',') as tempo from pinfo where u_name = ?) as sear where tempo ilike ?) "
					+" union "
					+" select name, u_name, image_link, location, biography, description, interests, employment, education from pinfo where u_name in "
					+" (select u_name from (select u_name,Array_To_String(interests, ',') as tempo from pinfo where u_name = ?) as sear where tempo ilike ?) "
					+" union "
					+" select name, u_name, image_link, location, biography, description, interests, employment, education from pinfo where u_name in "
					+" (select u_name from (select u_name,Array_To_String(employment, ',') as tempo from pinfo where u_name = ?) as sear where tempo ilike ?) "
					+" union "
					+" select name, u_name, image_link, location, biography, description, interests, employment, education from pinfo where u_name in "
					+" (select u_name from (select u_name,Array_To_String(education, ',') as tempo from pinfo where u_name = ?) as sear where tempo ilike ?) "
					+" or biography ilike ? "
					+" or description ilike ? "
					+" and u_name = ?");

			ps.setString(1, u_name.trim());
			ps.setString(2, "%"+searchText.trim()+"%");
			ps.setString(3, u_name.trim());
			ps.setString(4, "%"+searchText.trim()+"%");
			ps.setString(5, u_name.trim());
			ps.setString(6, "%"+searchText.trim()+"%");
			ps.setString(7, u_name.trim());
			ps.setString(8, "%"+searchText.trim()+"%");
			ps.setString(9, "%"+searchText.trim()+"%");
			ps.setString(10, "%"+searchText.trim()+"%");
			ps.setString(11, u_name.trim());


			rs = ps.executeQuery();
			while (rs.next()) {

				twitterBio.add(new userInfo(rs.getString("name"), rs.getString("u_name"), rs.getString("image_link"),
						rs.getArray("location")==null?new String[0]:(String[])rs.getArray("location").getArray(), 
								rs.getString("biography"), rs.getString("description")
								, rs.getArray("interests")==null?new String[0]:(String[])rs.getArray("interests").getArray(), 
										rs.getArray("employment")==null?new String[0]:(String[])rs.getArray("employment").getArray(), 
												rs.getArray("education")==null?new String[0]:(String[])rs.getArray("education").getArray()
						));
			}

			conn.close();
			ps.close();
			rs.close();

		} catch (Exception e) {
			System.err.println("Got an exception! " + methodName());
			e.printStackTrace();
		}

		return twitterBio;
	}


	public static ArrayList<Friends> friendSearch(String u_name, String searchText, String type) {
		Logger.info("Inside " + methodName() + " method!");

		ArrayList<Friends> friends = new ArrayList<Friends>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();
			String sqlQuery="";


			if(type.equalsIgnoreCase("twitter"))
				sqlQuery="select name, friendtype, dbid, sname from friends where name ilike ? "
						+" and friendtype in ('tFollowing','tFollowers') and dbid = ?";

			if(type.equalsIgnoreCase("quora"))
				sqlQuery="select name, friendtype, dbid, sname from friends where name ilike ? "
						+" and friendtype in ('qFollowing','qFollowers') and dbid = ?";

			if(type.equalsIgnoreCase("goodreads"))
				sqlQuery="select name, friendtype, dbid, sname from friends where name ilike ? "
						+" and friendtype in ('gFollowing','gFollowers','gFriend') and dbid = ?";

			Logger.info(sqlQuery+">>"+searchText+">>"+u_name);


			ps = conn.prepareStatement(sqlQuery);

			ps.setString(1, "%"+searchText.trim()+"%");
			ps.setString(2, u_name.trim());



			rs = ps.executeQuery();
			while (rs.next()) {

				friends.add(new Friends(rs.getString("name"), rs.getString("friendtype"), rs.getString("dbid"),
						rs.getString("sname")));
			}

			conn.close();
			ps.close();
			rs.close();

		} catch (Exception e) {
			System.err.println("Got an exception! " + methodName());
			e.printStackTrace();
		}

		return friends;
	}


}
