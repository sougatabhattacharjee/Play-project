package controllers;

import java.util.ArrayList;

import models.Choice;
import models.Friends;
import models.Goodreads;
import models.Grouplist;
import models.Quora;
import models.Twitter;
import models.UserInformation;
import models.Users;
import models.userInfo;
import models.SearchInput;
import play.*;
import play.data.Form;
import play.db.DB;
import play.mvc.*;
import play.Logger;

import views.html.*;
import org.apache.commons.lang.StringUtils;

import Persistence.ApplicationPersistence;
/**
 * @author Sougata
 * Aug 22, 2015 
 */
public class Application extends Controller {

	static Form<Choice> choiceForm = form(Choice.class);
	static Form<SearchInput> SearchInputForm = form(SearchInput.class);
	static Form<SearchInput> SearchFriendInputForm = form(SearchInput.class);

	public static Result index() {
		return redirect(routes.Application.users());
	}



	public static Result users() {
		ArrayList<Users> users = new ArrayList<Users>();

		users = ApplicationPersistence.users();

		return ok(index.render(users, choiceForm));
	}


	public static Result quora(String u_name) {
		Logger.info("Inside quora " + u_name);

		ArrayList<Quora> questions_answered = new ArrayList<Quora>();
		ArrayList<Quora> questions_asked = new ArrayList<Quora>();
		ArrayList<Quora> quora_blog = new ArrayList<Quora>();
		ArrayList<Quora> quora_post = new ArrayList<Quora>();
		ArrayList<userInfo> quoraBio = new ArrayList<userInfo>();

		u_name = ApplicationPersistence.userName(u_name, "quora_name");

		if(StringUtils.isNotBlank(u_name)) {
			questions_answered = ApplicationPersistence.quora_questions_answered(u_name);
			questions_asked = ApplicationPersistence.quora_questions_asked(u_name);
			quora_blog = ApplicationPersistence.quora_blog(u_name);
			quora_post = ApplicationPersistence.quora_post(u_name);
			quoraBio = ApplicationPersistence.quoraBio(u_name);
		}
		else{
			questions_answered = new ArrayList<Quora>(0);
			questions_asked = new ArrayList<Quora>(0);
			quora_blog = new ArrayList<Quora>(0);
			quora_post = new ArrayList<Quora>(0);
			quoraBio = new ArrayList<userInfo>(0);
		}

		return ok(quora.render(questions_answered, questions_asked, quora_blog, quora_post,quoraBio));
	}


	public static Result twitter(String u_name) {
		Logger.info("Inside twitter " + u_name);

		ArrayList<Twitter> tweeetMentioned = new ArrayList<Twitter>();
		ArrayList<Twitter> tweeetFavorites = new ArrayList<Twitter>();
		ArrayList<Twitter> tweets = new ArrayList<Twitter>();
		ArrayList<userInfo> twitterBio = new ArrayList<userInfo>();
		ArrayList<Grouplist> twitterGroup = new ArrayList<Grouplist>();

		u_name = ApplicationPersistence.userName(u_name, "twitter_name");

		if(StringUtils.isNotBlank(u_name)) {
			tweeetMentioned = ApplicationPersistence.tweeetMentioned(u_name);
			tweeetFavorites = ApplicationPersistence.tweeetFavorites(u_name);
			tweets = ApplicationPersistence.tweets(u_name);
			twitterBio = ApplicationPersistence.twitterBio(u_name);
			twitterGroup = ApplicationPersistence.GroupList(u_name, "Twitter");
		}
		else
		{
			tweeetMentioned = new ArrayList<Twitter>(0);
			tweeetFavorites = new ArrayList<Twitter>(0);
			tweets = new ArrayList<Twitter>(0);
			twitterBio = new ArrayList<userInfo>(0);
			twitterGroup = new ArrayList<Grouplist>(0);
		}

		return ok(twitter.render(tweeetMentioned, tweeetFavorites, tweets, twitterBio, twitterGroup));
	}


