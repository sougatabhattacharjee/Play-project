package models;
/**
 * @author Sougata
 * Aug 23, 2015 
 */
public class Users {

	//vars that make a user from pinfo
	public String name;
	public String u_name;

	//from registration
	public String username;
	public String user_id;

	public Users() {
		super();
	}

	//	public Users(String name, String u_name){
	//		this.name 	= name;
	//		this.u_name	= u_name;
	//	}

	public Users(String username, String user_id) {
		super();
		this.username = username;
		this.user_id = user_id;
	}


}
