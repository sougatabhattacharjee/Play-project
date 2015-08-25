package models;
import play.data.validation.Constraints;
/**
 * @author Sougata
 * Aug 23, 2015 
 */
public class Choice {

	@Constraints.Required	
	public int choice;

	@Constraints.Required
	public String u_name;

	public Choice() {
		super();
	}

	public Choice(int choice) {
		super();
		this.choice = choice;
	}



}