	public static Result goodreads(String u_name) {
		Logger.info("Inside goodreads " + u_name);

		ArrayList<Goodreads> goodReads = new ArrayList<Goodreads>();
		ArrayList<Goodreads> goodreadsreview = new ArrayList<Goodreads>();
		ArrayList<userInfo> goodreadsBio = new ArrayList<userInfo>();
		ArrayList<Grouplist> goodreadsGroup = new ArrayList<Grouplist>();

		u_name = ApplicationPersistence.userName(u_name, "goodreads_name");

		if(StringUtils.isNotBlank(u_name)) {
			goodReads = ApplicationPersistence.goodreads(u_name);
			goodreadsreview = ApplicationPersistence.goodreadsreview(u_name);		
			goodreadsBio = ApplicationPersistence.goodreadsBio(u_name);	
			goodreadsGroup = ApplicationPersistence.GroupList(u_name, "Goodreads");	
			
		}
		else{
			goodReads = new ArrayList<Goodreads>(0);
			goodreadsreview = new ArrayList<Goodreads>(0);
			goodreadsBio = new ArrayList<userInfo>(0);
			goodreadsGroup = new ArrayList<Grouplist>(0);
		}

		return ok(goodreads.render(goodReads,goodreadsreview,goodreadsBio,goodreadsGroup));
	}

	public static Result userBio(String u_name) {
		Logger.info("Inside userBio " + u_name);

		ArrayList<userInfo> twitterBio = new ArrayList<userInfo>();
		ArrayList<userInfo> quoraBio = new ArrayList<userInfo>();
		ArrayList<userInfo> goodreadsBio = new ArrayList<userInfo>();
		ArrayList<Grouplist> goodreadsGroup = new ArrayList<Grouplist>();
		ArrayList<Grouplist> twitterGroup = new ArrayList<Grouplist>();
		
		UserInformation ui = ApplicationPersistence.userNameAll(u_name);

		if(StringUtils.isNotBlank(ui.getTwitter_name())) {
			twitterBio = ApplicationPersistence.twitterBio(ui.getTwitter_name());	
			twitterGroup = ApplicationPersistence.GroupList(ui.getTwitter_name(), "Twitter");
		}
		else{
			twitterBio = new ArrayList<userInfo>(0);
			twitterGroup = new ArrayList<Grouplist>(0);
		}

		if(StringUtils.isNotBlank(ui.getQuora_name())) {
			quoraBio = ApplicationPersistence.quoraBio(ui.getQuora_name());
		}
		else{
			quoraBio = new ArrayList<userInfo>(0);
		}

		if(StringUtils.isNotBlank(ui.getGoodreads_name())) {
			goodreadsBio = ApplicationPersistence.goodreadsBio(ui.getGoodreads_name());		
			goodreadsGroup = ApplicationPersistence.GroupList(ui.getGoodreads_name(), "Goodreads");	
		}
		else{
			goodreadsBio = new ArrayList<userInfo>(0);
			goodreadsGroup = new ArrayList<Grouplist>(0);
		}

		return ok(userBio.render(twitterBio,quoraBio,goodreadsBio,goodreadsGroup,twitterGroup));
	}

	public static Result searchText(String u_name) {
		Logger.info("Inside searchText " + u_name);

		
		ArrayList<Twitter> tweeetMentioned = new ArrayList<Twitter>();
		ArrayList<Twitter> tweeetFavorites = new ArrayList<Twitter>();
		ArrayList<Twitter> tweets = new ArrayList<Twitter>();
		ArrayList<userInfo> twitterBio = new ArrayList<userInfo>();
		ArrayList<Grouplist> twitterGroup = new ArrayList<Grouplist>();

		
		ArrayList<Quora> questions_answered = new ArrayList<Quora>();
		ArrayList<Quora> questions_asked = new ArrayList<Quora>();
		ArrayList<Quora> quora_blog = new ArrayList<Quora>();
		ArrayList<Quora> quora_post = new ArrayList<Quora>();
		ArrayList<userInfo> quoraBio = new ArrayList<userInfo>();

		
		
		
		ArrayList<Goodreads> goodReads = new ArrayList<Goodreads>();
		ArrayList<Goodreads> goodreadsreview = new ArrayList<Goodreads>();
		ArrayList<userInfo> goodreadsBio = new ArrayList<userInfo>();
		ArrayList<Grouplist> goodreadsGroup = new ArrayList<Grouplist>();

		return ok(search.render("", u_name,
				tweeetMentioned, tweeetFavorites, tweets, twitterBio,
				questions_answered, questions_asked, quora_blog, quora_post,quoraBio,
				goodReads,goodreadsreview,goodreadsBio,
				twitterGroup,goodreadsGroup));
	}
	
	
	


