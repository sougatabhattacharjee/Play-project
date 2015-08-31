/**
 * 
 */
package controllers;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.node.ObjectNode;

import Persistence.ApplicationPersistence;
import models.Choice;
import models.UserInformation;
import models.Users;
import play.Logger;
import play.data.Form;
import play.mvc.BodyParser.Json;
import play.mvc.Controller;
import play.mvc.Result;
import models.*;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
/**
 * @author Sougata
 * Aug 25, 2015 
 */
public class CrawlingData extends Controller{
	static Form<UserInformation> UserInformationForm = form(UserInformation.class);
	
	public static Result index() {
		
		ArrayList<Users> users = new ArrayList<Users>();
		
		users = ApplicationPersistence.users();
		
		 return ok(
				    views.html.crawl.render(users, UserInformationForm)
				  );
	}
	
	
	public static Result userNameSearch(String username) {
		boolean flag =  UserInformation.userNameSearch(username);
		return TODO;
    }
	
	
	public static Result userInfoReview(){
		Form<UserInformation> filledForm = UserInformationForm.bindFromRequest();
		UserInformation choiceReview = filledForm.get();
		
		Logger.info("User details submitted = " + choiceReview.me);
		
		int choice = choiceReview.me;
		String username = choiceReview.username;
		String quora_name = choiceReview.quora_name;
		String quora_email = choiceReview.quora_email;
		String quora_password = choiceReview.quora_password;
		String twitter_name = choiceReview.twitter_name;
		String goodreads_name = choiceReview.goodreads_name;
		
		System.out.println(choice+quora_name+quora_email+quora_password+twitter_name);
		
		

		if (filledForm.hasErrors()) {
			// User did not fill everything properly
			//return badRequest(views.html.choice.render(filledForm));
			return badRequest("User details must be filled!");
		} else {

			Config myConfig = ConfigFactory.load("myfile.properties").getConfig("path");
			System.out.println(myConfig.toString());
			
			if(choice==1)	//me
			{
				String[] callAndArgs = {"python","/home/angeeka/MasterThesis/Thesis/main_prog.py",String.valueOf(choice),quora_name,quora_email,quora_password,"","",username};
				JavaRunPython.run(callAndArgs);
			}
			if(choice==2)	//other
			{
				String[] callAndArgs = {"python","/home/angeeka/MasterThesis/Thesis/main_prog.py",String.valueOf(choice),quora_name,quora_email,quora_password,twitter_name,goodreads_name,username};
				JavaRunPython.run(callAndArgs);
			}
			
		return ok("Input Submitted!!");
		}
	}
	
	
	
}
