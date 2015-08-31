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
	public String genre;
	public String shelf;
	public String isbn;
	public String isbn13;
	public String date;
	
	public Goodreads() {
		super();
	}

	public Goodreads(String rating, String title, String author, String reviewtext,
			String genre, String shelf, String isbn, String isbn13, String date) {
		super();
		this.rating = rating;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.shelf = shelf;
		this.isbn = isbn;
		this.isbn13 = isbn13;
		this.date = date;
		
	//	this.reviewtext = reviewtext;
	}
	
	public Goodreads(String title, String reviewtext) {
		super();
		this.title = title;
		this.reviewtext = reviewtext;
	}

}