	public static Result searchResult() {
		Form<SearchInput> filledForm = SearchInputForm.bindFromRequest();
		SearchInput choiceReview = filledForm.get();
		Logger.info("searchResult = " + choiceReview.u_name +">>" + choiceReview.textToSearch);

		String u_name = choiceReview.u_name;
		String textToSearch = choiceReview.textToSearch;
		
		if (filledForm.hasErrors()) {
			return badRequest("Text must be filled!");
		} else {
			
			
			//twitter
			ArrayList<Twitter> tweeetMentioned = new ArrayList<Twitter>();
			ArrayList<Twitter> tweeetFavorites = new ArrayList<Twitter>();
			ArrayList<Twitter> tweets = new ArrayList<Twitter>();
			ArrayList<userInfo> twitterBio = new ArrayList<userInfo>();
			ArrayList<Grouplist> twitterGroup = new ArrayList<Grouplist>();

			String tu_name = ApplicationPersistence.userName(u_name, "twitter_name");

			if(StringUtils.isNotBlank(tu_name)) {
				tweeetMentioned = ApplicationPersistence.tweeetMentioned(tu_name,textToSearch);
				tweeetFavorites = ApplicationPersistence.tweeetFavorites(tu_name,textToSearch);
				tweets = ApplicationPersistence.tweets(tu_name,textToSearch);
				twitterBio = ApplicationPersistence.twitterBio(tu_name,textToSearch);
				twitterGroup = ApplicationPersistence.GroupListSearch(tu_name, "Twitter",textToSearch);
			}
			else
			{
				tweeetMentioned = new ArrayList<Twitter>(0);
				tweeetFavorites = new ArrayList<Twitter>(0);
				tweets = new ArrayList<Twitter>(0);
				twitterBio = new ArrayList<userInfo>(0);
				twitterGroup = new ArrayList<Grouplist>(0);
			}
			
			
			
			
			
			
			//quora
			ArrayList<Quora> questions_answered = new ArrayList<Quora>();
			ArrayList<Quora> questions_asked = new ArrayList<Quora>();
			ArrayList<Quora> quora_blog = new ArrayList<Quora>();
			ArrayList<Quora> quora_post = new ArrayList<Quora>();
			ArrayList<userInfo> quoraBio = new ArrayList<userInfo>();
			
			String qu_name = ApplicationPersistence.userName(u_name, "quora_name");
			
			Logger.info("u_name>>>>>>>>>>>>>>>>>"+qu_name);
			
			if(StringUtils.isNotBlank(qu_name)) {
				questions_answered = ApplicationPersistence.quora_questions_answered(qu_name,textToSearch);
				questions_asked = ApplicationPersistence.quora_questions_asked(qu_name,textToSearch);
				quora_blog = ApplicationPersistence.quora_blog(qu_name,textToSearch);
				quora_post = ApplicationPersistence.quora_post(qu_name,textToSearch);
				quoraBio = ApplicationPersistence.quoraBio(qu_name,textToSearch);
			}
			else{
				questions_answered = new ArrayList<Quora>(0);
				questions_asked = new ArrayList<Quora>(0);
				quora_blog = new ArrayList<Quora>(0);
				quora_post = new ArrayList<Quora>(0);
				quoraBio = new ArrayList<userInfo>(0);
			}
			
			
			
			
			
			
			//goodreads
			ArrayList<Goodreads> goodReads = new ArrayList<Goodreads>();
			ArrayList<Goodreads> goodreadsreview = new ArrayList<Goodreads>();
			ArrayList<userInfo> goodreadsBio = new ArrayList<userInfo>();
			ArrayList<Grouplist> goodreadsGroup = new ArrayList<Grouplist>();

			String gu_name = ApplicationPersistence.userName(u_name, "goodreads_name");
			Logger.info("gu_name>>>>>>>>>>>>>>>>>"+gu_name);
			if(StringUtils.isNotBlank(gu_name)) {
				goodReads = ApplicationPersistence.goodreads(gu_name,textToSearch);
				goodreadsreview = ApplicationPersistence.goodreadsreview(gu_name,textToSearch);		
				goodreadsBio = ApplicationPersistence.goodreadsBio(gu_name,textToSearch);
				goodreadsGroup = ApplicationPersistence.GroupListSearch(gu_name, "Goodreads",textToSearch);
			}
			else{
				goodReads = new ArrayList<Goodreads>(0);
				goodreadsreview = new ArrayList<Goodreads>(0);
				goodreadsBio = new ArrayList<userInfo>(0);
				goodreadsGroup = new ArrayList<Grouplist>(0);
			}
			
			return ok(search.render(textToSearch, u_name,
					tweeetMentioned, tweeetFavorites, tweets, twitterBio,
					questions_answered, questions_asked, quora_blog, quora_post,quoraBio,
					goodReads,goodreadsreview,goodreadsBio,
					twitterGroup,goodreadsGroup));
			
				}

		//return TODO;
	}


