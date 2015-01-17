package controllers;

import play.*;
import play.data.*;
import play.mvc.*;
import model.*;

import java.util.ArrayList;

public class Application extends Controller {
    private static ArrayList<String> lifts = new ArrayList<String>();
    private static ArrayList<Person> persons = new ArrayList<Person>();
    
	static Form<SignIn> signInForm = Form.form(SignIn.class);

    public static Result index() {
        return ok(views.html.index.render("Welcome to Freezer!"));
    }

    public static Result signIn() {		
		return ok(views.html.signIn.render(Server.getMemList(), signInForm,""));
	}
	
	public static Result signUp(){
		return ok(views.html.signUp.render(Server.getMemList(), signInForm,""));
	}
	
	public static Result createProfile(){
		Form<SignIn> filled = signInForm.bindFromRequest();
		if((filled.hasErrors())||(Server.findPerson(filled.get().getName().toLowerCase())!=null)){
			return badRequest(views.html.signIn.render(Server.getMemList(), signInForm,"Info:something went wrong at the last login"));
		}else {
			Gen gender, preference;
			
			if(filled.get().getGender().equalsIgnoreCase(Gen.female.toString())){
				gender = Gen.female;
			} else if(filled.get().getGender().equalsIgnoreCase(Gen.male.toString())){
				gender = Gen.male;
			} else {
				return badRequest(views.html.signUp.render(Server.getMemList(), signInForm,"Info: the genders should be 'male' or 'female'"));
			}
			if(filled.get().getPreference().equalsIgnoreCase(Gen.female.toString())){
				preference = Gen.female;
			} else if(filled.get().getPreference().equalsIgnoreCase(Gen.male.toString())){
				preference = Gen.male;
			} else {
				return badRequest(views.html.signUp.render(Server.getMemList(), signInForm,"Info: the genders should be 'male' or 'female'"));
			}
			Person p = new Person(filled.get().getName().toLowerCase(),Integer.parseInt(filled.get().getAge()),gender,preference);
			try{
				Server.getMemList().add(p);
			} catch(Exception e){
				return badRequest(views.html.signUp.render(Server.getMemList(), signInForm,"Info: person not added to the server"));
			}
			
			return ok(views.html.profile.render(p));	
		}
	}
	
	public static Result toProfile(){
		Form<SignIn> filled = signInForm.bindFromRequest();
		if((filled.hasErrors())||(Server.findPerson(filled.get().getName().toLowerCase())==null)){
			return badRequest(views.html.signIn.render(Server.getMemList(), signInForm,"Info:something went wrong at the last login"));
		}else {
			return ok(views.html.profile.render(Server.findPerson(filled.get().getName().toLowerCase())));	
		}
	}
}
