package models;
/**
 * @author Sougata
 * Aug 24, 2015 
 */
public class Twitter {

	//vars that make tweet mentioned or favorites from twitter_other
	public String tweetOthers;
	public String type;
	public String tweetBy;
	public String tweetId;

	//vars that make tweet from twitter
	public String tweet;

	public Twitter() {
		super();
	}

	public Twitter(String tweetOthers, String type, String tweetBy, String tweetId) {
		super();
		this.tweetOthers = tweetOthers;
		this.type = type;
		this.tweetBy = tweetBy;
		this.tweetId = tweetId;
	}

	public Twitter(String tweet) {
		super();
		this.tweet = tweet;
	}


}
