package controllers;

import java.util.ArrayList;

import models.Choice;
import models.Goodreads;
import models.Quora;
import models.Twitter;
import models.Users;
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

		u_name = ApplicationPersistence.userName(u_name, "quora_name");

		if(StringUtils.isNotBlank(u_name)) {
			questions_answered = ApplicationPersistence.quora_questions_answered(u_name);
			questions_asked = ApplicationPersistence.quora_questions_asked(u_name);
		}
		else{
			questions_answered = new ArrayList<Quora>(0);
			questions_asked = new ArrayList<Quora>(0);
		}

		return ok(quora.render(questions_answered, questions_asked));
	}


	public static Result twitter(String u_name) {
		Logger.info("Inside twitter " + u_name);

		ArrayList<Twitter> tweeetMentioned = new ArrayList<Twitter>();
		ArrayList<Twitter> tweeetFavorites = new ArrayList<Twitter>();
		ArrayList<Twitter> tweets = new ArrayList<Twitter>();


		u_name = ApplicationPersistence.userName(u_name, "twitter_name");

		if(StringUtils.isNotBlank(u_name)) {
			tweeetMentioned = ApplicationPersistence.tweeetMentioned(u_name);
			tweeetFavorites = ApplicationPersistence.tweeetFavorites(u_name);
			tweets = ApplicationPersistence.tweets(u_name);
		}
		else
		{
			tweeetMentioned = new ArrayList<Twitter>(0);
			tweeetFavorites = new ArrayList<Twitter>(0);
			tweets = new ArrayList<Twitter>(0);
		}

		return ok(twitter.render(tweeetMentioned, tweeetFavorites, tweets));
	}


	public static Result goodreads(String u_name) {
		Logger.info("Inside goodreads " + u_name);

		ArrayList<Goodreads> goodReads = new ArrayList<Goodreads>();

		u_name = ApplicationPersistence.userName(u_name, "goodreads_name");

		if(StringUtils.isNotBlank(u_name)) {
			goodReads = ApplicationPersistence.goodreads(u_name);
		}
		else
			goodReads = new ArrayList<Goodreads>(0);

		return ok(goodreads.render(goodReads));
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
			else
				return redirect(routes.Application.goodreads(choiceReview.u_name));

		}
	}

}