	public static Result searchFriend(String u_name) {
		Logger.info("Inside searchFriend " + u_name);
		ArrayList<Friends> twitterFriend = new ArrayList<Friends>();
		ArrayList<Friends> quoraFriend = new ArrayList<Friends>();
		ArrayList<Friends> goodReadsFriend = new ArrayList<Friends>();
		return ok(searchFriend.render("", u_name,
				twitterFriend,quoraFriend,goodReadsFriend));
	}
	
	
	public static Result searchFriendResult() {
		Form<SearchInput> filledForm = SearchFriendInputForm.bindFromRequest();
		SearchInput choiceReview = filledForm.get();
		Logger.info("searchFriendResult = " + choiceReview.u_name +">>" + choiceReview.textToSearch);

		String u_name = choiceReview.u_name;
		String textToSearch = choiceReview.textToSearch;
		
		if (filledForm.hasErrors()) {
			return badRequest("Text must be filled!");
		} else {
			
			
			//twitter friend
			ArrayList<Friends> twitterFriend = new ArrayList<Friends>();
	
			String tu_name = ApplicationPersistence.userName(u_name, "twitter_name");
			Logger.info("tu_name>>>>>>>>>>>>>>>>>"+tu_name);
			
			if(StringUtils.isNotBlank(tu_name)) {
				twitterFriend = ApplicationPersistence.friendSearch(tu_name,textToSearch,"twitter");
			}
			else
			{
				twitterFriend = new ArrayList<Friends>(0);
			}
			
			//quora friend
			ArrayList<Friends> quoraFriend = new ArrayList<Friends>();
			String qu_name = ApplicationPersistence.userName(u_name, "quora_name");
			
			Logger.info("qu_name>>>>>>>>>>>>>>>>>"+qu_name);
			
			if(StringUtils.isNotBlank(qu_name)) {
				quoraFriend = ApplicationPersistence.friendSearch(qu_name,textToSearch,"quora");
			}
			else{
				quoraFriend = new ArrayList<Friends>(0);
			}
			
		//goodreads friend	
			ArrayList<Friends> goodReadsFriend = new ArrayList<Friends>();

			String gu_name = ApplicationPersistence.userName(u_name, "goodreads_name");
			
			Logger.info("gu_name>>>>>>>>>>>>>>>>>"+gu_name);
			
			if(StringUtils.isNotBlank(gu_name)) {
				goodReadsFriend = ApplicationPersistence.friendSearch(gu_name,textToSearch,"goodreads");
			}
			else{
				goodReadsFriend = new ArrayList<Friends>(0);
			}
			
			return ok(searchFriend.render(textToSearch, u_name,
					twitterFriend,quoraFriend,goodReadsFriend));
			
				}

		//return TODO;
	}
	



	public static Result doReview(){
		Form<Choice> filledForm = choiceForm.bindFromRequest();
		Choice choiceReview = filledForm.get();
		Logger.info("User details submitted = " + choiceReview.choice);

		if (filledForm.hasErrors()) {
			// User did not fill everything properly
			//return badRequest(views.html.choice.render(filledForm));
			return badRequest("User details must be filled!");
		} else {
			int ch = choiceReview.choice;

			if(ch==1)
				return redirect(routes.Application.twitter(choiceReview.u_name));
			if(ch==2)
				return redirect(routes.Application.quora(choiceReview.u_name));
			if(ch==3)
				return redirect(routes.Application.goodreads(choiceReview.u_name));
			if(ch==4)
				return redirect(routes.Application.userBio(choiceReview.u_name));
			if(ch==5)
				return redirect(routes.Application.searchText(choiceReview.u_name));
			else
				return redirect(routes.Application.searchFriend(choiceReview.u_name));

		}
	}

}

