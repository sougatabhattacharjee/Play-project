package models;
/**
 * @author Sougata
 * Aug 25, 2015 
 */
public class Goodreads {

	public String rating;
	public String title;
	public String author;
	public String reviewtext;

	public Goodreads() {
		super();
	}

	public Goodreads(String rating, String title, String author, String reviewtext) {
		super();
		this.rating = rating;
		this.title = title;
		this.author = author;
		this.reviewtext = reviewtext;
	}

}
