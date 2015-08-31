/**
 * 
 */
package models;

/**
 * @author Sougata
 * Aug 29, 2015 
 */
public class Grouplist {
	
	public String group_name;
	public String dbid;
	public String api_type;
	public String groupid;
	
	public Grouplist() {
		super();
	}

	public Grouplist(String group_name, String dbid, String api_type, String groupid) {
		super();
		this.group_name = group_name;
		this.dbid = dbid;
		this.api_type = api_type;
		this.groupid = groupid;
	}
}
