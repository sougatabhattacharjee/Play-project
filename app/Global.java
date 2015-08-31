/**
 * 
 */
import play.*;
import play.api.mvc.RequestHeader;
import play.api.mvc.SimpleResult;
import play.mvc.*;
import play.mvc.Http.*;
import play.libs.F.*;

import play.Application;
import play.GlobalSettings;
import play.Play;
import play.libs.Akka;
import play.libs.F;
import play.libs.F.Callback;
import play.libs.F.Promise;

import static play.mvc.Results.*;

/**
 * @author Sougata
 * Aug 29, 2015 
 */
public class Global extends GlobalSettings {

	 public void onStart(Application app) {
	        Logger.info("Application has started");
	    }

	    public void onStop(Application app) {
	        Logger.info("Application shutdown...");
	    }
	    
	    @Override
	    public Result onError(Throwable t) {
//	      return internalServerError(
//	        views.html.error(t)
//	      );
	    	Result pageNotFound = notFound("<h1>Page not found</h1>").as("text/html");
	    	
			return pageNotFound;
	    }  
	   
	}
