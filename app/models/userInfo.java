/**
 * 
 */
package models;

/**
 * @author Sougata
 * Aug 28, 2015 
 */
public class userInfo {
	
	public String name;
	public String u_name;
	public String image_link;
	public String gender;
	public int age;
	public String[] fav_authors;
	public String fav_books;
	public String website;
	public String[] location;
	public String biography;
	public String description;
	public String[] bookshelves;
	public String cover_photo;
	public String[] employment;
	public String[] education;
	public String[] interests;
	
	public userInfo() {
		super();
	}

	
	
	//goodreads
	public userInfo(String name, String u_name, String image_link, 
			String gender, int age, String[] location, 
			String website, String[] interests, String fav_books, 
			String fav_authors[], String[] bookshelves,  String description) {
		
		super();
		this.name = name;//
		this.u_name = u_name;//
		this.image_link = image_link;//
		this.gender = gender;//
		this.age = age;//
		this.fav_authors = fav_authors;//
		this.fav_books = fav_books;//
		this.website = website;//
		this.location = location;//
		this.description = description;
		this.bookshelves = bookshelves;
		this.interests = interests;
	}
	
	//twitter, quora
	public userInfo(String name, String u_name, String image_link, 
			String[] location, String biography, String description,
			String[] interests, String[] employment, String[] education) {
		
		super();
		this.name = name;//
		this.u_name = u_name;//
		this.image_link = image_link;//
		this.location = location;//
		this.biography = biography;//
		this.description = description;//
		this.interests = interests;
		this.employment = employment;
		this.education = education;
	}
	
	
	
	
	
	
	
	
	
	
	

}
